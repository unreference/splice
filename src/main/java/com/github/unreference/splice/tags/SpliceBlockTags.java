package com.github.unreference.splice.tags;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class SpliceBlockTags {
  public static final TagKey<Block> COPPER = create("copper");
  public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = create("incorrect_for_copper_tool");

  private static TagKey<Block> create(String key) {
    return TagKey.create(
        Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key));
  }
}
