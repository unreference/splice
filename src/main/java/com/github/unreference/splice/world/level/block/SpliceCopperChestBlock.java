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
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.neoforge.common.DataMapHooks;
import org.jetbrains.annotations.NotNull;

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
    if (state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
      return state;
    }

    final Direction connectedDirection = getConnectedDirection(state);
    final BlockPos neighborPosition = clickedPos.relative(connectedDirection);
    final BlockState neighborState = level.getBlockState(neighborPosition);

    if (state.getBlock() instanceof SpliceCopperChestBlock thisChest
        && neighborState.getBlock() instanceof SpliceCopperChestBlock otherChest) {
      BlockState left = state;
      BlockState right = neighborState;

      if (thisChest.isWaxed() != otherChest.isWaxed()) {
        left = unwax(thisChest, left).orElse(left);
        right = unwax(otherChest, right).orElse(right);
      }

      final Block chosen =
          thisChest.weatherState.compareTo(otherChest.weatherState) <= 0
              ? left.getBlock()
              : right.getBlock();

      return chosen.withPropertiesOf(left);
    }

    return state;
  }

  private static Optional<BlockState> unwax(SpliceCopperChestBlock copperChest, BlockState state) {
    if (!copperChest.isWaxed()) {
      return Optional.of(state);
    }

    final Block unwaxed = DataMapHooks.getBlockUnwaxed(state.getBlock());
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
  public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
    final BlockState initial = super.getStateForPlacement(context);
    return getLeastWeatheredChest(initial, context.getLevel(), context.getClickedPos());
  }

  @Override
  protected @NotNull BlockState updateShape(
      @NotNull BlockState state,
      @NotNull Direction facing,
      @NotNull BlockState facingState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos currentPos,
      @NotNull BlockPos facingPos) {
    final ChestType prevType = state.getValue(ChestBlock.TYPE);
    final Direction prevConnection = getConnectedDirection(state);
    final BlockState updated =
        super.updateShape(state, facing, facingState, level, currentPos, facingPos);

    if (prevType != ChestType.SINGLE && facing == prevConnection && isConnectable(facingState)) {
      BlockState result = facingState.getBlock().withPropertiesOf(updated);

      if (result.hasProperty(ChestBlock.TYPE)) {
        result = result.setValue(ChestBlock.TYPE, prevType);
      }

      // TODO: Find a way to carry the particles over to connected chest.
      return result;
    }

    return updated;
  }

  @Override
  protected void onRemove(
      BlockState state,
      @NotNull Level level,
      @NotNull BlockPos pos,
      BlockState newState,
      boolean isMoving) {
    if (state.getBlock() == newState.getBlock()) {
      return;
    }

    if (newState.is(SpliceBlockTags.COPPER_CHESTS)) {
      return;
    }

    BlockEntity blockEntity = level.getBlockEntity(pos);
    if (blockEntity instanceof Container container) {
      Containers.dropContents(level, pos, container);
      level.updateNeighbourForOutputSignal(pos, this);
    }

    super.onRemove(state, level, pos, newState, isMoving);
  }

  public boolean isWaxed() {
    return true;
  }

  @Override
  public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    return new SpliceCopperChestBlockEntity(pos, state);
  }
}
