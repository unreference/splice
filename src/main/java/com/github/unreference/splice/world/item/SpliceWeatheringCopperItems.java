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
    DeferredItem<BlockItem> waxedOxidized,
    ImmutableBiMap<DeferredItem<BlockItem>, DeferredItem<BlockItem>> waxedMap) {
  public static SpliceWeatheringCopperItems create(
      SpliceWeatheringCopperBlocks blocks,
      Function<DeferredBlock<? extends Block>, DeferredItem<BlockItem>> weathering) {
    final DeferredItem<BlockItem> unaffected = weathering.apply(blocks.unaffected());
    final DeferredItem<BlockItem> exposed = weathering.apply(blocks.exposed());
    final DeferredItem<BlockItem> weathered = weathering.apply(blocks.weathered());
    final DeferredItem<BlockItem> oxidized = weathering.apply(blocks.oxidized());

    final DeferredItem<BlockItem> waxed = weathering.apply(blocks.waxed());
    final DeferredItem<BlockItem> waxedExposed = weathering.apply(blocks.waxedExposed());
    final DeferredItem<BlockItem> waxedWeathered = weathering.apply(blocks.waxedWeathered());
    final DeferredItem<BlockItem> waxedOxidized = weathering.apply(blocks.waxedOxidized());

    final ImmutableBiMap<DeferredItem<BlockItem>, DeferredItem<BlockItem>> map =
        ImmutableBiMap.of(
            unaffected,
            waxed,
            exposed,
            waxedExposed,
            weathered,
            waxedWeathered,
            oxidized,
            waxedOxidized);

    return new SpliceWeatheringCopperItems(
        unaffected,
        exposed,
        weathered,
        oxidized,
        waxed,
        waxedExposed,
        waxedWeathered,
        waxedOxidized,
        map);
  }

  public void forEachInverse(Consumer<DeferredItem<BlockItem>> consumer) {
    consumer.accept(this.waxedOxidized);
    consumer.accept(this.waxedWeathered);
    consumer.accept(this.waxedExposed);
    consumer.accept(this.waxed);
    consumer.accept(this.oxidized);
    consumer.accept(this.weathered);
    consumer.accept(this.exposed);
    consumer.accept(this.unaffected);
  }
}
