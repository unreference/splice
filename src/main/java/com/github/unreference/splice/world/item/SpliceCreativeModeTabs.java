package com.github.unreference.splice.world.item;

import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class SpliceCreativeModeTabs {
  @SubscribeEvent
  public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
      paleGardenBuildingBlocks(event);
      copperBarsBuildingBlocks(event);
      copperChainBuildingBlocks(event);
      resinBuildingBlocks(event);
    }

    // if (event.getTabKey().equals(CreativeModeTabs.COLORED_BLOCKS)) {}

    if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
      paleGardenNaturalBlocks(event);
      resinNaturalBlocks(event);
    }

    if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
      paleGardenFunctionalBlocks(event);
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

    if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
      paleGardenSpawnEggs(event);
    }

    // if (event.getTabKey().equals(CreativeModeTabs.OP_BLOCKS)) {}

    // if (event.getTabKey().equals(CreativeModeTabs.INVENTORY)) {}
  }

  private static void paleGardenSpawnEggs(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.CREAKING_HEART, Items.TRIAL_SPAWNER);
  }

  private static void paleGardenNaturalBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(
        event,
        SpliceItems.CLOSED_EYEBLOSSOM,
        Items.TORCHFLOWER); // TODO: Replace with cactus flower
    insert(event, SpliceItems.OPEN_EYEBLOSSOM, SpliceItems.CLOSED_EYEBLOSSOM);
    insert(event, SpliceItems.PALE_MOSS_BLOCK, Items.MOSS_CARPET);
    insert(event, SpliceItems.PALE_MOSS_CARPET, SpliceItems.PALE_MOSS_BLOCK);
    insert(event, SpliceItems.PALE_HANGING_MOSS, SpliceItems.PALE_MOSS_CARPET);
    insert(event, SpliceItems.PALE_OAK_LEAVES, Items.CHERRY_LEAVES);
    insert(event, SpliceItems.PALE_OAK_SAPLING, Items.CHERRY_SAPLING);
  }

  private static void paleGardenFunctionalBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.PALE_OAK_SIGN, Items.CHERRY_HANGING_SIGN);
    insert(event, SpliceItems.PALE_OAK_HANGING_SIGN, SpliceItems.PALE_OAK_SIGN);
  }

  private static void paleGardenBuildingBlocks(BuildCreativeModeTabContentsEvent event) {
    insert(event, SpliceItems.PALE_OAK_LOG, Items.CHERRY_BUTTON);
    insert(event, SpliceItems.PALE_OAK_WOOD, SpliceItems.PALE_OAK_LOG);
    insert(event, SpliceItems.STRIPPED_OAK_LOG, SpliceItems.PALE_OAK_WOOD);
    insert(event, SpliceItems.STRIPPED_PALE_OAK_WOOD, SpliceItems.STRIPPED_OAK_LOG);
    insert(event, SpliceItems.PALE_OAK_PLANKS, SpliceItems.STRIPPED_PALE_OAK_WOOD);
    insert(event, SpliceItems.PALE_OAK_STAIRS, SpliceItems.PALE_OAK_PLANKS);
    insert(event, SpliceItems.PALE_OAK_SLAB, SpliceItems.PALE_OAK_STAIRS);
    insert(event, SpliceItems.PALE_OAK_FENCE, SpliceItems.PALE_OAK_SLAB);
    insert(event, SpliceItems.PALE_OAK_FENCE_GATE, SpliceItems.PALE_OAK_FENCE);
    insert(event, SpliceItems.PALE_OAK_DOOR, SpliceItems.PALE_OAK_FENCE_GATE);
    insert(event, SpliceItems.PALE_OAK_TRAPDOOR, SpliceItems.PALE_OAK_DOOR);
    insert(event, SpliceItems.PALE_OAK_PRESSURE_PLATE, SpliceItems.PALE_OAK_TRAPDOOR);
    insert(event, SpliceItems.PALE_OAK_BUTTON, SpliceItems.PALE_OAK_PRESSURE_PLATE);
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
