package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.world.level.block.entity.SpliceSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

public final class SpliceStandingSignBlock extends StandingSignBlock {
  public SpliceStandingSignBlock(WoodType type, Properties properties) {
    super(type, properties);
  }

  @Override
  public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    return new SpliceSignBlockEntity(pos, state);
  }
}
