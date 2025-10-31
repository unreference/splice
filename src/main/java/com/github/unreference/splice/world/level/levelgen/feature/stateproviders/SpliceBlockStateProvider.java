package com.github.unreference.splice.world.level.levelgen.feature.stateproviders;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceUtils;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
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
    this.createFamilies();
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::createCopperBars);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::createCopperChain);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::createCopperLantern);
    SpliceBlocks.COPPER_CHESTS.forEach(this::createChest);
    this.createTorch(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
    this.createBlock(SpliceBlocks.RESIN_BLOCK);
    this.createMultiface(SpliceBlocks.RESIN_CLUMP, SpliceItems.RESIN_CLUMP);
  }

  private void createFamilies() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel()) {
        continue;
      }

      final Block baseBlock = family.getBaseBlock();
      final ResourceLocation baseTexture = SpliceUtils.getLocation(baseBlock);

      this.simpleBlockWithItem(
          baseBlock, this.models().getExistingFile(SpliceUtils.getLocation(baseBlock)));

      final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
      this.simpleBlockWithItem(
          chiseled, this.models().getExistingFile(SpliceUtils.getLocation(chiseled)));

      final Block wall = family.get(BlockFamily.Variant.WALL);
      this.createWall((WallBlock) wall, baseTexture);

      final Block slab = family.get(BlockFamily.Variant.SLAB);
      this.createSlab((SlabBlock) slab, baseTexture);

      final Block stairs = family.get(BlockFamily.Variant.STAIRS);
      this.createStairs((StairBlock) stairs, baseTexture);
    }
  }

  private void createWall(WallBlock block, ResourceLocation texture) {
    this.wallBlock(block, texture);
    this.itemModels().wallInventory(SpliceUtils.getName(block), texture);
  }

  private void createStairs(StairBlock block, ResourceLocation texture) {
    this.stairsBlock(block, texture);
    this.itemModels()
        .withExistingParent(SpliceUtils.getName(block), SpliceUtils.getLocation(block));
  }

  private void createSlab(SlabBlock block, ResourceLocation texture) {
    this.slabBlock(block, texture, texture);
    this.itemModels()
        .withExistingParent(SpliceUtils.getName(block), SpliceUtils.getLocation(block));
  }

  private void createMultiface(DeferredBlock<Block> block, DeferredItem<BlockItem> item) {
    this.itemModels().basicItem(item.get());
    this.createMultifaceBlockStates(block);
  }

  private void createMultifaceBlockStates(DeferredBlock<Block> block) {
    final Block id = block.get();
    final MultiPartBlockStateBuilder builder = this.getMultipartBuilder(id);

    final ModelFile model = this.models().getExistingFile(SpliceUtils.getLocation(id));
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
    final Block id = block.get();
    final ModelFile model = this.models().getExistingFile(SpliceUtils.getLocation(id));
    this.simpleBlockWithItem(id, model);
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

    final String name = SpliceUtils.getName(id);
    final ModelFile standing = this.models().getExistingFile(SpliceUtils.getLocation(id));
    final ModelFile hanging =
        this.models()
            .getExistingFile(ResourceLocation.parse(SpliceUtils.getLocation(id) + "_hanging"));

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

    final ModelFile standingModel =
        this.models().getExistingFile(SpliceUtils.getLocation(standingId));
    final ModelFile wallModel = this.models().getExistingFile(SpliceUtils.getLocation(wallId));
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

    final ResourceLocation texture = SpliceUtils.getLocation(standingId);
    this.createItemModel(SpliceUtils.getName(standingId), texture);
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

    final String name = SpliceUtils.getName(id);
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

    final ModelFile model = this.models().getExistingFile(SpliceUtils.getLocation(id));
    this.axisBlock(chain, model, model);

    final String name = SpliceUtils.getName(id);
    final ResourceLocation texture = this.modLoc("item/" + stripWaxedPrefix(name));
    this.createItemModel(name, texture);
  }

  private void createChest(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    this.simpleBlockWithItem(id, this.models().getExistingFile(this.mcLoc("block/chest")));
  }
}
