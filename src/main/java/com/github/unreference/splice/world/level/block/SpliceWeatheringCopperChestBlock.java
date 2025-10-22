package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public final class SpliceWeatheringCopperChestBlock extends SpliceCopperChestBlock
    implements WeatheringCopper {
  public static final MapCodec<SpliceWeatheringCopperChestBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      WeatherState.CODEC
                          .fieldOf("weathering_state")
                          .forGetter(SpliceWeatheringCopperChestBlock::getState),
                      BuiltInRegistries.SOUND_EVENT
                          .holderByNameCodec()
                          .fieldOf("open_sound")
                          .forGetter(SpliceCopperChestBlock::getOpenSound),
                      BuiltInRegistries.SOUND_EVENT
                          .holderByNameCodec()
                          .fieldOf("close_sound")
                          .forGetter(SpliceCopperChestBlock::getCloseSound),
                      propertiesCodec())
                  .apply(instance, SpliceWeatheringCopperChestBlock::new));

  public SpliceWeatheringCopperChestBlock(
      WeatherState weatherState,
      Holder<SoundEvent> openSound,
      Holder<SoundEvent> closeSound,
      Properties properties) {
    super(weatherState, openSound, closeSound, properties);
  }

  @Override
  public WeatherState getAge() {
    return this.getState();
  }

  @Override
  public boolean isWaxed() {
    return false;
  }

  @Override
  protected void randomTick(
      BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (!(state.getValue(ChestBlock.TYPE)).equals(ChestType.RIGHT)
        && level.getBlockEntity(pos) instanceof SpliceCopperChestBlockEntity chestBlock
        && chestBlock.isEmpty()) {
      this.changeOverTime(state, level, pos, random);
    }
  }

  @Override
  protected boolean isRandomlyTicking(BlockState state) {
    return WeatheringCopper.getNext(state.getBlock()).isPresent();
  }
}
