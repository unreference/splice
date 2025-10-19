package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceBlocks {
  private static final DeferredRegister.Blocks BLOCKS =
      DeferredRegister.createBlocks(SpliceMain.MOD_ID);

  public static final SpliceWeatheringCopperBlocks COPPER_BARS =
      SpliceWeatheringCopperBlocks.create(
          "copper_bars",
          SpliceBlocks::register,
          IronBarsBlock::new,
          SpliceWeatheringCopperBarsBlock::new,
          weatherState ->
              BlockBehaviour.Properties.of()
                  .requiresCorrectToolForDrops()
                  .strength(5.0f, 6.0f)
                  .sound(SoundType.COPPER)
                  .noOcclusion());

  private static <B extends Block> DeferredBlock<B> register(
      String key,
      Function<BlockBehaviour.Properties, ? extends B> ctor,
      BlockBehaviour.Properties properties) {
    return BLOCKS.registerBlock(key, ctor, properties);
  }

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
