package com.github.unreference.splice.mixin.net.minecraft.world.level.biome;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomeData;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class SpliceMixinMultiNoiseBiomeSource {
  @Final @Shadow
  private Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>>
      parameters;

  @Unique private Climate.ParameterList<Holder<Biome>> splice$extendedOverworldParameterList;

  @Unique
  private static Climate.ParameterList<Holder<Biome>> splice$buildExtendedParameterList(
      Climate.ParameterList<Holder<Biome>> baseList) {
    final List<Pair<Climate.ParameterPoint, Holder<Biome>>> values = new ArrayList<>();

    try {
      for (Pair<Climate.ParameterPoint, Holder<Biome>> pair : baseList.values()) {
        final Climate.ParameterPoint point = pair.getFirst();
        final Holder<Biome> baseBiome = pair.getSecond();

        if (splice$IsPaleGardenCandidate(point, baseBiome)) {
          final Climate.ParameterPoint newPoint = splice$normalizeContinentalness(point);
          values.add(Pair.of(newPoint, SpliceBiomeData.PALE_GARDEN));
        } else {
          values.add(pair);
        }
      }
    } catch (Exception e) {
      SpliceMain.LOGGER.error("Failed to extend overworld biomes. Using fallback.", e);
      return baseList;
    }

    return new Climate.ParameterList<>(values);
  }

  @Unique
  private static boolean splice$IsPaleGardenCandidate(
      Climate.ParameterPoint point, Holder<Biome> biome) {
    if (!biome.is(Biomes.DARK_FOREST)) {
      return false;
    }

    final float temperature = Climate.unquantizeCoord(splice$getMid(point.temperature()));
    if (temperature < -0.15f || temperature > 0.2f) {
      return false;
    }

    final float humidity = Climate.unquantizeCoord(splice$getMid(point.humidity()));
    if (humidity < 0.3f) {
      return false;
    }

    final float erosion = Climate.unquantizeCoord(splice$getMid(point.erosion()));
    if (erosion < -0.78f || erosion > 0.05f) {
      return false;
    }

    final float weirdness = Climate.unquantizeCoord(splice$getMid(point.weirdness()));

    final float absWeirdness = Math.abs(weirdness);
    if (absWeirdness < 0.267f) {
      return false;
    }

    final float depth = Climate.unquantizeCoord(splice$getMid(point.depth()));
    if (depth < 0.0f || depth > 1.0f) {
      return false;
    }

    // ---- Classify erosion into Mojang's three plateau bands ----
    final boolean isErosionRough = erosion < -0.375f; // [-0.780, -0.375]
    final boolean isErosionMidBand = erosion >= -0.375f && erosion < -0.222f; // [-0.375, -0.222]
    final boolean isErosionSmooth = erosion >= -0.222f; // [-0.222,  0.050]

    // ---- Classify weirdness slices ----
    // We already know |weirdness| >= 0.267 here.
    final boolean isOuterSlice = absWeirdness >= 0.933f; // [-1.000,-0.933] & [0.933,1.000]
    final boolean isInnerSpikeSlice = absWeirdness <= 0.4f; // [-0.400,-0.267] & [0.267,0.400]

    // We use min to tell 0.03 vs. 0.3 bands apart.
    final float continentalness = Climate.unquantizeCoord(point.continentalness().min());

    // ---- Outer + inner spike slices ----
    // Mojang uses only rough + mid plateau, and only the inland half (>= 0.30).

    if (isOuterSlice || isInnerSpikeSlice) {
      if (!isErosionRough && !isErosionMidBand) {
        // No smooth plateau in these slices
        return false;
      }

      // Only inland continentalness
      return continentalness >= 0.3f;
    } else {

      // ---- Middle ring slices ----
      // Mojang pattern:
      // - Erosion mid band:  continentalness [0.030, 1.000]
      // - Erosion smooth:    continentalness [0.300, 1.000]
      // - No rough plateau here.

      if (isErosionMidBand) {
        // Near-inland strip: allow 0.03..1
        return continentalness >= 0.03f;
      }

      if (isErosionSmooth) {
        // Smooth plateau: only inland half
        return continentalness >= 0.3f;
      }

      // Don't use the rough plateau band in these slices
      return false;
    }
  }

  @Unique
  private static Climate.ParameterPoint splice$normalizeContinentalness(
      Climate.ParameterPoint point) {

    final Climate.Parameter continentalness = point.continentalness();

    final float min = Climate.unquantizeCoord(continentalness.min());

    final float max = Climate.unquantizeCoord(continentalness.max());

    if (min >= 0.03f && max >= 1.0f - 0.0f) {

      return point;
    }

    final float newMin = Math.max(min, 0.03f);

    final float newMax = 1.0f;

    final Climate.Parameter newContinentalness = Climate.Parameter.span(newMin, newMax);

    return new Climate.ParameterPoint(
        point.temperature(),
        point.humidity(),
        newContinentalness,
        point.erosion(),
        point.depth(),
        point.weirdness(),
        point.offset());
  }

  @Unique
  private static long splice$getMid(Climate.Parameter parameter) {
    final long min = parameter.min();
    final long max = parameter.max();

    if (min == max) {
      return min;
    }

    return (min + max) / 2;
  }

  @Inject(method = "parameters", at = @At("RETURN"), cancellable = true)
  private void splice$parameters(CallbackInfoReturnable<Climate.ParameterList<Holder<Biome>>> cir) {
    if (!this.splice$isOverworldPreset()) {
      return;
    }

    if (this.splice$extendedOverworldParameterList == null) {
      final Climate.ParameterList<Holder<Biome>> base = cir.getReturnValue();
      if (base == null) {
        return;
      }

      this.splice$extendedOverworldParameterList = splice$buildExtendedParameterList(base);
    }

    cir.setReturnValue(this.splice$extendedOverworldParameterList);
  }

  @Unique
  private boolean splice$isOverworldPreset() {
    return this.parameters
        .right()
        .map(preset -> preset.is(MultiNoiseBiomeSourceParameterLists.OVERWORLD))
        .orElse(false);
  }
}
