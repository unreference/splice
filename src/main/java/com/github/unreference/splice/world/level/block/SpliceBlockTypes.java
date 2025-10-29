package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceBlockTypes {
  private static final DeferredRegister<MapCodec<? extends Block>> CODEC =
      DeferredRegister.create(Registries.BLOCK_TYPE, SpliceMain.MOD_ID);

  private static final Supplier<MapCodec<SpliceCopperChestBlock>> COPPER_CHEST =
      CODEC.register("copper_chest", () -> SpliceCopperChestBlock.CODEC);

  private static final Supplier<MapCodec<SpliceWeatheringCopperBarsBlock>> WEATHERING_COPPER_BAR =
      CODEC.register("weathering_copper_bar", () -> SpliceWeatheringCopperBarsBlock.CODEC);

  private static final Supplier<MapCodec<SpliceWeatheringCopperChainBlock>>
      WEATHERING_COPPER_CHAIN =
          CODEC.register("weathering_copper_chain", () -> SpliceWeatheringCopperChainBlock.CODEC);

  private static final Supplier<MapCodec<SpliceWeatheringCopperChestBlock>>
      WEATHERING_COPPER_CHEST =
          CODEC.register("weathering_copper_chest", () -> SpliceWeatheringCopperChestBlock.CODEC);

  private static final Supplier<MapCodec<SpliceWeatheringLanternBlock>> WEATHERING_LANTERN =
      CODEC.register("weathering_lantern", () -> SpliceWeatheringLanternBlock.CODEC);

  private static final Supplier<MapCodec<SpliceResinClumpBlock>> RESIN =
      CODEC.register("resin", () -> SpliceResinClumpBlock.CODEC);

  public static void register(IEventBus bus) {
    CODEC.register(bus);
  }
}
