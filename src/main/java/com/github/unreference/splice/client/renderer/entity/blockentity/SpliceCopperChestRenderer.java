package com.github.unreference.splice.client.renderer.entity.blockentity;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;

public final class SpliceCopperChestRenderer extends ChestRenderer<SpliceCopperChestBlockEntity> {
  private static final Material SINGLE =
      new Material(
          Sheets.CHEST_SHEET,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entity/chest/copper"));
  private static final Material LEFT =
      new Material(
          Sheets.CHEST_SHEET,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entity/chest/copper_left"));
  private static final Material RIGHT =
      new Material(
          Sheets.CHEST_SHEET,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entity/chest/copper_right"));

  public SpliceCopperChestRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  protected Material getMaterial(SpliceCopperChestBlockEntity blockEntity, ChestType chestType) {
    return switch (chestType) {
      case SINGLE -> SINGLE;
      case LEFT -> LEFT;
      case RIGHT -> RIGHT;
    };
  }
}
