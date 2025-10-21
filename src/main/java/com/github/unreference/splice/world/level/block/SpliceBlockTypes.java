package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceBlockTypes {
  private static final DeferredRegister<MapCodec<? extends Block>> CODEC =
      DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, SpliceMain.MOD_ID);

  private static final DeferredHolder<
          MapCodec<? extends Block>, MapCodec<SpliceWeatheringCopperBarsBlock>>
      WEATHERING_COPPER_BAR =
          CODEC.register("weathering_copper_bar", SpliceWeatheringCopperBarsBlock::getCodec);

  private static final DeferredHolder<
          MapCodec<? extends Block>, MapCodec<SpliceWeatheringCopperChainBlock>>
      WEATHERING_COPPER_CHAIN =
          CODEC.register("weathering_copper_chain", SpliceWeatheringCopperChainBlock::getCodec);

  public static void register(IEventBus bus) {
    CODEC.register(bus);
  }
}
