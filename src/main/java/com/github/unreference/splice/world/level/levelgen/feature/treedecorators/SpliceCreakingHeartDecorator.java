package com.github.unreference.splice.world.level.levelgen.feature.treedecorators;

import com.github.unreference.splice.SpliceMain;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class SpliceCreakingHeartDecorator extends TreeDecorator {
  public static final MapCodec<SpliceCreakingHeartDecorator> CODEC =
      Codec.floatRange(0.0f, 1.0f)
          .fieldOf("chance")
          .xmap(SpliceCreakingHeartDecorator::new, decorator -> decorator.chance);

  private final float chance;

  public SpliceCreakingHeartDecorator(float chance) {
    this.chance = chance;
  }

  @Override
  protected @NotNull TreeDecoratorType<?> type() {
    return SpliceTreeDecoratorType.CREAKING_HEART.get();
  }

  @Override
  public void place(Context context) {
    final RandomSource random = context.random();
    final List<BlockPos> logs = Util.shuffledCopy(context.logs(), random);
    if (!logs.isEmpty()) {
      if (!(random.nextFloat() >= this.chance)) {
        final List<BlockPos> shuffled = new ArrayList<>(logs);
        Util.shuffle(shuffled, random);

        final Optional<BlockPos> optional =
            shuffled.stream()
                .filter(
                    pos -> {
                      for (Direction direction : Direction.values()) {
                        if (!context
                            .level()
                            .isStateAtPosition(
                                pos.relative(direction), state -> state.is(BlockTags.LOGS))) {
                          return false;
                        }
                      }

                      return true;
                    })
                .findFirst();

        optional.ifPresent(
            pos -> {
              context.setBlock(
                  pos, Blocks.NETHERRACK.defaultBlockState()); // TODO: Replace with creaking heart

              SpliceMain.LOGGER.info("Creaking heart placed at: {}", pos);
            });
      }
    }
  }
}
