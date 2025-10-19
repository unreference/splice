package com.github.unreference.splice.world.item;

import com.github.unreference.splice.world.level.block.SpliceWeatheringCopperBlocks;
import com.google.common.collect.ImmutableBiMap;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public record SpliceWeatheringCopperItems(
    DeferredItem<BlockItem> unaffected,
    DeferredItem<BlockItem> exposed,
    DeferredItem<BlockItem> weathered,
    DeferredItem<BlockItem> oxidized,
    DeferredItem<BlockItem> waxed,
    DeferredItem<BlockItem> waxedExposed,
    DeferredItem<BlockItem> waxedWeathered,
    DeferredItem<BlockItem> waxedOxidized) {
  public static SpliceWeatheringCopperItems create(
      SpliceWeatheringCopperBlocks weatheringCopperBlocks,
      Function<DeferredBlock<? extends Block>, DeferredItem<BlockItem>> weather) {
    return new SpliceWeatheringCopperItems(
        weather.apply(weatheringCopperBlocks.unaffected()),
        weather.apply(weatheringCopperBlocks.exposed()),
        weather.apply(weatheringCopperBlocks.weathered()),
        weather.apply(weatheringCopperBlocks.oxidized()),
        weather.apply(weatheringCopperBlocks.waxed()),
        weather.apply(weatheringCopperBlocks.waxedExposed()),
        weather.apply(weatheringCopperBlocks.waxedWeathered()),
        weather.apply(weatheringCopperBlocks.waxedOxidized()));
  }

  public ImmutableBiMap<DeferredItem<BlockItem>, DeferredItem<BlockItem>> waxedMapping() {
    return ImmutableBiMap.of(
        this.unaffected,
        this.waxed,
        this.exposed,
        this.waxedExposed,
        this.weathered,
        this.waxedWeathered,
        this.oxidized,
        this.waxedOxidized);
  }

  public void forEach(Consumer<DeferredItem<BlockItem>> consumer) {
    consumer.accept(this.unaffected);
    consumer.accept(this.exposed);
    consumer.accept(this.weathered);
    consumer.accept(this.oxidized);
    consumer.accept(this.waxed);
    consumer.accept(this.waxedExposed);
    consumer.accept(this.waxedWeathered);
    consumer.accept(this.waxedOxidized);
  }
}
