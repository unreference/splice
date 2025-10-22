package com.github.unreference.splice.tags;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public final class SpliceBannerPatternTags {
  public static TagKey<BannerPattern> PATTERN_ITEM_FIELD_MASONED = create("field_masoned");
  public static TagKey<BannerPattern> PATTERN_ITEM_BORDURE_INDENTED = create("bordure_indented");

  private static TagKey<BannerPattern> create(String key) {
    return TagKey.create(
        Registries.BANNER_PATTERN,
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "pattern_item/" + key));
  }
}
