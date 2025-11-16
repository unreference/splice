package com.github.unreference.splice.data.worldgen.features;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.data.worldgen.placement.SpliceTreePlacements;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.levelgen.feature.SpliceFeature;
import com.github.unreference.splice.world.level.levelgen.feature.configurations.SpliceSimpleBlockConfiguration;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
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
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
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
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_GARDEN_FLOWERS =
      create("pale_garden_flowers");
  public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_GARDEN_FLOWER =
      create("pale_garden_flower");

  private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
    return SpliceRegistries.createKey(Registries.CONFIGURED_FEATURE, name);
  }

  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    final HolderGetter<ConfiguredFeature<?, ?>> configured =
        context.lookup(Registries.CONFIGURED_FEATURE);

    paleGardenVegetation(context);
    paleMossVegetation(context);
    paleMossPatch(context, configured);
    paleMossPatchBoneMeal(context, configured);
    paleGardenFlowers(context);
    paleGardenFlower(context);
  }

  private static void paleGardenFlower(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    SpliceFeatureUtils.register(
        context,
        PALE_GARDEN_FLOWER,
        Feature.FLOWER,
        new RandomPatchConfiguration(
            1,
            0,
            0,
            PlacementUtils.onlyWhenEmpty(
                SpliceFeature.SIMPLE_BLOCK.get(),
                new SpliceSimpleBlockConfiguration(
                    BlockStateProvider.simple(SpliceBlocks.CLOSED_EYEBLOSSOM.get()), true))));
  }

  private static void paleGardenFlowers(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    SpliceFeatureUtils.register(
        context,
        PALE_GARDEN_FLOWERS,
        Feature.RANDOM_PATCH,
        FeatureUtils.simplePatchConfiguration(
            SpliceFeature.SIMPLE_BLOCK.get(),
            new SpliceSimpleBlockConfiguration(
                BlockStateProvider.simple(SpliceBlocks.CLOSED_EYEBLOSSOM.get()), true)));
  }

  private static void paleMossPatchBoneMeal(
      BootstrapContext<ConfiguredFeature<?, ?>> context,
      HolderGetter<ConfiguredFeature<?, ?>> configured) {
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

  private static void paleMossPatch(
      BootstrapContext<ConfiguredFeature<?, ?>> context,
      HolderGetter<ConfiguredFeature<?, ?>> configured) {
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
  }

  private static void paleMossVegetation(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    SpliceFeatureUtils.register(
        context,
        PALE_MOSS_VEGETATION,
        SpliceFeature.SIMPLE_BLOCK.get(),
        new SpliceSimpleBlockConfiguration(
            new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                    .add(SpliceBlocks.PALE_MOSS_CARPET.get().defaultBlockState(), 25)
                    .add(Blocks.SHORT_GRASS.defaultBlockState(), 25)
                    .add(Blocks.TALL_GRASS.defaultBlockState(), 10))));
  }

  private static void paleGardenVegetation(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    final HolderGetter<PlacedFeature> holderGetter = context.lookup(Registries.PLACED_FEATURE);
    final Holder<PlacedFeature> paleOakHolder =
        holderGetter.getOrThrow(SpliceTreePlacements.PALE_OAK);
    final Holder<PlacedFeature> paleOakCreakingHeartHolder =
        holderGetter.getOrThrow(SpliceTreePlacements.PALE_OAK_CREAKING_HEART);

    SpliceFeatureUtils.register(
        context,
        PALE_GARDEN_VEGETATION,
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(
            List.of(
                new WeightedPlacedFeature(paleOakCreakingHeartHolder, 0.1f),
                new WeightedPlacedFeature(paleOakHolder, 0.9f)),
            paleOakHolder));
  }
}
