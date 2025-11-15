package com.github.unreference.splice.world.level.levelgen.feature;

import com.github.unreference.splice.world.level.block.SpliceMossyCarpetBlock;
import com.github.unreference.splice.world.level.levelgen.feature.configurations.SpliceSimpleBlockConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public final class SpliceSimpleBlockFeature extends Feature<SpliceSimpleBlockConfiguration> {
  public SpliceSimpleBlockFeature(Codec<SpliceSimpleBlockConfiguration> codec) {
    super(codec);
  }

  @Override
  public boolean place(FeaturePlaceContext<SpliceSimpleBlockConfiguration> context) {
    final SpliceSimpleBlockConfiguration config = context.config();
    final WorldGenLevel level = context.level();
    final BlockPos pos = context.origin();
    final BlockState state = config.toPlace().getState(level.getRandom(), pos);

    if (state.canSurvive(level, pos)) {
      if (state.getBlock() instanceof DoublePlantBlock) {
        if (!level.isEmptyBlock(pos.above())) {
          return false;
        }

        DoublePlantBlock.placeAt(level, state, pos, Block.UPDATE_CLIENTS);
      } else if (state.getBlock() instanceof SpliceMossyCarpetBlock) {
        SpliceMossyCarpetBlock.placeAt(level, pos, level.getRandom(), Block.UPDATE_CLIENTS);
      } else {
        level.setBlock(pos, state, Block.UPDATE_CLIENTS);
      }

      if (config.hasScheduledTicks()) {
        level.scheduleTick(pos, level.getBlockState(pos).getBlock(), 1);
      }

      return true;
    }

    return false;
  }
}
