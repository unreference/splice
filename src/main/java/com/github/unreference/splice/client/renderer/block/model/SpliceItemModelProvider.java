package com.github.unreference.splice.client.renderer.block.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceModelUtils;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.*;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public final class SpliceItemModelProvider extends ItemModelProvider {

  private static final List<ResourceKey<TrimMaterial>> TRIM_ORDER =
      List.of(
          TrimMaterials.QUARTZ,
          TrimMaterials.IRON,
          TrimMaterials.NETHERITE,
          TrimMaterials.REDSTONE,
          TrimMaterials.COPPER,
          TrimMaterials.GOLD,
          TrimMaterials.EMERALD,
          TrimMaterials.DIAMOND,
          TrimMaterials.LAPIS,
          TrimMaterials.AMETHYST);

  public SpliceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    this.registerBlockFamilies();
    this.registerCopper();
    this.registerResin();
    this.registerPaleGarden();
    this.registerMiscellaneous();
  }

  // ===================================================================================================================
  // Pale Garden
  // ===================================================================================================================

  private void registerPaleGarden() {
    this.simpleBlockItem(SpliceBlocks.PALE_OAK_LOG.get());
    this.simpleBlockItem(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get());
    this.simpleBlockItem(SpliceBlocks.PALE_OAK_WOOD.get());
    this.simpleBlockItem(SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get());
    this.simpleBlockItem(SpliceBlocks.PALE_OAK_LEAVES.get());
    this.simpleBlockItem(SpliceBlocks.PALE_MOSS_CARPET.get());
    this.simpleBlockItem(SpliceBlocks.PALE_MOSS_BLOCK.get());
    this.simpleBlockItem(SpliceBlocks.CREAKING_HEART.get());

    this.generated(SpliceItems.PALE_OAK_HANGING_SIGN.get());
    this.generated(
        SpliceBlocks.PALE_OAK_SAPLING.get(),
        SpliceModelUtils.getLocation(SpliceBlocks.PALE_OAK_SAPLING.get()));
    this.generated(
        SpliceBlocks.PALE_HANGING_MOSS.get(),
        SpliceModelUtils.getLocation(SpliceBlocks.PALE_HANGING_MOSS.get()));

    this.eyeblossom(SpliceBlocks.CLOSED_EYEBLOSSOM.get(), false);
    this.eyeblossom(SpliceBlocks.OPEN_EYEBLOSSOM.get(), true);
  }

  private void eyeblossom(Block block, boolean isEmissive) {
    final String name = SpliceModelUtils.getName(block);
    final ResourceLocation base = SpliceModelUtils.getLocation(block);

    final ItemModelBuilder builder =
        withExistingParent(name, this.mcLoc("item/generated")).texture("layer0", base);

    if (isEmissive) {
      builder.texture("layer1", SpliceModelUtils.setSuffix(base, "_emissive"));
    }
  }

  // ===================================================================================================================
  // Resin
  // ===================================================================================================================

  private void registerResin() {
    this.simpleBlockItem(SpliceBlocks.RESIN_BLOCK.get());
    this.basicItem(SpliceItems.RESIN_CLUMP.get());
    this.basicItem(SpliceItems.RESIN_BRICK.get());
  }

  // ===================================================================================================================
  // Miscellaneous
  // ===================================================================================================================

  private void registerMiscellaneous() {
    this.basicItem(SpliceItems.FIELD_MASONED_BANNER_PATTERN.get());
    this.basicItem(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get());
    this.basicItem(SpliceItems.MUSIC_DISC_TEARS.get());
    this.basicItem(SpliceItems.MUSIC_DISC_LAVA_CHICKEN.get());
  }

  // ===================================================================================================================
  // Copper
  // ===================================================================================================================

  private void registerCopper() {
    SpliceBlocks.COPPER_BARS
        .waxedMapping()
        .forEach(
            (normal, waxed) -> {
              final ResourceLocation texture =
                  this.modLoc("block/" + SpliceModelUtils.getName(normal.get()));
              this.generated(normal.get(), texture);
              this.generated(waxed.get(), texture);
            });

    SpliceBlocks.COPPER_CHAIN
        .waxedMapping()
        .forEach(
            (normal, waxed) -> {
              final ResourceLocation texture =
                  this.modLoc("item/" + SpliceModelUtils.getName(normal.get()));
              this.generated(normal.get(), texture);
              this.generated(waxed.get(), texture);
            });

    SpliceBlocks.COPPER_LANTERN
        .waxedMapping()
        .forEach(
            (normal, waxed) -> {
              final ResourceLocation texture =
                  this.modLoc("item/" + SpliceModelUtils.getName(normal.get()));
              this.generated(normal.get(), texture);
              this.generated(waxed.get(), texture);
            });

    SpliceBlocks.COPPER_CHESTS.forEach(
        block ->
            withExistingParent(SpliceModelUtils.getName(block.get()), this.mcLoc("item/chest")));

    this.simpleBlockItem(SpliceBlocks.COPPER_TORCH.get());
    this.basicItem(SpliceItems.COPPER_NUGGET.get());
    this.basicItem(SpliceItems.COPPER_HORSE_ARMOR.get());

    List.of(
            SpliceItems.COPPER_SHOVEL,
            SpliceItems.COPPER_PICKAXE,
            SpliceItems.COPPER_AXE,
            SpliceItems.COPPER_HOE,
            SpliceItems.COPPER_SWORD)
        .forEach(item -> this.handheldItem(item.get()));

    List.of(
            SpliceItems.COPPER_HELMET,
            SpliceItems.COPPER_CHESTPLATE,
            SpliceItems.COPPER_LEGGINGS,
            SpliceItems.COPPER_BOOTS)
        .forEach(this::trimmableArmor);
  }

  private void trimmableArmor(DeferredItem<? extends ArmorItem> item) {
    final String name = SpliceModelUtils.getName(item.get());
    final String type = item.get().getType().getName();
    final ResourceLocation baseTexture = this.modLoc("item/" + name);

    final ItemModelBuilder builder =
        withExistingParent(name, this.mcLoc("item/generated")).texture("layer0", baseTexture);

    for (int i = 0; i < TRIM_ORDER.size(); i++) {
      final ResourceKey<TrimMaterial> trim = TRIM_ORDER.get(i);
      final String materialName = trim.location().getPath();
      float trimValue = (i + 1) * 0.1f;

      final String trimModelName = name + "_" + materialName + "_trim";
      final ResourceLocation trimTexture =
          this.mcLoc("trims/items/" + type + "_trim_" + materialName);

      this.withExistingParent(trimModelName, this.mcLoc("item/generated"))
          .texture("layer0", baseTexture)
          .texture("layer1", trimTexture);

      builder
          .override()
          .predicate(this.mcLoc("trim_type"), trimValue)
          .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + trimModelName)))
          .end();
    }
  }

  // ===================================================================================================================
  // Block Families
  // ===================================================================================================================

  private void registerBlockFamilies() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel() || family.getBaseBlock() == null) continue;

      Block base = family.getBaseBlock();
      this.simpleBlockItem(base);
      ResourceLocation tex = SpliceModelUtils.getLocation(base);

      if (family.get(BlockFamily.Variant.BUTTON) instanceof Block b)
        buttonInventory(SpliceModelUtils.getName(b), tex);
      if (family.get(BlockFamily.Variant.CHISELED) instanceof Block c) simpleBlockItem(c);
      if (family.get(BlockFamily.Variant.DOOR) instanceof Block d) basicItem(d.asItem());
      if (family.get(BlockFamily.Variant.FENCE) instanceof Block f)
        fenceInventory(SpliceModelUtils.getName(f), tex);
      if (family.get(BlockFamily.Variant.FENCE_GATE) instanceof Block fg) simpleBlockItem(fg);
      if (family.get(BlockFamily.Variant.SLAB) instanceof Block s) simpleBlockItem(s);
      if (family.get(BlockFamily.Variant.STAIRS) instanceof Block s) simpleBlockItem(s);
      if (family.get(BlockFamily.Variant.PRESSURE_PLATE) instanceof Block p) simpleBlockItem(p);
      if (family.get(BlockFamily.Variant.TRAPDOOR) instanceof Block t)
        trapdoorOrientableBottom(SpliceModelUtils.getName(t), SpliceModelUtils.getLocation(t));
      if (family.get(BlockFamily.Variant.WALL) instanceof Block w)
        wallInventory(SpliceModelUtils.getName(w), tex);

      if (family.get(BlockFamily.Variant.SIGN) instanceof Block s) basicItem(s.asItem());
    }
  }

  // ===================================================================================================================
  // Utilities & Helpers
  // ===================================================================================================================

  private void generated(ItemLike item, ResourceLocation texture) {
    this.withExistingParent(SpliceModelUtils.getName(item.asItem()), this.mcLoc("item/generated"))
        .texture("layer0", texture);
  }

  private void generated(ItemLike item) {
    this.basicItem(item.asItem());
  }
}
