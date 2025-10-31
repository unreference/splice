package com.github.unreference.splice.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class SpliceUtils {
  public static ResourceLocation getId(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block);
  }

  public static ResourceLocation getId(Item item) {
    return BuiltInRegistries.ITEM.getKey(item);
  }

  public static ResourceLocation getLocation(Block block) {
    final ResourceLocation id = getId(block);
    return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath());
  }

  public static ResourceLocation getLocation(Item item) {
    final ResourceLocation id = getId(item);
    return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "item/" + id.getPath());
  }

  public static String getName(Block block) {
    return getId(block).getPath();
  }
}
