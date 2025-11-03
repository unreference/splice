package com.github.unreference.splice.world.entity.decoration;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import java.awt.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public final class SplicePaintingVariants {
  public static final ResourceKey<PaintingVariant> DENNIS = create("dennis");

  private static void register(
      BootstrapContext<PaintingVariant> context,
      ResourceKey<PaintingVariant> key,
      int width,
      int height) {
    context.register(key, new PaintingVariant(width, height, key.location()));
  }

  private static ResourceKey<PaintingVariant> create(String name) {
    return SpliceRegistries.createKey(Registries.PAINTING_VARIANT, name);
  }

  public static void bootstrap(BootstrapContext<PaintingVariant> context) {
    register(context, DENNIS, 3, 3);
  }
}
