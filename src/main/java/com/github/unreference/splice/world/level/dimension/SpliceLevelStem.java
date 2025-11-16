package com.github.unreference.splice.world.level.dimension;

import com.github.unreference.splice.data.worldgen.biome.SpliceBiomes;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

// TODO: Figure this shit out... what the fuck is even going on
public final class SpliceLevelStem {
  public static void bootstrap(BootstrapContext<LevelStem> context) {
    final HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
    paleGarden(context, holderGetter);
  }

  private static void paleGarden(
      BootstrapContext<LevelStem> context, HolderGetter<Biome> holderGetter) {
    final Climate.ParameterList<Holder<Biome>> vanilla =
        new MultiNoiseBiomeSourceParameterList(
                MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, holderGetter)
            .parameters();

    final Holder.Reference<Biome> biome = holderGetter.getOrThrow(SpliceBiomes.PALE_GARDEN);

    final Climate.ParameterPoint climate =
        new Climate.ParameterPoint(
            Climate.Parameter.span(-0.15f, 0.2f), // Temperature
            Climate.Parameter.span(0.3f, 1.0f), // Humidity
            Climate.Parameter.span(0.03f, 1.0f), // Continentality
            Climate.Parameter.span(-1.0f, -0.375f), // Erosion
            Climate.Parameter.point(0.0f), // Depth
            Climate.Parameter.span(-0.05f, 0.05f), // Weirdness
            Climate.quantizeCoord(0.0f)); // Offset

    final Pair<Climate.ParameterPoint, Holder<Biome>> entry = Pair.of(climate, biome);
    final List<Pair<Climate.ParameterPoint, Holder<Biome>>> merged =
        new ArrayList<>(vanilla.values());
    merged.add(entry);

    final MultiNoiseBiomeSource newBiomeSource =
        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(merged));

    final Holder.Reference<DimensionType> overworldDimension =
        context.lookup(Registries.DIMENSION_TYPE).getOrThrow(BuiltinDimensionTypes.OVERWORLD);
    final Holder.Reference<NoiseGeneratorSettings> overworldNoiseSettings =
        context.lookup(Registries.NOISE_SETTINGS).getOrThrow(NoiseGeneratorSettings.OVERWORLD);
    final NoiseBasedChunkGenerator newNoiseBasedChunkGenerator =
        new NoiseBasedChunkGenerator(newBiomeSource, overworldNoiseSettings);

    context.register(
        LevelStem.OVERWORLD, new LevelStem(overworldDimension, newNoiseBasedChunkGenerator));
  }
}
