package com.github.unreference.splice.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public final class SpliceBlockUtil {
  public static ResourceLocation getId(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block);
  }

  public static ResourceLocation getTexture(Block block) {
    final var ID = getId(block);
    return ResourceLocation.fromNamespaceAndPath(ID.getNamespace(), "block/" + ID.getPath());
  }
}
