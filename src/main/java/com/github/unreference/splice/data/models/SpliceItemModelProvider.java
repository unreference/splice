package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.util.SpliceBlockUtil;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
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

  private static final float TRIM_STEP = 0.1f;

  public SpliceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
  }

  private static float getTrimIndex(int idx) {
    return (idx + 1) * TRIM_STEP;
  }

  private static String getNameOf(Block block) {
    return SpliceBlockUtil.getId(block).getPath();
  }

  private static String stripWaxedPrefix(String name) {
    return name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;
  }

  @Override
  protected void registerModels() {
    this.basicItem(SpliceItems.FIELD_MASONED_BANNER_PATTERN.get());
    this.basicItem(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get());
    this.registerCopperModels();
  }

  private void registerCopperModels() {
    this.basicItem(SpliceItems.COPPER_NUGGET.get());
    this.handheldItem(SpliceItems.COPPER_SHOVEL.get());
    this.handheldItem(SpliceItems.COPPER_PICKAXE.get());
    this.handheldItem(SpliceItems.COPPER_AXE.get());
    this.handheldItem(SpliceItems.COPPER_HOE.get());
    this.handheldItem(SpliceItems.COPPER_SWORD.get());
    this.trimmableArmor(SpliceItems.COPPER_HELMET);
    this.trimmableArmor(SpliceItems.COPPER_CHESTPLATE);
    this.trimmableArmor(SpliceItems.COPPER_LEGGINGS);
    this.trimmableArmor(SpliceItems.COPPER_BOOTS);
    this.basicItem(SpliceItems.COPPER_HORSE_ARMOR.get());
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::copperBars);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::copperChain);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::copperLantern);
    this.torchItem(SpliceBlocks.COPPER_TORCH);
    SpliceBlocks.COPPER_CHESTS.forEach(this::chestItem);
  }

  private void chestItem(DeferredBlock<? extends Block> block) {
    final String name = getNameOf(block.get());
    this.withExistingParent(name, mcLoc("item/chest"));
  }

  private void torchItem(DeferredBlock<Block> block) {
    final String name = getNameOf(block.get());
    this.withExistingParent(name, mcLoc("item/generated"))
        .texture("layer0", modLoc("block/" + name));
  }

  private void copperLantern(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    this.lantern(base);
    this.lantern(waxed);
  }

  private void lantern(DeferredBlock<? extends Block> block) {
    final String name = getNameOf(block.get());
    final String texture = stripWaxedPrefix(name);
    this.withExistingParent(name, mcLoc("item/generated"))
        .texture("layer0", modLoc("item/" + texture));
  }

  private void copperChain(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    this.chain(base);
    this.chain(waxed);
  }

  private void chain(DeferredBlock<? extends Block> block) {
    final String name = getNameOf(block.get());
    final String texture = stripWaxedPrefix(name);
    this.withExistingParent(name, mcLoc("item/generated")).texture("layer0", "item/" + texture);
  }

  private void copperBars(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    this.bars(base);
    this.bars(waxed);
  }

  private void bars(DeferredBlock<? extends Block> block) {
    final String name = getNameOf(block.get());
    final String texture = stripWaxedPrefix(name);
    this.withExistingParent(name, mcLoc("item/generated"))
        .texture("layer0", modLoc("block/" + texture));
  }

  private void trimmableArmor(DeferredItem<? extends ArmorItem> item) {
    final String armorItem = item.getId().getPath();
    final String armorKind = item.get().getType().getName();

    final ItemModelBuilder base =
        withExistingParent(armorItem, mcLoc("item/generated"))
            .texture("layer0", modLoc("item/" + armorItem));

    for (int i = 0; i < TRIM_ORDER.size(); ++i) {
      final ResourceKey<TrimMaterial> key = TRIM_ORDER.get(i);
      final String materialName = key.location().getPath();

      final ResourceLocation trimTex = mcLoc("trims/items/" + armorKind + "_trim_" + materialName);
      existingFileHelper.trackGenerated(trimTex, PackType.CLIENT_RESOURCES, ".png", "textures");

      final String trimModelName = armorItem + '_' + materialName + "_trim";

      getBuilder(trimModelName)
          .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
          .texture("layer0", modLoc("item/" + armorItem))
          .texture("layer1", trimTex);

      base.override()
          .predicate(mcLoc("trim_type"), getTrimIndex(i))
          .model(new ModelFile.UncheckedModelFile(modLoc("item/" + trimModelName)))
          .end();
    }
  }
}
