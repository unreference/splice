package com.github.unreference.splice.tags;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class SpliceItemTags {
  public static final TagKey<Item> COPPER_TOOL_MATERIALS = create("copper_tool_materials");
  public static final TagKey<Item> COPPER_CHESTS = create("copper_chests");
  public static final TagKey<Item> BARS = create("bars");
  public static final TagKey<Item> CHAINS = create("chains");
  public static final TagKey<Item> LANTERNS = create("lanterns");
  public static final TagKey<Item> PALE_OAK_LOGS = create("pale_oak_logs");

  private static TagKey<Item> create(String key) {
    return TagKey.create(
        Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key));
  }
}
