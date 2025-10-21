package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class SpliceItemModelProvider extends ItemModelProvider {
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
    this.handheldItem(SpliceItems.COPPER_SWORD.get());
  }
}
