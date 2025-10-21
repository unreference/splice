package com.github.unreference.splice.data;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.recipes.SpliceRecipeProvider;
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
    final var OXIDIZABLES = builder(NeoForgeDataMaps.OXIDIZABLES);
    final var WAXABLES = builder(NeoForgeDataMaps.WAXABLES);

    BARS.weatheringMapping()
        .forEach((from, to) -> OXIDIZABLES.add(from.getKey(), new Oxidizable(to.get()), false));

    for (var p : SpliceRecipeProvider.getWaxables()) {
      WAXABLES.add(p.first.getKey(), new Waxable(p.second.get()), false);
    }
  }

  @Override
  public String getName() {
    return "%s Data Maps".formatted(SpliceMain.MOD_ID);
  }
}
