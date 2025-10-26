package com.github.unreference.splice.world.level.levelgen.feature.stateproviders;

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
    SpliceBlocks.COPPER_CHESTS.forEach(this::createChest);
    this.createTorch(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
  }

  private void createLantern(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.createLantern(unaffected);
    this.createLantern(waxed);
  }

  private void createLantern(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof LanternBlock)) {
      throw new IllegalArgumentException("Expected LanternBlock, got: " + id);
    }

    final String name = getNameOf(id);
    final ModelFile standing = models().getExistingFile(modLoc("block/" + name));
    final ModelFile hanging = models().getExistingFile(modLoc("block/" + name + "_hanging"));

    final VariantBlockStateBuilder s = getVariantBuilder(id);
    s.partialState()
        .with(BlockStateProperties.HANGING, false)
        .addModels(new ConfiguredModel(standing));
    s.partialState()
        .with(BlockStateProperties.HANGING, true)
        .addModels(new ConfiguredModel(hanging));

    final ResourceLocation texture = modLoc("item/" + stripWaxedPrefix(name));
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
    final String standingName = getNameOf(standingId);
    final String wallName = getNameOf(wallId);

    final ModelFile standingModel = models().getExistingFile(modLoc("block/" + standingName));
    final ModelFile wallModel = models().getExistingFile(modLoc("block/" + wallName));
    this.simpleBlock(standingId, standingModel);

    final VariantBlockStateBuilder s = getVariantBuilder(wallId);
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

    final ResourceLocation texture = modLoc("block/" + standingName);
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

    final String name = getNameOf(id);
    final ResourceLocation texture = modLoc("block/" + stripWaxedPrefix(name));

    this.paneBlockWithRenderType(bars, texture, texture, mcLoc("cutout_mipped"));
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

    final String name = getNameOf(id);
    final ModelFile model = models().getExistingFile(modLoc("block/" + name));

    this.axisBlock(chain, model, model);

    final ResourceLocation texture = modLoc("item/" + stripWaxedPrefix(name));
    this.createItemModel(name, texture);
  }

  private void createChest(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    final String name = getNameOf(id);

    this.simpleBlock(id, models().getExistingFile(modLoc("block/" + name)));
    this.itemModels().withExistingParent(name, mcLoc("item/chest"));
  }
}
