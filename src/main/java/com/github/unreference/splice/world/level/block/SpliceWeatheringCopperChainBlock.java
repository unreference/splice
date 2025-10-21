package com.github.unreference.splice.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public final class SpliceWeatheringCopperChainBlock extends ChainBlock implements WeatheringCopper {
  private static final MapCodec<SpliceWeatheringCopperChainBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      WeatherState.CODEC
                          .fieldOf("weathering_state")
                          .forGetter(SpliceWeatheringCopperChainBlock::getAge),
                      propertiesCodec())
                  .apply(instance, SpliceWeatheringCopperChainBlock::new));

  private final WeatherState weatherState;

  public SpliceWeatheringCopperChainBlock(WeatherState weatherState, Properties properties) {
    super(properties);
    this.weatherState = weatherState;
  }

  public static MapCodec<SpliceWeatheringCopperChainBlock> getCodec() {
    return CODEC;
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
