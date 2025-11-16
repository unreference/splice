package com.github.unreference.splice.data.worldgen.biome;

import com.github.unreference.splice.data.worldgen.placement.SpliceVegetationPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class SpliceOverworldBiomes extends OverworldBiomes {
  public static Biome paleForest(
      HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carver) {
    final MobSpawnSettings.Builder mobSpawnSettings = new MobSpawnSettings.Builder();

    BiomeDefaultFeatures.commonSpawns(mobSpawnSettings);

    final BiomeGenerationSettings.Builder generationSettings =
        new BiomeGenerationSettings.Builder(placed, carver);

    globalOverworldGeneration(generationSettings);

    generationSettings.addFeature(
        GenerationStep.Decoration.VEGETAL_DECORATION,
        SpliceVegetationPlacements.PALE_GARDEN_VEGETATION);
    generationSettings.addFeature(
        GenerationStep.Decoration.VEGETAL_DECORATION, SpliceVegetationPlacements.PALE_MOSS_PATCH);
    generationSettings.addFeature(
        GenerationStep.Decoration.VEGETAL_DECORATION,
        SpliceVegetationPlacements.PALE_GARDEN_FLOWERS);

    BiomeDefaultFeatures.addDefaultOres(generationSettings);
    BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);

    generationSettings.addFeature(
        GenerationStep.Decoration.VEGETAL_DECORATION,
        SpliceVegetationPlacements.PALE_GARDEN_FLOWER);

    BiomeDefaultFeatures.addForestGrass(generationSettings);
    BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);

    return new Biome.BiomeBuilder()
        .hasPrecipitation(true)
        .temperature(0.7F)
        .downfall(0.8F)
        .specialEffects(
            new BiomeSpecialEffects.Builder()
                .waterColor(7768221)
                .waterFogColor(5597568)
                .fogColor(8484720)
                .skyColor(12171705)
                .grassColorOverride(7832178)
                .foliageColorOverride(8883574)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
        .mobSpawnSettings(mobSpawnSettings.build())
        .generationSettings(generationSettings.build())
        .build();
  }

  private static void globalOverworldGeneration(
      BiomeGenerationSettings.Builder generationSettings) {
    BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
    BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
    BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
    BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
    BiomeDefaultFeatures.addDefaultSprings(generationSettings);
    BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
  }
}
