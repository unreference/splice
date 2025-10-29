package com.github.unreference.splice.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import org.jetbrains.annotations.NotNull;

public final class SpliceResinClumpBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
  public static final MapCodec<SpliceResinClumpBlock> CODEC =
      simpleCodec(SpliceResinClumpBlock::new);

  public SpliceResinClumpBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected @NotNull MapCodec<SpliceResinClumpBlock> codec() {
    return CODEC;
  }

  @Override
  public MultifaceSpreader getSpreader() {
    return null;
  }
}
