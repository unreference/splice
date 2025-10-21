package com.github.unreference.splice.data;

import com.github.unreference.splice.data.models.SpliceBlockStateProvider;
import com.github.unreference.splice.data.models.SpliceItemModelProvider;
import com.github.unreference.splice.data.recipes.SpliceRecipeProvider;
import com.github.unreference.splice.data.tags.SpliceBannerPatternTagsProvider;
import com.github.unreference.splice.data.tags.SpliceBlockTagsProvider;
import com.github.unreference.splice.data.tags.SpliceItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class SpliceDataGenerator {
  public static void onGatherData(GatherDataEvent event) {
    var generator = event.getGenerator();
    var output = generator.getPackOutput();
    var helper = event.getExistingFileHelper();
    var provider = event.getLookupProvider();

    // Client
    var itemModel = new SpliceItemModelProvider(output, helper);
    generator.addProvider(event.includeClient(), itemModel);

    var blockState = new SpliceBlockStateProvider(output, helper);
    generator.addProvider(event.includeClient(), blockState);

    // Server
    var recipe = new SpliceRecipeProvider(output, provider);
    generator.addProvider(event.includeServer(), recipe);

    var blockTags = new SpliceBlockTagsProvider(output, provider, helper);
    generator.addProvider(event.includeServer(), blockTags);

    var itemTags = new SpliceItemTagsProvider(output, provider, blockTags.contentsGetter(), helper);
    generator.addProvider(event.includeServer(), itemTags);

    var bannerPattern = new SpliceBannerPatternTagsProvider(output, provider, helper);
    generator.addProvider(event.includeServer(), bannerPattern);

    var dataMaps = new SpliceDataMapsProvider(output, provider);
    generator.addProvider(event.includeServer(), dataMaps);
  }
}
