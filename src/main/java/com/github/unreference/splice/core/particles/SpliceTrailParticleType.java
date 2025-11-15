package com.github.unreference.splice.core.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public final class SpliceTrailParticleType extends ParticleType<SpliceTrailParticleOption> {
  SpliceTrailParticleType(boolean overrideLimiter) {
    super(overrideLimiter);
  }

  @Override
  public @NotNull MapCodec<SpliceTrailParticleOption> codec() {
    return SpliceTrailParticleOption.CODEC;
  }

  @Override
  public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, SpliceTrailParticleOption>
      streamCodec() {
    return SpliceTrailParticleOption.STREAM_CODEC;
  }
}
