package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceBlockTags;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.github.unreference.splice.world.item.SpliceItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpliceItemTagsProvider extends ItemTagsProvider {
  public SpliceItemTagsProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> blockTags,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, blockTags, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
    this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
    this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
    this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
    this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
    this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
    this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
    this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
    this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
    this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
    this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
    this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
    this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
    this.copy(BlockTags.SLABS, ItemTags.SLABS);
    this.copy(BlockTags.WALLS, ItemTags.WALLS);

    this.copy(SpliceBlockTags.COPPER_CHESTS, SpliceItemTags.COPPER_CHESTS);
    this.copy(SpliceBlockTags.BARS, SpliceItemTags.BARS);
    this.copy(SpliceBlockTags.CHAINS, SpliceItemTags.CHAINS);
    this.copy(SpliceBlockTags.LANTERNS, SpliceItemTags.LANTERNS);
    this.copy(SpliceBlockTags.PALE_OAK_LOGS, SpliceItemTags.PALE_OAK_LOGS);

    this.tag(SpliceItemTags.COPPER_TOOL_MATERIALS).add(Items.COPPER_INGOT);
    this.tag(ItemTags.SHOVELS).add(SpliceItems.COPPER_SHOVEL.get());
    this.tag(ItemTags.PICKAXES).add(SpliceItems.COPPER_PICKAXE.get());
    this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(SpliceItems.COPPER_PICKAXE.get());
    this.tag(ItemTags.AXES).add(SpliceItems.COPPER_AXE.get());
    this.tag(ItemTags.HOES).add(SpliceItems.COPPER_HOE.get());
    this.tag(ItemTags.SWORDS).add(SpliceItems.COPPER_SWORD.get());
    this.tag(ItemTags.HEAD_ARMOR).add(SpliceItems.COPPER_HELMET.get());
    this.tag(ItemTags.CHEST_ARMOR).add(SpliceItems.COPPER_CHESTPLATE.get());
    this.tag(ItemTags.LEG_ARMOR).add(SpliceItems.COPPER_LEGGINGS.get());
    this.tag(ItemTags.FOOT_ARMOR).add(SpliceItems.COPPER_BOOTS.get());
    this.tag(ItemTags.BOATS);
  }
}
