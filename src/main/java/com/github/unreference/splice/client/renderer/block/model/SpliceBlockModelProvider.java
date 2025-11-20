package com.github.unreference.splice.client.renderer.block.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceModelUtils;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceBlockModelProvider extends BlockModelProvider {

  public SpliceBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    this.registerBlockFamilies();
    this.registerCopper();
    this.registerResin();
    this.registerPaleGarden();
  }

  // ===================================================================================================================
  // Pale Garden
  // ===================================================================================================================

  private void registerPaleGarden() {
    this.simpleBlock(SpliceBlocks.PALE_OAK_LEAVES);
    this.simpleBlock(SpliceBlocks.PALE_MOSS_BLOCK);

    this.crossAndPotted(SpliceBlocks.PALE_OAK_SAPLING, SpliceBlocks.POTTED_PALE_OAK_SAPLING);
    this.crossAndPotted(SpliceBlocks.CLOSED_EYEBLOSSOM, SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM);

    this.emissiveCrossAndPotted(SpliceBlocks.OPEN_EYEBLOSSOM, SpliceBlocks.POTTED_OPEN_EYEBLOSSOM);
    this.hangingMoss(SpliceBlocks.PALE_HANGING_MOSS.get());
    this.mossyCarpetSide();
    this.mossCarpet(SpliceBlocks.PALE_MOSS_CARPET.get());
  }

  private void emissiveCrossAndPotted(DeferredBlock<?> plant, DeferredBlock<FlowerPotBlock> pot) {
    this.emissiveCross(plant.get());
    this.emissivePotted(pot.get(), plant.get());
  }

  private void emissiveCross(Block block) {
    final ResourceLocation base = SpliceModelUtils.getLocation(block);
    final ResourceLocation emissive = SpliceModelUtils.setSuffix(base, "_emissive");

    this.withExistingParent(SpliceModelUtils.getName(block), this.modLoc("block/cross_emissive"))
        .texture("cross", base)
        .texture("cross_emissive", emissive)
        .renderType("cutout");
  }

  private void emissivePotted(Block pot, Block plant) {
    final ResourceLocation plantLoc = SpliceModelUtils.getLocation(plant);
    final ResourceLocation emissive = SpliceModelUtils.setSuffix(plantLoc, "_emissive");

    this.withExistingParent(
            SpliceModelUtils.getName(pot), this.modLoc("block/flower_pot_cross_emissive"))
        .texture("plant", plantLoc)
        .texture("cross_emissive", emissive)
        .renderType("cutout");
  }

  private void hangingMoss(Block block) {
    final String name = SpliceModelUtils.getName(block);
    final ResourceLocation texture = SpliceModelUtils.getLocation(block);
    this.cross(name, texture).renderType("cutout");
    this.cross(name + "_tip", SpliceModelUtils.setSuffix(texture, "_tip")).renderType("cutout");
  }

  private void mossCarpet(Block block) {
    final String name = SpliceModelUtils.getName(block);
    final ResourceLocation texture = SpliceModelUtils.getLocation(block);
    final ResourceLocation parent = this.modLoc("block/mossy_carpet_side");

    this.withExistingParent(name, mcLoc("block/carpet")).texture("wool", texture);
    this.withExistingParent(name + "_side_tall", parent)
        .texture("side", SpliceModelUtils.setSuffix(texture, "_side_tall"))
        .renderType("cutout");
    this.withExistingParent(name + "_side_small", parent)
        .texture("side", SpliceModelUtils.setSuffix(texture, "_side_small"))
        .renderType("cutout");
  }

  // ===================================================================================================================
  // Copper
  // ===================================================================================================================

  private void registerCopper() {
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::chainPair);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::lanternPair);

    this.torchPair(SpliceBlocks.COPPER_TORCH.get(), SpliceBlocks.COPPER_WALL_TORCH.get());

    this.copperChestPair(
        SpliceBlocks.COPPER_CHEST, SpliceBlocks.WAXED_COPPER_CHEST, Blocks.COPPER_BLOCK);
    this.copperChestPair(
        SpliceBlocks.EXPOSED_COPPER_CHEST,
        SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST,
        Blocks.EXPOSED_COPPER);
    this.copperChestPair(
        SpliceBlocks.WEATHERED_COPPER_CHEST,
        SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST,
        Blocks.WEATHERED_COPPER);
    this.copperChestPair(
        SpliceBlocks.OXIDIZED_COPPER_CHEST,
        SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST,
        Blocks.OXIDIZED_COPPER);
  }

  private void lanternPair(
      DeferredBlock<? extends Block> normal, DeferredBlock<? extends Block> waxed) {
    final ResourceLocation texture = SpliceModelUtils.getLocation(normal.get());
    this.lantern(normal.get(), texture);
    this.lantern(waxed.get(), texture);
  }

  private void lantern(Block block, ResourceLocation texture) {
    final String name = SpliceModelUtils.getName(block);
    this.withExistingParent(name, mcLoc("block/template_lantern"))
        .texture("lantern", texture)
        .renderType("cutout");
    this.withExistingParent(name + "_hanging", mcLoc("block/template_hanging_lantern"))
        .texture("lantern", texture)
        .renderType("cutout");
  }

  private void chainPair(
      DeferredBlock<? extends Block> normal, DeferredBlock<? extends Block> waxed) {
    final ResourceLocation texture = SpliceModelUtils.getLocation(normal.get());
    this.chain(normal.get(), texture);
    this.chain(waxed.get(), texture);
  }

  private void chain(Block block, ResourceLocation texture) {
    this.withExistingParent(SpliceModelUtils.getName(block), mcLoc("block/chain"))
        .texture("all", texture)
        .renderType("cutout");
  }

  private void copperChestPair(
      DeferredBlock<Block> normal, DeferredBlock<Block> waxed, Block particleSource) {
    final ResourceLocation particle = SpliceModelUtils.getLocation(particleSource);
    this.withExistingParent(SpliceModelUtils.getName(normal.get()), mcLoc("block/chest"))
        .texture("particle", particle);
    this.withExistingParent(
        SpliceModelUtils.getName(waxed.get()),
        this.modLoc("block/" + SpliceModelUtils.getName(normal.get())));
  }

  private void torchPair(Block standing, Block wall) {
    final ResourceLocation texture = SpliceModelUtils.getLocation(standing);
    this.torch(SpliceModelUtils.getName(standing), texture).renderType("cutout");
    this.torchWall(SpliceModelUtils.getName(wall), texture).renderType("cutout");
  }

  // ===================================================================================================================
  // Resin
  // ===================================================================================================================

  private void registerResin() {
    this.simpleBlock(SpliceBlocks.RESIN_BLOCK);

    final ResourceLocation texture = this.modLoc("block/resin_clump");
    this.getBuilder("resin_clump")
        .renderType("cutout")
        .ao(false)
        .texture("particle", texture)
        .texture("texture", texture)
        .element()
        .from(0.0f, 0.0f, 0.1f)
        .to(16.0f, 16.0f, 0.1f)
        .face(Direction.NORTH)
        .uvs(16.0f, 0.0f, 0.0f, 16.0f)
        .texture("#texture")
        .end()
        .face(Direction.SOUTH)
        .uvs(0.0f, 0.0f, 16.0f, 16.0f)
        .texture("#texture")
        .end()
        .end();
  }

  // ===================================================================================================================
  // General & Helpers
  // ===================================================================================================================

  private void registerBlockFamilies() {
    SpliceBlockFamilies.getBlockFamilies().values().stream()
        .filter(BlockFamily::shouldGenerateModel)
        .forEach(
            family -> {
              if (family.getBaseBlock() != null) {
                this.simpleBlock(family.getBaseBlock());

                if (family.get(BlockFamily.Variant.CHISELED) != null) {
                  this.simpleBlock(family.get(BlockFamily.Variant.CHISELED));
                }
              }
            });
  }

  private void simpleBlock(DeferredBlock<?> block) {
    this.simpleBlock(block.get());
  }

  private void simpleBlock(Block block) {
    this.cubeAll(SpliceModelUtils.getName(block), SpliceModelUtils.getLocation(block));
  }

  private void crossAndPotted(DeferredBlock<?> plant, DeferredBlock<FlowerPotBlock> pot) {
    final Block plantBlock = plant.get();
    this.cross(SpliceModelUtils.getName(plantBlock), SpliceModelUtils.getLocation(plantBlock))
        .renderType("cutout");
    this.flowerPot(pot.get(), plantBlock);
  }

  private void flowerPot(Block pot, Block plant) {
    this.withExistingParent(SpliceModelUtils.getName(pot), mcLoc("block/flower_pot_cross"))
        .texture("plant", SpliceModelUtils.getLocation(plant))
        .renderType("cutout");
  }

  private void mossyCarpetSide() {
    final String side = "#side";
    this.getBuilder("mossy_carpet_side")
        .ao(true)
        .texture("particle", side)
        .element()
        .from(0.0f, 0.0f, 0.1f)
        .to(16.0f, 16.0f, 0.1f)
        .shade(true)
        .face(Direction.NORTH)
        .uvs(16.0f, 0.0f, 0.0f, 16.0f)
        .texture(side)
        .end()
        .face(Direction.SOUTH)
        .uvs(0.0f, 0.0f, 16.0f, 16.0f)
        .texture(side)
        .end()
        .end();
  }
}
