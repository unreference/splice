package com.github.unreference.splice.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpliceFlowerPotBlock extends FlowerPotBlock {
  public SpliceFlowerPotBlock(
      @Nullable Supplier<FlowerPotBlock> emptyPot,
      Supplier<? extends Block> supplier,
      Properties properties) {
    super(emptyPot, supplier, properties);
  }

  private static BlockState getOpposite(BlockState state) {
    if (state.is(SpliceBlocks.POTTED_OPEN_EYEBLOSSOM)) {
      return SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM.get().defaultBlockState();
    } else {
      return state.is(SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM)
          ? SpliceBlocks.POTTED_OPEN_EYEBLOSSOM.get().defaultBlockState()
          : state;
    }
  }

  @Override
  protected boolean isRandomlyTicking(BlockState state) {
    return state.is(SpliceBlocks.POTTED_OPEN_EYEBLOSSOM)
        || state.is(SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM);
  }

  @Override
  protected void randomTick(
      @NotNull BlockState state,
      @NotNull ServerLevel level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (this.isRandomlyTicking(state) && level.dimensionType().natural()) {
      final boolean isOpenEyeblossom = this.getPotted() == SpliceBlocks.OPEN_EYEBLOSSOM.get();
      final boolean isNight = SpliceEyeblossomBlock.isMoonVisible(level);

      if (isOpenEyeblossom != isNight) {
        level.setBlock(pos, getOpposite(state), Block.UPDATE_ALL);
        final SpliceEyeblossomBlock.Type type =
            SpliceEyeblossomBlock.Type.fromBoolean(isOpenEyeblossom).transform();
        type.spawnTransformParticle(level, pos, random);
        level.playSound(null, pos, type.getLongSwitchSound().get(), SoundSource.BLOCKS);
      }
    }

    super.randomTick(state, level, pos, random);
  }
}
