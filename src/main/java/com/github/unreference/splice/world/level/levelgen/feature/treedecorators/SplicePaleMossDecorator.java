package com.github.unreference.splice.world.level.levelgen.feature.treedecorators;

import com.github.unreference.splice.data.worldgen.features.SpliceVegetationFeatures;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.SpliceHangingMossBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.NotNull;

public class SplicePaleMossDecorator extends TreeDecorator {
  public static final MapCodec<SplicePaleMossDecorator> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      Codec.floatRange(0.0f, 1.0f)
                          .fieldOf("leaves_chance")
                          .forGetter(decorator -> decorator.leavesChance),
                      Codec.floatRange(0.0f, 1.0f)
                          .fieldOf("trunk_chance")
                          .forGetter(decorator -> decorator.trunkChance),
                      Codec.floatRange(0.0f, 1.0f)
                          .fieldOf("ground_chance")
                          .forGetter(decorator -> decorator.groundChance))
                  .apply(instance, SplicePaleMossDecorator::new));

  private final float leavesChance;
  private final float trunkChance;
  private final float groundChance;

  public SplicePaleMossDecorator(float leavesChance, float trunkChance, float groundChance) {
    this.leavesChance = leavesChance;
    this.trunkChance = trunkChance;
    this.groundChance = groundChance;
  }

  private static void addHangingMoss(BlockPos pos, Context context) {
    while (context.isAir(pos.below()) && !(context.random().nextFloat() < 0.5f)) {
      context.setBlock(
          pos,
          SpliceBlocks.PALE_HANGING_MOSS
              .get()
              .defaultBlockState()
              .setValue(SpliceHangingMossBlock.IS_JUST_THE_TIP, false));
      pos = pos.below();
    }

    context.setBlock(
        pos,
        SpliceBlocks.PALE_HANGING_MOSS
            .get()
            .defaultBlockState()
            .setValue(SpliceHangingMossBlock.IS_JUST_THE_TIP, true));
  }

  @Override
  protected @NotNull TreeDecoratorType<?> type() {
    return SpliceTreeDecoratorType.PALE_MOSS.get();
  }

  @Override
  public void place(Context context) {
    final RandomSource random = context.random();
    final WorldGenLevel level = ((WorldGenLevel) context.level());

    final List<BlockPos> logs = Util.shuffledCopy(context.logs(), random);
    if (!logs.isEmpty()) {
      final Mutable<BlockPos> mutable = new MutableObject<>(logs.getFirst());
      logs.forEach(
          pos -> {
            if (pos.getY() < mutable.getValue().getY()) {
              mutable.setValue(pos);
            }
          });

      final BlockPos blockPos = mutable.getValue();
      if (random.nextFloat() < this.groundChance) {
        level
            .registryAccess()
            .lookup(Registries.CONFIGURED_FEATURE)
            .flatMap(feature -> feature.get(SpliceVegetationFeatures.PALE_MOSS_PATCH))
            .ifPresent(
                feature ->
                    feature
                        .value()
                        .place(
                            level,
                            level.getLevel().getChunkSource().getGenerator(),
                            random,
                            blockPos.above()));

        context
            .logs()
            .forEach(
                pos -> {
                  if (random.nextFloat() < this.trunkChance) {
                    final BlockPos below = pos.below();
                    if (context.isAir(below)) {
                      addHangingMoss(below, context);
                    }
                  }
                });

        context
            .leaves()
            .forEach(
                pos -> {
                  if (random.nextFloat() < this.leavesChance) {
                    final BlockPos below = pos.below();
                    if (context.isAir(below)) {
                      addHangingMoss(below, context);
                    }
                  }
                });
      }
    }
  }
}
