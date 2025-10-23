package com.github.unreference.splice.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class SpliceDeferredWallTorchBlock extends WallTorchBlock {
  private final Supplier<? extends SimpleParticleType> flameParticle;

  public SpliceDeferredWallTorchBlock(
      Supplier<? extends SimpleParticleType> flameParticle, Properties properties) {
    super(ParticleTypes.FLAME, properties);
    this.flameParticle = flameParticle;
  }

  @Override
  public void animateTick(
      BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
    final double centerX = pos.getX() + 0.5;
    final double centerY = pos.getY() + 0.7;
    final double centerZ = pos.getZ() + 0.5;
    final double xOffset = 0.27;
    final double yOffset = 0.22;

    final Direction direction = state.getValue(FACING).getOpposite();

    final double x = centerX + xOffset * direction.getStepX();
    final double y = centerY + yOffset;
    final double z = centerZ + xOffset * direction.getStepZ();

    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
    level.addParticle(this.flameParticle.get(), x, y, z, 0.0, 0.0, 0.0);
  }
}
