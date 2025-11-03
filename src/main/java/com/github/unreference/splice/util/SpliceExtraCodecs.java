package com.github.unreference.splice.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;

public final class SpliceExtraCodecs {
  private static Codec<Float> floatRangeMinInclusiveWithMessage(
      float min, float max, Function<Float, String> message) {
    return Codec.FLOAT.validate(
        p_366383_ ->
            p_366383_.compareTo(min) >= 0 && p_366383_.compareTo(max) <= 0
                ? DataResult.success(p_366383_)
                : DataResult.error(() -> message.apply(p_366383_)));
  }

  public static Codec<Float> floatRange(float min, float max) {
    return floatRangeMinInclusiveWithMessage(
        min,
        max,
        p_386312_ -> "Value must be within range [" + min + ";" + max + "]: " + p_386312_);
  }
}
