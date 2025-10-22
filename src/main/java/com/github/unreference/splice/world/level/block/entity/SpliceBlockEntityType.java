package com.github.unreference.splice.world.level.block.entity;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceBlockEntityType {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
      DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SpliceMain.MOD_ID);

  public static void register(IEventBus bus) {
    BLOCK_ENTITY_TYPES.register(bus);
  }

  public static final Supplier<BlockEntityType<SpliceCopperChestBlockEntity>> COPPER_CHEST =
      BLOCK_ENTITY_TYPES.register(
          "copper_chest",
          () ->
              BlockEntityType.Builder.of(
                      SpliceCopperChestBlockEntity::new,
                      SpliceBlocks.COPPER_CHEST.get(),
                      SpliceBlocks.EXPOSED_COPPER_CHEST.get(),
                      SpliceBlocks.WEATHERED_COPPER_CHEST.get(),
                      SpliceBlocks.OXIDIZED_COPPER_CHEST.get(),
                      SpliceBlocks.WAXED_COPPER_CHEST.get(),
                      SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST.get(),
                      SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST.get(),
                      SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST.get())
                  .build(null));
}
