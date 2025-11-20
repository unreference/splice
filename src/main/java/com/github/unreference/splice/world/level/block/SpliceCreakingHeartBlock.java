package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.world.level.block.entity.SpliceCreakingHeartBlockEntity;
import com.github.unreference.splice.world.level.block.state.properties.SpliceBlockStateProperties;
import com.github.unreference.splice.world.level.block.state.properties.SpliceCreakingHeartState;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public final class SpliceCreakingHeartBlock extends BaseEntityBlock {
  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
  public static final EnumProperty<SpliceCreakingHeartState> STATE =
      SpliceBlockStateProperties.CREAKING_HEART_STATE;
  public static final BooleanProperty IS_NATURAL = SpliceBlockStateProperties.IS_NATURAL;
  public static final MapCodec<SpliceCreakingHeartBlock> CODEC =
      simpleCodec(SpliceCreakingHeartBlock::new);

  public SpliceCreakingHeartBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(
        this.defaultBlockState()
            .setValue(AXIS, Direction.Axis.Y)
            .setValue(STATE, SpliceCreakingHeartState.UPROOTED)
            .setValue(IS_NATURAL, false));
  }

  @Override
  protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    return new SpliceCreakingHeartBlockEntity(pos, state);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(AXIS, STATE, IS_NATURAL);
  }

  @Override
  protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.MODEL;
  }
}
