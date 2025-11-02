package com.github.unreference.splice.world.level.levelgen.feature.stateproviders;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceUtils;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
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

  @Override
  protected void registerStatesAndModels() {
    this.blockFamilyBlocks();
    this.copperBlocks();
    this.resinBlocks();
    this.paleOakBlocks();
  }

  private void paleOakBlocks() {
    this.log(SpliceBlocks.PALE_OAK_LOG);
    this.log(SpliceBlocks.STRIPPED_PALE_OAK_LOG);
    this.wood(SpliceBlocks.PALE_OAK_WOOD, SpliceUtils.getLocation(SpliceBlocks.PALE_OAK_LOG.get()));
    this.wood(
        SpliceBlocks.STRIPPED_PALE_OAK_WOOD,
        SpliceUtils.getLocation(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get()));
  }

  private void log(DeferredBlock<RotatedPillarBlock> block) {
    this.logBlock(block.get());
  }

  private void wood(DeferredBlock<RotatedPillarBlock> block, ResourceLocation texture) {
    final Block id = block.get();
    final String name = SpliceUtils.getName(id);

    final ModelFile model = this.models().cubeColumn(name, texture, texture);
    this.getVariantBuilder(id)
        .partialState()
        .with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
        .modelForState()
        .modelFile(model)
        .addModel()
        .partialState()
        .with(RotatedPillarBlock.AXIS, Direction.Axis.X)
        .modelForState()
        .modelFile(model)
        .rotationX(90)
        .rotationY(90)
        .addModel()
        .partialState()
        .with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
        .modelForState()
        .modelFile(model)
        .rotationX(90)
        .addModel();
  }

  private void resinBlocks() {
    this.block(SpliceBlocks.RESIN_BLOCK);
    this.multiface(SpliceBlocks.RESIN_CLUMP);
  }

  private void blockFamilyBlocks() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel()) {
        continue;
      }

      final Block base = family.getBaseBlock();
      if (base == null) {
        return;
      }

      this.simpleBlock(base);
      final ResourceLocation texture = SpliceUtils.getLocation(base);

      final Block button = family.get(BlockFamily.Variant.BUTTON);
      if (button != null) {
        this.buttonBlock(((ButtonBlock) button), texture);
      }

      final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
      if (chiseled != null) {
        this.simpleBlock(chiseled);
      }

      final Block fence = family.get(BlockFamily.Variant.FENCE);
      if (fence != null) {
        this.fenceBlock((FenceBlock) fence, texture);
      }

      final Block fenceGate = family.get(BlockFamily.Variant.FENCE_GATE);
      if (fenceGate != null) {
        this.fenceGateBlock((FenceGateBlock) fenceGate, texture);
      }

      final Block sign = family.get(BlockFamily.Variant.SIGN);
      final Block wallSign = family.get(BlockFamily.Variant.WALL_SIGN);
      if (sign != null && wallSign != null) {
        this.signBlock((StandingSignBlock) sign, ((WallSignBlock) wallSign), texture);
      }

      final Block slab = family.get(BlockFamily.Variant.SLAB);
      if (slab != null) {
        this.slabBlock(((SlabBlock) slab), texture, texture);
      }

      final Block stairs = family.get(BlockFamily.Variant.STAIRS);
      if (stairs != null) {
        this.stairsBlock(((StairBlock) stairs), texture);
      }

      final Block door = family.get(BlockFamily.Variant.DOOR);
      if (door != null) {
        this.doorBlock(
            (DoorBlock) door,
            ResourceLocation.parse(SpliceUtils.getLocation(door) + "_bottom"),
            ResourceLocation.parse(SpliceUtils.getLocation(door) + "_top"));
      }

      final Block pressurePlate = family.get(BlockFamily.Variant.PRESSURE_PLATE);
      if (pressurePlate != null) {
        this.pressurePlateBlock((PressurePlateBlock) pressurePlate, texture);
      }

      final Block wall = family.get(BlockFamily.Variant.WALL);
      if (wall != null) {
        this.wallBlock(((WallBlock) wall), texture);
      }
    }
  }

  private void copperBlocks() {
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::copperBars);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::copperChain);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::copperLantern);
    SpliceBlocks.COPPER_CHESTS.forEach(
        block ->
            this.simpleBlock(
                block.get(), this.models().getExistingFile(SpliceUtils.getLocation(block.get()))));
    this.torch(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
  }

  private void multiface(DeferredBlock<Block> block) {
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

  private void block(DeferredBlock<?> block) {
    final Block id = block.get();
    final ModelFile model = this.models().getExistingFile(SpliceUtils.getLocation(id));
    this.simpleBlock(id, model);
  }

  private void copperLantern(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.lantern(unaffected);
    this.lantern(waxed);
  }

  private void lantern(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof LanternBlock)) {
      throw new IllegalArgumentException("Expected LanternBlock, got: " + id);
    }

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
  }

  private void torch(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
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
  }

  private void copperBars(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.bars(unaffected);
    this.bars(waxed);
  }

  private void bars(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof IronBarsBlock bars)) {
      throw new IllegalArgumentException("Expected IronBarsBlock, got: " + id);
    }

    final String name = SpliceUtils.getName(id);
    final ResourceLocation texture = this.modLoc("block/" + SpliceUtils.stripWaxedPrefix(name));

    this.paneBlockWithRenderType(bars, texture, texture, this.mcLoc("cutout_mipped"));
  }

  private void copperChain(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.chain(unaffected);
    this.chain(waxed);
  }

  private void chain(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    if (!(id instanceof ChainBlock chain)) {
      throw new IllegalArgumentException("Expected ChainBlock, got: " + id);
    }

    final ModelFile model = this.models().getExistingFile(SpliceUtils.getLocation(id));
    this.axisBlock(chain, model, model);
  }
}
