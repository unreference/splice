package com.github.unreference.splice.data.registries;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomeData;
import com.github.unreference.splice.data.worldgen.features.SpliceFeatureUtils;
import com.github.unreference.splice.data.worldgen.placement.SplicePlacementUtils;
import com.github.unreference.splice.world.entity.decoration.SplicePaintingVariants;
import com.github.unreference.splice.world.item.SpliceJukeboxSongs;
import java.util.Set;
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
          // .add(Registries.LEVEL_STEM, SpliceLevelStem::bootstrap)
          .add(Registries.CONFIGURED_FEATURE, SpliceFeatureUtils::bootstrap)
          .add(Registries.PLACED_FEATURE, SplicePlacementUtils::bootstrap)
          .add(Registries.BIOME, SpliceBiomeData::bootstrap)
          .add(Registries.PAINTING_VARIANT, SplicePaintingVariants::bootstrap)
          .add(Registries.JUKEBOX_SONG, SpliceJukeboxSongs::bootstrap);

  public SpliceRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
    super(output, lookup, BUILDER, Set.of(SpliceMain.MOD_ID, ResourceLocation.DEFAULT_NAMESPACE));
  }

  public static <T> ResourceKey<T> createKey(ResourceKey<Registry<T>> registry, String name) {
    return ResourceKey.create(
        registry, ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, name));
  }
}
