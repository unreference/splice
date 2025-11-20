package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.github.unreference.splice.tags.SpliceBlockTags;
import com.github.unreference.splice.world.level.block.state.properties.SpliceBlockStateProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public final class SpliceHangingMossBlock extends Block implements BonemealableBlock {
  public static final BooleanProperty IS_JUST_THE_TIP = SpliceBlockStateProperties.IS_JUST_THE_TIP;
  public static final MapCodec<SpliceHangingMossBlock> CODEC =
      simpleCodec(SpliceHangingMossBlock::new);
  private static final VoxelShape SHAPE_BASE = SpliceBlock.column(14.0, 0.0, 16.0);
  private static final VoxelShape SHAPE_TIP = SpliceBlock.column(14.0, 2.0, 16.0);

  public SpliceHangingMossBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.getStateDefinition().any().setValue(IS_JUST_THE_TIP, true));
  }

  private static boolean canStayAtPosition(BlockGetter level, BlockPos pos) {
    final BlockPos up = pos.relative(Direction.UP);
    final BlockState state = level.getBlockState(up);
    return MultifaceBlock.canAttachTo(level, Direction.UP, up, state)
        || state.is(SpliceBlocks.PALE_HANGING_MOSS);
  }

  private static boolean canGrowInto(BlockState state) {
    return state.isAir();
  }

  @Override
  protected @NotNull VoxelShape getShape(
      BlockState state,
      @NotNull BlockGetter level,
      @NotNull BlockPos pos,
      @NotNull CollisionContext context) {
    return state.getValue(IS_JUST_THE_TIP) ? SHAPE_TIP : SHAPE_BASE;
  }

  @Override
  public void animateTick(
      @NotNull BlockState state,
      @NotNull Level level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (random.nextInt(500) == 0) {
      final BlockState blockState = level.getBlockState(pos.above());
      if (blockState.is(SpliceBlockTags.PALE_OAK_LOGS)
          || blockState.is(SpliceBlocks.PALE_OAK_LEAVES)) {
        level.playLocalSound(
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            SpliceSoundEvents.PALE_HANGING_MOSS_IDLE.get(),
            SoundSource.AMBIENT,
            1.0f,
            1.0f,
            false);
      }
    }
  }

  @Override
  protected boolean propagatesSkylightDown(
      @NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
    return true;
  }

  @Override
  protected boolean canSurvive(
      @NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
    return canStayAtPosition(level, pos);
  }

  @Override
  protected @NotNull BlockState updateShape(
      @NotNull BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState neighborState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos neighborPos) {
    if (!canStayAtPosition(level, pos)) {
      level.scheduleTick(pos, this, 1);
    }

    return state.setValue(IS_JUST_THE_TIP, !level.getBlockState(pos.below()).is(this));
  }

  @Override
  protected void tick(
      @NotNull BlockState state,
      @NotNull ServerLevel level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (!canStayAtPosition(level, pos)) {
      level.destroyBlock(pos, true);
    }
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(IS_JUST_THE_TIP);
  }

  @Override
  public boolean isValidBonemealTarget(
      @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
    return canGrowInto(level.getBlockState(this.getTip(level, pos).below()));
  }

  private BlockPos getTip(@NotNull LevelReader level, @NotNull BlockPos pos) {
    final BlockPos.MutableBlockPos mutable = pos.mutable();

    BlockState state;
    do {
      mutable.move(Direction.DOWN);
      state = level.getBlockState(mutable);
    } while (state.is(this));

    return mutable.relative(Direction.UP).immutable();
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
    final BlockPos tip = this.getTip(level, pos).below();
    if (canGrowInto(level.getBlockState(tip))) {
      level.setBlockAndUpdate(tip, state.setValue(IS_JUST_THE_TIP, true));
    }
  }
}
