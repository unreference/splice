package com.github.unreference.splice.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public abstract class SpliceSingleQuadParticle extends SingleQuadParticle {
  private final TextureAtlasSprite sprite;
  protected float quadSize;

  protected SpliceSingleQuadParticle(
      ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
    super(level, x, y, z);
    this.sprite = sprite;
    this.quadSize = 0.1f * (this.random.nextFloat() * 0.5f + 0.5f) * 2.0f;
  }

  protected SpliceSingleQuadParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      double xSpeed,
      double ySpeed,
      double zSpeed,
      TextureAtlasSprite sprite) {
    super(level, x, y, z, xSpeed, ySpeed, zSpeed);
    this.sprite = sprite;
    this.quadSize = 0.1f * (this.random.nextFloat() * 0.5f + 0.5f) * 2.0f;
  }

  @Override
  protected float getU0() {
    return this.sprite.getU0();
  }

  @Override
  protected float getU1() {
    return this.sprite.getU1();
  }

  @Override
  protected float getV0() {
    return this.sprite.getV0();
  }

  @Override
  protected float getV1() {
    return this.sprite.getV1();
  }
}
