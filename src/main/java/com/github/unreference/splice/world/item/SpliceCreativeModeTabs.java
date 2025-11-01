package com.github.unreference.splice.world.item;

import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class SpliceCreativeModeTabs {
  @SubscribeEvent
  public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
      paleOakBuildingBlocks(event);
      copperBarsBuildingBlocks(event);
      copperChainBuildingBlocks(event);
      resinBuildingBlocks(event);
    }

    // if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {}

    if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
      resinNaturalBlocks(event);
    }

    if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
      copperFunctionalBlocks(event);
    }

    if (event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS)) {
      copperRedstoneBlocks(event);
    }

    // if (event.getTabKey().equals(CreativeModeTabs.HOTBAR)) {}

    // if (event.getTabKey().equals(CreativeModeTabs.SEARCH)) {}

    if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
      copperToolsAndUtilities(event);
    }

    if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
      copperCombat(event);
    }

    // if (event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS)) {}

    if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
      resinIngredients(event);
      copperIngredients(event);
    }

    // if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {}

    // if (event.getTabKey().equals(CreativeModeTabs.OP_BLOCKS)) {}

    // if (event.getTabKey().equals(CreativeModeTabs.INVENTORY)) {}
  }

  private static void paleOakBuildingBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.PALE_OAK_LOG, Items.CHERRY_BUTTON);
    insert(event, SpliceItems.PALE_OAK_WOOD, SpliceItems.PALE_OAK_LOG);
    insert(event, SpliceItems.STRIPPED_OAK_LOG, SpliceItems.PALE_OAK_LOG);
    insert(event, SpliceItems.STRIPPED_PALE_OAK_WOOD, SpliceItems.STRIPPED_OAK_LOG);
    insert(event, SpliceItems.PALE_OAK_PLANKS, SpliceItems.STRIPPED_PALE_OAK_WOOD);
  }

  private static void copperIngredients(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.FIELD_MASONED_BANNER_PATTERN, Items.PHANTOM_MEMBRANE);
    insert(
        event,
        SpliceItems.BORDURE_INDENTED_BANNER_PATTERN,
        SpliceItems.FIELD_MASONED_BANNER_PATTERN);
  }

  private static void copperCombat(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.COPPER_SWORD, Items.STONE_SWORD);
    insert(event, SpliceItems.COPPER_AXE, Items.STONE_AXE);
    insert(event, SpliceItems.COPPER_HELMET, Items.LEATHER_BOOTS);
    insert(event, SpliceItems.COPPER_CHESTPLATE, SpliceItems.COPPER_HELMET);
    insert(event, SpliceItems.COPPER_LEGGINGS, SpliceItems.COPPER_CHESTPLATE);
    insert(event, SpliceItems.COPPER_BOOTS, SpliceItems.COPPER_LEGGINGS);
    insert(event, SpliceItems.COPPER_HORSE_ARMOR, Items.LEATHER_HORSE_ARMOR);
  }

  private static void copperToolsAndUtilities(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.COPPER_SHOVEL, Items.STONE_HOE);
    insert(event, SpliceItems.COPPER_PICKAXE, SpliceItems.COPPER_SHOVEL);
    insert(event, SpliceItems.COPPER_AXE, SpliceItems.COPPER_PICKAXE);
    insert(event, SpliceItems.COPPER_HOE, SpliceItems.COPPER_AXE);
    insert(event, SpliceItems.MUSIC_DISC_TEARS, Items.MUSIC_DISC_PIGSTEP);
    insert(event, SpliceItems.MUSIC_DISC_LAVA_CHICKEN, SpliceItems.MUSIC_DISC_TEARS);
    insert(event, SpliceItems.MUSIC_DISC_COFFEE_MACHINE, SpliceItems.MUSIC_DISC_LAVA_CHICKEN);
  }

  private static void copperRedstoneBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.WAXED_COPPER_CHEST, Items.CHEST);
  }

  private static void resinIngredients(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.RESIN_CLUMP, Items.HONEYCOMB);
    insert(event, SpliceItems.RESIN_BRICK, Items.NETHER_BRICK);
  }

  private static void copperFunctionalBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.COPPER_TORCH, Items.SOUL_TORCH);
    SpliceItems.COPPER_LANTERN.forEachInverse(item -> insert(event, item, Items.SOUL_LANTERN));
    SpliceItems.COPPER_CHAIN.forEachInverse(item -> insert(event, item, Items.CHAIN));
    insert(event, SpliceItems.COPPER_CHEST, Items.CHEST);
    insert(event, SpliceItems.EXPOSED_COPPER_CHEST, SpliceItems.COPPER_CHEST);
    insert(event, SpliceItems.WEATHERED_COPPER_CHEST, SpliceItems.EXPOSED_COPPER_CHEST);
    insert(event, SpliceItems.OXIDIZED_COPPER_CHEST, SpliceItems.WEATHERED_COPPER_CHEST);
    insert(event, SpliceItems.WAXED_COPPER_CHEST, SpliceItems.OXIDIZED_COPPER_CHEST);
    insert(event, SpliceItems.WAXED_EXPOSED_COPPER_CHEST, SpliceItems.WAXED_COPPER_CHEST);
    insert(event, SpliceItems.WAXED_WEATHERED_COPPER_CHEST, SpliceItems.WAXED_EXPOSED_COPPER_CHEST);
    insert(
        event, SpliceItems.WAXED_OXIDIZED_COPPER_CHEST, SpliceItems.WAXED_WEATHERED_COPPER_CHEST);
  }

  private static void resinNaturalBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.RESIN_BLOCK, Items.HONEY_BLOCK);
  }

  private static void resinBuildingBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.RESIN_BRICKS, Items.MUD_BRICK_WALL);
    insert(event, SpliceItems.RESIN_BRICK_STAIRS, SpliceItems.RESIN_BRICKS);
    insert(event, SpliceItems.RESIN_BRICK_SLAB, SpliceItems.RESIN_BRICK_STAIRS);
    insert(event, SpliceItems.RESIN_BRICK_WALL, SpliceItems.RESIN_BRICK_SLAB);
    insert(event, SpliceItems.CHISELED_RESIN_BRICKS, SpliceItems.RESIN_BRICK_WALL);
  }

  private static void insert(
      BuildCreativeModeTabContentsEvent event, ItemLike item, ItemLike after) {
    event.insertAfter(
        after.asItem().getDefaultInstance(),
        item.asItem().getDefaultInstance(),
        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
  }

  private static void copperChainBuildingBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.COPPER_CHAIN.unaffected(), Items.COPPER_BULB);
    insert(event, SpliceItems.COPPER_CHAIN.exposed(), Items.EXPOSED_COPPER_BULB);
    insert(event, SpliceItems.COPPER_CHAIN.weathered(), Items.WEATHERED_COPPER_BULB);
    insert(event, SpliceItems.COPPER_CHAIN.oxidized(), Items.OXIDIZED_COPPER_BULB);
    insert(event, SpliceItems.COPPER_CHAIN.waxed(), Items.WAXED_COPPER_BULB);
    insert(event, SpliceItems.COPPER_CHAIN.waxedExposed(), Items.WAXED_EXPOSED_COPPER_BULB);
    insert(event, SpliceItems.COPPER_CHAIN.waxedWeathered(), Items.WAXED_WEATHERED_COPPER_BULB);
    event.accept(SpliceItems.COPPER_CHAIN.waxedOxidized());
  }

  private static void copperBarsBuildingBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.COPPER_BARS.unaffected(), Items.CUT_COPPER_SLAB);
    insert(event, SpliceItems.COPPER_BARS.exposed(), Items.EXPOSED_CUT_COPPER_SLAB);
    insert(event, SpliceItems.COPPER_BARS.weathered(), Items.WEATHERED_CUT_COPPER_SLAB);
    insert(event, SpliceItems.COPPER_BARS.oxidized(), Items.OXIDIZED_CUT_COPPER);
    insert(event, SpliceItems.COPPER_BARS.waxed(), Items.WAXED_CUT_COPPER_SLAB);
    insert(event, SpliceItems.COPPER_BARS.waxedExposed(), Items.WAXED_EXPOSED_CUT_COPPER_SLAB);
    insert(event, SpliceItems.COPPER_BARS.waxedWeathered(), Items.WAXED_WEATHERED_CUT_COPPER_SLAB);
    insert(event, SpliceItems.COPPER_BARS.waxedOxidized(), Items.WAXED_OXIDIZED_CUT_COPPER_SLAB);
  }
}
