package com.github.unreference.splice.client.renderer.block.model;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.util.SpliceBlockUtil;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class SpliceBlockModelProvider extends BlockModelProvider {
  public SpliceBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpliceMain.MOD_ID, existingFileHelper);
  }

  private static String getNameOf(Block block) {
    return SpliceBlockUtil.getId(block).getPath();
  }

  private static String stripWaxedPrefix(String name) {
    return name.startsWith("waxed_") ? name.substring("waxed_".length()) : name;
  }

  @Override
  protected void registerModels() {
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::copperChainModel);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::copperLanternModel);
    this.torchModel(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
    this.copperChestModels();
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
    final String fromName = getNameOf(from.get());
    final String toName = getNameOf(to.get());

    this.withExistingParent(toName, modLoc("block/" + fromName));
  }

  private void chestModel(DeferredBlock<Block> block, Block particle) {
    final String name = getNameOf(block.get());

    this.withExistingParent(name, mcLoc("block/chest"))
        .texture("particle", SpliceBlockUtil.getTexture(particle));
  }

  private void torchModel(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
    final String standingName = getNameOf(standing.get());
    final String wallName = getNameOf(wall.get());
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
    final String name = getNameOf(id);
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
    final String name = getNameOf(id);
    final String texture = stripWaxedPrefix(name);

    this.withExistingParent(name, mcLoc("block/chain"))
        .texture("all", modLoc("block/" + texture))
        .renderType("minecraft:cutout");
  }
}
