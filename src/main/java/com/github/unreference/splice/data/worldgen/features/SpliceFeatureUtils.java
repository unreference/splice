package com.github.unreference.splice.data.worldgen.features;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public final class SpliceFeatureUtils {
  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    SpliceTreeFeatures.bootstrap(context);
    SpliceVegetationFeatures.bootstrap(context);
  }

  public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
      BootstrapContext<ConfiguredFeature<?, ?>> context,
      ResourceKey<ConfiguredFeature<?, ?>> key,
      F feature,
      FC config) {
    context.register(key, new ConfiguredFeature<>(feature, config));
  }
}
