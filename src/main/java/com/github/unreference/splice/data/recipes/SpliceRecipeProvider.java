package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class SpliceRecipeProvider extends RecipeProvider {
  public SpliceRecipeProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  private static void buildBannerPatternRecipes(RecipeOutput recipeOutput) {
    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.FIELD_MASONED_BANNER_PATTERN.get())
        .requires(Items.PAPER)
        .requires(Blocks.BRICKS)
        .unlockedBy(getHasName(Blocks.BRICKS), has(Blocks.BRICKS))
        .save(recipeOutput);

    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get())
        .requires(Items.PAPER)
        .requires(Blocks.VINE)
        .unlockedBy(getHasName(Blocks.VINE), has(Blocks.VINE))
        .save(recipeOutput);
  }

  private static void buildCopperRecipes(RecipeOutput recipeOutput) {
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

    // Chain
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_CHAIN.unaffected())
        .define('I', INGOT)
        .define('N', NUGGET)
        .pattern("N")
        .pattern("I")
        .pattern("N")
        .unlockedBy(getHasName(NUGGET), has(NUGGET))
        .unlockedBy(getHasName(INGOT), has(INGOT))
        .save(recipeOutput);
  }

  private static void waxableRecipe(RecipeOutput recipeOutput, Block from, Block to) {
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

  private static void buildWaxableRecipes(RecipeOutput output) {
    SpliceBlocks.getCopperFamily().stream()
        .flatMap(f -> f.waxedMapping().entrySet().stream())
        .forEach(entry -> waxableRecipe(output, entry.getKey().get(), entry.getValue().get()));
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {
    buildBannerPatternRecipes(recipeOutput);
    buildCopperRecipes(recipeOutput);
    buildWaxableRecipes(recipeOutput);
  }
}
