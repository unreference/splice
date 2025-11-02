package com.github.unreference.splice.client.renderer.block.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceUtils;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.*;
import net.minecraft.data.BlockFamily;
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

  private static float getTrimIndex(int index) {
    return (index + 1) * TRIM_STEP;
  }

  @Override
  protected void registerModels() {
    this.blockFamilyItems();
    this.bannerPatternItems();
    this.copperItems();
    this.musicDiscItems();
    this.resinItems();
    this.paleOakItems();
  }

  private void paleOakItems() {
    this.simpleBlockItem(SpliceBlocks.PALE_OAK_LOG.get());
    this.simpleBlockItem(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get());
    this.simpleBlockItem(SpliceBlocks.PALE_OAK_WOOD.get());
    this.simpleBlockItem(SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get());
  }

  private void resinItems() {
    this.simpleBlockItem(SpliceBlocks.RESIN_BLOCK.get());
    this.basicItem(SpliceItems.RESIN_CLUMP.get());
    this.basicItem(SpliceItems.RESIN_BRICK.get());
  }

  private void bannerPatternItems() {
    this.basicItem(SpliceItems.FIELD_MASONED_BANNER_PATTERN.get());
    this.basicItem(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get());
  }

  private void musicDiscItems() {
    this.basicItem(SpliceItems.MUSIC_DISC_TEARS.get());
    this.basicItem(SpliceItems.MUSIC_DISC_LAVA_CHICKEN.get());
    this.basicItem(SpliceItems.MUSIC_DISC_COFFEE_MACHINE.get());
  }

  private void blockFamilyItems() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel()) {
        continue;
      }

      final Block base = family.getBaseBlock();
      if (base == null) {
        return;
      }

      final ResourceLocation texture = SpliceUtils.getLocation(base);
      this.simpleBlockItem(base);

      final Block button = family.get(BlockFamily.Variant.BUTTON);
      if (button != null) {
        this.buttonInventory(SpliceUtils.getName(button), texture);
      }

      final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
      if (chiseled != null) {
        this.simpleBlockItem(chiseled);
      }

      final Block door = family.get(BlockFamily.Variant.DOOR);
      if (door != null) {
        this.basicItem(door.asItem());
      }

      final Block fence = family.get(BlockFamily.Variant.FENCE);
      if (fence != null) {
        this.fenceInventory(SpliceUtils.getName(fence), texture);
      }

      final Block fenceGate = family.get(BlockFamily.Variant.FENCE_GATE);
      if (fenceGate != null) {
        this.simpleBlockItem(fenceGate);
      }

      final Block sign = family.get(BlockFamily.Variant.SIGN);
      final Block wallSign = family.get(BlockFamily.Variant.WALL_SIGN);
      if (sign != null && wallSign != null) {
        this.basicItem(sign.asItem());
      }

      final Block slab = family.get(BlockFamily.Variant.SLAB);
      if (slab != null) {
        this.simpleBlockItem(slab);
      }

      final Block stairs = family.get(BlockFamily.Variant.STAIRS);
      if (stairs != null) {
        this.simpleBlockItem(stairs);
      }

      final Block pressurePlate = family.get(BlockFamily.Variant.PRESSURE_PLATE);
      if (pressurePlate != null) {
        this.simpleBlockItem(pressurePlate);
      }

      final Block trapdoor = family.get(BlockFamily.Variant.TRAPDOOR);
      if (trapdoor != null) {
        this.trapdoorOrientableBottom(
            SpliceUtils.getName(trapdoor), SpliceUtils.getLocation(trapdoor));
      }

      final Block wall = family.get(BlockFamily.Variant.WALL);
      if (wall != null) {
        this.wallInventory(SpliceUtils.getName(wall), texture);
      }
    }
  }

  private void copperItems() {
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::copperBarsItem);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::copperChainItem);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::copperLanternItem);
    SpliceBlocks.COPPER_CHESTS.forEach(this::copperChestItem);

    this.simpleBlockItem(SpliceBlocks.COPPER_TORCH.get());
    this.basicItem(SpliceItems.COPPER_NUGGET.get());
    this.handheldItem(SpliceItems.COPPER_SHOVEL.get());
    this.handheldItem(SpliceItems.COPPER_PICKAXE.get());
    this.handheldItem(SpliceItems.COPPER_AXE.get());
    this.handheldItem(SpliceItems.COPPER_HOE.get());
    this.handheldItem(SpliceItems.COPPER_SWORD.get());
    this.trimmableArmorItem(SpliceItems.COPPER_HELMET);
    this.trimmableArmorItem(SpliceItems.COPPER_CHESTPLATE);
    this.trimmableArmorItem(SpliceItems.COPPER_LEGGINGS);
    this.trimmableArmorItem(SpliceItems.COPPER_BOOTS);
    this.basicItem(SpliceItems.COPPER_HORSE_ARMOR.get());
  }

  private void copperChestItem(DeferredBlock<? extends Block> block) {
    this.withExistingParent(SpliceUtils.getName(block.get()), this.mcLoc("item/chest"));
  }

  private void copperLanternItem(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.inventoryItem(unaffected);
    this.inventoryItem(waxed);
  }

  private void copperChainItem(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.inventoryItem(unaffected);
    this.inventoryItem(waxed);
  }

  private void copperBarsItem(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.inventoryBlockItem(unaffected);
    this.inventoryBlockItem(waxed);
  }

  private void inventoryItem(DeferredBlock<? extends Block> block) {
    final String name = SpliceUtils.getName(block.get());
    final ResourceLocation texture = this.modLoc("item/" + SpliceUtils.stripWaxedPrefix(name));
    this.withExistingParent(name, this.mcLoc("item/generated")).texture("layer0", texture);
  }

  private void inventoryItem(Block block) {
    final String name = SpliceUtils.getName(block);
    final ResourceLocation texture = this.modLoc("item/" + SpliceUtils.stripWaxedPrefix(name));
    this.withExistingParent(name, this.mcLoc("item/generated")).texture("layer0", texture);
  }

  private void inventoryBlockItem(DeferredBlock<? extends Block> block) {
    final String name = SpliceUtils.getName(block.get());
    final ResourceLocation texture = this.modLoc("block/" + SpliceUtils.stripWaxedPrefix(name));
    this.withExistingParent(name, this.mcLoc("item/generated")).texture("layer0", texture);
  }

  private void inventoryBlockItem(Block block) {
    final String name = SpliceUtils.getName(block);
    final ResourceLocation texture = this.modLoc("block/" + SpliceUtils.stripWaxedPrefix(name));
    this.withExistingParent(name, this.mcLoc("item/generated")).texture("layer0", texture);
  }

  private void trimmableArmorItem(DeferredItem<? extends ArmorItem> item) {
    final String armorItem = item.getId().getPath();
    final String armorKind = item.get().getType().getName();

    final ItemModelBuilder base =
        withExistingParent(armorItem, mcLoc("item/generated"))
            .texture("layer0", modLoc("item/" + armorItem));

    for (int i = 0; i < TRIM_ORDER.size(); i++) {
      final ResourceKey<TrimMaterial> key = TRIM_ORDER.get(i);
      final ResourceLocation materialLocation = key.location();
      final String materialName = materialLocation.getPath();
      final String texturePath = "trims/items/" + armorKind + "_trim_" + materialName;
      final ResourceLocation trimTexture = this.mcLoc(texturePath);

      existingFileHelper.trackGenerated(trimTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

      final String trimModelName = armorItem + '_' + materialName + "_trim";
      this.getBuilder(trimModelName)
          .parent(new ModelFile.UncheckedModelFile(this.mcLoc("item/generated")))
          .texture("layer0", this.modLoc("item/" + armorItem))
          .texture("layer1", trimTexture);

      base.override()
          .predicate(this.mcLoc("trim_type"), getTrimIndex(i))
          .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + trimModelName)))
          .end();
    }
  }
}
