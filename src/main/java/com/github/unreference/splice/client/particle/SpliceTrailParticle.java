package com.github.unreference.splice.client.particle;

import com.github.unreference.splice.core.particles.SpliceTrailParticleOption;
import com.github.unreference.splice.util.SpliceArgb;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class SpliceTrailParticle extends SpliceSingleQuadParticle {
  private final Vec3 target;

  SpliceTrailParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      double xSpeed,
      double ySpeed,
      double zSpeed,
      Vec3 target,
      int color,
      TextureAtlasSprite sprite) {
    super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
    final float brightness = 0.875f + this.random.nextFloat() * 0.25f;
    color = SpliceArgb.scale(color, brightness);

    this.rCol = SpliceArgb.red(color) / 255.0f;
    this.gCol = SpliceArgb.green(color) / 255.0f;
    this.bCol = SpliceArgb.blue(color) / 255.0f;
    this.quadSize = 0.26f;
    this.target = target;
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
  }

  @Override
  public void tick() {
    this.xo = this.x;
    this.yo = this.y;
    this.zo = this.z;

    if (this.age++ >= this.lifetime) {
      this.remove();
    }

    final int i = this.lifetime - this.age;
    final double d = 1.0 / i;
    this.x = Mth.lerp(d, this.x, this.target.x());
    this.y = Mth.lerp(d, this.y, this.target.y());
    this.z = Mth.lerp(d, this.z, this.target.z());
  }

  @Override
  protected int getLightColor(float partialTick) {
    return 15728880;
  }

  public record Provider(SpriteSet spriteSet)
      implements ParticleProvider<SpliceTrailParticleOption> {

    @Override
    public @NotNull Particle createParticle(
        @NotNull SpliceTrailParticleOption type,
        @NotNull ClientLevel level,
        double x,
        double y,
        double z,
        double xSpeed,
        double ySpeed,
        double zSpeed) {
      final SpliceTrailParticle particle =
          new SpliceTrailParticle(
              level,
              x,
              y,
              z,
              xSpeed,
              ySpeed,
              zSpeed,
              type.target(),
              type.color(),
              this.spriteSet.get(level.random));

      particle.setLifetime(type.duration());
      return particle;
    }
  }
}
