package com.github.unreference.splice.data.loot.packs;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.item.SpliceItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public final class SpliceChestLootProvider implements LootTableSubProvider {
  public static final ResourceKey<LootTable> SIMPLE_DUNGEON =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/simple_dungeon"));

  public static final ResourceKey<LootTable> VILLAGE_WEAPONSMITH =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(
              SpliceMain.MOD_ID, "chests/village/village_weaponsmith"));

  public SpliceChestLootProvider(HolderLookup.Provider ignoredProvider) {}

  @Override
  public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
    final LootPool.Builder simpleDungeon =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(0.0f, 1.0f))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR));

    final LootPool.Builder villageWeaponsmith =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(0.0f, 1.0f))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR));

    output.accept(
        SIMPLE_DUNGEON,
        LootTable.lootTable().withPool(simpleDungeon).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        VILLAGE_WEAPONSMITH,
        LootTable.lootTable().withPool(villageWeaponsmith).setParamSet(LootContextParamSets.CHEST));
  }
}
