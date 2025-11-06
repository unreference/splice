package com.github.unreference.splice.data.worldgen.features;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.levelgen.feature.treedecorators.SplicePaleMossDecorator;
import com.google.common.collect.ImmutableList;
import java.util.OptionalInt;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;

public final class SpliceTreeFeatures {
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK = create("pale_oak");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK_BONE_MEAL =
      create("pale_oak_bone_meal");

  private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
    return SpliceRegistries.createKey(Registries.CONFIGURED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    SpliceFeatureUtils.register(context, PALE_OAK, Feature.TREE, paleOak().build());
    SpliceFeatureUtils.register(
        context, PALE_OAK_BONE_MEAL, Feature.TREE, paleOakBoneMeal().build());
  }

  private static TreeConfiguration.TreeConfigurationBuilder paleOakBoneMeal() {
    return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(SpliceBlocks.PALE_OAK_LOG.get()),
            new DarkOakTrunkPlacer(6, 2, 1),
            BlockStateProvider.simple(SpliceBlocks.PALE_OAK_LEAVES.get()),
            new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
            new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))
        .ignoreVines();
  }

  private static TreeConfiguration.TreeConfigurationBuilder paleOak() {
    return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(SpliceBlocks.PALE_OAK_LOG.get()),
            new DarkOakTrunkPlacer(6, 2, 1),
            BlockStateProvider.simple(SpliceBlocks.PALE_OAK_LEAVES.get()),
            new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
            new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))
        .decorators(ImmutableList.of(new SplicePaleMossDecorator(0.15f, 0.4f, 0.8f)))
        .ignoreVines();
  }
}
