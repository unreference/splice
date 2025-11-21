package com.github.unreference.splice.world.level.levelgen.feature.stateproviders;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceBlockPropertyUtils;
import com.github.unreference.splice.util.SpliceModelUtils;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.SpliceCreakingHeartBlock;
import com.github.unreference.splice.world.level.block.SpliceHangingMossBlock;
import com.github.unreference.splice.world.level.block.SpliceMossyCarpetBlock;
import com.github.unreference.splice.world.level.block.state.properties.SpliceCreakingHeartState;
import java.util.HashMap;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceBlockStateProvider extends BlockStateProvider {
  public SpliceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, SpliceMain.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    this.registerBlockFamilies();
    this.registerCopper();
    this.registerResin();
    this.registerPaleGarden();
  }

  // ===================================================================================================================
  // Pale Garden
  // ===================================================================================================================

  private void registerPaleGarden() {
    this.logBlock(SpliceBlocks.PALE_OAK_LOG.get());
    this.logBlock(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get());
    this.woodBlock(
        SpliceBlocks.PALE_OAK_WOOD.get(),
        SpliceModelUtils.getLocation(SpliceBlocks.PALE_OAK_LOG.get()));
    this.woodBlock(
        SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get(),
        SpliceModelUtils.getLocation(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get()));

    this.existingBlock(SpliceBlocks.PALE_OAK_SAPLING);
    this.existingBlock(SpliceBlocks.PALE_OAK_LEAVES);
    this.existingBlock(SpliceBlocks.POTTED_PALE_OAK_SAPLING);
    this.existingBlock(SpliceBlocks.PALE_MOSS_BLOCK);

    this.existingBlock(SpliceBlocks.CLOSED_EYEBLOSSOM);
    this.existingBlock(SpliceBlocks.OPEN_EYEBLOSSOM);
    this.existingBlock(SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM);
    this.existingBlock(SpliceBlocks.POTTED_OPEN_EYEBLOSSOM);

    this.hangingSignBlock(
        SpliceBlocks.PALE_OAK_HANGING_SIGN.get(),
        SpliceBlocks.PALE_OAK_WALL_HANGING_SIGN.get(),
        SpliceModelUtils.getLocation(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get()));
    this.hangingMoss();
    this.mossyCarpet();
    this.creakingHeart();
  }

  private void woodBlock(RotatedPillarBlock block, ResourceLocation texture) {
    this.axisBlock(block, texture, texture);
  }

  private void creakingHeart() {
    final Block block = SpliceBlocks.CREAKING_HEART.get();
    final String name = SpliceModelUtils.getName(block);
    final ResourceLocation side = modLoc("block/" + name);

    final HashMap<SpliceCreakingHeartState, ModelPair> models = new HashMap<>();
    models.put(
        SpliceCreakingHeartState.UPROOTED,
        new ModelPair(
            models().cubeColumn(name, side, SpliceModelUtils.setSuffix(side, "_top")),
            models()
                .cubeColumnHorizontal(
                    name + "_horizontal", side, SpliceModelUtils.setSuffix(side, "_top"))));
    models.put(
        SpliceCreakingHeartState.AWAKE,
        new ModelPair(
            models()
                .cubeColumn(
                    name + "_awake",
                    SpliceModelUtils.setSuffix(side, "_awake"),
                    SpliceModelUtils.setSuffix(side, "_top_awake")),
            models()
                .cubeColumnHorizontal(
                    name + "_awake_horizontal",
                    SpliceModelUtils.setSuffix(side, "_awake"),
                    SpliceModelUtils.setSuffix(side, "_top_awake"))));
    models.put(
        SpliceCreakingHeartState.DORMANT,
        new ModelPair(
            models()
                .cubeColumn(
                    name + "_dormant",
                    SpliceModelUtils.setSuffix(side, "_dormant"),
                    SpliceModelUtils.setSuffix(side, "_top_dormant")),
            models()
                .cubeColumnHorizontal(
                    name + "_dormant_horizontal",
                    SpliceModelUtils.setSuffix(side, "_dormant"),
                    SpliceModelUtils.setSuffix(side, "_top_dormant"))));

    this.getVariantBuilder(block)
        .forAllStatesExcept(
            state -> {
              final SpliceCreakingHeartState heartState =
                  state.getValue(SpliceCreakingHeartBlock.STATE);
              final Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
              final ModelPair pair =
                  models.getOrDefault(heartState, models.get(SpliceCreakingHeartState.UPROOTED));

              return switch (axis) {
                case Y -> ConfiguredModel.builder().modelFile(pair.vertical).build();
                case Z ->
                    ConfiguredModel.builder().modelFile(pair.horizontal).rotationX(90).build();
                case X ->
                    ConfiguredModel.builder()
                        .modelFile(pair.horizontal)
                        .rotationX(90)
                        .rotationY(90)
                        .build();
              };
            },
            SpliceCreakingHeartBlock.IS_NATURAL);
  }

  private void hangingMoss() {
    final Block block = SpliceBlocks.PALE_HANGING_MOSS.get();
    final ModelFile main = this.existingBlockModel(block);
    final ModelFile tip =
        this.existingBlockModel(
            SpliceModelUtils.setSuffix(SpliceModelUtils.getLocation(block), "_tip"));

    this.getVariantBuilder(block)
        .forAllStates(
            state -> {
              boolean isTip = state.getValue(SpliceHangingMossBlock.IS_JUST_THE_TIP);
              return ConfiguredModel.builder().modelFile(isTip ? tip : main).build();
            });
  }

  private void mossyCarpet() {
    final Block block = SpliceBlocks.PALE_MOSS_CARPET.get();
    final ResourceLocation baseLoc = SpliceModelUtils.getLocation(block);

    final ModelFile carpet = this.existingBlockModel(baseLoc);
    final ModelFile tall =
        this.existingBlockModel(SpliceModelUtils.setSuffix(baseLoc, "_side_tall"));
    final ModelFile low =
        this.existingBlockModel(SpliceModelUtils.setSuffix(baseLoc, "_side_small"));

    final MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

    builder.part().modelFile(carpet).addModel().condition(SpliceMossyCarpetBlock.IS_BASE, true);

    for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
      final int y = ((int) direction.toYRot() + 180) % 360;
      final EnumProperty<WallSide> side = SpliceBlockPropertyUtils.getWallSideProperty(direction);

      if (side != null) {
        builder
            .part()
            .modelFile(tall)
            .rotationY(y)
            .uvLock(direction != Direction.NORTH)
            .addModel()
            .condition(side, WallSide.TALL);
        builder
            .part()
            .modelFile(low)
            .rotationY(y)
            .uvLock(direction != Direction.NORTH)
            .addModel()
            .condition(side, WallSide.LOW);
      }
    }

    builder
        .part()
        .rotationY(0)
        .modelFile(carpet)
        .addModel()
        .condition(SpliceMossyCarpetBlock.IS_BASE, false)
        .condition(SpliceMossyCarpetBlock.NORTH, WallSide.NONE)
        .condition(SpliceMossyCarpetBlock.SOUTH, WallSide.NONE)
        .condition(SpliceMossyCarpetBlock.EAST, WallSide.NONE)
        .condition(SpliceMossyCarpetBlock.WEST, WallSide.NONE);
  }

  private void registerResin() {
    this.existingBlock(SpliceBlocks.RESIN_BLOCK);
    this.multiface(SpliceBlocks.RESIN_CLUMP.get());
  }

  // ===================================================================================================================
  // Resin (Multiface)
  // ===================================================================================================================

  private void multiface(Block block) {
    final MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
    final ModelFile model = this.existingBlockModel(block);

    for (Direction direction : Direction.values()) {
      int x = 0;
      int y = 0;

      switch (direction) {
        case UP -> x = 270;
        case DOWN -> x = 90;
        case SOUTH -> y = 180;
        case WEST -> y = 270;
        case EAST -> y = 90;
      }

      builder
          .part()
          .modelFile(model)
          .rotationX(x)
          .rotationY(y)
          .uvLock(true)
          .addModel()
          .condition(SpliceBlockPropertyUtils.getDirectionProperty(direction), true);
    }

    builder
        .part()
        .modelFile(model)
        .addModel()
        .condition(BlockStateProperties.DOWN, false)
        .condition(BlockStateProperties.UP, false)
        .condition(BlockStateProperties.NORTH, false)
        .condition(BlockStateProperties.SOUTH, false)
        .condition(BlockStateProperties.EAST, false)
        .condition(BlockStateProperties.WEST, false);
  }

  private void registerCopper() {
    SpliceBlocks.COPPER_BARS.waxedMapping().forEach(this::barsPair);
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::chainPair);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::lanternPair);

    SpliceBlocks.COPPER_CHESTS.forEach(this::existingBlock);
    this.torchPair(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
  }

  // ===================================================================================================================
  // Copper
  // ===================================================================================================================

  private void barsPair(
      DeferredBlock<? extends Block> normal, DeferredBlock<? extends Block> waxed) {
    final ResourceLocation texture = this.modLoc("block/" + SpliceModelUtils.getName(normal.get()));
    this.bars((IronBarsBlock) normal.get(), texture);
    this.bars((IronBarsBlock) waxed.get(), texture);
  }

  private void bars(IronBarsBlock block, ResourceLocation texture) {
    this.paneBlockWithRenderType(block, texture, texture, this.mcLoc("cutout_mipped"));
  }

  private void chainPair(
      DeferredBlock<? extends Block> normal, DeferredBlock<? extends Block> waxed) {
    final ModelFile model = this.existingBlockModel(normal.get());
    this.axisBlock((RotatedPillarBlock) normal.get(), model, model);
    this.axisBlock((RotatedPillarBlock) waxed.get(), model, model);
  }

  private void lanternPair(
      DeferredBlock<? extends Block> normal, DeferredBlock<? extends Block> waxed) {
    this.lantern(normal.get());
    this.lantern(waxed.get());
  }

  private void lantern(Block block) {
    final ModelFile standing = this.existingBlockModel(block);
    final ModelFile hanging =
        this.existingBlockModel(
            SpliceModelUtils.setSuffix(SpliceModelUtils.getLocation(block), "_hanging"));

    this.getVariantBuilder(block)
        .partialState()
        .with(BlockStateProperties.HANGING, false)
        .modelForState()
        .modelFile(standing)
        .addModel()
        .partialState()
        .with(BlockStateProperties.HANGING, true)
        .modelForState()
        .modelFile(hanging)
        .addModel();
  }

  private void torchPair(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
    final ModelFile standingModel = this.existingBlockModel(standing.get());
    final ModelFile wallModel = this.existingBlockModel(wall.get());

    this.simpleBlock(standing.get(), standingModel);

    this.getVariantBuilder(wall.get())
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        .modelForState()
        .modelFile(wallModel)
        .rotationY(270)
        .addModel()
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
        .modelForState()
        .modelFile(wallModel)
        .rotationY(90)
        .addModel()
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
        .modelForState()
        .modelFile(wallModel)
        .addModel()
        .partialState()
        .with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
        .modelForState()
        .modelFile(wallModel)
        .rotationY(180)
        .addModel();
  }

  private void registerBlockFamilies() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel() || family.getBaseBlock() == null) {
        continue;
      }

      final Block base = family.getBaseBlock();
      this.simpleBlock(base);
      final ResourceLocation texture = SpliceModelUtils.getLocation(base);

      if (family.get(BlockFamily.Variant.BUTTON) instanceof ButtonBlock button) {
        this.buttonBlock(button, texture);
      }

      if (family.get(BlockFamily.Variant.CHISELED) instanceof Block chiseled) {
        this.simpleBlock(chiseled);
      }

      if (family.get(BlockFamily.Variant.DOOR) instanceof DoorBlock door) {
        this.doorBlock(
            door,
            SpliceModelUtils.setSuffix(SpliceModelUtils.getLocation(door), "_bottom"),
            SpliceModelUtils.setSuffix(SpliceModelUtils.getLocation(door), "_top"));
      }

      if (family.get(BlockFamily.Variant.FENCE) instanceof FenceBlock fence) {
        this.fenceBlock(fence, texture);
      }

      if (family.get(BlockFamily.Variant.FENCE_GATE) instanceof FenceGateBlock fenceGate) {
        this.fenceGateBlock(fenceGate, texture);
      }

      if (family.get(BlockFamily.Variant.SIGN) instanceof StandingSignBlock standingSign
          && family.get(BlockFamily.Variant.WALL_SIGN) instanceof WallSignBlock wallSign) {
        this.signBlock(standingSign, wallSign, texture);
      }

      if (family.get(BlockFamily.Variant.SLAB) instanceof SlabBlock slab) {
        this.slabBlock(slab, texture, texture);
      }

      if (family.get(BlockFamily.Variant.STAIRS) instanceof StairBlock stairs) {
        this.stairsBlock(stairs, texture);
      }

      if (family.get(BlockFamily.Variant.PRESSURE_PLATE)
          instanceof PressurePlateBlock pressurePlate) {
        this.pressurePlateBlock(pressurePlate, texture);
      }

      if (family.get(BlockFamily.Variant.TRAPDOOR) instanceof TrapDoorBlock trapdoor)
        this.trapdoorBlock(trapdoor, SpliceModelUtils.getLocation(trapdoor), true);

      if (family.get(BlockFamily.Variant.WALL) instanceof WallBlock wall) {
        this.wallBlock(wall, texture);
      }
    }
  }

  // ===================================================================================================================
  // Block Families
  // ===================================================================================================================

  private void existingBlock(DeferredBlock<?> block) {
    this.existingBlock(block.get());
  }

  // ===================================================================================================================
  // Helpers
  // ===================================================================================================================

  private void existingBlock(Block block) {
    this.simpleBlock(block, this.existingBlockModel(block));
  }

  private ModelFile existingBlockModel(Block block) {
    return this.models().getExistingFile(SpliceModelUtils.getLocation(block));
  }

  private ModelFile existingBlockModel(ResourceLocation location) {
    return this.models().getExistingFile(location);
  }

  private record ModelPair(ModelFile vertical, ModelFile horizontal) {}
}
