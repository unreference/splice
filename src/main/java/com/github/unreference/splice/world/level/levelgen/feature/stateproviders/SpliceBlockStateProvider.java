package com.github.unreference.splice.world.level.levelgen.feature.stateproviders;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.util.SpliceBlockUtil;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public final class SpliceBlockStateProvider extends BlockStateProvider {
  public SpliceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SpliceMain.MOD_ID, exFileHelper);
  }

  private static String stripWaxedPrefix(String name) {
    return name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;
  }

  @Override
  protected void registerStatesAndModels() {
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::createCopperBars);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::createCopperChain);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::createCopperLantern);
    SpliceBlocks.COPPER_CHESTS.forEach(this::createChest);
    this.createTorch(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
    this.createBlock(SpliceBlocks.RESIN_BLOCK);
    this.createMultiface(SpliceBlocks.RESIN_CLUMP, SpliceItems.RESIN_CLUMP);
  }

  private void createMultiface(DeferredBlock<Block> block, DeferredItem<BlockItem> item) {
    this.itemModels().basicItem(item.get());
    this.createMultifaceBlockStates(block);
  }

  private void createMultifaceBlockStates(DeferredBlock<Block> block) {
    final Block b = block.get();
    final MultiPartBlockStateBuilder builder = this.getMultipartBuilder(b);
    final String name = SpliceBlockUtil.getNameOf(b);

    final ModelFile model = this.models().getExistingFile(modLoc("block/" + name));
    builder
        .part()
        .modelFile(model)
        .addModel()
        .condition(BlockStateProperties.NORTH, true)
        .end()
        .part()
        .modelFile(model)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.WEST, false)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(90)
        .addModel()
        .condition(BlockStateProperties.EAST, true)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(90)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.WEST, false)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(180)
        .addModel()
        .condition(BlockStateProperties.SOUTH, true)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(180)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.WEST, false)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(270)
        .addModel()
        .condition(BlockStateProperties.WEST, true)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(270)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.WEST, false)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationX(270)
        .addModel()
        .condition(BlockStateProperties.UP, true)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationY(270)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.WEST, false)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationX(90)
        .addModel()
        .condition(BlockStateProperties.DOWN, true)
        .end()
        .part()
        .modelFile(model)
        .uvLock(true)
        .rotationX(90)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.WEST, false)
        .end();
  }

  private void createBlock(DeferredBlock<Block> block) {
    final Block b = block.get();
    final String name = SpliceBlockUtil.getNameOf(b);
    final ModelFile model = this.models().getExistingFile(this.modLoc("block/" + name));
    this.simpleBlockWithItem(b, model);
  }

  private void createCopperLantern(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.createLantern(unaffected);
    this.createLantern(waxed);
  }

  private void createLantern(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof LanternBlock)) {
      throw new IllegalArgumentException("Expected LanternBlock, got: " + id);
    }

    final String name = SpliceBlockUtil.getNameOf(id);
    final ModelFile standing = this.models().getExistingFile(this.modLoc("block/" + name));
    final ModelFile hanging =
        this.models().getExistingFile(this.modLoc("block/" + name + "_hanging"));

    final VariantBlockStateBuilder s = this.getVariantBuilder(id);
    s.partialState()
        .with(BlockStateProperties.HANGING, false)
        .addModels(new ConfiguredModel(standing));
    s.partialState()
        .with(BlockStateProperties.HANGING, true)
        .addModels(new ConfiguredModel(hanging));

    final ResourceLocation texture = this.modLoc("item/" + stripWaxedPrefix(name));
    this.createItemModel(name, texture);
  }

  private void createItemModel(String modelName, ResourceLocation texture) {
    this.itemModels()
        .withExistingParent(modelName, this.mcLoc("item/generated"))
        .texture("layer0", texture);
  }

  private void createTorch(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
    final Block standingId = standing.get();
    final Block wallId = wall.get();
    final String standingName = SpliceBlockUtil.getNameOf(standingId);
    final String wallName = SpliceBlockUtil.getNameOf(wallId);

    final ModelFile standingModel =
        this.models().getExistingFile(this.modLoc("block/" + standingName));
    final ModelFile wallModel = this.models().getExistingFile(this.modLoc("block/" + wallName));
    this.simpleBlock(standingId, standingModel);

    final VariantBlockStateBuilder s = this.getVariantBuilder(wallId);
    s.partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).rotationY(270).build());
    s.partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).rotationY(90).build());
    s.partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).build());
    s.partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
        .addModels(ConfiguredModel.builder().modelFile(wallModel).rotationY(180).build());

    final ResourceLocation texture = this.modLoc("block/" + standingName);
    this.createItemModel(standingName, texture);
  }

  private void createCopperBars(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.createBars(unaffected);
    this.createBars(waxed);
  }

  private void createBars(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof IronBarsBlock bars)) {
      throw new IllegalArgumentException("Expected IronBarsBlock, got: " + id);
    }

    final String name = SpliceBlockUtil.getNameOf(id);
    final ResourceLocation texture = this.modLoc("block/" + stripWaxedPrefix(name));

    this.paneBlockWithRenderType(bars, texture, texture, this.mcLoc("cutout_mipped"));
    this.createItemModel(name, texture);
  }

  private void createCopperChain(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.createChain(unaffected);
    this.createChain(waxed);
  }

  private void createChain(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof ChainBlock chain)) {
      throw new IllegalArgumentException("Expected ChainBlock, got: " + id);
    }

    final String name = SpliceBlockUtil.getNameOf(id);
    final ModelFile model = this.models().getExistingFile(this.modLoc("block/" + name));

    this.axisBlock(chain, model, model);

    final ResourceLocation texture = this.modLoc("item/" + stripWaxedPrefix(name));
    this.createItemModel(name, texture);
  }

  private void createChest(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    final String name = SpliceBlockUtil.getNameOf(id);
    this.simpleBlock(id, this.models().getExistingFile(this.modLoc("block/" + name)));
  }
}
