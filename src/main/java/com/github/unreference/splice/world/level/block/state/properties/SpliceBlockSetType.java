package com.github.unreference.splice.world.level.block.state.properties;

import net.minecraft.world.level.block.state.properties.BlockSetType;

public final class SpliceBlockSetType {
  public static final BlockSetType PALE_OAK = register(new BlockSetType("pale_oak"));

  private static BlockSetType register(BlockSetType blockSetType) {
    return BlockSetType.register(blockSetType);
  }
}
