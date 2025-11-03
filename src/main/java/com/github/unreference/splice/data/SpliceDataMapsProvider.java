package com.github.unreference.splice.data;

import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import org.jetbrains.annotations.NotNull;

public final class SpliceDataMapsProvider extends DataMapProvider {
  public SpliceDataMapsProvider(
      PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(packOutput, lookupProvider);
  }

  @Override
  protected void gather(HolderLookup.@NotNull Provider provider) {
    this.createCopperData();
  }

  private void createCopperData() {
    final Builder<Waxable, Block> waxable = builder(NeoForgeDataMaps.WAXABLES);
    final Builder<Oxidizable, Block> weathering = builder(NeoForgeDataMaps.OXIDIZABLES);

    SpliceBlocks.COPPER_FAMILY.forEach(
        block -> {
          block
              .waxedMapping()
              .forEach((from, to) -> waxable.add(from.getKey(), new Waxable(to.get()), false));
          block
              .weatheringMapping()
              .forEach(
                  (from, to) -> weathering.add(from.getKey(), new Oxidizable(to.get()), false));
        });
  }
}
