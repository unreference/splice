package com.github.unreference.splice.data.loot;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.loot.packs.SpliceChestLootProvider;
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

    this.add(
        "simple_dungeon_modifier",
        new AddTableLootModifier(simpleDungeon, SpliceChestLootProvider.SIMPLE_DUNGEON));

    this.add(
        "village_weaponsmith_modifier",
        new AddTableLootModifier(villageWeaponsmith, SpliceChestLootProvider.VILLAGE_WEAPONSMITH));
  }
}
