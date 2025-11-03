package com.github.unreference.splice.data.registries;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomeData;
import com.github.unreference.splice.data.worldgen.features.SpliceFeatureUtils;
import com.github.unreference.splice.data.worldgen.placement.SplicePlacementUtils;
import com.github.unreference.splice.world.entity.decoration.SplicePaintingVariants;
import com.github.unreference.splice.world.item.SpliceJukeboxSongs;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public final class SpliceRegistries extends DatapackBuiltinEntriesProvider {
  public static final RegistrySetBuilder BUILDER =
      new RegistrySetBuilder()
          // Dimension type
          // Configured carver
          // Configured feature
          .add(Registries.CONFIGURED_FEATURE, SpliceFeatureUtils::bootstrap)
          // Placed feature
          .add(Registries.PLACED_FEATURE, SplicePlacementUtils::bootstrap)
          // Structure
          // Structure set
          // Processor list
          // Template pool
          // Biome
          .add(Registries.BIOME, SpliceBiomeData::bootstrap)
          // Multi-noise biome source parameter list
          // Noise
          // Density function
          // Noise settings
          // World preset
          // Flat level generator preset
          // Chat type
          // Trim pattern
          // Trim material
          // Wolf variant
          // Wolf sound variant
          // Painting variant
          .add(Registries.PAINTING_VARIANT, SplicePaintingVariants::bootstrap)
          // Damage type
          // Banner pattern
          // Enchantment
          // Enchantment provider
          // Jukebox song
          .add(Registries.JUKEBOX_SONG, SpliceJukeboxSongs::bootstrap);

  // Instrument
  // Pig variant
  // Cow variant
  // Chicken variant
  // Frog variant
  // Cat variant
  // Dialog

  SpliceRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
    super(output, lookup, BUILDER, Collections.singleton(SpliceMain.MOD_ID));
  }

  public static <T> ResourceKey<T> createKey(ResourceKey<Registry<T>> registry, String name) {
    return ResourceKey.create(
        registry, ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, name));
  }
}
