package com.github.unreference.splice.compat.terrablender;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomes;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

public final class SpliceOverworldRegion extends Region {
  public SpliceOverworldRegion(int weight) {
    super(
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "overworld"),
        RegionType.OVERWORLD,
        weight);
  }

  @Override
  public void addBiomes(
      Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
    this.addModifiedVanillaOverworldBiomes(
        mapper,
        builder -> {
          builder.replaceBiome(Biomes.DARK_FOREST, SpliceBiomes.PALE_GARDEN);
        });
  }
}
