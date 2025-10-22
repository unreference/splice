package com.github.unreference.splice.client.renderer.entity.blockentity;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.SpliceCopperChestBlock;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

public final class SpliceCopperChestRenderer extends ChestRenderer<SpliceCopperChestBlockEntity> {
  private static final Map<String, ChestMaterials> CACHE = new HashMap<>();

  public SpliceCopperChestRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  private static Material getMaterial(String key) {
    return new Material(
        Sheets.CHEST_SHEET,
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entity/chest/" + key));
  }

  private static ChestMaterials getMaterials(String key) {
    return CACHE.computeIfAbsent(
        key,
        k ->
            new ChestMaterials(
                getMaterial(k), getMaterial(k + "_left"), getMaterial(k + "_right")));
  }

  private static String getVariantKey(SpliceCopperChestBlockEntity blockEntity) {
    if (blockEntity.getBlockState().getBlock() instanceof SpliceCopperChestBlock chest) {
      return switch (chest.getState()) {
        case UNAFFECTED -> "copper";
        case EXPOSED -> "copper_exposed";
        case WEATHERED -> "copper_weathered";
        case OXIDIZED -> "copper_oxidized";
      };
    }

    return "copper";
  }

  @Override
  protected @NotNull Material getMaterial(@NotNull SpliceCopperChestBlockEntity blockEntity, ChestType chestType) {
    ChestMaterials material = getMaterials(getVariantKey(blockEntity));
    return switch (chestType) {
      case LEFT -> material.left();
      case RIGHT -> material.right();
      default -> material.single();
    };
  }

  private record ChestMaterials(Material single, Material left, Material right) {}
}
