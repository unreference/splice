package com.github.unreference.splice.data.worldgen.region;

import com.github.unreference.splice.data.worldgen.biome.SpliceBiomeData;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

public final class SpliceOverworldRegion implements SpliceRegion {
  @Override
  public void addBiomes(Consumer<Pair<Climate.ParameterPoint, Holder<Biome>>> sink) {
    final Holder<Biome> biome = SpliceBiomeData.PALE_GARDEN;
    if (biome == null) {
      return;
    }

    final Climate.Parameter temperature = Climate.Parameter.span(0.5F, 1.0F); // warm to hot
    final Climate.Parameter humidity = Climate.Parameter.span(0.6F, 1.0F); // fairly humid
    final Climate.Parameter continentalness =
        Climate.Parameter.span(0.2F, 0.8F); // not coast, not deep ocean
    final Climate.Parameter erosion = Climate.Parameter.span(0.0F, 0.9F); // most erosion values
    final Climate.Parameter depth = Climate.Parameter.point(0.0F); // surface
    final Climate.Parameter weirdness = Climate.Parameter.span(-0.1F, 0.3F); // fairly “normal”
    final long offset = 0L;

    final Climate.ParameterPoint point =
        new Climate.ParameterPoint(
            temperature, humidity, continentalness, erosion, depth, weirdness, offset);

    sink.accept(Pair.of(point, biome));
  }
}
