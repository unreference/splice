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
    final var UNAFFECTED = weathering.apply(blocks.unaffected());
    final var EXPOSED = weathering.apply(blocks.exposed());
    final var WEATHERED = weathering.apply(blocks.weathered());
    final var OXIDIZED = weathering.apply(blocks.oxidized());

    final var WAXED = weathering.apply(blocks.waxed());
    final var WAXED_EXPOSED = weathering.apply(blocks.waxedExposed());
    final var WAXED_WEATHERED = weathering.apply(blocks.waxedWeathered());
    final var WAXED_OXIDIZED = weathering.apply(blocks.waxedOxidized());

    final var MAP =
        ImmutableBiMap.of(
            UNAFFECTED,
            WAXED,
            EXPOSED,
            WAXED_EXPOSED,
            WEATHERED,
            WAXED_WEATHERED,
            OXIDIZED,
            WAXED_OXIDIZED);

    return new SpliceWeatheringCopperItems(
        UNAFFECTED,
        EXPOSED,
        WEATHERED,
        OXIDIZED,
        WAXED,
        WAXED_EXPOSED,
        WAXED_WEATHERED,
        WAXED_OXIDIZED,
        MAP);
  }

  public ImmutableBiMap<DeferredItem<BlockItem>, DeferredItem<BlockItem>> waxedMapping() {
    return waxedMap;
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
