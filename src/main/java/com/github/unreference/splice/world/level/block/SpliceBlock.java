package com.github.unreference.splice.world.level.block;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class SpliceBlock {
  public static VoxelShape box(
      double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
    return Shapes.box(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0);
  }

  public static VoxelShape[] boxes(int maxIndex, IntFunction<VoxelShape> factory) {
    return IntStream.rangeClosed(0, maxIndex).mapToObj(factory).toArray(VoxelShape[]::new);
  }

  public static VoxelShape cube(double size) {
    return cube(size, size, size);
  }

  public static VoxelShape cube(double width, double height, double depth) {
    double halfHeight = height * 0.5;
    return column(width, depth, 8.0 - halfHeight, 8.0 + halfHeight);
  }

  public static VoxelShape column(double size, double minY, double maxY) {
    return column(size, size, minY, maxY);
  }

  public static VoxelShape column(double width, double depth, double minY, double maxY) {
    double halfWidth = width * 0.5;
    double halfDepth = depth * 0.5;
    return box(8.0 - halfWidth, minY, 8.0 - halfDepth, 8.0 + halfWidth, maxY, 8.0 + halfDepth);
  }

  public static VoxelShape boxZ(double size, double minZ, double maxZ) {
    return boxZ(size, size, minZ, maxZ);
  }

  public static VoxelShape boxZ(double width, double height, double minZ, double maxZ) {
    double halfHeight = height * 0.5;
    return boxZ(width, 8.0 - halfHeight, 8.0 + halfHeight, minZ, maxZ);
  }

  public static VoxelShape boxZ(double width, double minY, double maxY, double minZ, double maxZ) {
    double halfWidth = width * 0.5;
    return box(8.0 - halfWidth, minY, minZ, 8.0 + halfWidth, maxY, maxZ);
  }

  public static VoxelShape face(Direction direction) {
    return switch (direction) {
      case UP -> box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
      case DOWN -> box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
      case NORTH -> box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
      case SOUTH -> box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
      case EAST -> box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
      case WEST -> box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    };
  }
}
