package com.github.unreference.splice.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public final class SpliceFallingLeavesParticle extends SpliceSingleQuadParticle {
  private static final float ACCELERATION_SCALE = 0.0025f;
  private static final int INITIAL_LIFETIME = 300;
  private static final int CURVE_ENDPOINT_TIME = 300;

  private final float spinAcceleration;
  private final float windBig;
  private final boolean isSwirling;
  private final boolean isFlowingAway;
  private final double xAccelerationFlowScale;
  private final double zAccelerationFlowScale;
  private final double swirlPeriod;

  private float rotationSpeed;

  private SpliceFallingLeavesParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      TextureAtlasSprite sprite,
      float acceleration,
      float windBig,
      boolean isSwirling,
      boolean isFlowingAway,
      float xDirection,
      float yDirection) {
    super(level, x, y, z, sprite);

    this.windBig = windBig;
    this.isSwirling = isSwirling;
    this.isFlowingAway = isFlowingAway;
    this.lifetime = INITIAL_LIFETIME;
    this.gravity = acceleration * 1.2f * ACCELERATION_SCALE;
    float size = xDirection * (this.random.nextBoolean() ? 0.05f : 0.075f);
    this.quadSize = size;
    this.setSize(size, size);
    this.friction = 1.0f;
    this.yd = -yDirection;

    this.rotationSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0f : 30.0f);
    this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0f : 5.0f);

    float randomFloat = this.random.nextFloat();
    this.xAccelerationFlowScale = Math.cos(Math.toRadians(randomFloat * 60.0f)) * this.windBig;
    this.zAccelerationFlowScale = Math.sin(Math.toRadians(randomFloat * 60.0f)) * this.windBig;
    this.swirlPeriod = Math.toRadians(1000.0f + randomFloat * 3000.0f);
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

    if (this.lifetime-- <= 0) {
      this.remove();
    }

    if (!this.removed) {
      final int curve = CURVE_ENDPOINT_TIME - this.lifetime;
      final float clamp = Math.min((float) curve / INITIAL_LIFETIME, 1.0f);
      double xVelocity = 0.0;
      double zVelocity = 0.0;

      if (this.isFlowingAway) {
        xVelocity += this.xAccelerationFlowScale * Math.pow(clamp, 1.25);
        zVelocity += this.zAccelerationFlowScale * Math.pow(clamp, 1.25);
      }

      if (this.isSwirling) {
        xVelocity += clamp * Math.cos(clamp * this.swirlPeriod) * this.windBig;
        zVelocity += clamp * Math.sin(clamp * this.swirlPeriod) * this.windBig;
      }

      this.xd += xVelocity * ACCELERATION_SCALE;
      this.zd += zVelocity * ACCELERATION_SCALE;
      this.yd -= this.gravity;
      this.rotationSpeed += this.spinAcceleration / 20.0f;
      this.oRoll = this.roll;
      this.roll += this.rotationSpeed / 20.0f;
      this.move(this.xd, this.yd, this.zd);

      if (this.onGround
          || this.lifetime < CURVE_ENDPOINT_TIME - 1 && (this.xd == 0.0 || this.zd == 0.0)) {
        this.remove();
      }

      if (!this.removed) {
        this.xd *= this.friction;
        this.yd *= this.friction;
        this.zd *= this.friction;
      }
    }
  }

  public record PaleOakProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
    @Override
    public @NotNull Particle createParticle(
        @NotNull SimpleParticleType type,
        @NotNull ClientLevel level,
        double x,
        double y,
        double z,
        double xSpeed,
        double ySpeed,
        double zSpeed) {
      return new SpliceFallingLeavesParticle(
          level, x, y, z, this.sprites.get(level.random), 0.07f, 10.0f, true, false, 2.0f, 0.021f);
    }
  }
}
