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
    }

    if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
      SpliceItems.COPPER_BARS.forEach(event::accept);
    }
  }
}
