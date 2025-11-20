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
    this.blockFamily();
    this.copper();
    this.resin();
    this.mossyCarpetSide();
    this.paleGarden();
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
        .end();
  }

  private void paleGarden() {
    final Block sapling = SpliceBlocks.PALE_OAK_SAPLING.get();
    this.cross(SpliceUtils.getName(sapling), SpliceUtils.getLocation(sapling)).renderType("cutout");

    final Block leaves = SpliceBlocks.PALE_OAK_LEAVES.get();
    this.leaves(SpliceUtils.getName(leaves), SpliceUtils.getLocation(leaves));

    final Block pottedSapling = SpliceBlocks.POTTED_PALE_OAK_SAPLING.get();
    this.flowerPot(SpliceUtils.getName(pottedSapling), SpliceBlocks.PALE_OAK_SAPLING);

    final Block mossCarpet = SpliceBlocks.PALE_MOSS_CARPET.get();
    this.mossCarpet(mossCarpet);

    final Block mossBlock = SpliceBlocks.PALE_MOSS_BLOCK.get();
    this.cube(mossBlock);

    final Block hangingMoss = SpliceBlocks.PALE_HANGING_MOSS.get();
    this.hangingMoss(hangingMoss);

    final Block closedEyeblossom = SpliceBlocks.CLOSED_EYEBLOSSOM.get();
    this.cross(SpliceUtils.getName(closedEyeblossom), SpliceUtils.getLocation(closedEyeblossom))
        .renderType("cutout");

    this.openEyeblossom();

    final Block pottedClosedEyeblossom = SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM.get();
    this.flowerPot(SpliceUtils.getName(pottedClosedEyeblossom), SpliceBlocks.CLOSED_EYEBLOSSOM);

    this.pottedOpenEyeblossom();
    this.creakingHeart();
  }

  private void pottedOpenEyeblossom() {
    final Block pottedId = SpliceBlocks.POTTED_OPEN_EYEBLOSSOM.get();
    final String name = SpliceUtils.getName(pottedId);
    final ResourceLocation plant = SpliceUtils.getLocation(SpliceBlocks.OPEN_EYEBLOSSOM.get());
    final ResourceLocation emissive = ResourceLocation.parse(plant + "_emissive");

    this.withExistingParent(name, this.modLoc("block/flower_pot_cross_emissive"))
        .texture("plant", plant)
        .texture("cross_emissive", emissive)
        .renderType("cutout");
  }

  private void openEyeblossom() {
    final Block id = SpliceBlocks.OPEN_EYEBLOSSOM.get();
    final String name = SpliceUtils.getName(id);
    final ResourceLocation base = SpliceUtils.getLocation(id);
    final ResourceLocation emissive = ResourceLocation.parse(base + "_emissive");

    this.withExistingParent(name, this.modLoc("block/cross_emissive"))
        .texture("cross", base)
        .texture("cross_emissive", emissive)
        .renderType("cutout");
  }

  private void creakingHeart() {
    final Block creakingHeart = SpliceBlocks.CREAKING_HEART.get();
    this.cubeColumn(
        SpliceUtils.getName(creakingHeart),
        SpliceUtils.getLocation(creakingHeart),
        ResourceLocation.parse(SpliceUtils.getLocation(creakingHeart) + "_top"));
  }

  private void hangingMoss(Block block) {
    final String name = SpliceUtils.getName(block);
    this.cross(name, SpliceUtils.getLocation(block)).renderType("cutout");
    this.cross(name + "_tip", ResourceLocation.parse(SpliceUtils.getLocation(block) + "_tip"))
        .renderType("cutout");
  }

  private void mossCarpet(Block block) {
    final String name = SpliceUtils.getName(block);
    final ResourceLocation texture = SpliceUtils.getLocation(block);
    final ResourceLocation mossyCarpetSide = this.modLoc("block/mossy_carpet_side");
    final ResourceLocation sideTallTexture = ResourceLocation.parse(texture + "_side_tall");
    final ResourceLocation sideSmallTexture = ResourceLocation.parse(texture + "_side_small");

    this.withExistingParent(name, this.mcLoc("block/carpet")).texture("wool", texture);
    this.withExistingParent(name + "_side_tall", mossyCarpetSide)
        .texture("side", sideTallTexture)
        .renderType("cutout");
    this.withExistingParent(name + "_side_small", mossyCarpetSide)
        .texture("side", sideSmallTexture)
        .renderType("cutout");
  }

  private void flowerPot(String name, DeferredBlock<?> plant) {
    this.withExistingParent(name, this.mcLoc("block/flower_pot_cross"))
        .texture("plant", SpliceUtils.getLocation(plant.get()))
        .renderType("cutout");
  }

  private void resin() {
    this.cube(SpliceBlocks.RESIN_BLOCK);
    this.resinClump();
  }

  private void copper() {
    SpliceBlocks.COPPER_CHAIN.waxedMapping().forEach(this::copperChain);
    SpliceBlocks.COPPER_LANTERN.waxedMapping().forEach(this::copperLantern);
    this.torch(SpliceBlocks.COPPER_TORCH, SpliceBlocks.COPPER_WALL_TORCH);
    this.copperChest();
  }

  private void blockFamily() {
    for (BlockFamily family : SpliceBlockFamilies.getBlockFamilies().values()) {
      if (!family.shouldGenerateModel()) {
        continue;
      }

      final Block base = family.getBaseBlock();
      if (base == null) {
        return;
      }

      this.cube(base);

      final Block chiseled = family.get(BlockFamily.Variant.CHISELED);
      if (chiseled != null) {
        this.cube(chiseled);
      }
    }
  }

  private void resinClump() {
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
        .end();
  }

  private void cube(DeferredBlock<Block> block) {
    final String name = SpliceUtils.getName(block.get());
    final ResourceLocation texture = SpliceUtils.getLocation(block.get());
    this.cubeAll(name, texture);
  }

  private void cube(Block block) {
    final String name = SpliceUtils.getName(block);
    final ResourceLocation texture = SpliceUtils.getLocation(block);
    this.cubeAll(name, texture);
  }

  private void copperChest() {
    this.chest(SpliceBlocks.COPPER_CHEST, Blocks.COPPER_BLOCK);
    this.chest(SpliceBlocks.EXPOSED_COPPER_CHEST, Blocks.EXPOSED_COPPER);
    this.chest(SpliceBlocks.WEATHERED_COPPER_CHEST, Blocks.WEATHERED_COPPER);
    this.chest(SpliceBlocks.OXIDIZED_COPPER_CHEST, Blocks.OXIDIZED_COPPER);
    this.copy(SpliceBlocks.COPPER_CHEST, SpliceBlocks.WAXED_COPPER_CHEST);
    this.copy(SpliceBlocks.EXPOSED_COPPER_CHEST, SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST);
    this.copy(SpliceBlocks.WEATHERED_COPPER_CHEST, SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST);
    this.copy(SpliceBlocks.OXIDIZED_COPPER_CHEST, SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST);
  }

  private void copy(DeferredBlock<Block> from, DeferredBlock<Block> to) {
    final String fromName = SpliceUtils.getName(from.get());
    final String toName = SpliceUtils.getName(to.get());
    this.withExistingParent(toName, this.modLoc("block/" + fromName));
  }

  private void chest(DeferredBlock<Block> block, Block particle) {
    final String name = SpliceUtils.getName(block.get());
    this.withExistingParent(name, this.mcLoc("block/chest"))
        .texture("particle", SpliceUtils.getLocation(particle));
  }

  private void torch(DeferredBlock<Block> standing, DeferredBlock<Block> wall) {
    final String standingName = SpliceUtils.getName(standing.get());
    final String wallName = SpliceUtils.getName(wall.get());
    final ResourceLocation texture = this.modLoc("block/" + standingName);

    this.torch(standingName, texture).renderType("cutout");
    this.torchWall(wallName, texture).renderType("cutout");
  }

  private void copperLantern(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.lantern(unaffected);
    this.lantern(waxed);
  }

  private void lantern(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    final String name = SpliceUtils.getName(id);
    final String textureName = stripWaxedPrefix(name);
    final ResourceLocation texture = this.modLoc("block/" + textureName);

    this.withExistingParent(name, this.mcLoc("block/template_lantern"))
        .texture("lantern", texture)
        .renderType("cutout");

    this.withExistingParent(name + "_hanging", this.mcLoc("block/template_hanging_lantern"))
        .texture("lantern", texture)
        .renderType("cutout");
  }

  private void copperChain(
      DeferredBlock<? extends Block> unaffected, DeferredBlock<? extends Block> waxed) {
    this.chain(unaffected);
    this.chain(waxed);
  }

  private void chain(DeferredBlock<? extends Block> block) {
    final Block id = block.get();
    final String name = SpliceUtils.getName(id);
    final String texture = stripWaxedPrefix(name);

    this.withExistingParent(name, this.mcLoc("block/chain"))
        .texture("all", this.modLoc("block/" + texture))
        .renderType("cutout");
  }
}
