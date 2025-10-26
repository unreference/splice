package com.github.unreference.splice.world.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class SpliceCreativeModeTabs {
  @SubscribeEvent
  public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
      SpliceItems.COPPER_BARS.forEach(event::accept);
      SpliceItems.COPPER_CHAIN.forEach(event::accept);
    }

    if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
      SpliceItems.COPPER_LANTERN.forEach(event::accept);
      SpliceItems.COPPER_CHAIN.forEach(event::accept);
      event.accept(SpliceItems.COPPER_TORCH);
      event.accept(SpliceItems.COPPER_CHEST);
      event.accept(SpliceItems.EXPOSED_COPPER_CHEST);
      event.accept(SpliceItems.WEATHERED_COPPER_CHEST);
      event.accept(SpliceItems.OXIDIZED_COPPER_CHEST);
      event.accept(SpliceItems.WAXED_COPPER_CHEST);
      event.accept(SpliceItems.WAXED_EXPOSED_COPPER_CHEST);
      event.accept(SpliceItems.WAXED_WEATHERED_COPPER_CHEST);
      event.accept(SpliceItems.WAXED_OXIDIZED_COPPER_CHEST);
    }

    if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
      event.accept(SpliceItems.COPPER_SHOVEL);
      event.accept(SpliceItems.COPPER_PICKAXE);
      event.accept(SpliceItems.COPPER_AXE);
      event.accept(SpliceItems.COPPER_HOE);
      event.accept(SpliceItems.MUSIC_DISC_TEARS);
      event.accept(SpliceItems.MUSIC_DISC_LAVA_CHICKEN);
    }

    if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
      event.accept(SpliceItems.COPPER_SWORD);
      event.accept(SpliceItems.COPPER_AXE);
      event.accept(SpliceItems.COPPER_HELMET);
      event.accept(SpliceItems.COPPER_CHESTPLATE);
      event.accept(SpliceItems.COPPER_LEGGINGS);
      event.accept(SpliceItems.COPPER_BOOTS);
      event.accept(SpliceItems.COPPER_HORSE_ARMOR);
    }

    if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
      event.accept(SpliceItems.FIELD_MASONED_BANNER_PATTERN);
      event.accept(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN);
      event.accept(SpliceItems.COPPER_NUGGET);
    }
  }
}
