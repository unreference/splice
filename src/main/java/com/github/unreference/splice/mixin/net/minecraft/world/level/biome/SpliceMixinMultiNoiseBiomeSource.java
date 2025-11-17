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
    final List<Pair<Climate.ParameterPoint, Holder<Biome>>> values =
        new ArrayList<>(baseList.values());

    try {
      for (Pair<Climate.ParameterPoint, Holder<Biome>> pair : baseList.values()) {
        final Climate.ParameterPoint point = pair.getFirst();
        final Holder<Biome> baseBiome = pair.getSecond();

        if (!splice$IsPaleGardenCandidate(point, baseBiome)) {
          continue;
        }

        values.add(Pair.of(point, SpliceBiomeData.PALE_GARDEN));
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
    final float humidity = Climate.unquantizeCoord(splice$getMid(point.humidity()));
    final float continentalness = Climate.unquantizeCoord(splice$getMid(point.continentalness()));
    final float erosion = Climate.unquantizeCoord(splice$getMid(point.erosion()));
    final float depth = Climate.unquantizeCoord(splice$getMid(point.depth()));
    final float weirdness = Climate.unquantizeCoord(splice$getMid(point.weirdness()));

    if (temperature < -0.15f || temperature > 0.2f) {
      return false;
    }

    if (humidity < 0.3f) {
      return false;
    }

    if (continentalness < 0.03f) {
      return false;
    }

    if (erosion < -0.78f || erosion > 0.05f) {
      return false;
    }

    if (Math.abs(weirdness) < 0.267f) {
      return false;
    }

    return !(depth < 0.0f) && !(depth > 1.0f);
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
