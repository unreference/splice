package com.github.unreference.splice.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public final class SplicePlacementUtils {
  public static void bootstrap(BootstrapContext<PlacedFeature> context) {
    SpliceTreePlacements.bootstrap(context);
    SpliceVegetationPlacements.bootstrap(context);
  }

  public static void register(
      BootstrapContext<PlacedFeature> context,
      ResourceKey<PlacedFeature> key,
      Holder<ConfiguredFeature<?, ?>> holder,
      PlacementModifier... modifiers) {
    register(context, key, holder, List.of(modifiers));
  }

  public static void register(
      BootstrapContext<PlacedFeature> context,
      ResourceKey<PlacedFeature> key,
      Holder<ConfiguredFeature<?, ?>> holder,
      List<PlacementModifier> modifiers) {
    context.register(key, new PlacedFeature(holder, List.copyOf(modifiers)));
  }
}
