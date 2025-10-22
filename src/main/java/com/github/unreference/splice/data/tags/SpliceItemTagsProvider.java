package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.github.unreference.splice.world.item.SpliceItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
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
  }
}
