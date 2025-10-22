package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceBlockTags;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpliceBlockTagsProvider extends BlockTagsProvider {
  public SpliceBlockTagsProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> lookupProvider,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    this.tag(SpliceBlockTags.INCORRECT_FOR_COPPER_TOOL)
        .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
        .addTag(BlockTags.NEEDS_IRON_TOOL);

    this.tag(SpliceBlockTags.COPPER_CHESTS)
        .add(SpliceBlocks.COPPER_CHEST.get())
        .add(SpliceBlocks.EXPOSED_COPPER_CHEST.get())
        .add(SpliceBlocks.WEATHERED_COPPER_CHEST.get());

    this.tag(BlockTags.GUARDED_BY_PIGLINS).addTag(SpliceBlockTags.COPPER_CHESTS);
    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(SpliceBlockTags.COPPER_CHESTS);
    this.tag(BlockTags.NEEDS_STONE_TOOL).addTag(SpliceBlockTags.COPPER_CHESTS);
  }
}
