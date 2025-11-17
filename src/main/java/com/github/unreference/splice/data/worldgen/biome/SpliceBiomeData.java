package com.github.unreference.splice.data.worldgen.biome;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class SpliceBiomeData {
  public static Holder<Biome> PALE_GARDEN;

  public static void bootstrap(BootstrapContext<Biome> context) {
    final HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
    final HolderGetter<ConfiguredWorldCarver<?>> carver =
        context.lookup(Registries.CONFIGURED_CARVER);

    context.register(SpliceBiomes.PALE_GARDEN, SpliceOverworldBiomes.paleGarden(placed, carver));
  }
}
