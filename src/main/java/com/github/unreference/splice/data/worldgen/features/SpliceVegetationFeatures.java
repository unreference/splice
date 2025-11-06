package com.github.unreference.splice.data.worldgen.features;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.data.worldgen.placement.SpliceTreePlacements;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.levelgen.feature.SpliceFeature;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class SpliceVegetationFeatures {
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_GARDEN_VEGETATION =
      create("pale_garden_vegetation");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_VEGETATION =
      create("pale_moss_vegetation");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH =
      create("pale_moss_patch");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH_BONE_MEAL =
      create("pale_moss_patch_bone_meal");

  private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
    return SpliceRegistries.createKey(Registries.CONFIGURED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    final HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
    final HolderGetter<ConfiguredFeature<?, ?>> configured =
        context.lookup(Registries.CONFIGURED_FEATURE);
    final Holder<PlacedFeature> paleOakChecked =
        placed.getOrThrow(SpliceTreePlacements.PALE_OAK_CHECKED);

    SpliceFeatureUtils.register(
        context,
        PALE_GARDEN_VEGETATION,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(
            List.of(new WeightedPlacedFeature(paleOakChecked, 0.9f)), paleOakChecked));

    SpliceFeatureUtils.register(
        context,
        PALE_MOSS_VEGETATION,
        SpliceFeature.SIMPLE_BLOCK.get(),
        new SimpleBlockConfiguration(
            new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                    .add(SpliceBlocks.PALE_MOSS_CARPET.get().defaultBlockState(), 25)
                    .add(Blocks.SHORT_GRASS.defaultBlockState(), 25)
                    .add(Blocks.TALL_GRASS.defaultBlockState(), 10))));

    SpliceFeatureUtils.register(
        context,
        PALE_MOSS_PATCH,
        Feature.VEGETATION_PATCH,
        new VegetationPatchConfiguration(
            BlockTags.MOSS_REPLACEABLE,
            BlockStateProvider.simple(SpliceBlocks.PALE_MOSS_BLOCK.get()),
            PlacementUtils.inlinePlaced(configured.getOrThrow(PALE_MOSS_VEGETATION)),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.0f,
            5,
            0.3f,
            UniformInt.of(2, 4),
            0.75f));

    SpliceFeatureUtils.register(
        context,
        PALE_MOSS_PATCH_BONE_MEAL,
        Feature.VEGETATION_PATCH,
        new VegetationPatchConfiguration(
            BlockTags.MOSS_REPLACEABLE,
            BlockStateProvider.simple(SpliceBlocks.PALE_MOSS_BLOCK.get()),
            PlacementUtils.inlinePlaced(configured.getOrThrow(PALE_MOSS_VEGETATION)),
            CaveSurface.FLOOR,
            ConstantInt.of(1),
            0.0f,
            5,
            0.6f,
            UniformInt.of(1, 2),
            0.75f));
  }
}
