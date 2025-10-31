package com.github.unreference.splice.client.renderer.block.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import java.util.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
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

  private static final float TRIM_STEP = 0.1f;

  public SpliceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
  }

  private static float getTrimIndex(int idx) {
    return (idx + 1) * TRIM_STEP;
  }

  @Override
  protected void registerModels() {
    this.basicItem(SpliceItems.FIELD_MASONED_BANNER_PATTERN.get());
    this.basicItem(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get());
    this.registerCopperModels();
    this.basicItem(SpliceItems.MUSIC_DISC_TEARS.get());
    this.basicItem(SpliceItems.MUSIC_DISC_LAVA_CHICKEN.get());
    this.basicItem(SpliceItems.MUSIC_DISC_COFFEE_MACHINE.get());
    this.basicItem(SpliceItems.RESIN_BRICK.get());
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
  }

  private void trimmableArmor(DeferredItem<? extends ArmorItem> item) {
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
