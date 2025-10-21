package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.ibm.icu.impl.Pair;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceRecipeProvider extends RecipeProvider {
  private static final List<
          Pair<? extends DeferredBlock<? extends Block>, ? extends DeferredBlock<? extends Block>>>
      WAXABLES =
          List.of(
              Pair.of(SpliceBlocks.COPPER_BARS.unaffected(), SpliceBlocks.COPPER_BARS.waxed()),
              Pair.of(SpliceBlocks.COPPER_BARS.exposed(), SpliceBlocks.COPPER_BARS.waxedExposed()),
              Pair.of(
                  SpliceBlocks.COPPER_BARS.weathered(), SpliceBlocks.COPPER_BARS.waxedWeathered()),
              Pair.of(
                  SpliceBlocks.COPPER_BARS.oxidized(), SpliceBlocks.COPPER_BARS.waxedOxidized()));

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
    twoByTwoPacker(
        recipeOutput, RecipeCategory.REDSTONE, Blocks.COPPER_TRAPDOOR, Items.COPPER_INGOT);

    ShapedRecipeBuilder.shaped(
            RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_BARS.unaffected(), 16)
        .define('#', Items.COPPER_INGOT)
        .pattern("###")
        .pattern("###")
        .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
        .save(recipeOutput);
  }

  private static void buildWaxableRecipes(RecipeOutput recipeOutput) {
    for (var pair : WAXABLES) {
      Block from = pair.first.get();
      Block to = pair.second.get();

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
  }

  public static List<
          Pair<? extends DeferredBlock<? extends Block>, ? extends DeferredBlock<? extends Block>>>
      getWaxables() {
    return WAXABLES;
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {
    buildBannerPatternRecipes(recipeOutput);
    buildCopperRecipes(recipeOutput);
    buildWaxableRecipes(recipeOutput);
  }
}
