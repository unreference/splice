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
    final var ITEM = item.getId().getPath();
    final var ARMOR = item.get().getType().getName();
    final var BASE =
        this.withExistingParent(ITEM, mcLoc("item/generated"))
            .texture("layer0", modLoc("item/%s".formatted(ITEM)));

    final var TO_SORT = new ArrayList<>(TRIM_MATERIALS.entrySet());
    TO_SORT.sort(Comparator.comparingDouble(Map.Entry::getValue));

    for (var entry : TO_SORT) {
      final var MATERIAL = entry.getKey().location().getPath();
      final var MODEL_INDEX = entry.getValue();
      final var TRIM_TEXTURE =
          ResourceLocation.withDefaultNamespace(
              "trims/items/%s_trim_%s".formatted(ARMOR, MATERIAL));

      existingFileHelper.trackGenerated(
          TRIM_TEXTURE, PackType.CLIENT_RESOURCES, ".png", "textures");

      final var TRIM_MODEL = "%s_%s_trim".formatted(ITEM, MATERIAL);

      getBuilder(TRIM_MODEL)
          .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
          .texture("layer0", modLoc("item/%s".formatted(ITEM)))
          .texture("layer1", TRIM_TEXTURE);

      BASE.override()
          .predicate(mcLoc("trim_type"), MODEL_INDEX)
          .model(new ModelFile.UncheckedModelFile(modLoc("item/%s".formatted(TRIM_MODEL))))
          .end();
    }
  }
}
