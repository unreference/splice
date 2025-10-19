package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.world.item.SpliceItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public final class SpliceRecipeProvider extends RecipeProvider {
  public SpliceRecipeProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  private static void buildBanners(RecipeOutput recipeOutput) {
    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.FIELD_MASONED_BANNER_PATTERN.get())
        .requires(Items.PAPER)
        .requires(Blocks.BRICKS)
        .unlockedBy("has_bricks", has(Blocks.BRICKS))
        .save(recipeOutput);

    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get())
        .requires(Items.PAPER)
        .requires(Blocks.VINE)
        .unlockedBy("has_vine", has(Blocks.VINE))
        .save(recipeOutput);
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {
    buildBanners(recipeOutput);
  }
}
