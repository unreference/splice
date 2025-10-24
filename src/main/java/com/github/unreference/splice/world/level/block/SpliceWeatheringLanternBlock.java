package com.github.unreference.splice.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class SpliceWeatheringLanternBlock extends LanternBlock implements WeatheringCopper {
  public static final MapCodec<SpliceWeatheringLanternBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      WeatherState.CODEC
                          .fieldOf("weathering_state")
                          .forGetter(SpliceWeatheringLanternBlock::getAge),
                      propertiesCodec())
                  .apply(instance, SpliceWeatheringLanternBlock::new));

  private final WeatherState weatherState;

  public SpliceWeatheringLanternBlock(WeatherState weatherState, Properties properties) {
    super(properties);
    this.weatherState = weatherState;
  }

  @Override
  public @NotNull WeatherState getAge() {
    return this.weatherState;
  }

  @Override
  protected void randomTick(
      @NotNull BlockState state,
      @NotNull ServerLevel level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    this.changeOverTime(state, level, pos, random);
  }

  @Override
  protected boolean isRandomlyTicking(BlockState state) {
    return WeatheringCopper.getNext(state.getBlock()).isPresent();
  }
}
