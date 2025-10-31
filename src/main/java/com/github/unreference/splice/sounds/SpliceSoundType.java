package com.github.unreference.splice.sounds;

import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public final class SpliceSoundType {
  public static final DeferredSoundType RESIN =
      new DeferredSoundType(
          1.0f,
          1.0f,
          SpliceSoundEvents.RESIN_BREAK,
          SpliceSoundEvents.RESIN_STEP,
          SpliceSoundEvents.RESIN_PLACE,
          null,
          SpliceSoundEvents.RESIN_FALL);

  public static final SoundType RESIN_BRICKS =
      new DeferredSoundType(
          1.0f,
          1.0f,
          SpliceSoundEvents.RESIN_BRICKS_BREAK,
          SpliceSoundEvents.RESIN_BRICKS_STEP,
          SpliceSoundEvents.RESIN_BRICKS_PLACE,
          SpliceSoundEvents.RESIN_BRICKS_HIT,
          SpliceSoundEvents.RESIN_BRICKS_FALL);
}
