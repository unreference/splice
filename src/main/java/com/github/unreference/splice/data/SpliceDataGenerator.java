package com.github.unreference.splice.data;

import com.github.unreference.splice.client.particle.SpliceParticleDescriptionProvider;
import com.github.unreference.splice.client.renderer.block.model.SpliceBlockModelProvider;
import com.github.unreference.splice.client.renderer.block.model.SpliceItemModelProvider;
import com.github.unreference.splice.data.loot.SpliceLootModifierProvider;
import com.github.unreference.splice.data.loot.packs.SpliceLootTableProvider;
import com.github.unreference.splice.data.recipes.SpliceRecipeProvider;
import com.github.unreference.splice.data.registries.SpliceRegistries;
import com.github.unreference.splice.data.sounds.SpliceSoundDefinitionProvider;
import com.github.unreference.splice.data.tags.*;
import com.github.unreference.splice.world.level.levelgen.feature.stateproviders.SpliceBlockStateProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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
    generator.addProvider(true, new SpliceParticleDescriptionProvider(output, helper));
    generator.addProvider(true, new SpliceSoundDefinitionProvider(output, helper));
    generator.addProvider(true, new SpliceBlockModelProvider(output, helper));
    generator.addProvider(true, new SpliceBlockStateProvider(output, helper));
    generator.addProvider(true, new SpliceItemModelProvider(output, helper));
  }

  private static void addServerProviders(
      DataGenerator generator,
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> lookup,
      ExistingFileHelper helper) {
    final SpliceRegistries registries = new SpliceRegistries(output, lookup);
    generator.addProvider(true, registries);

    final CompletableFuture<HolderLookup.Provider> futureLookup = registries.getRegistryProvider();

    generator.addProvider(true, SpliceLootTableProvider.create(output, futureLookup));
    generator.addProvider(true, new SpliceLootModifierProvider(output, futureLookup));
    generator.addProvider(true, new SpliceRecipeProvider(output, futureLookup));

    final SpliceBlockTagsProvider blockTags =
        new SpliceBlockTagsProvider(output, futureLookup, helper);
    generator.addProvider(true, blockTags);

    generator.addProvider(
        true, new SpliceItemTagsProvider(output, futureLookup, blockTags.contentsGetter(), helper));

    generator.addProvider(true, new SpliceDataMapsProvider(output, futureLookup));
    generator.addProvider(true, new SpliceBiomeTagsProvider(output, futureLookup, helper));
    generator.addProvider(true, new SpliceBannerPatternTagsProvider(output, futureLookup, helper));
    generator.addProvider(
        true, new SplicePaintingVariantTagsProvider(output, futureLookup, helper));
  }
}
