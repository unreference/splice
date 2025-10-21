package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceBlockTypes {
  private static final DeferredRegister<MapCodec<? extends Block>> REGISTRAR =
      DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, SpliceMain.MOD_ID);

  private static final DeferredHolder<
          MapCodec<? extends Block>, MapCodec<SpliceWeatheringCopperBarsBlock>>
      COPPER_BAR_CODEC =
          REGISTRAR.register("copper_bar", () -> SpliceWeatheringCopperBarsBlock.CODEC);

  public static void register(IEventBus bus) {
    REGISTRAR.register(bus);
  }
}
