package com.github.unreference.splice.world.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class SpliceBlock {
  public static VoxelShape column(double x, double y, double z) {
    return column(x, x, y, z);
  }

  public static VoxelShape column(
      double p_393678_, double p_394077_, double p_394409_, double p_394538_) {
    double d0 = p_393678_ / 2.0;
    double d1 = p_394077_ / 2.0;
    return Block.box(8.0 - d0, p_394409_, 8.0 - d1, 8.0 + d0, p_394538_, 8.0 + d1);
  }
}
