package com.github.unreference.splice.client.renderer.block.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.SpliceBlockFamilies;
import com.github.unreference.splice.util.SpliceUtils;
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

  private static String stripWaxedPrefix(String name) {
    return name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;
  }

  @Override
  protected void registerModels() {
    this.createFamilies();
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::copperChainModel);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::copperLanternModel);
    this.torchModel(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
    this.copperChestModels();
    this.cubeModel(SpliceBlocks.RESIN_BLOCK);
    this.resinClump();
  }

  private void createFamilies() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel()) {
        continue;
      }

      final Block baseBlock = family.getBaseBlock();
      final ResourceLocation baseTexture = SpliceUtils.getLocation(baseBlock);

      this.cubeModel(baseBlock);

      final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
      this.cubeModel(chiseled);
    }
  }

  private void resinClump() {
    final ResourceLocation texture = this.modLoc("block/resin_clump");
    this.getBuilder("resin_clump")
        .renderType("minecraft:cutout")
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
        .end();
  }

  private void cubeModel(DeferredBlock<Block> block) {
    final String name = SpliceUtils.getName(block.get());
    final ResourceLocation texture = SpliceUtils.getLocation(block.get());
    this.cubeAll(name, texture);
  }

  private void cubeModel(Block block) {
    final String name = SpliceUtils.getName(block);
    final ResourceLocation texture = SpliceUtils.getLocation(block);
    this.cubeAll(name, texture);
  }

  private void copperChestModels() {
    this.chestModel(SpliceBlocks.COPPER_CHEST, Blocks.COPPER_BLOCK);
    this.chestModel(SpliceBlocks.EXPOSED_COPPER_CHEST, Blocks.EXPOSED_COPPER);
    this.chestModel(SpliceBlocks.WEATHERED_COPPER_CHEST, Blocks.WEATHERED_COPPER);
    this.chestModel(SpliceBlocks.OXIDIZED_COPPER_CHEST, Blocks.OXIDIZED_COPPER);
    this.copyModel(SpliceBlocks.COPPER_CHEST, SpliceBlocks.WAXED_COPPER_CHEST);
    this.copyModel(SpliceBlocks.EXPOSED_COPPER_CHEST, SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST);
    this.copyModel(SpliceBlocks.WEATHERED_COPPER_CHEST, SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST);
    this.copyModel(SpliceBlocks.OXIDIZED_COPPER_CHEST, SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST);
  }

  private void copyModel(DeferredBlock<Block> from, DeferredBlock<Block> to) {
    final String fromName = SpliceUtils.getName(from.get());
    final String toName = SpliceUtils.getName(to.get());
    this.withExistingParent(toName, modLoc("block/" + fromName));
  }

  private void chestModel(DeferredBlock<Block> block, Block particle) {
    final String name = SpliceUtils.getName(block.get());
    this.withExistingParent(name, mcLoc("block/chest"))
        .texture("particle", SpliceUtils.getLocation(particle));
  }

  private void torchModel(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
    final String standingName = SpliceUtils.getName(standing.get());
    final String wallName = SpliceUtils.getName(wall.get());
    final ResourceLocation texture = modLoc("block/" + standingName);

    this.torch(standingName, texture).renderType("minecraft:cutout");
    this.torchWall(wallName, texture).renderType("minecraft:cutout");
  }

  private void copperLanternModel(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.lanternModel(unaffected);
    this.lanternModel(waxed);
  }

  private void lanternModel(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    final String name = SpliceUtils.getName(id);
    final String textureName = stripWaxedPrefix(name);
    final ResourceLocation texture = modLoc("block/" + textureName);

    this.withExistingParent(name, mcLoc("block/template_lantern"))
        .texture("lantern", texture)
        .renderType("minecraft:cutout");

    this.withExistingParent(name + "_hanging", mcLoc("block/template_hanging_lantern"))
        .texture("lantern", texture)
        .renderType("minecraft:cutout");
  }

  private void copperChainModel(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.chainModel(unaffected);
    this.chainModel(waxed);
  }

  private void chainModel(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    final String name = SpliceUtils.getName(id);
    final String texture = stripWaxedPrefix(name);

    this.withExistingParent(name, mcLoc("block/chain"))
        .texture("all", modLoc("block/" + texture))
        .renderType("minecraft:cutout");
  }
}
