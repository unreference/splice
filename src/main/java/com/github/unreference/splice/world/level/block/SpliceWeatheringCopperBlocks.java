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
    DeferredBlock<? extends Block> waxedOxidized) {
  public static <WaxedBlock extends Block, WeatheringBlock extends Block & WeatheringCopper>
      SpliceWeatheringCopperBlocks create(
          String base,
          TriFunction<
                  String,
                  Function<BlockBehaviour.Properties, ? extends Block>,
                  BlockBehaviour.Properties,
                  DeferredBlock<? extends Block>>
              register,
          Function<BlockBehaviour.Properties, ? extends WaxedBlock> waxed,
          BiFunction<
                  WeatheringCopper.WeatherState,
                  BlockBehaviour.Properties,
                  ? extends WeatheringBlock>
              weathering,
          Function<WeatheringCopper.WeatherState, BlockBehaviour.Properties> weatherProps) {
    return new SpliceWeatheringCopperBlocks(
        register.apply(
            base,
            props -> weathering.apply(WeatheringCopper.WeatherState.UNAFFECTED, props),
            weatherProps.apply(WeatheringCopper.WeatherState.UNAFFECTED)),
        register.apply(
            "exposed_%s".formatted(base),
            props -> weathering.apply(WeatheringCopper.WeatherState.EXPOSED, props),
            weatherProps.apply(WeatheringCopper.WeatherState.EXPOSED)),
        register.apply(
            "weathered_%s".formatted(base),
            props -> weathering.apply(WeatheringCopper.WeatherState.WEATHERED, props),
            weatherProps.apply(WeatheringCopper.WeatherState.WEATHERED)),
        register.apply(
            "oxidized_%s".formatted(base),
            props -> weathering.apply(WeatheringCopper.WeatherState.OXIDIZED, props),
            weatherProps.apply(WeatheringCopper.WeatherState.OXIDIZED)),
        register.apply(
            "waxed_%s".formatted(base),
            waxed,
            weatherProps.apply(WeatheringCopper.WeatherState.UNAFFECTED)),
        register.apply(
            "waxed_exposed_%s".formatted(base),
            waxed,
            weatherProps.apply(WeatheringCopper.WeatherState.EXPOSED)),
        register.apply(
            "waxed_weathered_%s".formatted(base),
            waxed,
            weatherProps.apply(WeatheringCopper.WeatherState.WEATHERED)),
        register.apply(
            "waxed_oxidized_%s".formatted(base),
            waxed,
            weatherProps.apply(WeatheringCopper.WeatherState.OXIDIZED)));
  }

  public ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
      weatheringMapping() {
    return ImmutableBiMap.of(
        this.unaffected, this.exposed, this.exposed, this.weathered, this.weathered, this.oxidized);
  }

  public ImmutableBiMap<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
      waxedMapping() {
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

  public ImmutableList<DeferredBlock<? extends Block>> asList() {
    return ImmutableList.of(
        this.unaffected,
        this.waxed,
        this.exposed,
        this.waxedExposed,
        this.weathered,
        this.waxedWeathered,
        this.oxidized,
        this.waxedOxidized);
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
