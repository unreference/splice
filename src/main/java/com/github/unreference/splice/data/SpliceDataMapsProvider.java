package com.github.unreference.splice.data;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.SpliceWeatheringCopperBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

public final class SpliceDataMapsProvider extends DataMapProvider {
  private final SpliceWeatheringCopperBlocks BARS = SpliceBlocks.COPPER_BARS;

  public SpliceDataMapsProvider(
      PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(packOutput, lookupProvider);
  }

  @Override
  protected void gather(HolderLookup.Provider provider) {
    final var OXIDIZABLE = builder(NeoForgeDataMaps.OXIDIZABLES);
    BARS.weatheringMapping()
        .forEach((from, to) -> OXIDIZABLE.add(from.getKey(), new Oxidizable(to.get()), false));

    final var WAXABLE = builder(NeoForgeDataMaps.WAXABLES);
    BARS.waxedMapping()
        .forEach((from, to) -> WAXABLE.add(from.getKey(), new Waxable(to.get()), false));
  }

  @Override
  public String getName() {
    return "%s Data Maps".formatted(SpliceMain.MOD_ID);
  }
}
