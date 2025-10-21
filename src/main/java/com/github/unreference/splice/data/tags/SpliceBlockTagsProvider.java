package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceBlockTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public final class SpliceBlockTagsProvider extends BlockTagsProvider {
  public SpliceBlockTagsProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> lookupProvider,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    this.tag(SpliceBlockTags.INCORRECT_FOR_COPPER_TOOL)
        .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
        .addTag(BlockTags.NEEDS_IRON_TOOL);
  }
}
