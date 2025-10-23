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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public final class SpliceRecipeProvider extends RecipeProvider {
  private static final float XP_NUGGET = 0.1f;
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
            inputs, RecipeCategory.MISC, nugget, XP_NUGGET, TIME_SMELTING);

    for (ItemLike i : meltable) {
      smelt.unlockedBy(getHasName(i), has(i));
    }

    smelt.save(recipeOutput, getResourceLocation(getSmeltingRecipeName(nugget)));

    final SimpleCookingRecipeBuilder blasting =
        SimpleCookingRecipeBuilder.blasting(
            inputs, RecipeCategory.MISC, nugget, XP_NUGGET, TIME_BLASTING);

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

  @Override
  protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
    buildBannerPatternRecipes(recipeOutput);
    buildCopperRecipes(recipeOutput);
  }
}
