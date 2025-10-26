package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.entity.decoration.SplicePaintingVariants;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SplicePaintingVariantTagsProvider extends PaintingVariantTagsProvider {
  public SplicePaintingVariantTagsProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> provider,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, provider, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    this.tag(PaintingVariantTags.PLACEABLE).add(SplicePaintingVariants.DENNIS);
  }
}
