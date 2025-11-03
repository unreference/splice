package com.github.unreference.splice.data.worldgen.placement;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.data.worldgen.features.SpliceTreeFeatures;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class SpliceTreePlacements {
  public static final ResourceKey<PlacedFeature> PALE_OAK_CHECKED = create("pale_oak_checked");

  private static ResourceKey<PlacedFeature> create(String name) {
    return SpliceRegistries.createKey(Registries.PLACED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<PlacedFeature> context) {
    final HolderGetter<ConfiguredFeature<?, ?>> features =
        context.lookup(Registries.CONFIGURED_FEATURE);
    final Holder<ConfiguredFeature<?, ?>> paleOak =
        features.getOrThrow(SpliceTreeFeatures.PALE_OAK);

    SplicePlacementUtils.register(
        context,
        PALE_OAK_CHECKED,
        paleOak,
        PlacementUtils.filteredByBlockSurvival(SpliceBlocks.PALE_OAK_SAPLING.get()));
  }
}
