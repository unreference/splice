package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import java.util.LinkedHashMap;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public final class SpliceItemModelProvider extends ItemModelProvider {
  private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> TRIM_MATERIALS =
      new LinkedHashMap<>();

  static {
    TRIM_MATERIALS.put(TrimMaterials.QUARTZ, 0.1f);
    TRIM_MATERIALS.put(TrimMaterials.IRON, 0.2f);
    TRIM_MATERIALS.put(TrimMaterials.NETHERITE, 0.3f);
    TRIM_MATERIALS.put(TrimMaterials.REDSTONE, 0.4f);
    TRIM_MATERIALS.put(TrimMaterials.COPPER, 0.5f);
    TRIM_MATERIALS.put(TrimMaterials.GOLD, 0.6f);
    TRIM_MATERIALS.put(TrimMaterials.EMERALD, 0.7f);
    TRIM_MATERIALS.put(TrimMaterials.DIAMOND, 0.8f);
    TRIM_MATERIALS.put(TrimMaterials.LAPIS, 0.9f);
    TRIM_MATERIALS.put(TrimMaterials.AMETHYST, 1.0f);
  }

  public SpliceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
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

    this.generateTrimmableItem(SpliceItems.COPPER_HELMET);
  }

  private void generateTrimmableItem(DeferredItem<ArmorItem> item) {
    final String MOD_ID = SpliceMain.MOD_ID;

    Item armorItem = item.get();
    TRIM_MATERIALS.forEach(
        (trimMaterial, value) -> {
          float trimValue = value;

          String armorType = "";
          if (armorItem.toString().contains("helmet")) {
            armorType = "helmet";
          } else if (armorItem.toString().contains("chestplate")) {
            armorType = "chestplate";
          } else if (armorItem.toString().contains("leggings")) {
            armorType = "leggings";
          } else if (armorItem.toString().contains("boots")) {
            armorType = "boots";
          }

          String armorItemPath = armorItem.toString();
          String trimPath =
              "trims/items/%s_trim_%s".formatted(armorType, trimMaterial.location().getPath());
          String currentTrimName =
              "%s_%s_trim".formatted(armorItemPath, trimMaterial.location().getPath());
          ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
          ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
          ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

          existingFileHelper.trackGenerated(
              trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

          getBuilder(currentTrimName)
              .parent(new ModelFile.UncheckedModelFile("item/generated"))
              .texture(
                  "layer0",
                  "%s:item/%s".formatted(armorItemResLoc.getNamespace(), armorItemResLoc.getPath()))
              .texture("layer1", trimResLoc);

          this.withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
              .override()
              .model(
                  new ModelFile.UncheckedModelFile(
                      "%s:item/%s"
                          .formatted(trimNameResLoc.getNamespace(), trimNameResLoc.getPath())))
              .predicate(mcLoc("trim_type"), trimValue)
              .end()
              .texture(
                  "layer0",
                  ResourceLocation.fromNamespaceAndPath(
                      MOD_ID, "item/%s".formatted(item.getId().getPath())));
        });
  }
}
