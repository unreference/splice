package com.github.unreference.splice.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class SpliceTorchBlock extends TorchBlock {
  private final Supplier<? extends SimpleParticleType> flameParticle;

  public SpliceTorchBlock(
      Supplier<? extends SimpleParticleType> flameParticle, Properties properties) {
    super(ParticleTypes.FLAME, properties);
    this.flameParticle = flameParticle;
  }

  @Override
  public void animateTick(
      @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
    final double x = pos.getX() + 0.5;
    final double y = pos.getY() + 0.7;
    final double z = pos.getZ() + 0.5;

    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
    level.addParticle(this.flameParticle.get(), x, y, z, 0.0, 0.0, 0.0);
  }
}
