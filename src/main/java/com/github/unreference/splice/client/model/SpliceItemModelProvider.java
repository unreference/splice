package com.github.unreference.splice.client.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

@OnlyIn(Dist.CLIENT)
public final class SpliceItemModelProvider extends ItemModelProvider {
  public SpliceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    this.basicItem(SpliceItems.FIELD_MASONED_BANNER_PATTERN.get());
    this.basicItem(SpliceItems.BORDURE_INDENTED_BANNER_PATTERN.get());
  }
}
