package com.github.unreference.splice.world.level.block.entity;

import com.github.unreference.splice.world.level.block.SpliceCopperChestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

public final class SpliceCopperChestBlockEntity extends ChestBlockEntity {
  private final ContainerOpenersCounter openersCounter =
      new ContainerOpenersCounter() {
        @Override
        protected void onOpen(@NotNull Level level, @NotNull BlockPos pos, BlockState state) {
          if (state.getBlock() instanceof SpliceCopperChestBlock chest) {
            playSound(level, pos, state, chest.getOpenSound());
          }
        }

        @Override
        protected void onClose(@NotNull Level level, @NotNull BlockPos pos, BlockState state) {
          if (state.getBlock() instanceof SpliceCopperChestBlock chest) {
            playSound(level, pos, state, chest.getCloseSound());
          }
        }

        @Override
        protected void openerCountChanged(
            @NotNull Level level,
            @NotNull BlockPos pos,
            @NotNull BlockState state,
            int count,
            int openCount) {
          SpliceCopperChestBlockEntity.this.signalOpenCount(level, pos, state, count, openCount);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
          if (!(player.containerMenu instanceof ChestMenu)) {
            return false;
          }

          final var CONTAINER = ((ChestMenu) player.containerMenu).getContainer();
          return CONTAINER == SpliceCopperChestBlockEntity.this
              || CONTAINER instanceof CompoundContainer
                  && ((CompoundContainer) CONTAINER).contains(SpliceCopperChestBlockEntity.this);
        }
      };

  public SpliceCopperChestBlockEntity(BlockPos pos, BlockState blockState) {
    super(SpliceBlockEntityType.COPPER_CHEST.get(), pos, blockState);
  }

  private static void playSound(
      Level level, BlockPos blockPos, BlockState blockState, Holder<SoundEvent> soundEvent) {
    final var TYPE = blockState.getValue(SpliceCopperChestBlock.TYPE);
    if (TYPE != ChestType.LEFT) {
      var directionX = blockPos.getX() + 0.5;
      var directionY = blockPos.getY() + 0.5;
      var directionZ = blockPos.getZ() + 0.5;

      if (TYPE == ChestType.RIGHT) {
        Direction direction = SpliceCopperChestBlock.getConnectedDirection(blockState);
        directionX += direction.getStepX() * 0.5;
        directionZ += direction.getStepZ() * 0.5;
      }

      level.playSound(
          null,
          directionX,
          directionY,
          directionZ,
          soundEvent,
          SoundSource.BLOCKS,
          0.5F,
          level.random.nextFloat() * 0.1F + 0.9F);
    }
  }

  @Override
  public void startOpen(@NotNull Player player) {
    if (!this.remove && !player.isSpectator()) {
      this.openersCounter.incrementOpeners(
          player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public void stopOpen(@NotNull Player player) {
    if (!this.remove && !player.isSpectator()) {
      this.openersCounter.decrementOpeners(
          player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public void recheckOpen() {
    if (!this.remove) {
      this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }
}
