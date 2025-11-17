package com.github.unreference.splice.data.worldgen.region;

import com.github.unreference.splice.data.worldgen.biome.SpliceBiomeData;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

public final class SpliceOverworldRegion implements SpliceRegion {
  private static void addPaleGarden(Consumer<Pair<Climate.ParameterPoint, Holder<Biome>>> sink) {
    final Holder<Biome> biome = SpliceBiomeData.PALE_GARDEN;
    if (biome == null) {
      return;
    }

    final Climate.Parameter temperature = Climate.Parameter.span(-0.15f, 0.2f);
    final Climate.Parameter humidity = Climate.Parameter.span(0.3f, 1.0f);
    final Climate.Parameter continentalness = Climate.Parameter.span(0.15f, 0.7f);
    final Climate.Parameter erosion = Climate.Parameter.span(-0.3f, -0.05f);
    final Climate.Parameter depth = Climate.Parameter.point(0.0f);
    final Climate.Parameter weirdness = Climate.Parameter.span(0.4f, 0.85f);
    final long offset = 0L;

    final Climate.ParameterPoint point =
        new Climate.ParameterPoint(
            temperature, humidity, continentalness, erosion, depth, weirdness, offset);

    sink.accept(Pair.of(point, biome));
  }

  @Override
  public void addBiomes(Consumer<Pair<Climate.ParameterPoint, Holder<Biome>>> sink) {
    addPaleGarden(sink);
  }
}
