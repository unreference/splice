package com.github.unreference.splice.data.worldgen.biome;

import com.github.unreference.splice.data.registries.SpliceRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public final class SpliceBiomes {
  public static final ResourceKey<Biome> PALE_GARDEN = create("pale_garden");

  private static ResourceKey<Biome> create(String name) {
    return SpliceRegistries.createKey(Registries.BIOME, name);
  }
}
