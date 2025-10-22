package com.github.unreference.splice.world.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class SpliceCreativeModeTabs {
  @SubscribeEvent
  public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
      event.accept(SpliceItems.FIELD_MASONED_BANNER_PATTERN.get());
      event.accept(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get());
      event.accept(SpliceItems.COPPER_NUGGET.get());
    }

    if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
      SpliceItems.COPPER_BARS.forEach(event::accept);
      SpliceItems.COPPER_CHAIN.forEach(event::accept);
    }

    if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
      SpliceItems.COPPER_CHAIN.forEach(event::accept);
      event.accept(SpliceItems.COPPER_CHEST.get());
      event.accept(SpliceItems.EXPOSED_COPPER_CHEST.get());
    }

    if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
      event.accept(SpliceItems.COPPER_SHOVEL.get());
      event.accept(SpliceItems.COPPER_PICKAXE.get());
      event.accept(SpliceItems.COPPER_AXE.get());
      event.accept(SpliceItems.COPPER_HOE.get());
    }

    if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
      event.accept(SpliceItems.COPPER_SWORD.get());
      event.accept(SpliceItems.COPPER_AXE.get());
      event.accept(SpliceItems.COPPER_HELMET.get());
      event.accept(SpliceItems.COPPER_CHESTPLATE.get());
      event.accept(SpliceItems.COPPER_LEGGINGS.get());
      event.accept(SpliceItems.COPPER_BOOTS.get());
      event.accept(SpliceItems.COPPER_HORSE_ARMOR.get());
    }
  }
}
