package com.github.unreference.splice.data;

import com.github.unreference.splice.client.particle.SpliceParticleDescriptionProvider;
import com.github.unreference.splice.client.renderer.block.model.SpliceBlockModelProvider;
import com.github.unreference.splice.client.renderer.block.model.SpliceItemModelProvider;
import com.github.unreference.splice.data.loot.SpliceLootModifierProvider;
import com.github.unreference.splice.data.loot.packs.SpliceBlockLootProvider;
import com.github.unreference.splice.data.loot.packs.SpliceChestLootProvider;
import com.github.unreference.splice.data.loot.packs.SpliceEntityLootProvider;
import com.github.unreference.splice.data.recipes.SpliceRecipeProvider;
import com.github.unreference.splice.data.sounds.SpliceSoundDefinitionProvider;
import com.github.unreference.splice.data.tags.SpliceBannerPatternTagsProvider;
import com.github.unreference.splice.data.tags.SpliceBlockTagsProvider;
import com.github.unreference.splice.data.tags.SpliceItemTagsProvider;
import com.github.unreference.splice.world.level.levelgen.feature.stateproviders.SpliceBlockStateProvider;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class SpliceDataGenerator {
  public static void onGatherData(GatherDataEvent event) {
    final DataGenerator generator = event.getGenerator();
    final PackOutput output = generator.getPackOutput();
    final CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
    final ExistingFileHelper helper = event.getExistingFileHelper();

    if (event.includeClient()) {
      addClientProviders(generator, output, helper);
    }

    if (event.includeServer()) {
      addServerProviders(generator, output, lookup, helper);
    }
  }

  private static void addClientProviders(
      DataGenerator generator, PackOutput output, ExistingFileHelper helper) {
    generator.addProvider(true, new SpliceItemModelProvider(output, helper));
    generator.addProvider(true, new SpliceBlockModelProvider(output, helper));
    generator.addProvider(true, new SpliceBlockStateProvider(output, helper));
    generator.addProvider(true, new SpliceParticleDescriptionProvider(output, helper));
    generator.addProvider(true, new SpliceSoundDefinitionProvider(output, helper));
  }

  private static void addServerProviders(
      DataGenerator generator,
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> lookup,
      ExistingFileHelper helper) {
    generator.addProvider(true, new SpliceRecipeProvider(output, lookup));

    final SpliceBlockTagsProvider blockTags = new SpliceBlockTagsProvider(output, lookup, helper);

    generator.addProvider(true, blockTags);
    generator.addProvider(
        true, new SpliceItemTagsProvider(output, lookup, blockTags.contentsGetter(), helper));
    generator.addProvider(true, new SpliceBannerPatternTagsProvider(output, lookup, helper));
    generator.addProvider(true, new SpliceDataMapsProvider(output, lookup));

    final LootTableProvider.SubProviderEntry blockLoot =
        new LootTableProvider.SubProviderEntry(
            SpliceBlockLootProvider::new, LootContextParamSets.BLOCK);
    final LootTableProvider.SubProviderEntry chestLoot =
        new LootTableProvider.SubProviderEntry(
            SpliceChestLootProvider::new, LootContextParamSets.CHEST);
    final LootTableProvider.SubProviderEntry entityLoot =
        new LootTableProvider.SubProviderEntry(
            SpliceEntityLootProvider::new, LootContextParamSets.ENTITY);

    generator.addProvider(
        true,
        new LootTableProvider(
            output, Collections.emptySet(), List.of(blockLoot, chestLoot, entityLoot), lookup));
    generator.addProvider(true, new SpliceLootModifierProvider(output, lookup));

    generator.addProvider(true, new SpliceDatapackEntries(output, lookup));
  }
}
