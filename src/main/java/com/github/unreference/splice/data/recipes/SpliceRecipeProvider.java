package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class SpliceRecipeProvider extends RecipeProvider {
  public SpliceRecipeProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
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
    final var INGOT = Items.COPPER_INGOT;
    final var NUGGET = SpliceItems.COPPER_NUGGET.get();

    // Trapdoor
    twoByTwoPacker(recipeOutput, RecipeCategory.REDSTONE, Blocks.COPPER_TRAPDOOR, INGOT);

    // Bars
    ShapedRecipeBuilder.shaped(
            RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_BARS.unaffected(), 16)
        .define('I', INGOT)
        .pattern("III")
        .pattern("III")
        .unlockedBy(getHasName(INGOT), has(INGOT))
        .save(recipeOutput);

    // Ingot
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, INGOT)
        .define('I', NUGGET)
        .pattern("III")
        .pattern("III")
        .pattern("III")
        .unlockedBy(getHasName(NUGGET), has(NUGGET))
        .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                SpliceMain.MOD_ID, getConversionRecipeName(INGOT, NUGGET)));

    // Nugget
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NUGGET, 9)
        .requires(INGOT)
        .unlockedBy(getHasName(INGOT), has(INGOT))
        .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                SpliceMain.MOD_ID, getConversionRecipeName(NUGGET, INGOT)));

    // Chain
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_CHAIN.unaffected())
        .define('N', NUGGET)
        .define('I', INGOT)
        .pattern("N")
        .pattern("I")
        .pattern("N")
        .unlockedBy(getHasName(NUGGET), has(NUGGET))
        .unlockedBy(getHasName(INGOT), has(INGOT))
        .save(recipeOutput);
  }

  private static void waxable(RecipeOutput recipeOutput, Block from, Block to) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, to)
        .requires(from)
        .requires(Items.HONEYCOMB)
        .group(getItemName(to))
        .unlockedBy(getHasName(from), has(from))
        .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                SpliceMain.MOD_ID, getConversionRecipeName(to, Items.HONEYCOMB)));
  }

  private static void buildCopperEquipmentRecipes(RecipeOutput recipeOutput) {
    final var INGOT = Items.COPPER_INGOT;
    final var TOOL_MATERIALS = SpliceItemTags.COPPER_TOOL_MATERIALS;

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_SHOVEL.get())
        .define('C', TOOL_MATERIALS)
        .define('S', Items.STICK)
        .pattern("C")
        .pattern("S")
        .pattern("S")
        .unlockedBy(getHasName(INGOT), has(TOOL_MATERIALS))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_PICKAXE.get())
        .define('C', TOOL_MATERIALS)
        .define('S', Items.STICK)
        .pattern("CCC")
        .pattern(" S ")
        .pattern(" S ")
        .unlockedBy(getHasName(INGOT), has(TOOL_MATERIALS))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_AXE.get())
        .define('C', TOOL_MATERIALS)
        .define('S', Items.STICK)
        .pattern("CC")
        .pattern("CS")
        .pattern(" S")
        .unlockedBy(getHasName(INGOT), has(TOOL_MATERIALS))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_SWORD.get())
        .define('C', TOOL_MATERIALS)
        .define('S', Items.STICK)
        .pattern("C")
        .pattern("C")
        .pattern("S")
        .unlockedBy(getHasName(INGOT), has(TOOL_MATERIALS))
        .save(recipeOutput);
  }

  private static void buildCopperFurnaceRecipes(RecipeOutput recipeOutput) {
    var SHOVEL = SpliceItems.COPPER_SHOVEL.get();
    var PICKAXE = SpliceItems.COPPER_PICKAXE.get();
    var AXE = SpliceItems.COPPER_AXE.get();
    var SWORD = SpliceItems.COPPER_SWORD.get();
    var NUGGET = SpliceItems.COPPER_NUGGET.get();

    SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(SHOVEL, PICKAXE, AXE, SWORD), RecipeCategory.MISC, NUGGET, 0.1f, 200)
        .unlockedBy(getHasName(SHOVEL), has(SHOVEL))
        .unlockedBy(getHasName(PICKAXE), has(PICKAXE))
        .unlockedBy(getHasName(AXE), has(AXE))
        .unlockedBy(getHasName(SWORD), has(SWORD))
        .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                SpliceMain.MOD_ID, getSmeltingRecipeName(NUGGET)));

    SimpleCookingRecipeBuilder.blasting(
            Ingredient.of(SHOVEL, PICKAXE, AXE, SWORD), RecipeCategory.MISC, NUGGET, 0.1f, 100)
        .unlockedBy(getHasName(SHOVEL), has(SHOVEL))
        .unlockedBy(getHasName(PICKAXE), has(PICKAXE))
        .unlockedBy(getHasName(AXE), has(AXE))
        .unlockedBy(getHasName(SWORD), has(SWORD))
        .save(
            recipeOutput,
            ResourceLocation.fromNamespaceAndPath(
                SpliceMain.MOD_ID, getBlastingRecipeName(NUGGET)));
  }

  private static void buildWaxableRecipes(RecipeOutput output) {
    SpliceBlocks.getCopperFamily().stream()
        .flatMap(f -> f.waxedMapping().entrySet().stream())
        .forEach(entry -> waxable(output, entry.getKey().get(), entry.getValue().get()));
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {
    buildBannerPatternRecipes(recipeOutput);
    buildCopperBlockRecipes(recipeOutput);
    buildCopperEquipmentRecipes(recipeOutput);
    buildCopperFurnaceRecipes(recipeOutput);
    buildWaxableRecipes(recipeOutput);
  }
}
