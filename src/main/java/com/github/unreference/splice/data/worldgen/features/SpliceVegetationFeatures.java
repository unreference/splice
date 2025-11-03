package com.github.unreference.splice.data.worldgen.features;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.data.worldgen.placement.SpliceTreePlacements;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class SpliceVegetationFeatures {
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_GARDEN_VEGETATION =
      create("pale_garden_vegetation");

  private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
    return SpliceRegistries.createKey(Registries.CONFIGURED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    final HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
    final Holder<PlacedFeature> paleOakChecked =
        placed.getOrThrow(SpliceTreePlacements.PALE_OAK_CHECKED);

    SpliceFeatureUtils.register(
        context,
        PALE_GARDEN_VEGETATION,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(
            List.of(new WeightedPlacedFeature(paleOakChecked, 0.9f)), paleOakChecked));
  }
}
