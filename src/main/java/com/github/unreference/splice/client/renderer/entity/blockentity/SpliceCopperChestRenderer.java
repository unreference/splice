package com.github.unreference.splice.client.renderer.entity.blockentity;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.SpliceCopperChestBlock;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

public final class SpliceCopperChestRenderer extends ChestRenderer<SpliceCopperChestBlockEntity> {

  private static final ChestMaterials MAT_COPPER = getMaterials("copper");
  private static final ChestMaterials MAT_COPPER_EXPOSED = getMaterials("copper_exposed");
  private static final ChestMaterials MAT_COPPER_WEATHERED = getMaterials("copper_weathered");
  private static final ChestMaterials MAT_COPPER_OXIDIZED = getMaterials("copper_oxidized");

  public SpliceCopperChestRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  private static Material getMaterial(String key) {
    return new Material(
        Sheets.CHEST_SHEET,
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entity/chest/" + key));
  }

  private static ChestMaterials getMaterials(String base) {
    return new ChestMaterials(
        getMaterial(base), getMaterial(base + "_left"), getMaterial(base + "_right"));
  }

  private static ChestMaterials getMaterialsFor(SpliceCopperChestBlockEntity be) {
    if (be.getBlockState().getBlock() instanceof SpliceCopperChestBlock chest) {
      return switch (chest.getState()) {
        case UNAFFECTED -> MAT_COPPER;
        case EXPOSED -> MAT_COPPER_EXPOSED;
        case WEATHERED -> MAT_COPPER_WEATHERED;
        case OXIDIZED -> MAT_COPPER_OXIDIZED;
      };
    }

    return MAT_COPPER;
  }

  @Override
  protected @NotNull Material getMaterial(
      @NotNull SpliceCopperChestBlockEntity blockEntity, ChestType chestType) {
    ChestMaterials material = getMaterialsFor(blockEntity);
    return switch (chestType) {
      case LEFT -> material.left();
      case RIGHT -> material.right();
      case SINGLE -> material.single();
    };
  }

  private record ChestMaterials(Material single, Material left, Material right) {}
}
