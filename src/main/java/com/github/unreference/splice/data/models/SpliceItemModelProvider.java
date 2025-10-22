package com.github.unreference.splice.data.models;

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

    this.trimmableArmor(SpliceItems.COPPER_HELMET);
    this.trimmableArmor(SpliceItems.COPPER_CHESTPLATE);
    this.trimmableArmor(SpliceItems.COPPER_LEGGINGS);
    this.trimmableArmor(SpliceItems.COPPER_BOOTS);

    this.basicItem(SpliceItems.COPPER_HORSE_ARMOR.get());
  }

  private void trimmableArmor(DeferredItem<? extends ArmorItem> item) {
    final String armorItem = item.getId().getPath();
    final String armor = item.get().getType().getName();
    final ItemModelBuilder base =
        this.withExistingParent(armorItem, mcLoc("item/generated"))
            .texture("layer0", modLoc("item/%s".formatted(armorItem)));

    final ArrayList<Map.Entry<ResourceKey<TrimMaterial>, Float>> toSort =
        new ArrayList<>(TRIM_MATERIALS.entrySet());
    toSort.sort(Comparator.comparingDouble(Map.Entry::getValue));

    toSort.forEach(
        entry -> {
          final String material = entry.getKey().location().getPath();
          final float modelIndex = entry.getValue();
          final ResourceLocation trimTexture =
              ResourceLocation.withDefaultNamespace(
                  "trims/items/%s_trim_%s".formatted(armor, material));

          existingFileHelper.trackGenerated(
              trimTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

          final String trimModel = "%s_%s_trim".formatted(armorItem, material);

          getBuilder(trimModel)
              .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
              .texture("layer0", modLoc("item/%s".formatted(armorItem)))
              .texture("layer1", trimTexture);

          base.override()
              .predicate(mcLoc("trim_type"), modelIndex)
              .model(new ModelFile.UncheckedModelFile(modLoc("item/%s".formatted(trimModel))))
              .end();
        });
  }
}
