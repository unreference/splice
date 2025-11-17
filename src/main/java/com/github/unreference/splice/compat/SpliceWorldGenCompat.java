package com.github.unreference.splice.compat;

import com.github.unreference.splice.SpliceMain;
import net.neoforged.fml.loading.LoadingModList;

public final class SpliceWorldGenCompat {
  private static final String TERRABLENDER_ID = "terrablender";
  private static final String BIOLITH_ID = "biolith";

  private static Boolean isCached;

  public static boolean isInjectionSkipped() {
    if (isCached != null) {
      return isCached;
    }

    final LoadingModList loading = LoadingModList.get();
    if (loading == null) {
      isCached = false;
      return false;
    }

    final boolean isTerraBlenderLoaded = loading.getModFileById(TERRABLENDER_ID) != null;
    final boolean isBiolithLoaded = loading.getModFileById(BIOLITH_ID) != null;

    isCached = isTerraBlenderLoaded || isBiolithLoaded;
    if (isCached) {
      SpliceMain.LOGGER.warn(
          "{} detected: skipping overworld biome injection",
          isTerraBlenderLoaded ? "TerraBlender" : "Biolith");
    }

    return isCached;
  }
}
