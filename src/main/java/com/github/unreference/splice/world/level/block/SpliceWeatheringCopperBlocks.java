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
          Function<BlockBehaviour.Properties, ? extends Waxed> waxedFunction,
          BiFunction<WeatheringCopper.WeatherState, BlockBehaviour.Properties, ? extends Weathering>
              weatheringFunc,
          Function<WeatheringCopper.WeatherState, BlockBehaviour.Properties> weatherProps) {
    final BlockBehaviour.Properties propsUnaffected =
        weatherProps.apply(WeatheringCopper.WeatherState.UNAFFECTED);
    final BlockBehaviour.Properties propsExposed =
        weatherProps.apply(WeatheringCopper.WeatherState.EXPOSED);
    final BlockBehaviour.Properties propsWeathered =
        weatherProps.apply(WeatheringCopper.WeatherState.WEATHERED);
    final BlockBehaviour.Properties propsOxidized =
        weatherProps.apply(WeatheringCopper.WeatherState.OXIDIZED);

    final DeferredBlock<?> unaffected =
        register.apply(
            base,
            props -> weatheringFunc.apply(WeatheringCopper.WeatherState.UNAFFECTED, props),
            propsUnaffected);

    final DeferredBlock<?> exposed =
        register.apply(
            "exposed_" + base,
            props -> weatheringFunc.apply(WeatheringCopper.WeatherState.EXPOSED, props),
            propsExposed);

    final DeferredBlock<?> weathered =
        register.apply(
            "weathered_" + base,
            props -> weatheringFunc.apply(WeatheringCopper.WeatherState.WEATHERED, props),
            propsWeathered);

    final DeferredBlock<?> oxidized =
        register.apply(
            "oxidized_" + base,
            props -> weatheringFunc.apply(WeatheringCopper.WeatherState.OXIDIZED, props),
            propsOxidized);

    final DeferredBlock<?> waxed = register.apply("waxed_" + base, waxedFunction, propsUnaffected);
    final DeferredBlock<?> waxedExposed =
        register.apply("waxed_exposed_" + base, waxedFunction, propsExposed);
    final DeferredBlock<?> waxedWeathered =
        register.apply("waxed_weathered_" + base, waxedFunction, propsWeathered);
    final DeferredBlock<?> waxedOxidized =
        register.apply("waxed_oxidized_" + base, waxedFunction, propsOxidized);

    final ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
        weatheringMap =
            ImmutableBiMap.of(unaffected, exposed, exposed, weathered, weathered, oxidized);

    final ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>> waxedMap =
        ImmutableBiMap.of(
            unaffected,
            waxed,
            exposed,
            waxedExposed,
            weathered,
            waxedWeathered,
            oxidized,
            waxedOxidized);

    final ImmutableList<DeferredBlock<? extends Block>> all =
        ImmutableList.of(
            unaffected,
            waxed,
            exposed,
            waxedExposed,
            weathered,
            waxedWeathered,
            oxidized,
            waxedOxidized);

    return new SpliceWeatheringCopperBlocks(
        unaffected,
        exposed,
        weathered,
        oxidized,
        waxed,
        waxedExposed,
        waxedWeathered,
        waxedOxidized,
        weatheringMap,
        waxedMap,
        all);
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
