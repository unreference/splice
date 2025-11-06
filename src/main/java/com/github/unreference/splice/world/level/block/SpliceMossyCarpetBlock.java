package com.github.unreference.splice.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpliceMossyCarpetBlock extends Block implements BonemealableBlock {
  public static final BooleanProperty IS_BASE = BlockStateProperties.BOTTOM;
  public static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
  public static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
  public static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
  public static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
  public static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION =
      ImmutableMap.copyOf(
          Maps.newEnumMap(
              Map.of(
                  Direction.NORTH, NORTH,
                  Direction.SOUTH, SOUTH,
                  Direction.EAST, EAST,
                  Direction.WEST, WEST)));
  private static final VoxelShape UP_SHAPE = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
  private static final VoxelShape DOWN_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
  private static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
  private static final VoxelShape SOUTH_SHAPE = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
  private static final VoxelShape EAST_SHAPE = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
  private static final VoxelShape WEST_SHAPE = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
  private static final Map<Direction, VoxelShape> TALL_SIDE =
      ImmutableMap.of(
          Direction.UP,
          UP_SHAPE,
          Direction.DOWN,
          DOWN_SHAPE,
          Direction.NORTH,
          NORTH_SHAPE,
          Direction.SOUTH,
          SOUTH_SHAPE,
          Direction.EAST,
          EAST_SHAPE,
          Direction.WEST,
          WEST_SHAPE);
  private static final Map<Direction, VoxelShape> LOW_SIDE =
      ImmutableMap.of(
          Direction.UP,
          Shapes.empty(),
          Direction.DOWN,
          DOWN_SHAPE,
          Direction.NORTH,
          NORTH_SHAPE,
          Direction.SOUTH,
          SOUTH_SHAPE,
          Direction.EAST,
          EAST_SHAPE,
          Direction.WEST,
          WEST_SHAPE);
  public static final MapCodec<SpliceMossyCarpetBlock> CODEC =
      simpleCodec(SpliceMossyCarpetBlock::new);
  private final Function<BlockState, VoxelShape> shapes;

  public SpliceMossyCarpetBlock(Properties properties) {
    super(properties);

    this.registerDefaultState(
        this.getStateDefinition()
            .any()
            .setValue(IS_BASE, true)
            .setValue(NORTH, WallSide.NONE)
            .setValue(SOUTH, WallSide.NONE)
            .setValue(EAST, WallSide.NONE)
            .setValue(WEST, WallSide.NONE));

    this.shapes = this.makeShapes();
  }

  private static boolean hasFaces(BlockState state) {
    if (state.getValue(IS_BASE)) {
      return true;
    }

    for (EnumProperty<WallSide> direction : PROPERTY_BY_DIRECTION.values()) {
      if (state.getValue(direction) != WallSide.NONE) {
        return true;
      }
    }

    return false;
  }

  private static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
    if (direction == Direction.UP) {
      return false;
    }

    final BlockPos neighborPos = pos.relative(direction);
    final BlockState neighborState = level.getBlockState(neighborPos);

    return MultifaceBlock.canAttachTo(level, direction.getOpposite(), neighborPos, neighborState);
  }

  private static BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos) {
    final Block paleMossCarpet = SpliceBlocks.PALE_MOSS_CARPET.get();

    if (!state.getValue(IS_BASE)) {
      final BlockState below = level.getBlockState(pos.below());
      if (below.is(paleMossCarpet) && below.getValue(IS_BASE)) {
        BlockState updated = state;

        for (Map.Entry<Direction, EnumProperty<WallSide>> entry :
            PROPERTY_BY_DIRECTION.entrySet()) {
          final WallSide side = below.getValue(entry.getValue());
          updated =
              updated.setValue(
                  entry.getValue(), side == WallSide.TALL ? WallSide.LOW : WallSide.NONE);
        }

        return updated;
      }

      return Blocks.AIR.defaultBlockState();
    }

    BlockState updated = state;
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      final EnumProperty<WallSide> side = getPropertyForFace(direction);

      if (canSupportAtFace(level, pos, direction)) {
        if (updated.getValue(side) == WallSide.NONE) {
          updated = updated.setValue(side, WallSide.LOW);
        }
      } else {
        updated = updated.setValue(side, WallSide.NONE);
      }
    }

    return updated;
  }

  private static EnumProperty<WallSide> getPropertyForFace(Direction direction) {
    return PROPERTY_BY_DIRECTION.get(direction);
  }

  public static void placeAt(LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
    final BlockState state = SpliceBlocks.PALE_MOSS_CARPET.get().defaultBlockState();
    final BlockState base = getUpdatedState(state, level, pos);
    level.setBlock(pos, base, flags);

    final BlockState topper = createTopperWithSideChance(level, pos, random::nextBoolean);
    if (!topper.isAir()) {
      BlockState updated = level.getBlockState(pos);

      for (Direction direction : Direction.Plane.HORIZONTAL) {
        if (topper.getValue(getPropertyForFace(direction)) == WallSide.LOW) {
          updated = updated.setValue(getPropertyForFace(direction), WallSide.TALL);
        }
      }

      level.setBlock(pos, updated, flags);
      level.setBlock(pos.above(), topper, flags);
    }
  }

  private static BlockState createTopperWithSideChance(
      LevelReader level, BlockPos pos, BooleanSupplier supplier) {
    final BlockState state = level.getBlockState(pos);
    final Block paleMossCarpet = SpliceBlocks.PALE_MOSS_CARPET.get();

    if (!state.is(paleMossCarpet) || !state.getValue(IS_BASE)) {
      return Blocks.AIR.defaultBlockState();
    }

    final BlockState above = level.getBlockState(pos.above());
    BlockState topper = Blocks.AIR.defaultBlockState();
    boolean isTopperPlaceable = false;

    if (above.isAir()) {
      topper = paleMossCarpet.defaultBlockState().setValue(IS_BASE, false);
      isTopperPlaceable = true;
    } else if (above.is(paleMossCarpet) && !above.getValue(IS_BASE)) {
      topper = above;
      isTopperPlaceable = true;
    }

    if (!isTopperPlaceable) {
      return Blocks.AIR.defaultBlockState();
    }

    boolean isTopperGrowing = false;
    for (Direction direction : Direction.Plane.HORIZONTAL) {
      if (state.getValue(getPropertyForFace(direction)) == WallSide.LOW) {
        final BlockPos neighbor = pos.relative(direction);
        final boolean isTwoBlocksTall =
            level.getBlockState(neighbor).isFaceSturdy(level, neighbor, direction.getOpposite())
                && level
                    .getBlockState(neighbor.above())
                    .isFaceSturdy(level, neighbor.above(), direction.getOpposite());

        if (isTwoBlocksTall && supplier.getAsBoolean()) {
          topper = topper.setValue(getPropertyForFace(direction), WallSide.LOW);
          isTopperGrowing = true;
        }
      }
    }

    return isTopperGrowing ? topper : Blocks.AIR.defaultBlockState();
  }

  @Override
  protected @NotNull VoxelShape getOcclusionShape(
      @NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
    return Shapes.empty();
  }

  private Function<BlockState, VoxelShape> makeShapes() {
    return (state) -> {
      VoxelShape shape = state.getValue(IS_BASE) ? DOWN_SHAPE : Shapes.empty();

      for (Map.Entry<Direction, EnumProperty<WallSide>> entry : PROPERTY_BY_DIRECTION.entrySet()) {
        switch (state.getValue(entry.getValue())) {
          case LOW:
            shape = Shapes.or(shape, LOW_SIDE.get(entry.getKey()));
            break;
          case TALL:
            shape = Shapes.or(shape, TALL_SIDE.get(entry.getKey()));
            break;
          default:
            break;
        }
      }

      return shape.isEmpty() ? Shapes.block() : shape;
    };
  }

  @Override
  protected @NotNull VoxelShape getShape(
      @NotNull BlockState state,
      @NotNull BlockGetter level,
      @NotNull BlockPos pos,
      @NotNull CollisionContext context) {
    return this.shapes.apply(state);
  }

  @Override
  protected @NotNull VoxelShape getCollisionShape(
      BlockState state,
      @NotNull BlockGetter level,
      @NotNull BlockPos pos,
      @NotNull CollisionContext context) {
    return state.getValue(IS_BASE) ? DOWN_SHAPE : Shapes.empty();
  }

  @Override
  protected boolean propagatesSkylightDown(
      @NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
    return true;
  }

  @Override
  protected boolean canSurvive(
      @NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
    final BlockState stateBelow = level.getBlockState(pos.below());

    if (state.getValue(IS_BASE)) {
      return stateBelow.isFaceSturdy(level, pos.below(), Direction.UP);
    }

    return stateBelow.is(this) && stateBelow.getValue(IS_BASE);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
    return getUpdatedState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
  }

  @Override
  public void setPlacedBy(
      Level level,
      @NotNull BlockPos pos,
      @NotNull BlockState state,
      @Nullable LivingEntity placer,
      @NotNull ItemStack stack) {
    if (!level.isClientSide()) {
      final RandomSource random = level.getRandom();
      final BlockState topper = createTopperWithSideChance(level, pos, random::nextBoolean);

      if (!topper.isAir()) {
        BlockState base = level.getBlockState(pos);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
          if (topper.getValue(getPropertyForFace(direction)) == WallSide.LOW) {
            base = base.setValue(getPropertyForFace(direction), WallSide.TALL);
          }
        }

        level.setBlock(pos, base, 3);
        level.setBlock(pos.above(), topper, 3);
      }
    }
  }

  @Override
  protected @NotNull BlockState updateShape(
      BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState neighborState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos neighborPos) {
    if (!state.canSurvive(level, pos)) {
      return Blocks.AIR.defaultBlockState();
    }

    if (!state.getValue(IS_BASE)) {
      if (direction == Direction.DOWN) {
        final BlockState updated = getUpdatedState(state, level, pos);
        return hasFaces(updated) ? updated : Blocks.AIR.defaultBlockState();
      }

      return state;
    }

    if (direction.getAxis().isHorizontal()) {
      return getUpdatedState(state, level, pos);
    }

    if (direction == Direction.UP) {
      if (!neighborState.is(this) || neighborState.getValue(IS_BASE)) {
        BlockState updated = state;

        for (Direction horizontal : Direction.Plane.HORIZONTAL) {
          if (updated.getValue(getPropertyForFace(horizontal)) == WallSide.TALL) {
            if (canSupportAtFace(level, pos, horizontal)) {
              updated = updated.setValue(getPropertyForFace(horizontal), WallSide.LOW);
            } else {
              updated = updated.setValue(getPropertyForFace(horizontal), WallSide.NONE);
            }
          }
        }

        return updated;
      }
    }

    return state;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(IS_BASE, NORTH, SOUTH, EAST, WEST);
  }

  @Override
  protected @NotNull BlockState rotate(@NotNull BlockState state, Rotation rotation) {
    return switch (rotation) {
      case CLOCKWISE_90 ->
          state
              .setValue(NORTH, state.getValue(WEST))
              .setValue(SOUTH, state.getValue(EAST))
              .setValue(EAST, state.getValue(NORTH))
              .setValue(WEST, state.getValue(SOUTH));
      case CLOCKWISE_180 ->
          state
              .setValue(NORTH, state.getValue(SOUTH))
              .setValue(SOUTH, state.getValue(NORTH))
              .setValue(EAST, state.getValue(WEST))
              .setValue(WEST, state.getValue(EAST));
      case COUNTERCLOCKWISE_90 ->
          state
              .setValue(NORTH, state.getValue(EAST))
              .setValue(SOUTH, state.getValue(WEST))
              .setValue(EAST, state.getValue(SOUTH))
              .setValue(WEST, state.getValue(NORTH));
      default -> state;
    };
  }

  @Override
  protected @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
    return switch (mirror) {
      case LEFT_RIGHT ->
          state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
      case FRONT_BACK ->
          state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
      default -> super.mirror(state, mirror);
    };
  }

  @Override
  public boolean isValidBonemealTarget(
      @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
    return true;
  }

  @Override
  public boolean isBonemealSuccess(
      @NotNull Level level,
      @NotNull RandomSource random,
      @NotNull BlockPos pos,
      @NotNull BlockState state) {
    return true;
  }

  @Override
  public void performBonemeal(
      @NotNull ServerLevel level,
      @NotNull RandomSource random,
      @NotNull BlockPos pos,
      @NotNull BlockState state) {
    final BlockState topper = createTopperWithSideChance(level, pos, () -> true);
    if (!topper.isAir()) {
      BlockState base = level.getBlockState(pos);

      for (Direction direction : Direction.Plane.HORIZONTAL) {
        if (topper.getValue(getPropertyForFace(direction)) == WallSide.LOW) {
          base = base.setValue(getPropertyForFace(direction), WallSide.TALL);
        }
      }

      level.setBlock(pos, base, 3);
      level.setBlock(pos.above(), topper, 3);
    }
  }
}
