package com.github.unreference.splice.data;

import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

public final class SpliceBlockFamilies {
  private static final Map<Block, BlockFamily> BLOCK_FAMILIES = new HashMap<>();

  private static final BlockFamily RESIN_BRICKS =
      buildFamily(SpliceBlocks.RESIN_BRICKS.get())
          .chiseled(SpliceBlocks.CHISELED_RESIN_BRICKS.get())
          .slab(SpliceBlocks.RESIN_BRICK_SLAB.get())
          .stairs(SpliceBlocks.RESIN_BRICK_STAIRS.get())
          .wall(SpliceBlocks.RESIN_BRICK_WALL.get())
          .getFamily();

  private static final BlockFamily PALE_OAK_PLANKS =
      buildFamily(SpliceBlocks.PALE_OAK_PLANKS.get())
          .button(SpliceBlocks.PALE_OAK_BUTTON.get())
          .fence(SpliceBlocks.PALE_OAK_FENCE.get())
          .fenceGate(SpliceBlocks.PALE_OAK_FENCE_GATE.get())
          .pressurePlate(SpliceBlocks.PALE_OAK_PRESSURE_PLATE.get())
          .sign(SpliceBlocks.PALE_OAK_SIGN.get(), SpliceBlocks.PALE_OAK_WALL_SIGN.get())
          .slab(SpliceBlocks.PALE_OAK_SLAB.get())
          .stairs(SpliceBlocks.PALE_OAK_STAIRS.get())
          .getFamily();

  private static BlockFamily.Builder buildFamily(Block block) {
    final BlockFamily.Builder builder = new BlockFamily.Builder(block);
    final BlockFamily family = BLOCK_FAMILIES.put(block, builder.getFamily());
    if (family != null) {
      throw new IllegalStateException(
          "Duplicate family definition: " + BuiltInRegistries.BLOCK.getKey(block));
    }

    return builder;
  }

  public static Map<Block, BlockFamily> getBlockFamilies() {
    return BLOCK_FAMILIES;
  }
}
