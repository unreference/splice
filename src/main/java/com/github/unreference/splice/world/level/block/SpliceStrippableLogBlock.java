package com.github.unreference.splice.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SpliceStrippableLogBlock extends RotatedPillarBlock {
  private final Supplier<RotatedPillarBlock> strippedBlock;

  public SpliceStrippableLogBlock(
      Supplier<RotatedPillarBlock> strippedBlock, BlockBehaviour.Properties properties) {
    super(properties);
    this.strippedBlock = strippedBlock;
  }

  @Override
  public @Nullable BlockState getToolModifiedState(
      @NotNull BlockState state,
      @NotNull UseOnContext context,
      ItemAbility itemAbility,
      boolean simulate) {
    if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
      final Block stripped = strippedBlock.get();
      if (stripped == null) {
        return null;
      }

      BlockState strippedState = strippedBlock.get().defaultBlockState();
      strippedState =
          strippedState.setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));

      if (!simulate) {
        final Level level = context.getLevel();
        final BlockPos pos = context.getClickedPos();
        final Player player = context.getPlayer();

        level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

        if (player != null) {
          context
              .getItemInHand()
              .hurtAndBreak(1, player, player.getEquipmentSlotForItem(context.getItemInHand()));
        }
      }

      return strippedState;
    }

    return super.getToolModifiedState(state, context, itemAbility, simulate);
  }
}
