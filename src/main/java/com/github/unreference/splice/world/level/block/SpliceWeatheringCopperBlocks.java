package com.github.unreference.splice.world.level.block;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.apache.commons.lang3.function.TriFunction;

public record SpliceWeatheringCopperBlocks(
    DeferredBlock<? extends Block> unaffected,
    DeferredBlock<? extends Block> exposed,
    DeferredBlock<? extends Block> weathered,
    DeferredBlock<? extends Block> oxidized,
    DeferredBlock<? extends Block> waxed,
    DeferredBlock<? extends Block> waxedExposed,
    DeferredBlock<? extends Block> waxedWeathered,
    DeferredBlock<? extends Block> waxedOxidized,
    ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>> weatheringMap,
    ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>> waxedMap,
    ImmutableList<DeferredBlock<? extends Block>> all) {
  public static <Waxed extends Block, Weathering extends Block & WeatheringCopper>
      SpliceWeatheringCopperBlocks create(
          String base,
          TriFunction<
                  String,
                  Function<BlockBehaviour.Properties, ? extends Block>,
                  BlockBehaviour.Properties,
                  DeferredBlock<? extends Block>>
              register,
          Function<BlockBehaviour.Properties, ? extends Waxed> waxed,
          BiFunction<WeatheringCopper.WeatherState, BlockBehaviour.Properties, ? extends Weathering>
              weathering,
          Function<WeatheringCopper.WeatherState, BlockBehaviour.Properties> weatherProps) {
    final var PROPS_UNAFFECTED = weatherProps.apply(WeatheringCopper.WeatherState.UNAFFECTED);
    final var PROPS_EXPOSED = weatherProps.apply(WeatheringCopper.WeatherState.EXPOSED);
    final var PROPS_WEATHERED = weatherProps.apply(WeatheringCopper.WeatherState.WEATHERED);
    final var PROPS_OXIDIZED = weatherProps.apply(WeatheringCopper.WeatherState.OXIDIZED);

    final var UNAFFECTED =
        register.apply(
            base,
            props -> weathering.apply(WeatheringCopper.WeatherState.UNAFFECTED, props),
            PROPS_UNAFFECTED);

    final var EXPOSED =
        register.apply(
            "exposed_" + base,
            props -> weathering.apply(WeatheringCopper.WeatherState.EXPOSED, props),
            PROPS_EXPOSED);

    final var WEATHERED =
        register.apply(
            "weathered_" + base,
            props -> weathering.apply(WeatheringCopper.WeatherState.WEATHERED, props),
            PROPS_WEATHERED);

    final var OXIDIZED =
        register.apply(
            "oxidized_" + base,
            props -> weathering.apply(WeatheringCopper.WeatherState.OXIDIZED, props),
            PROPS_OXIDIZED);

    final var WAXED = register.apply("waxed_" + base, waxed, PROPS_UNAFFECTED);
    final var WAXED_EXPOSED = register.apply("waxed_exposed_" + base, waxed, PROPS_EXPOSED);
    final var WAXED_WEATHERED = register.apply("waxed_weathered_" + base, waxed, PROPS_WEATHERED);
    final var WAXED_OXIDIZED = register.apply("waxed_oxidized_" + base, waxed, PROPS_OXIDIZED);

    final var WEATHERING_MAP =
        ImmutableBiMap.of(UNAFFECTED, EXPOSED, EXPOSED, WEATHERED, WEATHERED, OXIDIZED);

    final var WAXED_MAP =
        ImmutableBiMap.of(
            UNAFFECTED,
            WAXED,
            EXPOSED,
            WAXED_EXPOSED,
            WEATHERED,
            WAXED_WEATHERED,
            OXIDIZED,
            WAXED_OXIDIZED);

    final var ALL =
        ImmutableList.of(
            UNAFFECTED,
            WAXED,
            EXPOSED,
            WAXED_EXPOSED,
            WEATHERED,
            WAXED_WEATHERED,
            OXIDIZED,
            WAXED_OXIDIZED);

    return new SpliceWeatheringCopperBlocks(
        UNAFFECTED,
        EXPOSED,
        WEATHERED,
        OXIDIZED,
        WAXED,
        WAXED_EXPOSED,
        WAXED_WEATHERED,
        WAXED_OXIDIZED,
        WEATHERING_MAP,
        WAXED_MAP,
        ALL);
  }

  public ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
      weatheringMapping() {
    return weatheringMap;
  }

  public ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
      waxedMapping() {
    return waxedMap;
  }

  public ImmutableList<DeferredBlock<? extends Block>> asList() {
    return all;
  }

  public void forEach(Consumer<DeferredBlock<? extends Block>> consumer) {
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
