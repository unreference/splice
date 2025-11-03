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

  private static ResourceKey<PlacedFeature> create(String name) {
    return SpliceRegistries.createKey(Registries.PLACED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<PlacedFeature> context) {
    final HolderGetter<ConfiguredFeature<?, ?>> configured =
        context.lookup(Registries.CONFIGURED_FEATURE);
    final Holder<ConfiguredFeature<?, ?>> paleGarden =
        configured.getOrThrow(SpliceVegetationFeatures.PALE_GARDEN_VEGETATION);

    SplicePlacementUtils.register(
        context,
        PALE_GARDEN_VEGETATION,
        paleGarden,
        CountPlacement.of(16),
        InSquarePlacement.spread(),
        SurfaceWaterDepthFilter.forMaxDepth(0),
        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
        BiomeFilter.biome());
  }
}
