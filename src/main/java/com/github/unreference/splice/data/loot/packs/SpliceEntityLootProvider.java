package com.github.unreference.splice.data.loot.packs;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import java.util.function.BiConsumer;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import org.jetbrains.annotations.NotNull;

public final class SpliceEntityLootProvider implements LootTableSubProvider {
  public static final ResourceKey<LootTable> ZOMBIE =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entities/zombie"));

  public static final ResourceKey<LootTable> GHAST =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entities/ghast"));

  public SpliceEntityLootProvider(HolderLookup.Provider ignoredProvider) {}

  @Override
  public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
    final LootTable.Builder zombie =
        LootTable.lootTable()
            .withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(SpliceItems.MUSIC_DISC_LAVA_CHICKEN))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .when(
                        LootItemEntityPropertyCondition.hasProperties(
                            LootContext.EntityTarget.THIS,
                            EntityPredicate.Builder.entity()
                                .flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true))
                                .vehicle(
                                    EntityPredicate.Builder.entity().of(EntityType.CHICKEN)))));

    final LootTable.Builder ghast =
        LootTable.lootTable()
            .withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(SpliceItems.MUSIC_DISC_TEARS))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .when(
                        DamageSourceCondition.hasDamageSource(
                            DamageSourcePredicate.Builder.damageType()
                                .tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))
                                .direct(
                                    EntityPredicate.Builder.entity().of(EntityType.FIREBALL)))));

    output.accept(ZOMBIE, zombie);
    output.accept(GHAST, ghast);
  }
}
