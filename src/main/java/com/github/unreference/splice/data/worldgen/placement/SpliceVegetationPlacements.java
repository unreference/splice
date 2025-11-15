package com.github.unreference.splice.data.worldgen.placement;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.data.worldgen.features.SpliceVegetationFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public final class SpliceVegetationPlacements {
  public static final ResourceKey<PlacedFeature> PALE_GARDEN_VEGETATION =
      create("pale_garden_vegetation");
  public static final ResourceKey<PlacedFeature> PALE_MOSS_PATCH = create("pale_moss_patch");
  public static final ResourceKey<PlacedFeature> PALE_GARDEN_FLOWERS =
      create("pale_garden_flowers");
  public static final ResourceKey<PlacedFeature> PALE_GARDEN_FLOWER = create("pale_garden_flower");

  private static ResourceKey<PlacedFeature> create(String name) {
    return SpliceRegistries.createKey(Registries.PLACED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<PlacedFeature> context) {
    final HolderGetter<ConfiguredFeature<?, ?>> configured =
        context.lookup(Registries.CONFIGURED_FEATURE);
    final Holder<ConfiguredFeature<?, ?>> paleGardenVegetation =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_VEGETATION);
    final Holder<ConfiguredFeature<?, ?>> paleMossPatch =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_MOSS_PATCH);
    final Holder<ConfiguredFeature<?, ?>> paleGardenFlowers =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_FLOWERS);
    final Holder<ConfiguredFeature<?, ?>> paleGardenFlower =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_FLOWER);

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_VEGETATION,
        paleGardenVegetation,
        CountPlacement.of(16),
        InSquarePlacement.spread(),
        SurfaceWaterDepthFilter.forMaxDepth(0),
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome());

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_FLOWERS,
        paleGardenFlowers,
        RarityFilter.onAverageOnceEvery(8),
        InSquarePlacement.spread(),
        SplicePlacementUtils.HEIGHTMAP_NO_LEAVES,
        BiomeFilter.biome());

    SplicePlacementUtils.register(
        context,
        PALE_MOSS_PATCH,
        paleMossPatch,
        CountPlacement.of(1),
        InSquarePlacement.spread(),
        SplicePlacementUtils.HEIGHTMAP_NO_LEAVES,
        BiomeFilter.biome());

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_FLOWER,
        paleGardenFlower,
        RarityFilter.onAverageOnceEvery(32),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome());
  }
}
