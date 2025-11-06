package com.github.unreference.splice.data.recipes;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
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

  private static void bannerPatterns(RecipeOutput recipeOutput) {
    // Field masoned
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SpliceItems.FIELD_MASONED_BANNER_PATTERN)
        .requires(Items.PAPER)
        .requires(Blocks.BRICKS)
        .unlockedBy(getHasName(Blocks.BRICKS), has(Blocks.BRICKS))
        .save(recipeOutput);

    // Bordure indented
    ShapelessRecipeBuilder.shapeless(
            RecipeCategory.MISC, SpliceItems.BORDURE_INDENTED_BANNER_PATTERN)
        .requires(Items.PAPER)
        .requires(Blocks.VINE)
        .unlockedBy(getHasName(Blocks.VINE), has(Blocks.VINE))
        .save(recipeOutput);
  }

  private static void copperBlocks(RecipeOutput recipeOutput) {
    final Item ingot = Items.COPPER_INGOT;
    final Item nugget = SpliceItems.COPPER_NUGGET.get();

    // Trapdoor
    twoByTwoPacker(recipeOutput, RecipeCategory.REDSTONE, Blocks.COPPER_TRAPDOOR, ingot);

    // Ingot <-> nugget
    nineBlockStorageRecipesWithCustomPacking(
        recipeOutput,
        RecipeCategory.MISC,
        nugget,
        RecipeCategory.MISC,
        ingot,
        getConversionRecipeName(ingot, nugget),
        getItemName(ingot));

    // Bars
    ShapedRecipeBuilder.shaped(
            RecipeCategory.DECORATIONS, SpliceBlocks.COPPER_BARS.unaffected(), 16)
        .define('I', ingot)
        .pattern("III")
        .pattern("III")
        .unlockedBy(getHasName(ingot), has(ingot))
        .save(recipeOutput);

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
        .unlockedBy(getHasName(SpliceBlocks.COPPER_CHEST), has(SpliceBlocks.COPPER_CHEST))
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

  private static void copperEquipment(RecipeOutput recipeOutput) {
    final Item ingot = Items.COPPER_INGOT;
    final Item stick = Items.STICK;
    final TagKey<Item> copperMaterials = SpliceItemTags.COPPER_TOOL_MATERIALS;

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_SHOVEL)
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("C")
        .pattern("S")
        .pattern("S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_PICKAXE)
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("CCC")
        .pattern(" S ")
        .pattern(" S ")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_AXE)
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("CC")
        .pattern("CS")
        .pattern(" S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SpliceItems.COPPER_HOE)
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("CC")
        .pattern(" S")
        .pattern(" S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_SWORD)
        .define('C', copperMaterials)
        .define('S', stick)
        .pattern("C")
        .pattern("C")
        .pattern("S")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_HELMET)
        .define('C', copperMaterials)
        .pattern("CCC")
        .pattern("C C")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_CHESTPLATE)
        .define('C', copperMaterials)
        .pattern("C C")
        .pattern("CCC")
        .pattern("CCC")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_LEGGINGS)
        .define('C', copperMaterials)
        .pattern("CCC")
        .pattern("C C")
        .pattern("C C")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SpliceItems.COPPER_BOOTS)
        .define('C', copperMaterials)
        .pattern("C C")
        .pattern("C C")
        .unlockedBy(getHasName(ingot), has(copperMaterials))
        .save(recipeOutput);
  }

  private static void copperCooking(RecipeOutput recipeOutput) {
    final Item nugget = SpliceItems.COPPER_NUGGET.get();

    final ItemLike[] meltable = {
      SpliceItems.COPPER_SHOVEL,
      SpliceItems.COPPER_PICKAXE,
      SpliceItems.COPPER_AXE,
      SpliceItems.COPPER_HOE,
      SpliceItems.COPPER_SWORD,
      SpliceItems.COPPER_HELMET,
      SpliceItems.COPPER_CHESTPLATE,
      SpliceItems.COPPER_LEGGINGS,
      SpliceItems.COPPER_BOOTS,
      SpliceItems.COPPER_HORSE_ARMOR,
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

  private static void waxable(RecipeOutput output) {
    SpliceBlocks.COPPER_FAMILY.stream()
        .flatMap(f -> f.waxedMapping().entrySet().stream())
        .forEach(entry -> waxable(output, entry.getKey().get(), entry.getValue().get()));
  }

  private static void copper(RecipeOutput recipeOutput) {
    copperBlocks(recipeOutput);
    copperEquipment(recipeOutput);
    copperCooking(recipeOutput);
    waxable(recipeOutput);
  }

  private static void saddle(@NotNull RecipeOutput recipeOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.SADDLE)
        .define('L', Items.LEATHER)
        .define('I', Items.IRON_INGOT)
        .pattern(" L ")
        .pattern("LIL")
        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
        .save(recipeOutput);
  }

  private static void lead(@NotNull RecipeOutput recipeOutput) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEAD, 2)
        .define('S', Items.STRING)
        .pattern("SS ")
        .pattern("SS ")
        .pattern("  S")
        .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
        .save(recipeOutput);
  }

  private static void blockFamily(@NotNull RecipeOutput recipeOutput) {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateRecipe()) {
        continue;
      }

      final Block base = family.getBaseBlock();
      final Block button = family.get(BlockFamily.Variant.BUTTON);

      if (button != null) {
        button(recipeOutput, button, base);
      }

      final Block door = family.get(BlockFamily.Variant.DOOR);
      if (door != null) {
        door(recipeOutput, door, base);
      }

      final Block fence = family.get(BlockFamily.Variant.FENCE);
      if (fence != null) {
        fence(recipeOutput, fence, base);
      }

      final Block fenceGate = family.get(BlockFamily.Variant.FENCE_GATE);
      if (fenceGate != null) {
        fenceGate(recipeOutput, fenceGate, base);
      }

      final Block sign = family.get(BlockFamily.Variant.SIGN);
      final Block wallSign = family.get(BlockFamily.Variant.WALL_SIGN);
      if (sign != null && wallSign != null) {
        sign(recipeOutput, sign, base);
      }

      final Block slab = family.get(BlockFamily.Variant.SLAB);
      if (slab != null) {
        slab(recipeOutput, slab, base);
        stonecutter(recipeOutput, slab, base, 2);

        final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
        if (chiseled != null) {
          chiseled(recipeOutput, chiseled, slab);
          stonecutter(recipeOutput, chiseled, base);
        }
      }

      final Block stairs = family.get(BlockFamily.Variant.STAIRS);
      if (stairs != null) {
        stairs(recipeOutput, stairs, base);
        stonecutter(recipeOutput, stairs, base);
      }

      final Block pressurePlate = family.get(BlockFamily.Variant.PRESSURE_PLATE);
      if (pressurePlate != null) {
        pressurePlate(recipeOutput, pressurePlate, base);
      }

      final Block trapdoor = family.get(BlockFamily.Variant.TRAPDOOR);
      if (trapdoor != null) {
        trapdoor(recipeOutput, trapdoor, base);
      }

      final Block wall = family.get(BlockFamily.Variant.WALL);
      if (wall != null) {
        wall(recipeOutput, wall, base);
        stonecutter(recipeOutput, wall, base);
      }
    }
  }

  private static void trapdoor(@NotNull RecipeOutput recipeOutput, Block trapdoor, Block material) {
    trapdoorBuilder(trapdoor, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void door(@NotNull RecipeOutput recipeOutput, Block door, Block material) {
    doorBuilder(door, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void sign(@NotNull RecipeOutput recipeOutput, Block sign, Block material) {
    signBuilder(sign, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void fence(@NotNull RecipeOutput recipeOutput, Block fence, Block material) {
    fenceBuilder(fence, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void fenceGate(
      @NotNull RecipeOutput recipeOutput, Block fenceGate, Block material) {
    fenceGateBuilder(fenceGate, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void button(
      @NotNull RecipeOutput recipeOutput, ItemLike button, ItemLike material) {
    buttonBuilder(button, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void chiseled(RecipeOutput recipeOutput, ItemLike chiseled, ItemLike material) {
    chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, chiseled, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void slab(RecipeOutput recipeOutput, ItemLike slab, ItemLike material) {
    slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void stairs(RecipeOutput recipeOutput, ItemLike stairs, ItemLike material) {
    stairBuilder(stairs, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void stonecutter(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
    SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result)
        .unlockedBy(getHasName(material), has(material))
        .save(
            recipeOutput,
            getResourceLocation(getConversionRecipeName(result, material) + "_stonecutting"));
  }

  private static void stonecutter(
      @NotNull RecipeOutput recipeOutput, Block result, Block material, int amount) {
    SingleItemRecipeBuilder.stonecutting(
            Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, amount)
        .unlockedBy(getHasName(material), has(material))
        .save(
            recipeOutput,
            getResourceLocation(getConversionRecipeName(result, material) + "_stonecutting"));
  }

  private static void wall(RecipeOutput recipeOutput, ItemLike wall, ItemLike material) {
    wallBuilder(RecipeCategory.DECORATIONS, wall, Ingredient.of(material))
        .unlockedBy(getHasName(material), has(material))
        .save(recipeOutput);
  }

  private static void resin(@NotNull RecipeOutput recipeOutput) {
    final Item block = SpliceItems.RESIN_BLOCK.get();
    final Item clump = SpliceItems.RESIN_CLUMP.get();
    final Item brick = SpliceItems.RESIN_BRICK.get();
    final Block bricks = SpliceBlocks.RESIN_BRICKS.get();

    // Block <-> clump
    nineBlockStorageRecipes(
        recipeOutput, RecipeCategory.MISC, clump, RecipeCategory.BUILDING_BLOCKS, block);

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

  protected static void nineBlockStorageRecipesWithCustomPacking(
      @NotNull RecipeOutput recipeOutput,
      @NotNull RecipeCategory unpackedCategory,
      ItemLike unpacked,
      @NotNull RecipeCategory packedCategory,
      ItemLike packed,
      String packedName,
      @NotNull String packedGroup) {
    nineBlockStorageRecipes(
        recipeOutput,
        unpackedCategory,
        unpacked,
        packedCategory,
        packed,
        packedName,
        packedGroup,
        getSimpleRecipeName(unpacked),
        null);
  }

  protected static void nineBlockStorageRecipes(
      @NotNull RecipeOutput recipeOutput,
      @NotNull RecipeCategory unpackedCategory,
      ItemLike unpacked,
      @NotNull RecipeCategory packedCategory,
      ItemLike packed) {
    nineBlockStorageRecipes(
        recipeOutput,
        unpackedCategory,
        unpacked,
        packedCategory,
        packed,
        getSimpleRecipeName(packed),
        null,
        getSimpleRecipeName(unpacked),
        null);
  }

  protected static void nineBlockStorageRecipes(
      @NotNull RecipeOutput recipeOutput,
      @NotNull RecipeCategory unpackedCategory,
      ItemLike unpacked,
      @NotNull RecipeCategory packedCategory,
      ItemLike packed,
      String packedName,
      @Nullable String packedGroup,
      String unpackedName,
      @Nullable String unpackedGroup) {
    ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9)
        .requires(packed)
        .group(unpackedGroup)
        .unlockedBy(getHasName(packed), has(packed))
        .save(recipeOutput, getResourceLocation(unpackedName));
    ShapedRecipeBuilder.shaped(packedCategory, packed)
        .define('U', unpacked)
        .pattern("UUU")
        .pattern("UUU")
        .pattern("UUU")
        .group(packedGroup)
        .unlockedBy(getHasName(unpacked), has(unpacked))
        .save(recipeOutput, getResourceLocation(packedName));
  }

  private static void paleGarden(@NotNull RecipeOutput recipeOutput) {
    planksFromLog(recipeOutput, SpliceBlocks.PALE_OAK_PLANKS, SpliceItemTags.PALE_OAK_LOGS, 4);
    woodFromLogs(recipeOutput, SpliceBlocks.PALE_OAK_WOOD, SpliceBlocks.PALE_OAK_LOG);
    woodFromLogs(
        recipeOutput, SpliceBlocks.STRIPPED_PALE_OAK_WOOD, SpliceBlocks.STRIPPED_PALE_OAK_LOG);
    hangingSign(
        recipeOutput, SpliceBlocks.PALE_OAK_HANGING_SIGN, SpliceBlocks.STRIPPED_PALE_OAK_LOG);
    carpet(recipeOutput, SpliceBlocks.PALE_MOSS_CARPET, SpliceBlocks.PALE_MOSS_BLOCK);
  }

  @Override
  protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
    blockFamily(recipeOutput);
    bannerPatterns(recipeOutput);
    saddle(recipeOutput);
    lead(recipeOutput);
    copper(recipeOutput);
    resin(recipeOutput);
    paleGarden(recipeOutput);
  }
}
