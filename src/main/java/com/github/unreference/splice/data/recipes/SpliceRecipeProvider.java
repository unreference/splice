package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class SpliceRecipeProvider extends RecipeProvider {
  private static final float XP_COOKING = 0.1f;
  private static final int TIME_SMELTING = 200;
  private static final int TIME_BLASTING = 100;

  public SpliceRecipeProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  private static String getName(ItemLike item) {
    return getSimpleRecipeName(item);
  }

  // ===================================================================================================================
  // 1. Pale Garden
  // ===================================================================================================================

  @Override
  protected void buildRecipes(@NotNull RecipeOutput output) {
    this.registerBlockFamilies(output);
    this.registerCopper(output);
    this.registerResin(output);
    this.registerPaleGarden(output);
    this.registerMiscellaneous(output);
  }

  // ===================================================================================================================
  // 2. Resin
  // ===================================================================================================================

  private void registerPaleGarden(RecipeOutput output) {
    planksFromLog(output, SpliceBlocks.PALE_OAK_PLANKS.get(), SpliceItemTags.PALE_OAK_LOGS, 4);
    woodFromLogs(output, SpliceBlocks.PALE_OAK_WOOD.get(), SpliceBlocks.PALE_OAK_LOG.get());
    woodFromLogs(
        output,
        SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get(),
        SpliceBlocks.STRIPPED_PALE_OAK_LOG.get());
    hangingSign(
        output, SpliceBlocks.PALE_OAK_HANGING_SIGN.get(), SpliceBlocks.STRIPPED_PALE_OAK_LOG.get());

    carpet(output, SpliceBlocks.PALE_MOSS_CARPET.get(), SpliceBlocks.PALE_MOSS_BLOCK.get());

    this.oneToOne(output, Items.GRAY_DYE, SpliceBlocks.CLOSED_EYEBLOSSOM, "gray_dye");
    this.oneToOne(output, Items.ORANGE_DYE, SpliceBlocks.OPEN_EYEBLOSSOM, "orange_dye");

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SpliceBlocks.CREAKING_HEART)
        .define('P', SpliceBlocks.PALE_OAK_LOG)
        .define('R', SpliceBlocks.RESIN_BLOCK)
        .pattern(" P ")
        .pattern(" R ")
        .pattern(" P ")
        .unlockedBy(getName(SpliceItems.RESIN_BLOCK), has(SpliceItems.RESIN_BLOCK))
        .save(output);
  }

  // ===================================================================================================================
  // 3. Copper
  // ===================================================================================================================

  private void registerResin(RecipeOutput output) {
    final Item block = SpliceItems.RESIN_BLOCK.get();
    final Item clump = SpliceItems.RESIN_CLUMP.get();
    final Item brick = SpliceItems.RESIN_BRICK.get();

    this.nineBlockStorage(
        output, RecipeCategory.MISC, clump, RecipeCategory.BUILDING_BLOCKS, block);

    this.twoByTwo(output, RecipeCategory.BUILDING_BLOCKS, SpliceBlocks.RESIN_BRICKS.get(), brick);

    SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(clump), RecipeCategory.MISC, brick, XP_COOKING, TIME_SMELTING)
        .unlockedBy(getName(clump), has(clump))
        .save(output);
  }

  private void registerCopper(RecipeOutput output) {
    SpliceBlocks.COPPER_FAMILY.stream()
        .flatMap(block -> block.waxedMapping().entrySet().stream())
        .forEach(block -> this.waxing(output, block.getKey().get(), block.getValue().get()));

    this.copperBlocks(output);
    this.copperEquipment(output);
    this.copperCooking(output);
  }

  private void copperBlocks(RecipeOutput output) {
    final Item ingot = Items.COPPER_INGOT;
    final Item nugget = SpliceItems.COPPER_NUGGET.get();

    this.twoByTwo(output, RecipeCategory.REDSTONE, Blocks.COPPER_TRAPDOOR, ingot);

    this.nineBlockStorage(
        output,
        RecipeCategory.MISC,
        nugget,
        RecipeCategory.MISC,
        ingot,
        getConversionRecipeName(ingot, nugget),
        getItemName(ingot));

    ShapedRecipeBuilder.shaped(
            RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_BARS.unaffected(), 16)
        .define('I', ingot)
        .pattern("III")
        .pattern("III")
        .unlockedBy(getName(ingot), has(ingot))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_CHAIN.unaffected())
        .define('N', nugget)
        .define('I', ingot)
        .pattern("N")
        .pattern("I")
        .pattern("N")
        .unlockedBy(getName(nugget), has(nugget))
        .unlockedBy(getName(ingot), has(ingot))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_CHEST)
        .define('I', ingot)
        .define('C', Blocks.CHEST)
        .pattern("III")
        .pattern("ICI")
        .pattern("III")
        .unlockedBy(getName(SpliceBlocks.COPPER_CHEST), has(SpliceBlocks.COPPER_CHEST))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_TORCH, 4)
        .define('N', nugget)
        .define('C', Ingredient.of(Items.COAL, Items.CHARCOAL))
        .define('S', Items.STICK)
        .pattern("N")
        .pattern("C")
        .pattern("S")
        .unlockedBy(getName(nugget), has(nugget))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_LANTERN.unaffected())
        .define('N', nugget)
        .define('T', SpliceItems.COPPER_TORCH)
        .pattern("NNN")
        .pattern("NTN")
        .pattern("NNN")
        .unlockedBy(getName(SpliceItems.COPPER_TORCH), has(SpliceItems.COPPER_TORCH))
        .save(output);
  }

  private void copperEquipment(RecipeOutput output) {
    final TagKey<Item> material = SpliceItemTags.COPPER_TOOL_MATERIALS;

    this.shovel(output, SpliceItems.COPPER_SHOVEL.get(), material);
    this.pickaxe(output, SpliceItems.COPPER_PICKAXE.get(), material);
    this.axe(output, SpliceItems.COPPER_AXE.get(), material);
    this.hoe(output, SpliceItems.COPPER_HOE.get(), material);
    this.sword(output, SpliceItems.COPPER_SWORD.get(), material);

    this.helmet(output, SpliceItems.COPPER_HELMET.get(), material);
    this.chestplate(output, SpliceItems.COPPER_CHESTPLATE.get(), material);
    this.leggings(output, SpliceItems.COPPER_LEGGINGS.get(), material);
    this.boots(output, SpliceItems.COPPER_BOOTS.get(), material);
  }

  // ===================================================================================================================
  // 4. Block Families
  // ===================================================================================================================

  private void copperCooking(RecipeOutput output) {
    final Item nugget = SpliceItems.COPPER_NUGGET.get();
    final ItemLike[] meltables = {
      SpliceItems.COPPER_SHOVEL, SpliceItems.COPPER_PICKAXE, SpliceItems.COPPER_AXE,
      SpliceItems.COPPER_HOE, SpliceItems.COPPER_SWORD, SpliceItems.COPPER_HELMET,
      SpliceItems.COPPER_CHESTPLATE, SpliceItems.COPPER_LEGGINGS, SpliceItems.COPPER_BOOTS,
      SpliceItems.COPPER_HORSE_ARMOR
    };

    this.recycleSmelting(
        output, Arrays.asList(meltables), nugget, XP_COOKING, TIME_SMELTING, TIME_BLASTING);
  }

  // ===================================================================================================================
  // Miscellaneous
  // ===================================================================================================================

  private void registerBlockFamilies(RecipeOutput output) {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateRecipe()) {
        continue;
      }

      final Block base = family.getBaseBlock();

      if (family.get(BlockFamily.Variant.BUTTON) instanceof Block button) {
        buttonBuilder(button, Ingredient.of(base))
            .unlockedBy(getName(base), has(base))
            .save(output);
      }

      if (family.get(BlockFamily.Variant.DOOR) instanceof Block door) {
        doorBuilder(door, Ingredient.of(base)).unlockedBy(getName(base), has(base)).save(output);
      }

      if (family.get(BlockFamily.Variant.FENCE) instanceof Block fence) {
        fenceBuilder(fence, Ingredient.of(base)).unlockedBy(getName(base), has(base)).save(output);
      }

      if (family.get(BlockFamily.Variant.FENCE_GATE) instanceof Block fenceGate) {
        fenceGateBuilder(fenceGate, Ingredient.of(base))
            .unlockedBy(getName(base), has(base))
            .save(output);
      }

      if (family.get(BlockFamily.Variant.SIGN) instanceof Block sign) {
        signBuilder(sign, Ingredient.of(base)).unlockedBy(getName(base), has(base)).save(output);
      }

      if (family.get(BlockFamily.Variant.PRESSURE_PLATE) instanceof Block pressurePlate) {
        pressurePlate(output, pressurePlate, base);
      }

      if (family.get(BlockFamily.Variant.TRAPDOOR) instanceof Block trapdoor) {
        trapdoorBuilder(trapdoor, Ingredient.of(base))
            .unlockedBy(getName(base), has(base))
            .save(output);
      }

      if (family.get(BlockFamily.Variant.WALL) instanceof Block wall) {
        wall(output, RecipeCategory.DECORATIONS, wall, base);
        this.stonecutter(output, RecipeCategory.DECORATIONS, wall, base, 1);
      }

      if (family.get(BlockFamily.Variant.SLAB) instanceof Block slab) {
        slab(output, RecipeCategory.BUILDING_BLOCKS, slab, base);
        this.stonecutter(output, RecipeCategory.BUILDING_BLOCKS, slab, base, 2);

        if (family.get(BlockFamily.Variant.CHISELED) instanceof Block chiseled) {
          chiseled(output, RecipeCategory.BUILDING_BLOCKS, chiseled, slab);
          this.stonecutter(output, RecipeCategory.BUILDING_BLOCKS, chiseled, base, 1);
        }
      }

      if (family.get(BlockFamily.Variant.STAIRS) instanceof Block stairs) {
        stairBuilder(stairs, Ingredient.of(base)).unlockedBy(getName(base), has(base)).save(output);
        this.stonecutter(output, RecipeCategory.BUILDING_BLOCKS, stairs, base, 1);
      }
    }
  }

  // ===================================================================================================================
  // Helpers
  // ===================================================================================================================

  private void registerMiscellaneous(RecipeOutput output) {
    this.shapeless(
        output,
        RecipeCategory.MISC,
        SpliceItems.FIELD_MASONED_BANNER_PATTERN,
        1,
        Items.PAPER,
        Blocks.BRICKS);
    this.shapeless(
        output,
        RecipeCategory.MISC,
        SpliceItems.BORDURE_INDENTED_BANNER_PATTERN,
        1,
        Items.PAPER,
        Blocks.VINE);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.SADDLE)
        .define('L', Items.LEATHER)
        .define('I', Items.IRON_INGOT)
        .pattern(" L ")
        .pattern("LIL")
        .unlockedBy(getName(Items.LEATHER), has(Items.LEATHER))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEAD, 2)
        .define('S', Items.STRING)
        .pattern("SS ")
        .pattern("SS ")
        .pattern("  S")
        .unlockedBy(getName(Items.STRING), has(Items.STRING))
        .save(output);
  }

  private void waxing(RecipeOutput output, Block from, Block to) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, to)
        .requires(from)
        .requires(Items.HONEYCOMB)
        .group(getItemName(to))
        .unlockedBy(getName(from), has(from))
        .save(output, this.getResourceLocation(getConversionRecipeName(to, Items.HONEYCOMB)));
  }

  private void nineBlockStorage(
      RecipeOutput output,
      RecipeCategory unpackCat,
      ItemLike unpacked,
      RecipeCategory packCat,
      ItemLike packed) {
    this.nineBlockStorage(
        output,
        unpackCat,
        unpacked,
        packCat,
        packed,
        getSimpleRecipeName(packed),
        null,
        getSimpleRecipeName(unpacked),
        null);
  }

  private void nineBlockStorage(
      RecipeOutput output,
      RecipeCategory unpackedCategory,
      ItemLike unpacked,
      RecipeCategory packedCategory,
      ItemLike packed,
      String packedName,
      String packedGroup) {

    this.nineBlockStorage(
        output,
        unpackedCategory,
        unpacked,
        packedCategory,
        packed,
        packedName,
        packedGroup,
        getSimpleRecipeName(unpacked),
        null);
  }

  private void nineBlockStorage(
      RecipeOutput output,
      RecipeCategory unpackedCategory,
      ItemLike unpacked,
      RecipeCategory packedCategory,
      ItemLike packed,
      String packedName,
      String packedGroup,
      String unpackedName,
      String unpackedGroup) {
    ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9)
        .requires(packed)
        .group(unpackedGroup)
        .unlockedBy(getName(packed), has(packed))
        .save(output, this.getResourceLocation(unpackedName));

    ShapedRecipeBuilder.shaped(packedCategory, packed)
        .define('U', unpacked)
        .pattern("UUU")
        .pattern("UUU")
        .pattern("UUU")
        .group(packedGroup)
        .unlockedBy(getName(unpacked), has(unpacked))
        .save(output, this.getResourceLocation(packedName));
  }

  private void twoByTwo(
      RecipeOutput output, RecipeCategory category, ItemLike result, ItemLike input) {
    ShapedRecipeBuilder.shaped(category, result)
        .define('#', input)
        .pattern("##")
        .pattern("##")
        .unlockedBy(getName(input), has(input))
        .save(output);
  }

  private void oneToOne(RecipeOutput output, ItemLike result, ItemLike ingredient, String group) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
        .requires(ingredient)
        .group(group)
        .unlockedBy(getName(ingredient), has(ingredient))
        .save(output, this.getResourceLocation(getConversionRecipeName(result, ingredient)));
  }

  private void shapeless(
      RecipeOutput output,
      RecipeCategory category,
      ItemLike result,
      int count,
      ItemLike... ingredients) {
    final ShapelessRecipeBuilder builder =
        ShapelessRecipeBuilder.shapeless(category, result, count);
    for (ItemLike i : ingredients) {
      builder.requires(i);
    }

    if (ingredients.length > 0) {
      builder.unlockedBy(getName(ingredients[0]), has(ingredients[0]));
    }

    builder.save(output);
  }

  private void stonecutter(
      RecipeOutput output, RecipeCategory category, ItemLike result, ItemLike material, int count) {
    SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, count)
        .unlockedBy(getName(material), has(material))
        .save(
            output,
            this.getResourceLocation(getConversionRecipeName(result, material) + "_stonecutting"));
  }

  private void recycleSmelting(
      RecipeOutput output,
      List<ItemLike> inputs,
      ItemLike result,
      float xp,
      int timeSmelt,
      int timeBlast) {
    final Ingredient ingredient = Ingredient.of(inputs.toArray(new ItemLike[0]));
    final String name = getSimpleRecipeName(result);

    SimpleCookingRecipeBuilder.smelting(ingredient, RecipeCategory.MISC, result, xp, timeSmelt)
        .unlockedBy("has_item", has(inputs.getFirst()))
        .save(output, this.getResourceLocation("smelting_" + name));

    SimpleCookingRecipeBuilder.blasting(ingredient, RecipeCategory.MISC, result, xp, timeBlast)
        .unlockedBy("has_item", has(inputs.getFirst()))
        .save(output, this.getResourceLocation("blasting_" + name));
  }

  private void shovel(RecipeOutput out, Item result, TagKey<Item> material) {
    this.tool(out, result, material, " M ", " S ", " S ");
  }

  private void pickaxe(RecipeOutput out, Item result, TagKey<Item> material) {
    this.tool(out, result, material, "MMM", " S ", " S ");
  }

  private void axe(RecipeOutput out, Item result, TagKey<Item> material) {
    this.tool(out, result, material, "MM ", "MS ", " S ");
  }

  private void hoe(RecipeOutput out, Item result, TagKey<Item> material) {
    this.tool(out, result, material, "MM ", " S ", " S ");
  }

  private void sword(RecipeOutput out, Item result, TagKey<Item> material) {
    this.tool(out, result, material, " M ", " M ", " S ");
  }

  private void tool(RecipeOutput out, Item result, TagKey<Item> material, String... pattern) {
    final ShapedRecipeBuilder builder =
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
            .define('M', material)
            .define('S', Items.STICK);
    for (String p : pattern) {
      builder.pattern(p);
    }

    builder.unlockedBy("has_material", has(material)).save(out);
  }

  private void helmet(RecipeOutput out, Item result, TagKey<Item> material) {
    this.armor(out, result, material, "MMM", "M M");
  }

  private void chestplate(RecipeOutput out, Item result, TagKey<Item> material) {
    this.armor(out, result, material, "M M", "MMM", "MMM");
  }

  private void leggings(RecipeOutput out, Item result, TagKey<Item> material) {
    this.armor(out, result, material, "MMM", "M M", "M M");
  }

  private void boots(RecipeOutput out, Item result, TagKey<Item> material) {
    this.armor(out, result, material, "M M", "M M");
  }

  private void armor(RecipeOutput out, Item result, TagKey<Item> material, String... pattern) {
    final ShapedRecipeBuilder builder =
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result).define('M', material);
    for (String p : pattern) {
      builder.pattern(p);
    }

    builder.unlockedBy("has_material", has(material)).save(out);
  }

  private ResourceLocation getResourceLocation(String path) {
    return ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, path);
  }
}
