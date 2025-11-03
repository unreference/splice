package com.github.unreference.splice.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class SpliceHangingSignBlockEntity extends HangingSignBlockEntity {
  public SpliceHangingSignBlockEntity(BlockPos pos, BlockState state) {
    super(pos, state);
  }

  @Override
  public @NotNull BlockEntityType<?> getType() {
    return SpliceBlockEntityType.HANGING_SIGN.get();
  }
}
