package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.util.SpliceBlockUtil;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
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

    createCopperChests();
  }

  private void createCopperBars(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createCopperBars(base);
    createCopperBars(waxed);
  }

  private void createCopperBars(DeferredBlock<? extends Block> block) {
    final Block b = block.get();
    if (!(b instanceof IronBarsBlock bars)) {
      throw new IllegalArgumentException("Expected IronBarsBlock, got: " + b);
    }

    final String name = SpliceBlockUtil.getId(b).getPath();
    final String textureName = name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;
    final ResourceLocation blockTex = modLoc("block/" + textureName);

    this.paneBlockWithRenderType(bars, blockTex, blockTex, mcLoc("cutout_mipped"));
    itemModels().withExistingParent(name, mcLoc("item/generated")).texture("layer0", blockTex);
  }

  private void createCopperChain(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createCopperChain(base);
    createCopperChain(waxed);
  }

  private void createCopperChain(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof ChainBlock chain)) {
      throw new IllegalArgumentException("Expected ChainBlock, got: " + id);
    }

    final String name = SpliceBlockUtil.getId(id).getPath();
    final String textureName = name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;

    final ResourceLocation blockTex = modLoc("block/" + textureName);
    final ResourceLocation itemTex = modLoc("item/" + textureName);

    final ModelFile model =
        models()
            .withExistingParent(name, mcLoc("block/chain"))
            .renderType("minecraft:cutout")
            .texture("all", blockTex);

    this.axisBlock(chain, model, model);
    itemModels().withExistingParent(name, mcLoc("item/generated")).texture("layer0", itemTex);
  }

  private void createCopperChests() {
    this.createChest(SpliceBlocks.COPPER_CHEST, Blocks.COPPER_BLOCK);
    this.createChest(SpliceBlocks.EXPOSED_COPPER_CHEST, Blocks.EXPOSED_COPPER);
    this.createChest(SpliceBlocks.WEATHERED_COPPER_CHEST, Blocks.WEATHERED_COPPER);
    this.createChest(SpliceBlocks.OXIDIZED_COPPER_CHEST, Blocks.OXIDIZED_COPPER);
    this.copyModel(SpliceBlocks.COPPER_CHEST, SpliceBlocks.WAXED_COPPER_CHEST);
    this.copyModel(SpliceBlocks.EXPOSED_COPPER_CHEST, SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST);
    this.copyModel(SpliceBlocks.WEATHERED_COPPER_CHEST, SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST);
    this.copyModel(SpliceBlocks.OXIDIZED_COPPER_CHEST, SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST);
  }

  private void createChest(DeferredBlock<? extends Block> block, Block particle) {
    final Block id = block.get();
    final String name = SpliceBlockUtil.getId(id).getPath();

    final BlockModelBuilder model =
        models()
            .withExistingParent(name, mcLoc("block/chest"))
            .texture("particle", SpliceBlockUtil.getTexture(particle));

    simpleBlock(id, model);
    itemModels().withExistingParent(name, mcLoc("item/chest"));
  }

  private void copyModel(DeferredBlock<? extends Block> from, DeferredBlock<? extends Block> to) {
    final Block fromBlock = from.get();
    final Block toBlock = to.get();

    final String fromName = SpliceBlockUtil.getId(fromBlock).getPath();
    final String toName = SpliceBlockUtil.getId(toBlock).getPath();

    final ModelFile toModel = models().withExistingParent(toName, modLoc("block/" + fromName));

    simpleBlock(toBlock, toModel);
    itemModels().withExistingParent(toName, modLoc("item/" + fromName));
  }
}
