package com.github.unreference.splice.core.particles;

import com.github.unreference.splice.util.SpliceExtraCodecs;
import com.github.unreference.splice.world.phys.SpliceVec3;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record SpliceTrailParticleOption(Vec3 target, int color, int duration)
    implements ParticleOptions {
  public static final MapCodec<SpliceTrailParticleOption> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      Vec3.CODEC.fieldOf("target").forGetter(SpliceTrailParticleOption::target),
                      SpliceExtraCodecs.RGB_COLOR_CODEC
                          .fieldOf("color")
                          .forGetter(SpliceTrailParticleOption::color),
                      ExtraCodecs.POSITIVE_INT
                          .fieldOf("duration")
                          .forGetter(SpliceTrailParticleOption::duration))
                  .apply(instance, SpliceTrailParticleOption::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, SpliceTrailParticleOption> STREAM_CODEC =
      StreamCodec.composite(
          SpliceVec3.STREAM_CODEC,
          SpliceTrailParticleOption::target,
          ByteBufCodecs.INT,
          SpliceTrailParticleOption::color,
          ByteBufCodecs.VAR_INT,
          SpliceTrailParticleOption::duration,
          SpliceTrailParticleOption::new);

  @Override
  public @NotNull ParticleType<?> getType() {
    return SpliceParticleTypes.TRAIL.get();
  }
}
