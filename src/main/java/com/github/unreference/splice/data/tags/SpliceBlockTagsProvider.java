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

    SpliceBlocks.COPPER_BARS.forEach(block -> this.tag(SpliceBlockTags.BARS).add(block.get()));
    SpliceBlocks.COPPER_CHAIN.forEach(block -> this.tag(SpliceBlockTags.CHAINS).add(block.get()));
    SpliceBlocks.COPPER_LANTERN.forEach(
        block -> this.tag(SpliceBlockTags.LANTERNS).add(block.get()));
    SpliceBlocks.COPPER_CHESTS.forEach(
        block -> this.tag(SpliceBlockTags.COPPER_CHESTS).add(block.get()));

    this.tag(BlockTags.GUARDED_BY_PIGLINS).addTag(SpliceBlockTags.COPPER_CHESTS);

    this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .addTag(SpliceBlockTags.BARS)
        .addTag(SpliceBlockTags.CHAINS)
        .addTag(SpliceBlockTags.LANTERNS)
        .addTag(SpliceBlockTags.COPPER_CHESTS)
        .add(SpliceBlocks.RESIN_BRICKS.get())
        .add(SpliceBlocks.CHISELED_RESIN_BRICKS.get());

    this.tag(BlockTags.NEEDS_STONE_TOOL).addTag(SpliceBlockTags.COPPER_CHESTS);
    this.tag(BlockTags.WALL_POST_OVERRIDE).add(SpliceBlocks.COPPER_TORCH.get());
    this.tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(SpliceBlocks.RESIN_CLUMP.get());
    this.tag(BlockTags.STAIRS).add(SpliceBlocks.RESIN_BRICK_STAIRS.get());
    this.tag(BlockTags.SLABS).add(SpliceBlocks.RESIN_BRICK_SLAB.get());
    this.tag(BlockTags.WALLS).add(SpliceBlocks.RESIN_BRICK_WALL.get());

    this.tag(SpliceBlockTags.PALE_OAK_LOGS)
        .add(
            SpliceBlocks.PALE_OAK_LOG.get(),
            SpliceBlocks.PALE_OAK_WOOD.get(),
            SpliceBlocks.STRIPPED_PALE_OAK_LOG.get(),
            SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get());
    this.tag(BlockTags.LOGS).addTag(SpliceBlockTags.PALE_OAK_LOGS);
    this.tag(BlockTags.LOGS_THAT_BURN).addTag(SpliceBlockTags.PALE_OAK_LOGS);
    this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(SpliceBlocks.PALE_OAK_LOG.get());
  }
}
