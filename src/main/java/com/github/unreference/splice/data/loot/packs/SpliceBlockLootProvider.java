package com.github.unreference.splice.data.loot.packs;

import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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
    this.dropSelf(SpliceBlocks.RESIN_BLOCK.get());
    this.add(SpliceBlocks.RESIN_CLUMP.get(), this::createMultifaceBlockDrops);
    this.dropSelf(SpliceBlocks.RESIN_BRICKS.get());
    this.dropSelf(SpliceBlocks.RESIN_BRICK_STAIRS.get());
    this.dropSelf(SpliceBlocks.CHISELED_RESIN_BRICKS.get());
    this.dropSelf(SpliceBlocks.RESIN_BRICK_SLAB.get());
    this.dropSelf(SpliceBlocks.RESIN_BRICK_WALL.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_WOOD.get());
    this.dropSelf(SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_LOG.get());
    this.dropSelf(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_PLANKS.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_BUTTON.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_FENCE.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_FENCE_GATE.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_PRESSURE_PLATE.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_SIGN.get());
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return SpliceBlocks.getBlocks().getEntries().stream().map(Holder::value)::iterator;
  }

  private LootTable.Builder createMultifaceBlockDrops(Block block) {
    return LootTable.lootTable()
        .withPool(
            LootPool.lootPool()
                .add(
                    this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(block)
                            .apply(
                                Direction.values(),
                                direction ->
                                    SetItemCountFunction.setCount(ConstantValue.exactly(1.0f), true)
                                        .when(
                                            LootItemBlockStatePropertyCondition
                                                .hasBlockStateProperties(block)
                                                .setProperties(
                                                    StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(
                                                            MultifaceBlock.getFaceProperty(
                                                                direction),
                                                            true))))
                            .apply(
                                SetItemCountFunction.setCount(
                                    ConstantValue.exactly(-1.0f), true)))));
  }
}
