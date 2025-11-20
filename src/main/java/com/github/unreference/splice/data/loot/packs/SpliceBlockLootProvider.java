package com.github.unreference.splice.data.loot.packs;

import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.SpliceMossyCarpetBlock;
import java.util.Set;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public final class SpliceBlockLootProvider extends BlockLootSubProvider {
  public SpliceBlockLootProvider(HolderLookup.Provider registries) {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
  }

  @Override
  protected void generate() {
    final HolderLookup<Enchantment> enchantmentLookup =
        this.registries.lookupOrThrow(Registries.ENCHANTMENT);

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
    this.dropSelf(SpliceBlocks.PALE_OAK_SLAB.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_STAIRS.get());
    this.add(SpliceBlocks.PALE_OAK_DOOR.get(), this::createDoorTable);
    this.dropSelf(SpliceBlocks.PALE_OAK_TRAPDOOR.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_HANGING_SIGN.get());
    this.dropSelf(SpliceBlocks.PALE_OAK_SAPLING.get());

    this.add(
        SpliceBlocks.PALE_OAK_LEAVES.get(),
        block ->
            this.createLeavesDrops(
                block, SpliceBlocks.PALE_OAK_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

    this.dropPottedContents(SpliceBlocks.POTTED_PALE_OAK_SAPLING.get());
    this.add(SpliceBlocks.PALE_MOSS_CARPET.get(), this::createMossyCarpetBlockDrops);
    this.dropSelf(SpliceBlocks.PALE_MOSS_BLOCK.get());
    this.add(SpliceBlocks.PALE_HANGING_MOSS.get(), this::createShearsOrSilkTouchDrop);
    this.dropSelf(SpliceBlocks.CLOSED_EYEBLOSSOM.get());
    this.dropSelf(SpliceBlocks.OPEN_EYEBLOSSOM.get());
    this.dropPottedContents(SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM.get());
    this.dropPottedContents(SpliceBlocks.POTTED_OPEN_EYEBLOSSOM.get());

    this.add(
        SpliceBlocks.CREAKING_HEART.get(),
        block ->
            this.createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                    block,
                    LootItem.lootTableItem(SpliceItems.RESIN_CLUMP)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)))
                        .apply(
                            ApplyBonusCount.addUniformBonusCount(
                                enchantmentLookup.getOrThrow(Enchantments.FORTUNE)))
                        .apply(LimitCount.limitCount(IntRange.upperBound(9))))));
  }

  private LootTable.Builder createShearsOrSilkTouchDrop(Block block) {
    return LootTable.lootTable()
        .withPool(
            LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0f))
                .when(this.hasShearsOrSilkTouch())
                .add(LootItem.lootTableItem(block)));
  }

  private LootItemCondition.Builder hasShearsOrSilkTouch() {
    return this.hasShears().or(this.hasSilkTouch());
  }

  private LootItemCondition.Builder hasShears() {
    return MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return SpliceBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
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

  private LootTable.Builder createMossyCarpetBlockDrops(Block block) {
    return LootTable.lootTable()
        .withPool(
            LootPool.lootPool()
                .add(
                    this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(block)
                            .when(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                    .setProperties(
                                        StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(SpliceMossyCarpetBlock.IS_BASE, true))))));
  }
}
