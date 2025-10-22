package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.tags.SpliceBlockTags;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.neoforge.common.DataMapHooks;

public class SpliceCopperChestBlock extends ChestBlock {
  public static final MapCodec<SpliceCopperChestBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      WeatheringCopper.WeatherState.CODEC
                          .fieldOf("weathering_state")
                          .forGetter(SpliceCopperChestBlock::getState),
                      BuiltInRegistries.SOUND_EVENT
                          .holderByNameCodec()
                          .fieldOf("open_sound")
                          .forGetter(SpliceCopperChestBlock::getOpenSound),
                      BuiltInRegistries.SOUND_EVENT
                          .holderByNameCodec()
                          .fieldOf("close_sound")
                          .forGetter(SpliceCopperChestBlock::getCloseSound),
                      propertiesCodec())
                  .apply(instance, SpliceCopperChestBlock::new));

  private final WeatheringCopper.WeatherState weatherState;
  private final Holder<SoundEvent> openSound;
  private final Holder<SoundEvent> closeSound;

  public SpliceCopperChestBlock(
      WeatheringCopper.WeatherState weatherState,
      Holder<SoundEvent> openSound,
      Holder<SoundEvent> closeSound,
      Properties properties) {
    super(properties, SpliceBlockEntityType.COPPER_CHEST::get);
    this.weatherState = weatherState;
    this.openSound = openSound;
    this.closeSound = closeSound;
  }

  private static BlockState getLeastWeatheredChest(
      BlockState state, Level level, BlockPos clickedPos) {
    final var STATE_2 = level.getBlockState(clickedPos.relative(getConnectedDirection(state)));
    if (!state.getValue(ChestBlock.TYPE).equals(ChestType.SINGLE)
        && state.getBlock() instanceof SpliceCopperChestBlock chest
        && STATE_2.getBlock() instanceof SpliceCopperChestBlock chest2) {

      var state3 = state;
      var state4 = STATE_2;

      if (chest.isWaxed() != chest2.isWaxed()) {
        state3 = unwax(chest, state).orElse(state);
        state4 = unwax(chest2, STATE_2).orElse(STATE_2);
      }

      final var BLOCK =
          chest.weatherState.ordinal() <= chest2.weatherState.ordinal()
              ? state3.getBlock()
              : state4.getBlock();
      return BLOCK.withPropertiesOf(state3);
    }

    return state;
  }

  private static Optional<BlockState> unwax(SpliceCopperChestBlock copperChest, BlockState state) {
    if (!copperChest.isWaxed()) {
      return Optional.of(state);
    }

    var unwaxed = DataMapHooks.getBlockUnwaxed(state.getBlock());
    return Optional.ofNullable(unwaxed).map(block -> block.withPropertiesOf(state));
  }

  public WeatheringCopper.WeatherState getState() {
    return this.weatherState;
  }

  public Holder<SoundEvent> getOpenSound() {
    return this.openSound;
  }

  public Holder<SoundEvent> getCloseSound() {
    return this.closeSound;
  }

  private boolean isConnectable(BlockState state) {
    return state.is(SpliceBlockTags.COPPER_CHESTS) && state.hasProperty(ChestBlock.TYPE);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    var state = super.getStateForPlacement(context);
    return getLeastWeatheredChest(state, context.getLevel(), context.getClickedPos());
  }

  @Override
  protected BlockState updateShape(
      BlockState state,
      Direction facing,
      BlockState facingState,
      LevelAccessor level,
      BlockPos currentPos,
      BlockPos facingPos) {
    final var STATE_3 = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    if (this.isConnectable(facingState)) {
      var chestType = STATE_3.getValue(ChestBlock.TYPE);
      if (!chestType.equals(ChestType.SINGLE) && getConnectedDirection(STATE_3) == facing) {
        return facingState.getBlock().withPropertiesOf(STATE_3);
      }
    }

    return STATE_3;
  }

  public boolean isWaxed() {
    return true;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new SpliceCopperChestBlockEntity(pos, state);
  }
}
