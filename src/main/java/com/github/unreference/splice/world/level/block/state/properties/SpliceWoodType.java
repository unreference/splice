package com.github.unreference.splice.world.level.block.state.properties;

import net.minecraft.world.level.block.state.properties.WoodType;

public final class SpliceWoodType {
  public static final WoodType PALE_OAK =
      register(new WoodType("pale_oak", SpliceBlockSetType.PALE_OAK));

  private static WoodType register(WoodType woodType) {
    return WoodType.register(woodType);
  }
}
