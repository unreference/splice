package com.github.unreference.splice.data;

import com.github.unreference.splice.data.models.SpliceBlockStateProvider;
import com.github.unreference.splice.data.models.SpliceItemModelProvider;
import com.github.unreference.splice.data.recipes.SpliceRecipeProvider;
import com.github.unreference.splice.data.tags.SpliceBannerPatternTagsProvider;
import com.github.unreference.splice.data.tags.SpliceBlockTagsProvider;
import com.github.unreference.splice.data.tags.SpliceItemTagsProvider;
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
    final ExistingFileHelper helper = event.getExistingFileHelper();
    final CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

    // Client
    final SpliceItemModelProvider itemModel = new SpliceItemModelProvider(output, helper);
    generator.addProvider(event.includeClient(), itemModel);

    final SpliceBlockStateProvider blockState = new SpliceBlockStateProvider(output, helper);
    generator.addProvider(event.includeClient(), blockState);

    // Server
    final SpliceRecipeProvider recipe = new SpliceRecipeProvider(output, provider);
    generator.addProvider(event.includeServer(), recipe);

    final SpliceBlockTagsProvider blockTags = new SpliceBlockTagsProvider(output, provider, helper);
    generator.addProvider(event.includeServer(), blockTags);

    final SpliceItemTagsProvider itemTags =
        new SpliceItemTagsProvider(output, provider, blockTags.contentsGetter(), helper);
    generator.addProvider(event.includeServer(), itemTags);

    final SpliceBannerPatternTagsProvider bannerPattern =
        new SpliceBannerPatternTagsProvider(output, provider, helper);
    generator.addProvider(event.includeServer(), bannerPattern);

    final SpliceDataMapsProvider dataMaps = new SpliceDataMapsProvider(output, provider);
    generator.addProvider(event.includeServer(), dataMaps);
  }
}
