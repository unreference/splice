package com.github.unreference.splice.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SpliceCreakingHeartBlockEntity extends BlockEntity {
  public SpliceCreakingHeartBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    super(SpliceBlockEntityType.CREAKING_HEART.get(), pos, state);
  }
}
