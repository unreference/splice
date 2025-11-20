package com.github.unreference.splice.world.item;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceBannerPatternTags;
import com.github.unreference.splice.world.item.equipment.SpliceArmorMaterials;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceItems {

  private static final DeferredRegister.Items ITEMS =
      DeferredRegister.createItems(SpliceMain.MOD_ID);

  public static final DeferredItem<Item> FIELD_MASONED_BANNER_PATTERN =
      ITEMS.registerItem(
          "field_masoned_banner_pattern",
          props ->
              new BannerPatternItem(
                  SpliceBannerPatternTags.PATTERN_ITEM_FIELD_MASONED, props.stacksTo(1)));

  public static final DeferredItem<Item> BORDURE_INDENTED_BANNER_PATTERN =
      ITEMS.registerItem(
          "bordure_indented_banner_pattern",
          props ->
              new BannerPatternItem(
                  SpliceBannerPatternTags.PATTERN_ITEM_BORDURE_INDENTED, props.stacksTo(1)));

  public static final SpliceWeatheringCopperItems COPPER_BARS =
      SpliceWeatheringCopperItems.create(SpliceBlocks.COPPER_BARS, ITEMS::registerSimpleBlockItem);

  public static final SpliceWeatheringCopperItems COPPER_CHAIN =
      SpliceWeatheringCopperItems.create(SpliceBlocks.COPPER_CHAIN, ITEMS::registerSimpleBlockItem);

  public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.registerSimpleItem("copper_nugget");

  public static final DeferredItem<ShovelItem> COPPER_SHOVEL =
      ITEMS.registerItem(
          "copper_shovel",
          props ->
              new ShovelItem(
                  SpliceTiers.COPPER,
                  props.attributes(ShovelItem.createAttributes(SpliceTiers.COPPER, 1.5f, -3.0f))));

  public static final DeferredItem<PickaxeItem> COPPER_PICKAXE =
      ITEMS.registerItem(
          "copper_pickaxe",
          props ->
              new PickaxeItem(
                  SpliceTiers.COPPER,
                  props.attributes(PickaxeItem.createAttributes(SpliceTiers.COPPER, 1.0f, -2.8f))));

  public static final DeferredItem<AxeItem> COPPER_AXE =
      ITEMS.registerItem(
          "copper_axe",
          props ->
              new AxeItem(
                  SpliceTiers.COPPER,
                  props.attributes(AxeItem.createAttributes(SpliceTiers.COPPER, 7.0f, -3.2f))));

  public static final DeferredItem<HoeItem> COPPER_HOE =
      ITEMS.registerItem(
          "copper_hoe",
          props ->
              new HoeItem(
                  SpliceTiers.COPPER,
                  props.attributes(HoeItem.createAttributes(SpliceTiers.COPPER, -1.0f, -2.0f))));

  public static final DeferredItem<SwordItem> COPPER_SWORD =
      ITEMS.registerItem(
          "copper_sword",
          props ->
              new SwordItem(
                  SpliceTiers.COPPER,
                  props.attributes(SwordItem.createAttributes(SpliceTiers.COPPER, 3.0f, -2.4f))));

  public static final DeferredItem<ArmorItem> COPPER_HELMET =
      ITEMS.registerItem(
          "copper_helmet",
          props ->
              new ArmorItem(
                  SpliceArmorMaterials.COPPER,
                  ArmorItem.Type.HELMET,
                  props.durability(ArmorItem.Type.HELMET.getDurability(11))));

  public static final DeferredItem<ArmorItem> COPPER_CHESTPLATE =
      ITEMS.registerItem(
          "copper_chestplate",
          props ->
              new ArmorItem(
                  SpliceArmorMaterials.COPPER,
                  ArmorItem.Type.CHESTPLATE,
                  props.durability(ArmorItem.Type.CHESTPLATE.getDurability(11))));

  public static final DeferredItem<ArmorItem> COPPER_LEGGINGS =
      ITEMS.registerItem(
          "copper_leggings",
          props ->
              new ArmorItem(
                  SpliceArmorMaterials.COPPER,
                  ArmorItem.Type.LEGGINGS,
                  props.durability(ArmorItem.Type.LEGGINGS.getDurability(11))));

  public static final DeferredItem<ArmorItem> COPPER_BOOTS =
      ITEMS.registerItem(
          "copper_boots",
          props ->
              new ArmorItem(
                  SpliceArmorMaterials.COPPER,
                  ArmorItem.Type.BOOTS,
                  props.durability(ArmorItem.Type.BOOTS.getDurability(11))));

  public static final DeferredItem<AnimalArmorItem> COPPER_HORSE_ARMOR =
      ITEMS.registerItem(
          "copper_horse_armor",
          props ->
              new AnimalArmorItem(
                  SpliceArmorMaterials.COPPER,
                  AnimalArmorItem.BodyType.EQUESTRIAN,
                  false,
                  props.stacksTo(1)));

  public static final DeferredItem<BlockItem> COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.COPPER_CHEST);

  public static final DeferredItem<BlockItem> EXPOSED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.EXPOSED_COPPER_CHEST);

  public static final DeferredItem<BlockItem> WEATHERED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.WEATHERED_COPPER_CHEST);

  public static final DeferredItem<BlockItem> OXIDIZED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.OXIDIZED_COPPER_CHEST);

  public static final DeferredItem<BlockItem> WAXED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.WAXED_COPPER_CHEST);

  public static final DeferredItem<BlockItem> WAXED_EXPOSED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST);

  public static final DeferredItem<BlockItem> WAXED_WEATHERED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST);

  public static final DeferredItem<BlockItem> WAXED_OXIDIZED_COPPER_CHEST =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST);

  public static final DeferredItem<StandingAndWallBlockItem> COPPER_TORCH =
      ITEMS.register(
          "copper_torch",
          () ->
              new StandingAndWallBlockItem(
                  SpliceBlocks.COPPER_TORCH.get(),
                  SpliceBlocks.COPPER_WALL_TORCH.get(),
                  new Item.Properties(),
                  Direction.DOWN));

  public static final SpliceWeatheringCopperItems COPPER_LANTERN =
      SpliceWeatheringCopperItems.create(
          SpliceBlocks.COPPER_LANTERN, ITEMS::registerSimpleBlockItem);

  public static final DeferredItem<BlockItem> RESIN_BLOCK =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.RESIN_BLOCK);

  public static final DeferredItem<BlockItem> RESIN_CLUMP =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.RESIN_CLUMP);

  public static final DeferredItem<BlockItem> RESIN_BRICKS =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.RESIN_BRICKS);

  public static final DeferredItem<Item> RESIN_BRICK = ITEMS.registerSimpleItem("resin_brick");

  public static final DeferredItem<BlockItem> RESIN_BRICK_STAIRS =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.RESIN_BRICK_STAIRS);

  public static final DeferredItem<BlockItem> CHISELED_RESIN_BRICKS =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.CHISELED_RESIN_BRICKS);

  public static final DeferredItem<BlockItem> RESIN_BRICK_SLAB =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.RESIN_BRICK_SLAB);

  public static final DeferredItem<BlockItem> RESIN_BRICK_WALL =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.RESIN_BRICK_WALL);

  public static final DeferredItem<BlockItem> PALE_OAK_WOOD =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_WOOD);

  public static final DeferredItem<BlockItem> STRIPPED_PALE_OAK_WOOD =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.STRIPPED_PALE_OAK_WOOD);

  public static final DeferredItem<BlockItem> PALE_OAK_LOG =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_LOG);

  public static final DeferredItem<BlockItem> STRIPPED_OAK_LOG =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.STRIPPED_PALE_OAK_LOG);

  // TODO: Add to composter
  public static final DeferredItem<BlockItem> PALE_OAK_SAPLING =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_SAPLING);

  // TODO: Add to composter
  public static final DeferredItem<BlockItem> PALE_OAK_LEAVES =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_LEAVES);

  // TODO: Add to composter
  public static final DeferredItem<BlockItem> PALE_MOSS_CARPET =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_MOSS_CARPET);

  // TODO: Add to villager trades
  // TODO: Add to composter
  public static final DeferredItem<BlockItem> PALE_MOSS_BLOCK =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_MOSS_BLOCK);

  // TODO: Add to villager trades
  // TODO: Add to composter
  public static final DeferredItem<BlockItem> PALE_HANGING_MOSS =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_HANGING_MOSS);

  // TODO: Add to villager trades
  // TODO: Add to composter
  public static final DeferredItem<BlockItem> OPEN_EYEBLOSSOM =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.OPEN_EYEBLOSSOM);

  // TODO: Add to composter
  public static final DeferredItem<BlockItem> CLOSED_EYEBLOSSOM =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.CLOSED_EYEBLOSSOM);

  public static final DeferredItem<BlockItem> PALE_OAK_PLANKS =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_PLANKS);

  public static final DeferredItem<BlockItem> PALE_OAK_BUTTON =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_BUTTON);

  public static final DeferredItem<BlockItem> PALE_OAK_FENCE =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_FENCE);

  public static final DeferredItem<BlockItem> PALE_OAK_FENCE_GATE =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_FENCE_GATE);

  public static final DeferredItem<BlockItem> PALE_OAK_PRESSURE_PLATE =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_PRESSURE_PLATE);

  public static final DeferredItem<SignItem> PALE_OAK_SIGN =
      ITEMS.registerItem(
          "pale_oak_sign",
          props ->
              new SignItem(
                  props, SpliceBlocks.PALE_OAK_SIGN.get(), SpliceBlocks.PALE_OAK_WALL_SIGN.get()),
          new Item.Properties().stacksTo(16));

  public static final DeferredItem<HangingSignItem> PALE_OAK_HANGING_SIGN =
      ITEMS.register(
          "pale_oak_hanging_sign",
          () ->
              new HangingSignItem(
                  SpliceBlocks.PALE_OAK_HANGING_SIGN.get(),
                  SpliceBlocks.PALE_OAK_WALL_HANGING_SIGN.get(),
                  new Item.Properties().stacksTo(16)));

  public static final DeferredItem<BlockItem> PALE_OAK_SLAB =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_SLAB);

  public static final DeferredItem<BlockItem> PALE_OAK_STAIRS =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_STAIRS);

  public static final DeferredItem<BlockItem> PALE_OAK_DOOR =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_DOOR);

  public static final DeferredItem<BlockItem> PALE_OAK_TRAPDOOR =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.PALE_OAK_TRAPDOOR);

  public static final DeferredItem<BlockItem> CREAKING_HEART =
      ITEMS.registerSimpleBlockItem(SpliceBlocks.CREAKING_HEART);

  public static final DeferredItem<Item> MUSIC_DISC_TEARS =
      ITEMS.registerSimpleItem(
          "music_disc_tears",
          new Item.Properties()
              .stacksTo(1)
              .rarity(Rarity.RARE)
              .jukeboxPlayable(SpliceJukeboxSongs.TEARS));

  public static final DeferredItem<Item> MUSIC_DISC_LAVA_CHICKEN =
      ITEMS.registerSimpleItem(
          "music_disc_lava_chicken",
          new Item.Properties()
              .stacksTo(1)
              .rarity(Rarity.RARE)
              .jukeboxPlayable(SpliceJukeboxSongs.LAVA_CHICKEN));

  public static void register(IEventBus bus) {
    bannerPatterns();
    ITEMS.register(bus);
  }

  private static void bannerPatterns() {}
}
