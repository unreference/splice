package com.github.unreference.splice.data.models;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.util.SpliceBlockUtil;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceBlockStateProvider extends BlockStateProvider {
  public SpliceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SpliceMain.MOD_ID, exFileHelper);
  }

  private static String getNameOf(Block block) {
    return SpliceBlockUtil.getId(block).getPath();
  }

  private static String stripWaxedPrefix(String name) {
    return name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;
  }

  @Override
  protected void registerStatesAndModels() {
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::createCopperBars);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::createCopperChain);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::createLantern);

    createTorch(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
    createCopperChests();
  }

  private void createLantern(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createLantern(base);
    createLantern(waxed);
  }

  private void createLantern(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof LanternBlock)) {
      throw new IllegalArgumentException("Expected LanternBlock, got: " + id);
    }

    final String name = getNameOf(id);
    final String textureName = stripWaxedPrefix(name);
    final ResourceLocation blockTex = modLoc("block/" + textureName);

    final ModelFile standingModel =
        models()
            .withExistingParent(name, mcLoc("block/template_lantern"))
            .renderType("minecraft:cutout")
            .texture("lantern", blockTex);

    final ModelFile hangingModel =
        models()
            .withExistingParent(name + "_hanging", mcLoc("block/template_hanging_lantern"))
            .renderType("minecraft:cutout")
            .texture("lantern", blockTex);

    final VariantBlockStateBuilder state = getVariantBuilder(id);
    state
        .partialState()
        .with(BlockStateProperties.HANGING, false)
        .addModels(new ConfiguredModel(standingModel));
    state
        .partialState()
        .with(BlockStateProperties.HANGING, true)
        .addModels(new ConfiguredModel(hangingModel));
  }

  private void createTorch(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
    final Block standingId = standing.get();
    final Block wallId = wall.get();

    final String standingName = getNameOf(standingId);
    final String wallName = getNameOf(wallId);
    final ResourceLocation torchTex = modLoc("block/" + standingName);

    final ModelFile standingModel =
        models().torch(standingName, torchTex).renderType("minecraft:cutout");
    final ModelFile wallModel =
        models().torchWall(wallName, torchTex).renderType("minecraft:cutout");

    simpleBlock(standingId, standingModel);

    final VariantBlockStateBuilder state = getVariantBuilder(wallId);
    state
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).build());
    state
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).rotationY(90).build());
    state
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).rotationY(180).build());
    state
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).rotationY(270).build());
  }

  private void createCopperBars(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createBars(base);
    createBars(waxed);
  }

  private void createBars(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof IronBarsBlock bars)) {
      throw new IllegalArgumentException("Expected IronBarsBlock, got: " + id);
    }

    final String name = getNameOf(id);
    final String textureName = stripWaxedPrefix(name);
    final ResourceLocation barTex = modLoc("block/" + textureName);

    this.paneBlockWithRenderType(bars, barTex, barTex, mcLoc("cutout_mipped"));
  }

  private void createCopperChain(
      DeferredBlock<? extends Block> base, DeferredBlock<? extends Block> waxed) {
    createChain(base);
    createChain(waxed);
  }

  private void createChain(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof ChainBlock chain)) {
      throw new IllegalArgumentException("Expected ChainBlock, got: " + id);
    }

    final String name = getNameOf(id);
    final String textureName = stripWaxedPrefix(name);

    final ResourceLocation blockTex = modLoc("block/" + textureName);

    final ModelFile model =
        models()
            .withExistingParent(name, mcLoc("block/chain"))
            .renderType("minecraft:cutout")
            .texture("all", blockTex);

    this.axisBlock(chain, model, model);
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
    final String name = getNameOf(id);

    final BlockModelBuilder model =
        models()
            .withExistingParent(name, mcLoc("block/chest"))
            .texture("particle", SpliceBlockUtil.getTexture(particle));

    simpleBlock(id, model);
  }

  private void copyModel(DeferredBlock<? extends Block> from, DeferredBlock<? extends Block> to) {
    final Block fromId = from.get();
    final Block toId = to.get();

    final String fromName = getNameOf(fromId);
    final String toName = getNameOf(toId);

    final ModelFile toModel = models().withExistingParent(toName, modLoc("block/" + fromName));
    simpleBlock(toId, toModel);
  }
}
