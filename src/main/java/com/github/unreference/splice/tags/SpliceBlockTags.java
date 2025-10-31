package com.github.unreference.splice.tags;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class SpliceBlockTags {
  public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = create("incorrect_for_copper_tool");
  public static final TagKey<Block> BARS = create("bars");
  public static final TagKey<Block> CHAINS = create("chains");
  public static final TagKey<Block> LANTERNS = create("lanterns");
  public static final TagKey<Block> COPPER_CHESTS = create("copper_chests");
  public static final TagKey<Block> PALE_OAK_LOGS = create("pale_oak_logs");

  private static TagKey<Block> create(String key) {
    return TagKey.create(
        Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key));
  }
}
