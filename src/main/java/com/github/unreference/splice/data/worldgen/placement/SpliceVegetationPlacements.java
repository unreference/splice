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

    paleGardenVegetation(context, configured);
    paleGardenFlowers(context, configured);
    paleMossPatch(context, configured);
    paleGardenFlower(context, configured);
  }

  private static void paleGardenFlower(
      BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured) {
    final Holder<ConfiguredFeature<?, ?>> holder =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_FLOWER);

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_FLOWER,
        holder,
        RarityFilter.onAverageOnceEvery(32),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP,
        BiomeFilter.biome());
  }

  private static void paleMossPatch(
      BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured) {
    final Holder<ConfiguredFeature<?, ?>> holder =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_MOSS_PATCH);

    SplicePlacementUtils.register(
        context,
        PALE_MOSS_PATCH,
        holder,
        CountPlacement.of(1),
        InSquarePlacement.spread(),
        SplicePlacementUtils.HEIGHTMAP_NO_LEAVES,
        BiomeFilter.biome());
  }

  private static void paleGardenFlowers(
      BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured) {
    final Holder<ConfiguredFeature<?, ?>> holder =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_FLOWERS);

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_FLOWERS,
        holder,
        RarityFilter.onAverageOnceEvery(8),
        InSquarePlacement.spread(),
        SplicePlacementUtils.HEIGHTMAP_NO_LEAVES,
        BiomeFilter.biome());
  }

  private static void paleGardenVegetation(
      BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configured) {
    final Holder<ConfiguredFeature<?, ?>> holder =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_VEGETATION);

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_VEGETATION,
        holder,
        CountPlacement.of(16),
        InSquarePlacement.spread(),
        SurfaceWaterDepthFilter.forMaxDepth(0),
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome());
  }
}
