package com.github.unreference.splice.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class SpliceArgb {
  public static int alpha(int alpha) {
    return alpha >>> 24;
  }

  public static int red(int red) {
    return red >> 16 & 0xFF;
  }

  public static int green(int green) {
    return green >> 8 & 0xFF;
  }

  public static int blue(int blue) {
    return blue & 0xFF;
  }

  public static int color(int alpha, int red, int green, int blue) {
    return ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
  }

  public static int color(int red, int green, int blue) {
    return color(255, red, green, blue);
  }

  public static int color(Vec3 argb) {
    return color(to8Bit((float) argb.x()), to8Bit((float) argb.y()), to8Bit((float) argb.z()));
  }

  public static int to8Bit(float color) {
    return Mth.floor(color * 255.0f);
  }

  public static int multiply(int color, int multiplier) {
    if (multiplier == -1) {
      return multiplier;
    }

    return color(
        alpha(color) * alpha(multiplier) / 255,
        red(color) * red(multiplier) / 255,
        green(color) * green(multiplier) / 255,
        blue(color) * blue(multiplier) / 255);
  }

  public static int scale(int color, float factor) {
    return scale(color, factor, factor, factor);
  }

  public static int scale(int color, float redFactor, float greenFactor, float blueFactor) {
    return color(
        alpha(color),
        Math.clamp((int) (red(color) * redFactor), 0, 255),
        Math.clamp((int) (green(color) * greenFactor), 0, 255),
        Math.clamp((int) (blue(color) * blueFactor), 0, 255));
  }

  public static int scale(int color, int factor) {
    return color(
        alpha(color),
        Math.clamp((long) red(color) * factor / 255L, 0, 255),
        Math.clamp((long) green(color) * factor / 255L, 0, 255),
        Math.clamp((long) blue(color) * factor / 255L, 0, 255));
  }

  public static int grayscale(int color) {
    final int component = (int) (red(color) * 0.3f + green(color) * 0.59f + blue(color) * 0.11f);
    return color(component, component, component);
  }

  public static int lerp(float delta, int startColor, int endColor) {
    int a = Mth.lerpInt(delta, alpha(startColor), alpha(endColor));
    int r = Mth.lerpInt(delta, red(startColor), red(endColor));
    int g = Mth.lerpInt(delta, green(startColor), green(endColor));
    int b = Mth.lerpInt(delta, blue(startColor), blue(endColor));
    return color(a, r, g, b);
  }

  public static int opaque(int color) {
    return color | 0xFF000000;
  }

  public static int transparent(int color) {
    return color & 0xFFFFFF;
  }

  public static int color(int alpha, int rgb) {
    return alpha << 24 | rgb & 0xFFFFFF;
  }

  public static int color(float opacity, int rgb) {
    return to8Bit(opacity) << 24 | rgb & 0xFFFFFF;
  }

  public static int white(float opacity) {
    return to8Bit(opacity) << 24 | 0xFFFFFF;
  }

  public static int color(float alpha, float red, float green, float blue) {
    return color(to8Bit(alpha), to8Bit(red), to8Bit(green), to8Bit(blue));
  }

  public static Vector3f vector3f(int rgb) {
    float red = red(rgb) / 255.0f;
    float green = green(rgb) / 255.0f;
    float blue = blue(rgb) / 255.0f;
    return new Vector3f(red, green, blue);
  }

  public static int average(int color1, int color2) {
    return color(
        (alpha(color1) + alpha(color2)) / 2,
        (red(color1) + red(color2)) / 2,
        (green(color1) + green(color2)) / 2,
        (blue(color1) + blue(color2)) / 2);
  }

  public static float alphaFloat(int color) {
    return from8Bit(alpha(color));
  }

  public static float redFloat(int color) {
    return from8Bit(red(color));
  }

  public static float greenFloat(int color) {
    return from8Bit(green(color));
  }

  public static float blueFloat(int color) {
    return from8Bit(blue(color));
  }

  public static float from8Bit(int color8) {
    return color8 / 255.0f;
  }

  public static int abgr(int color) {
    return (color & 0xFF00FF00) | ((color & 0xFF0000) >> 16) | (color & 0xFF) << 16;
  }

  public static int brightness(int color, float brightness) {
    int alpha = alpha(color);
    float red = red(color) / 255.0f;
    float green = green(color) / 255.0f;
    float blue = blue(color) / 255.0f;

    float max = Math.max(red, Math.max(green, blue));
    float min = Math.min(red, Math.min(green, blue));
    float delta = max - min;

    float saturation = max == 0.0f ? 0.0f : delta / max;
    float hue;

    if (delta == 0.0f) {
      hue = 0.0f;
    } else if (max == red) {
      hue = ((green - blue) / delta + (green < blue ? 6.0f : 0.0f)) / 6.0f;
    } else if (max == green) {
      hue = ((blue - red) / delta + 2.0f) / 6.0f;
    } else {
      hue = ((red - green) / delta + 4.0f) / 6.0f;
    }

    brightness = Math.clamp(brightness, 0.0f, 1.0f);

    if (saturation == 0.0f) {
      int gray = Math.round(brightness * 255.0f);
      return color(alpha, gray, gray, gray);
    }

    float hueScaled = (hue - (float) Math.floor(hue)) * 6.0f;
    int sector = (int) hueScaled;
    float fractional = hueScaled - sector;

    float lowChannel = brightness * (1.0f - saturation);
    float decreaseChannel = brightness * (1.0f - saturation * fractional);
    float increaseChannel = brightness * (1.0f - saturation * (1.0f - fractional));

    float r;
    float g;
    float b;

    switch (sector) {
      case 0 -> {
        r = brightness;
        g = increaseChannel;
        b = lowChannel;
      }

      case 1 -> {
        r = decreaseChannel;
        g = brightness;
        b = lowChannel;
      }

      case 2 -> {
        r = lowChannel;
        g = brightness;
        b = increaseChannel;
      }

      case 3 -> {
        r = lowChannel;
        g = decreaseChannel;
        b = brightness;
      }

      case 4 -> {
        r = increaseChannel;
        g = lowChannel;
        b = brightness;
      }

      default -> {
        r = brightness;
        g = lowChannel;
        b = decreaseChannel;
      }
    }

    return color(alpha, Math.round(r * 255.0f), Math.round(g * 255.0f), Math.round(b * 255.0f));
  }
}
