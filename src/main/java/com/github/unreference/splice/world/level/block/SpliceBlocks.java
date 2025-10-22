package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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

  public static final SpliceWeatheringCopperBlocks COPPER_CHAIN =
      SpliceWeatheringCopperBlocks.create(
          "copper_chain",
          SpliceBlocks::register,
          ChainBlock::new,
          SpliceWeatheringCopperChainBlock::new,
          weatherState ->
              BlockBehaviour.Properties.of()
                  .forceSolidOn()
                  .requiresCorrectToolForDrops()
                  .strength(5.0f, 6.0f)
                  .sound(SoundType.CHAIN)
                  .noOcclusion());

  private static final List<SpliceWeatheringCopperBlocks> COPPER_FAMILY =
      List.of(COPPER_BARS, COPPER_CHAIN);

  // TODO: Add to block loot
  public static final DeferredBlock<Block> COPPER_CHEST =
      register(
          "copper_chest",
          props ->
              new SpliceWeatheringCopperChestBlock(
                  WeatheringCopper.WeatherState.UNAFFECTED,
                  SpliceSoundEvents.COPPER_CHEST_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_CLOSE,
                  props),
          unaffectedCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> EXPOSED_COPPER_CHEST =
      register(
          "exposed_copper_chest",
          props ->
              new SpliceWeatheringCopperChestBlock(
                  WeatheringCopper.WeatherState.EXPOSED,
                  SpliceSoundEvents.COPPER_CHEST_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_CLOSE,
                  props),
          getExposedCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> WEATHERED_COPPER_CHEST =
      register(
          "weathered_copper_chest",
          props ->
              new SpliceWeatheringCopperChestBlock(
                  WeatheringCopper.WeatherState.WEATHERED,
                  SpliceSoundEvents.COPPER_CHEST_WEATHERED_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_WEATHERED_CLOSE,
                  props),
          getWeatheredCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> OXIDIZED_COPPER_CHEST =
      register(
          "oxidized_copper_chest",
          props ->
              new SpliceWeatheringCopperChestBlock(
                  WeatheringCopper.WeatherState.OXIDIZED,
                  SpliceSoundEvents.COPPER_CHEST_OXIDIZED_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_OXIDIZED_CLOSE,
                  props),
          getOxidizedCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> WAXED_COPPER_CHEST =
      register(
          "waxed_copper_chest",
          props ->
              new SpliceCopperChestBlock(
                  WeatheringCopper.WeatherState.UNAFFECTED,
                  SpliceSoundEvents.COPPER_CHEST_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_CLOSE,
                  props),
          unaffectedCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_CHEST =
      register(
          "waxed_exposed_copper_chest",
          props ->
              new SpliceCopperChestBlock(
                  WeatheringCopper.WeatherState.EXPOSED,
                  SpliceSoundEvents.COPPER_CHEST_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_CLOSE,
                  props),
          getExposedCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_CHEST =
      register(
          "waxed_weathered_copper_chest",
          props ->
              new SpliceCopperChestBlock(
                  WeatheringCopper.WeatherState.WEATHERED,
                  SpliceSoundEvents.COPPER_CHEST_WEATHERED_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_WEATHERED_CLOSE,
                  props),
          getWeatheredCopperChestProps());

  // TODO: Add to block loot
  public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_CHEST =
      register(
          "waxed_oxidized_copper_chest",
          props ->
              new SpliceCopperChestBlock(
                  WeatheringCopper.WeatherState.OXIDIZED,
                  SpliceSoundEvents.COPPER_CHEST_OXIDIZED_OPEN,
                  SpliceSoundEvents.COPPER_CHEST_OXIDIZED_CLOSE,
                  props),
          getOxidizedCopperChestProps());

  private static BlockBehaviour.Properties unaffectedCopperChestProps() {
    return BlockBehaviour.Properties.of()
        .mapColor(Blocks.COPPER_BLOCK.defaultMapColor())
        .strength(3.0f, 6.0f)
        .sound(SoundType.COPPER)
        .requiresCorrectToolForDrops();
  }

  private static BlockBehaviour.Properties getExposedCopperChestProps() {
    return unaffectedCopperChestProps().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY);
  }

  private static BlockBehaviour.Properties getWeatheredCopperChestProps() {
    return unaffectedCopperChestProps().mapColor(MapColor.WARPED_STEM);
  }

  private static BlockBehaviour.Properties getOxidizedCopperChestProps() {
    return unaffectedCopperChestProps().mapColor(MapColor.WARPED_NYLIUM);
  }

  private static <B extends Block> DeferredBlock<B> register(
      String key,
      Function<BlockBehaviour.Properties, ? extends B> ctor,
      BlockBehaviour.Properties properties) {
    return BLOCKS.registerBlock(key, ctor, properties);
  }

  public static List<SpliceWeatheringCopperBlocks> getCopperFamily() {
    return COPPER_FAMILY;
  }

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
