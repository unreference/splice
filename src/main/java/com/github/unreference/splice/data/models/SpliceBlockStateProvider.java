package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceBlockStateProvider extends BlockStateProvider {
  public SpliceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SpliceMain.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::createCopperBars);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::createCopperChain);
  }

  private void createCopperBars(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createCopperBars(base);
    createCopperBars(waxed);
  }

  private void createCopperBars(DeferredBlock<? extends Block> block) {
    if (!(block.get() instanceof IronBarsBlock ironBarsBlock)) {
      throw new IllegalArgumentException("Expected IronBarsBlock: %s".formatted(block.get()));
    }

    final String NAME = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    final String TEXTURE_NAME =
        NAME.startsWith("waxed_") ? NAME.substring("waxed_".length()) : NAME;
    final ResourceLocation PANE_TEXTURE = modLoc("block/%s".formatted(TEXTURE_NAME));

    this.paneBlockWithRenderType(ironBarsBlock, PANE_TEXTURE, PANE_TEXTURE, mcLoc("cutout_mipped"));
    itemModels().withExistingParent(NAME, mcLoc("item/generated")).texture("layer0", PANE_TEXTURE);
  }

  private void createCopperChain(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createCopperChain(base);
    createCopperChain(waxed);
  }

  private void createCopperChain(DeferredBlock<? extends Block> block) {
    if (!(block.get() instanceof ChainBlock chainBlock)) {
      throw new IllegalArgumentException("Expected ChainBlock: %s".formatted(block.get()));
    }

    final String NAME = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    final String TEXTURE_NAME =
        NAME.startsWith("waxed_") ? NAME.substring("waxed_".length()) : NAME;

    final ResourceLocation COPPER_CHAIN_BLOCK_TEXTURE = modLoc("block/%s".formatted(TEXTURE_NAME));
    final ResourceLocation COPPER_CHAIN_ITEM_TEXTURE = modLoc("item/%s".formatted(TEXTURE_NAME));
    final ModelFile COPPER_CHAIN_MODEL =
        models()
            .withExistingParent(NAME, mcLoc("block/chain"))
            .renderType("minecraft:cutout")
            .texture("all", COPPER_CHAIN_BLOCK_TEXTURE);

    this.axisBlock(chainBlock, COPPER_CHAIN_MODEL, COPPER_CHAIN_MODEL);
    itemModels()
        .withExistingParent(NAME, mcLoc("item/generated"))
        .texture("layer0", COPPER_CHAIN_ITEM_TEXTURE);
  }
}
