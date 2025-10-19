package com.github.unreference.splice.world.item;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceBannerPatternTags;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.function.Supplier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceItems {
  private static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(SpliceMain.MOD_ID);

  public static final Supplier<Item> FIELD_MASONED_BANNER_PATTERN =
      ITEMS.registerItem(
          "field_masoned_banner_pattern",
          props ->
              new BannerPatternItem(
                  SpliceBannerPatternTags.PATTERN_ITEM_FIELD_MASONED, props.stacksTo(1)));

  public static final Supplier<Item> BORDURE_INDENTED_BANNER_PATTERN =
      ITEMS.registerItem(
          "bordure_indented_banner_pattern",
          props ->
              new BannerPatternItem(
                  SpliceBannerPatternTags.PATTERN_ITEM_BORDURE_INDENTED, props.stacksTo(1)));

  public static final SpliceWeatheringCopperItems COPPER_BARS =
      SpliceWeatheringCopperItems.create(SpliceBlocks.COPPER_BARS, SpliceItems::registerBlock);

  private static DeferredItem<BlockItem> registerBlock(DeferredBlock<? extends Block> block) {
    return ITEMS.registerSimpleBlockItem(block);
  }

  public static <T extends Block> void registerBlock(String key, DeferredBlock<T> block) {
    ITEMS.register(key, () -> new BlockItem(block.get(), new Item.Properties()));
  }

  public static void register(IEventBus bus) {
    ITEMS.register(bus);
  }
}
