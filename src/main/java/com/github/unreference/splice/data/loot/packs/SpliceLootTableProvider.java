package com.github.unreference.splice.data.loot.packs;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class SpliceLootTableProvider {
  public static LootTableProvider create(
      PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
    return new LootTableProvider(
        output,
        Collections.emptySet(),
        List.of(
            // Fishing
            // Chest
            new LootTableProvider.SubProviderEntry(
                SpliceChestLootProvider::new, LootContextParamSets.CHEST),
            // Entity
            new LootTableProvider.SubProviderEntry(
                SpliceEntityLootProvider::new, LootContextParamSets.ENTITY),
            // Equipment
            // Block
            new LootTableProvider.SubProviderEntry(
                SpliceBlockLootProvider::new, LootContextParamSets.BLOCK)),
        // Piglin barter
        // Gift
        // Archaeology
        // Shearing
        // Entity interact
        // Block interact
        // Charged Creeper
        provider);
  }
}
