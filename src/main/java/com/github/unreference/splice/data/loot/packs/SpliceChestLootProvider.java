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
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
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

  public static final ResourceKey<LootTable> END_CITY_TREASURE =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/end_city_treasure"));

  public static final ResourceKey<LootTable> NETHER_BRIDGE =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/nether_bridge"));

  public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/stronghold_corridor"));

  public static final ResourceKey<LootTable> JUNGLE_TEMPLE =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/jungle_temple"));

  public static final ResourceKey<LootTable> DESERT_PYRAMID =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/desert_pyramid"));

  public static final ResourceKey<LootTable> WOODLAND_MANSION =
      ResourceKey.create(
          Registries.LOOT_TABLE,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "chests/woodland_mansion"));

  public SpliceChestLootProvider(HolderLookup.Provider ignoredProvider) {}

  @Override
  public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
    final LootPool.Builder simpleDungeon =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(1.0f, 3.0f))
            .add(EmptyLootItem.emptyItem().setWeight(129))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR).setWeight(15));

    final LootPool.Builder villageWeaponsmith =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(3.0f, 8.0f))
            .add(EmptyLootItem.emptyItem().setWeight(93))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR));

    final LootPool.Builder endCityTreasure =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(2.0f, 6.0f))
            .add(EmptyLootItem.emptyItem().setWeight(85))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR));

    final LootPool.Builder netherBridge =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(2.0f, 4.0f))
            .add(EmptyLootItem.emptyItem().setWeight(73))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR).setWeight(5));

    final LootPool.Builder strongholdCorridor =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(2.0f, 3.0f))
            .add(EmptyLootItem.emptyItem().setWeight(100))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR));

    final LootPool.Builder jungleTemple =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(2.0f, 6.0f))
            .add(EmptyLootItem.emptyItem().setWeight(88))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR));

    final LootPool.Builder desertPyramid =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(2.0f, 4.0f))
            .add(EmptyLootItem.emptyItem().setWeight(232))
            .add(LootItem.lootTableItem(SpliceItems.COPPER_HORSE_ARMOR).setWeight(15));

    final LootPool.Builder woodlandMansion =
        LootPool.lootPool()
            .setRolls(UniformGenerator.between(1.0f, 4.0f))
            .add(EmptyLootItem.emptyItem().setWeight(125))
            .add(
                LootItem.lootTableItem(SpliceItems.RESIN_CLUMP)
                    .setWeight(50)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))));

    output.accept(
        SIMPLE_DUNGEON,
        LootTable.lootTable().withPool(simpleDungeon).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        VILLAGE_WEAPONSMITH,
        LootTable.lootTable().withPool(villageWeaponsmith).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        END_CITY_TREASURE,
        LootTable.lootTable().withPool(endCityTreasure).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        NETHER_BRIDGE,
        LootTable.lootTable().withPool(netherBridge).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        STRONGHOLD_CORRIDOR,
        LootTable.lootTable().withPool(strongholdCorridor).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        JUNGLE_TEMPLE,
        LootTable.lootTable().withPool(jungleTemple).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        DESERT_PYRAMID,
        LootTable.lootTable().withPool(desertPyramid).setParamSet(LootContextParamSets.CHEST));

    output.accept(
        WOODLAND_MANSION,
        LootTable.lootTable().withPool(woodlandMansion).setParamSet(LootContextParamSets.CHEST));
  }
}
