package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.util.SpliceExtraCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class SpliceUntintedParticleLeavesBlock extends SpliceLeavesBlock {
  public static final MapCodec<SpliceUntintedParticleLeavesBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      SpliceExtraCodecs.floatRange(0.0f, 1.0f)
                          .fieldOf("leaf_particle_chance")
                          .forGetter(block -> block.leafParticleChance),
                      ParticleTypes.CODEC
                          .fieldOf("leaf_particle")
                          .forGetter(block -> block.leafParticle.get()),
                      propertiesCodec())
                  .apply(
                      instance,
                      (chance, particle, properties) ->
                          new SpliceUntintedParticleLeavesBlock(
                              chance, () -> (SimpleParticleType) particle, properties)));

  protected final Supplier<? extends SimpleParticleType> leafParticle;

  public SpliceUntintedParticleLeavesBlock(
      float particleChance,
      Supplier<? extends SimpleParticleType> leafParticle,
      Properties properties) {
    super(particleChance, properties);
    this.leafParticle = leafParticle;
  }

  @Override
  protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
    ParticleUtils.spawnParticleBelow(level, pos, random, leafParticle.get());
  }
}
