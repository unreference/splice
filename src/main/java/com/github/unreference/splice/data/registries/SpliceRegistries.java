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
          .add(Registries.PAINTING_VARIANT, SplicePaintingVariants::bootstrap)
          .add(Registries.JUKEBOX_SONG, SpliceJukeboxSongs::bootstrap);

  SpliceRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
    super(output, lookup, BUILDER, Collections.singleton(SpliceMain.MOD_ID));
  }
}
