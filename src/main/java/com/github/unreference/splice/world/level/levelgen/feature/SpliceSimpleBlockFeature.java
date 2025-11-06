package com.github.unreference.splice.world.level.levelgen.feature;

import com.github.unreference.splice.world.level.block.SpliceMossyCarpetBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import org.jetbrains.annotations.NotNull;

public final class SpliceSimpleBlockFeature extends Feature<SimpleBlockConfiguration> {
  public SpliceSimpleBlockFeature(Codec<SimpleBlockConfiguration> codec) {
    super(codec);
  }

  @Override
  public boolean place(@NotNull FeaturePlaceContext<SimpleBlockConfiguration> context) {
    final SimpleBlockConfiguration config = context.config();
    final WorldGenLevel level = context.level();
    final BlockPos pos = context.origin();
    final BlockState state = config.toPlace().getState(level.getRandom(), pos);

    if (state.canSurvive(level, pos)) {
      if (state.getBlock() instanceof DoublePlantBlock) {
        if (!level.isEmptyBlock(pos.above())) {
          return false;
        }

        DoublePlantBlock.placeAt(level, state, pos, 2);
      } else if (state.getBlock() instanceof SpliceMossyCarpetBlock) {
        SpliceMossyCarpetBlock.placeAt(level, pos, level.getRandom(), 2);
      } else {
        level.setBlock(pos, state, 2);
      }

      level.scheduleTick(pos, level.getBlockState(pos).getBlock(), 1);
      return true;
    }

    return false;
  }
}
