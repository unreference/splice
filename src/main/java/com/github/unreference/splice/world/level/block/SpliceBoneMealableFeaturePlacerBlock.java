package com.github.unreference.splice.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public final class SpliceBoneMealableFeaturePlacerBlock extends Block implements BonemealableBlock {
  public static final MapCodec<SpliceBoneMealableFeaturePlacerBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      ResourceKey.codec(Registries.CONFIGURED_FEATURE)
                          .fieldOf("feature")
                          .forGetter(block -> block.feature),
                      propertiesCodec())
                  .apply(instance, SpliceBoneMealableFeaturePlacerBlock::new));

  private final ResourceKey<ConfiguredFeature<?, ?>> feature;

  public SpliceBoneMealableFeaturePlacerBlock(
      ResourceKey<ConfiguredFeature<?, ?>> feature, Properties properties) {
    super(properties);
    this.feature = feature;
  }

  @Override
  public boolean isValidBonemealTarget(
      @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
    return level.getBlockState(pos.above()).isAir();
  }

  @Override
  public boolean isBonemealSuccess(
      @NotNull Level level,
      @NotNull RandomSource random,
      @NotNull BlockPos pos,
      @NotNull BlockState state) {
    return true;
  }

  @Override
  public void performBonemeal(
      @NotNull ServerLevel level,
      @NotNull RandomSource random,
      @NotNull BlockPos pos,
      @NotNull BlockState state) {
    level
        .registryAccess()
        .lookup(Registries.CONFIGURED_FEATURE)
        .flatMap(feature -> feature.get(this.feature))
        .ifPresent(
            feature ->
                feature
                    .value()
                    .place(level, level.getChunkSource().getGenerator(), random, pos.above()));
  }

  @Override
  public @NotNull Type getType() {
    return Type.NEIGHBOR_SPREADER;
  }
}
