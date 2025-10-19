package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceBlockStateProvider extends BlockStateProvider {
  public SpliceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SpliceMain.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    // TODO: Temporary -- should be `waxedMapping`
    SpliceBlocks.COPPER_BARS.weatheringMapping().forEach(this::createBarsAndItem);
  }

  private void createBarsAndItem(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createBarsAndItem(base);
    createBarsAndItem(waxed);
  }

  private void createBarsAndItem(DeferredBlock<? extends Block> block) {
    if (!(block.get() instanceof IronBarsBlock ironBarsBlock)) {
      throw new IllegalArgumentException("Expected IronBarsBlock: %s".formatted(block.get()));
    }

    final String NAME = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    ResourceLocation pane = modLoc("block/%s".formatted(NAME));
    ResourceLocation edge = pane;

    paneBlockWithRenderType(ironBarsBlock, pane, edge, mcLoc("cutout_mipped"));
    itemModels().withExistingParent(NAME, mcLoc("item/generated")).texture("layer0", pane);
  }
}
