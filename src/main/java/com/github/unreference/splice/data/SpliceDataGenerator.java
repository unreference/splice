package com.github.unreference.splice.data;

import com.github.unreference.splice.client.model.SpliceItemModelProvider;
import com.github.unreference.splice.data.recipes.SpliceRecipeProvider;
import com.github.unreference.splice.data.tags.SpliceBannerPatternTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class SpliceDataGenerator {
  public static void onGatherData(GatherDataEvent event) {
    var generator = event.getGenerator();
    var output = generator.getPackOutput();
    var helper = event.getExistingFileHelper();
    var provider = event.getLookupProvider();

    var itemModel = new SpliceItemModelProvider(output, helper);
    generator.addProvider(event.includeClient(), itemModel);

    var recipe = new SpliceRecipeProvider(output, provider);
    generator.addProvider(event.includeServer(), recipe);

    var bannerPattern = new SpliceBannerPatternTagsProvider(output, provider, helper);
    generator.addProvider(event.includeServer(), bannerPattern);
  }
}
