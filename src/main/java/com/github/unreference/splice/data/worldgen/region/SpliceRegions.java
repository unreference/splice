package com.github.unreference.splice.data.worldgen.region;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

public final class SpliceRegions {
  private static final List<SpliceRegion> REGIONS = new ArrayList<>();

  public static void register(SpliceRegion region) {
    REGIONS.add(region);
  }

  public static void contribute(Consumer<Pair<Climate.ParameterPoint, Holder<Biome>>> sink) {
    for (SpliceRegion region : REGIONS) {
      region.addBiomes(sink);
    }
  }
}
