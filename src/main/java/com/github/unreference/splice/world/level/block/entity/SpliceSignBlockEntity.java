package com.github.unreference.splice.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class SpliceSignBlockEntity extends SignBlockEntity {
  public SpliceSignBlockEntity(BlockPos pos, BlockState blockState) {
    super(SpliceBlockEntityType.SIGN.get(), pos, blockState);
  }
}
