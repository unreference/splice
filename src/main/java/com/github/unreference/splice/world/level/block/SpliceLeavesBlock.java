package com.github.unreference.splice.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class SpliceLeavesBlock extends LeavesBlock {
  private static final int TICK_DELAY = 1;
  protected final float leafParticleChance;

  protected SpliceLeavesBlock(float leafParticleChance, Properties properties) {
    super(properties);
    this.leafParticleChance = leafParticleChance;
    this.registerDefaultState(
        this.getStateDefinition()
            .any()
            .setValue(DISTANCE, DECAY_DISTANCE)
            .setValue(PERSISTENT, false)
            .setValue(WATERLOGGED, false));
  }

  private static void makeDrippingWaterParticles(
      Level level, BlockPos pos, RandomSource random, BlockState state, BlockPos pos2) {
    if (level.isRainingAt(pos.above())) {
      if (random.nextInt(15) == TICK_DELAY) {
        if (!state.canOcclude() || !state.isFaceSturdy(level, pos2, Direction.UP)) {
          ParticleUtils.spawnParticleBelow(level, pos, random, ParticleTypes.DRIPPING_WATER);
        }
      }
    }
  }

  @Override
  public void animateTick(
      @NotNull BlockState state,
      @NotNull Level level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    final BlockPos posBelow = pos.below();
    final BlockState stateBelow = level.getBlockState(posBelow);
    makeDrippingWaterParticles(level, pos, random, stateBelow, posBelow);
    this.makeFallingLeavesParticle(level, pos, random, stateBelow, posBelow);
  }

  private void makeFallingLeavesParticle(
      Level level, BlockPos pos, RandomSource random, BlockState state, BlockPos pos2) {
    if (!(random.nextFloat() >= this.leafParticleChance)) {
      if (!isFaceFull(state.getCollisionShape(level, pos2), Direction.UP)) {
        this.spawnFallingLeavesParticle(level, pos, random);
      }
    }
  }

  protected abstract void spawnFallingLeavesParticle(
      Level level, BlockPos pos, RandomSource random);
}
