package com.github.unreference.splice.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public final class SpliceWeatheringCopperBarsBlock extends IronBarsBlock
    implements WeatheringCopper {
  public static final MapCodec<SpliceWeatheringCopperBarsBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      WeatherState.CODEC
                          .fieldOf("weathering_state")
                          .forGetter(SpliceWeatheringCopperBarsBlock::getAge),
                      propertiesCodec())
                  .apply(instance, SpliceWeatheringCopperBarsBlock::new));

  private final WeatheringCopper.WeatherState weatherState;

  public SpliceWeatheringCopperBarsBlock(
      WeatheringCopper.WeatherState weatherState, Properties properties) {
    super(properties);
    this.weatherState = weatherState;
  }

  @Override
  protected void randomTick(
      BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.changeOverTime(state, level, pos, random);
  }

  @Override
  protected boolean isRandomlyTicking(BlockState state) {
    return WeatheringCopper.getNext(state.getBlock()).isPresent();
  }

  @Override
  public WeatherState getAge() {
    return this.weatherState;
  }
}
