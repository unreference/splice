package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
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
  private static final float COOKING_XP = 0.1f;
  private static final int TIME_SMELTING = 200;
  private static final int TIME_BLASTING = 100;

  public SpliceRecipeProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  private static ResourceLocation getResourceLocation(String path) {
    return ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, path);
  }

  private static void buildBannerPatternRecipes(RecipeOutput recipeOutput) {
    // Field masoned
    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.FIELD_MASONED_BANNER_PATTERN.get())
        .requires(Items.PAPER)
        .requires(Blocks.BRICKS)
        .unlockedBy(getHasName(Blocks.BRICKS), has(Blocks.BRICKS))
        .save(recipeOutput);

    // Bordure indented
    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get())
        .requires(Items.PAPER)
        .requires(Blocks.VINE)
        .unlockedBy(getHasName(Blocks.VINE), has(Blocks.VINE))
        .save(recipeOutput);
  }

  private static void buildCopperBlockRecipes(RecipeOutput recipeOutput) {
    final Item ingot = Items.COPPER_INGOT;
    final Item nugget = SpliceItems.COPPER_NUGGET.get();

    // Trapdoor
    twoByTwoPacker(recipeOutput, RecipeCategory.REDSTONE, Blocks.COPPER_TRAPDOOR, ingot);

    // Bars
    ShapedRecipeBuilder.shaped(
            RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_BARS.unaffected(), 16)
        .define('I', ingot)
        .pattern("III")
        .pattern("III")
        .unlockedBy(getHasName(ingot), has(ingot))
        .save(recipeOutput);

    // Ingot
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
        .define('I', nugget)
        .pattern("III")
        .pattern("III")
        .pattern("III")
        .unlockedBy(getHasName(nugget), has(nugget))
        .save(recipeOutput, getResourceLocation(getConversionRecipeName(ingot, nugget)));

    // Nugget
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
        .requires(ingot)
        .unlockedBy(getHasName(ingot), has(ingot))
        .save(recipeOutput, getResourceLocation(getConversionRecipeName(nugget, ingot)));

    // Chain
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_CHAIN.unaffected())
        .define('N', nugget)
        .define('I', ingot)
        .pattern("N")
        .pattern("I")
        .pattern("N")
        .unlockedBy(getHasName(nugget), has(nugget))
        .unlockedBy(getHasName(ingot), has(ingot))
        .save(recipeOutput);

    // Chest
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_CHEST)
        .define('I', ingot)
        .define('C', Blocks.CHEST)
        .pattern("III")
        .pattern("ICI")
        .pattern("III")
        .unlockedBy(
            getHasName(SpliceBlocks.COPPER_CHEST.get()), has(SpliceBlocks.COPPER_CHEST.get()))
        .save(recipeOutput);

    // Torch
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_TORCH, 4)
        .define('N', nugget)
        .define('C', Ingredient.of(Items.COAL, Items.CHARCOAL))
        .define('S', Items.STICK)
        .pattern("N")
        .pattern("C")
        .pattern("S")
        .unlockedBy(getHasName(nugget), has(nugget))
        .save(recipeOutput);

    // Lantern
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_LANTERN.unaffected())
        .define('N', nugget)
        .define('T', SpliceItems.COPPER_TORCH)
        .pattern("NNN")
        .pattern("NTN")
        .pattern("NNN")
        .unlockedBy(getHasName(SpliceItems.COPPER_TORCH), has(SpliceItems.COPPER_TORCH))
        .save(recipeOutput);
  }

  private static void waxable(RecipeOutput recipeOutput, Block from, Block to) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, to)
        .requires(from)
        .requires(Items.HONEYCOMB)
        .group(getItemName(to))
        .unlockedBy(getHasName(from), has(from))
        .save(recipeOutput, getResourceLocation(getConversionRecipeName(to, Items.HONEYCOMB)));
  }

  private static void buildCopperEquipmentRecipes(RecipeOutput recipeOutput) {
    final Item ingot = Items.COPPER_INGOT;
    final Item stick = Items.STICK;
    final TagKey<Item> copperMaterials = SpliceItemTags.COPPER_TOOL_MATERIALS;

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_SHOVEL.get())
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("C")
        .pattern("S")
        .pattern("S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_PICKAXE.get())
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("CCC")
        .pattern(" S ")
        .pattern(" S ")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_AXE.get())
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("CC")
        .pattern("CS")
        .pattern(" S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_HOE.get())
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("CC")
        .pattern(" S")
        .pattern(" S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_SWORD.get())
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("C")
        .pattern("C")
        .pattern("S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_HELMET.get())
        .define('C', copperMaterials)
        .pattern("CCC")
        .pattern("C C")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_CHESTPLATE.get())
        .define('C', copperMaterials)
        .pattern("C C")
        .pattern("CCC")
        .pattern("CCC")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_LEGGINGS.get())
        .define('C', copperMaterials)
        .pattern("CCC")
        .pattern("C C")
        .pattern("C C")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_BOOTS.get())
        .define('C', copperMaterials)
        .pattern("C C")
        .pattern("C C")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);
  }

  private static void buildCopperFurnaceRecipes(RecipeOutput recipeOutput) {
    final Item nugget = SpliceItems.COPPER_NUGGET.get();

    final ItemLike[] meltable = {
      SpliceItems.COPPER_SHOVEL.get(),
      SpliceItems.COPPER_PICKAXE.get(),
      SpliceItems.COPPER_AXE.get(),
      SpliceItems.COPPER_HOE,
      SpliceItems.COPPER_SWORD.get(),
      SpliceItems.COPPER_HELMET.get(),
      SpliceItems.COPPER_CHESTPLATE.get(),
      SpliceItems.COPPER_LEGGINGS.get(),
      SpliceItems.COPPER_BOOTS.get(),
      SpliceItems.COPPER_HORSE_ARMOR.get(),
    };

    final Ingredient inputs = Ingredient.of(meltable);
    final SimpleCookingRecipeBuilder smelt =
        SimpleCookingRecipeBuilder.smelting(
            inputs, RecipeCategory.MISC, nugget, COOKING_XP, TIME_SMELTING);

    for (ItemLike i : meltable) {
      smelt.unlockedBy(getHasName(i), has(i));
    }

    smelt.save(recipeOutput, getResourceLocation(getSmeltingRecipeName(nugget)));

    final SimpleCookingRecipeBuilder blasting =
        SimpleCookingRecipeBuilder.blasting(
            inputs, RecipeCategory.MISC, nugget, COOKING_XP, TIME_BLASTING);

    for (ItemLike i : meltable) {
      blasting.unlockedBy(getHasName(i), has(i));
    }

    blasting.save(recipeOutput, getResourceLocation(getBlastingRecipeName(nugget)));
  }

  private static void buildWaxableRecipes(RecipeOutput output) {
    SpliceBlocks.getCopperFamily().stream()
        .flatMap(f -> f.waxedMapping().entrySet().stream())
        .forEach(entry -> waxable(output, entry.getKey().get(), entry.getValue().get()));
  }

  private static void buildCopperRecipes(RecipeOutput recipeOutput) {
    buildCopperBlockRecipes(recipeOutput);
    buildCopperEquipmentRecipes(recipeOutput);
    buildCopperFurnaceRecipes(recipeOutput);
    buildWaxableRecipes(recipeOutput);
  }

  private static void buildSaddleRecipe(@NotNull RecipeOutput recipeOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.SADDLE)
        .define('L', Items.LEATHER)
        .define('I', Items.IRON_INGOT)
        .pattern(" L ")
        .pattern("LIL")
        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
        .save(recipeOutput);
  }

  private static void buildLeadRecipe(@NotNull RecipeOutput recipeOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEAD, 2)
        .define('S', Items.STRING)
        .pattern("SS ")
        .pattern("SS ")
        .pattern("  S")
        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
        .save(recipeOutput);
  }

  private static void buildBlockFamilyRecipes(@NotNull RecipeOutput recipeOutput) {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateRecipe()) {
        continue;
      }

      final Block base = family.getBaseBlock();

      final Block slab = family.get(BlockFamily.Variant.SLAB);
      buildSlabRecipe(recipeOutput, slab, base);
      buildStonecutterRecipe(recipeOutput, slab, base, 2);

      final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
      buildChiseledRecipe(recipeOutput, chiseled, slab);
      buildStonecutterRecipe(recipeOutput, chiseled, base);

      final Block stairs = family.get(BlockFamily.Variant.STAIRS);
      buildStairsRecipe(recipeOutput, stairs, base);
      buildStonecutterRecipe(recipeOutput, stairs, base);

      final Block wall = family.get(BlockFamily.Variant.WALL);
      buildWallRecipe(recipeOutput, wall, base);
    }
  }

  private static void buildChiseledRecipe(
      RecipeOutput recipeOutput, ItemLike chiseled, ItemLike material) {
    chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, chiseled, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void buildSlabRecipe(RecipeOutput recipeOutput, ItemLike slab, ItemLike material) {
    slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void buildStairsRecipe(
      RecipeOutput recipeOutput, ItemLike stairs, ItemLike material) {
    stairBuilder(stairs, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void buildStonecutterRecipe(
      RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
    SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result)
        .unlockedBy(getHasName(material), has(material))
        .save(
            recipeOutput,
            getResourceLocation(getConversionRecipeName(result, material) + "_stonecutting"));
  }

  private static void buildStonecutterRecipe(
      @NotNull RecipeOutput recipeOutput, Block result, Block material, int amount) {
    SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, amount)
        .unlockedBy(getHasName(material), has(material))
        .save(
            recipeOutput,
            getResourceLocation(getConversionRecipeName(result, material) + "_stonecutting"));
  }

  private static void buildWallRecipe(RecipeOutput recipeOutput, ItemLike wall, ItemLike material) {
    wallBuilder(RecipeCategory.DECORATIONS, wall, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void buildResinRecipes(@NotNull RecipeOutput recipeOutput) {
    final Item block = SpliceItems.RESIN_BLOCK.get();
    final Item clump = SpliceItems.RESIN_CLUMP.get();
    final Item brick = SpliceItems.RESIN_BRICK.get();
    final Block bricks = SpliceBlocks.RESIN_BRICKS.get();

    // Block
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
        .define('R', clump)
        .pattern("RRR")
        .pattern("RRR")
        .pattern("RRR")
        .unlockedBy(getHasName(block), has(block))
        .save(recipeOutput, getResourceLocation(getConversionRecipeName(block, clump)));

    // Clump
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, clump, 9)
        .requires(block)
        .unlockedBy(getHasName(block), has(block))
        .save(recipeOutput, getResourceLocation(getConversionRecipeName(clump, block)));

    // Bricks
    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bricks)
        .define('R', brick)
        .pattern("RR")
        .pattern("RR")
        .unlockedBy(getHasName(brick), has(brick))
        .save(recipeOutput);

    // Smelting
    SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(clump), RecipeCategory.MISC, brick, COOKING_XP, TIME_SMELTING)
        .unlockedBy(getHasName(clump), has(clump))
        .save(recipeOutput);
  }

  @Override
  protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
    buildBlockFamilyRecipes(recipeOutput);
    buildBannerPatternRecipes(recipeOutput);
    buildCopperRecipes(recipeOutput);
    buildSaddleRecipe(recipeOutput);
    buildLeadRecipe(recipeOutput);
    buildResinRecipes(recipeOutput);
  }
}
