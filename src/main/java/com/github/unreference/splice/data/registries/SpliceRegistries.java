package com.github.unreference.splice.data.registries;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.entity.decoration.SplicePaintingVariants;
import com.github.unreference.splice.world.item.SpliceJukeboxSongs;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public final class SpliceRegistries extends DatapackBuiltinEntriesProvider {
  public static final RegistrySetBuilder BUILDER =
      new RegistrySetBuilder()
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
}
