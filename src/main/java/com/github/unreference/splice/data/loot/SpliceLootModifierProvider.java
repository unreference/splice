package com.github.unreference.splice.data.loot;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.loot.packs.SpliceChestLootProvider;
import com.github.unreference.splice.data.loot.packs.SpliceEntityLootProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public final class SpliceLootModifierProvider extends GlobalLootModifierProvider {
  public SpliceLootModifierProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, SpliceMain.MOD_ID);
  }

  @Override
  protected void start() {
    // Chests
    final LootItemCondition[] simpleDungeon =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/simple_dungeon"))
              .build()
        };

    final LootItemCondition[] villageWeaponsmith =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/village/village_weaponsmith"))
              .build()
        };

    final LootItemCondition[] endCityTreasure =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/end_city_treasure"))
              .build()
        };

    final LootItemCondition[] netherBridge =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/nether_bridge"))
              .build()
        };

    final LootItemCondition[] strongholdCorridor =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/stronghold_corridor"))
              .build()
        };

    final LootItemCondition[] jungleTemple =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/jungle_temple"))
              .build()
        };

    final LootItemCondition[] desertPyramid =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(
                  ResourceLocation.withDefaultNamespace("chests/desert_pyramid"))
              .build()
        };

    this.add(
        "simple_dungeon_modifier",
        new AddTableLootModifier(simpleDungeon, SpliceChestLootProvider.SIMPLE_DUNGEON));

    this.add(
        "village_weaponsmith_modifier",
        new AddTableLootModifier(villageWeaponsmith, SpliceChestLootProvider.VILLAGE_WEAPONSMITH));

    this.add(
        "end_city_treasure_modifier",
        new AddTableLootModifier(endCityTreasure, SpliceChestLootProvider.END_CITY_TREASURE));

    this.add(
        "nether_bridge_modifier",
        new AddTableLootModifier(netherBridge, SpliceChestLootProvider.NETHER_BRIDGE));

    this.add(
        "stronghold_corridor",
        new AddTableLootModifier(strongholdCorridor, SpliceChestLootProvider.STRONGHOLD_CORRIDOR));

    this.add(
        "jungle_temple_modifier",
        new AddTableLootModifier(jungleTemple, SpliceChestLootProvider.JUNGLE_TEMPLE));

    this.add(
        "desert_pyramid_modifier",
        new AddTableLootModifier(desertPyramid, SpliceChestLootProvider.DESERT_PYRAMID));

    // Entities
    final LootItemCondition[] zombie =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/zombie"))
              .build()
        };

    final LootItemCondition[] ghast =
        new LootItemCondition[] {
          new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/ghast"))
              .build()
        };

    this.add("zombie_modifier", new AddTableLootModifier(zombie, SpliceEntityLootProvider.ZOMBIE));
    this.add("ghast_modifier", new AddTableLootModifier(ghast, SpliceEntityLootProvider.GHAST));
  }
}
