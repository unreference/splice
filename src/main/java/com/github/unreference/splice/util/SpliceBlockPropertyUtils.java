package com.github.unreference.splice.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;

public final class SpliceBlockPropertyUtils {
  public static Property<Boolean> getDirectionProperty(Direction direction) {
    return switch (direction) {
      case DOWN -> BlockStateProperties.DOWN;
      case UP -> BlockStateProperties.UP;
      case NORTH -> BlockStateProperties.NORTH;
      case SOUTH -> BlockStateProperties.SOUTH;
      case WEST -> BlockStateProperties.WEST;
      case EAST -> BlockStateProperties.EAST;
    };
  }

  public static EnumProperty<WallSide> getWallSideProperty(Direction direction) {
    return switch (direction) {
      case NORTH -> BlockStateProperties.NORTH_WALL;
      case SOUTH -> BlockStateProperties.SOUTH_WALL;
      case EAST -> BlockStateProperties.EAST_WALL;
      case WEST -> BlockStateProperties.WEST_WALL;
      default -> null;
    };
  }
}
