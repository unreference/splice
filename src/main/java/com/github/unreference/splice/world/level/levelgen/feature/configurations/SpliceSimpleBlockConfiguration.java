package com.github.unreference.splice.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SpliceSimpleBlockConfiguration(BlockStateProvider toPlace, boolean hasScheduledTicks)
    implements FeatureConfiguration {
  public static final Codec<SpliceSimpleBlockConfiguration> CODEC =
      RecordCodecBuilder.create(
          instance ->
              instance
                  .group(
                      BlockStateProvider.CODEC
                          .fieldOf("to_place")
                          .forGetter(configuration -> configuration.toPlace),
                      Codec.BOOL
                          .optionalFieldOf("schedule_tick", false)
                          .forGetter(configuration -> configuration.hasScheduledTicks))
                  .apply(instance, SpliceSimpleBlockConfiguration::new));

  public SpliceSimpleBlockConfiguration(BlockStateProvider blockStateProvider) {
    this(blockStateProvider, false);
  }
}
