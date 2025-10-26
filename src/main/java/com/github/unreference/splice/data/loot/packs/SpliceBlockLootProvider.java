package com.github.unreference.splice.data.loot.packs;

import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public final class SpliceBlockLootProvider extends BlockLootSubProvider {
  public SpliceBlockLootProvider(HolderLookup.Provider registries) {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
  }

  @Override
  protected void generate() {
    SpliceBlocks.COPPER_BARS.forEach(block -> this.dropSelf(block.get()));
    SpliceBlocks.COPPER_CHAIN.forEach(block -> this.dropSelf(block.get()));
    SpliceBlocks.COPPER_CHESTS.forEach(
        block -> this.add(block.get(), this.createNameableBlockEntityTable(block.get())));
    SpliceBlocks.COPPER_LANTERN.forEach(
        block -> this.add(block.get(), this::createSingleItemTable));

    this.dropSelf(SpliceBlocks.COPPER_TORCH.get());
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return SpliceBlocks.getBlocks().getEntries().stream().map(Holder::value)::iterator;
  }
}
