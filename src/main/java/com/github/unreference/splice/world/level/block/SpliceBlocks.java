package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.github.unreference.splice.sounds.SpliceSoundType;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceBlocks {
  private static final DeferredRegister.Blocks BLOCKS =
      DeferredRegister.createBlocks(SpliceMain.MOD_ID);

  public static final SpliceWeatheringCopperBlocks COPPER_BARS =
      SpliceWeatheringCopperBlocks.create(
          "copper_bars",
          BLOCKS::registerBlock,
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
          BLOCKS::registerBlock,
          ChainBlock::new,
          SpliceWeatheringCopperChainBlock::new,
          weatherState ->
              BlockBehaviour.Properties.of()
                  .forceSolidOn()
                  .requiresCorrectToolForDrops()
                  .strength(5.0f, 6.0f)
                  .sound(SoundType.CHAIN)
                  .noOcclusion());

  public static final DeferredBlock<Block> COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.UNAFFECTED, false);

  public static final DeferredBlock<Block> EXPOSED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.EXPOSED, false);

  public static final DeferredBlock<Block> WEATHERED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.WEATHERED, false);

  public static final DeferredBlock<Block> OXIDIZED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.OXIDIZED, false);

  private static final ImmutableBiMap<
          DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
      CHEST_WEATHERING_CHAIN =
          ImmutableBiMap.<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>builder()
              .put(COPPER_CHEST, EXPOSED_COPPER_CHEST)
              .put(EXPOSED_COPPER_CHEST, WEATHERED_COPPER_CHEST)
              .put(WEATHERED_COPPER_CHEST, OXIDIZED_COPPER_CHEST)
              .build();

  public static final DeferredBlock<Block> COPPER_TORCH =
      BLOCKS.registerBlock(
          "copper_torch",
          props -> new SpliceDeferredTorchBlock(SpliceParticleTypes.COPPER_FIRE_FLAME, props),
          BlockBehaviour.Properties.of()
              .noCollission()
              .instabreak()
              .lightLevel(emission -> 14)
              .sound(SoundType.WOOD)
              .pushReaction(PushReaction.DESTROY));

  public static final DeferredBlock<Block> COPPER_WALL_TORCH =
      BLOCKS.registerBlock(
          "copper_wall_torch",
          props -> new SpliceDeferredWallTorchBlock(SpliceParticleTypes.COPPER_FIRE_FLAME, props),
          BlockBehaviour.Properties.of()
              .noCollission()
              .instabreak()
              .lightLevel(emission -> 14)
              .sound(SoundType.WOOD)
              .lootFrom(COPPER_TORCH)
              .pushReaction(PushReaction.DESTROY));

  public static final SpliceWeatheringCopperBlocks COPPER_LANTERN =
      SpliceWeatheringCopperBlocks.create(
          "copper_lantern",
          BLOCKS::registerBlock,
          LanternBlock::new,
          SpliceWeatheringLanternBlock::new,
          weatherState ->
              BlockBehaviour.Properties.of()
                  .mapColor(MapColor.METAL)
                  .forceSolidOn()
                  .strength(3.5f)
                  .sound(SoundType.LANTERN)
                  .lightLevel(emission -> 15)
                  .noOcclusion()
                  .pushReaction(PushReaction.DESTROY));

  public static final DeferredBlock<Block> WAXED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.UNAFFECTED, true);
  public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.EXPOSED, true);
  public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.WEATHERED, true);
  public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_CHEST =
      registerChest(WeatheringCopper.WeatherState.OXIDIZED, true);
  private static final ImmutableBiMap<
          DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>
      CHEST_WAXED_MAP =
          ImmutableBiMap.<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>builder()
              .put(COPPER_CHEST, WAXED_COPPER_CHEST)
              .put(EXPOSED_COPPER_CHEST, WAXED_EXPOSED_COPPER_CHEST)
              .put(WEATHERED_COPPER_CHEST, WAXED_WEATHERED_COPPER_CHEST)
              .put(OXIDIZED_COPPER_CHEST, WAXED_OXIDIZED_COPPER_CHEST)
              .build();
  private static final ImmutableList<DeferredBlock<? extends Block>> ALL_CHEST_VARIANTS =
      ImmutableList.of(
          COPPER_CHEST, WAXED_COPPER_CHEST,
          EXPOSED_COPPER_CHEST, WAXED_EXPOSED_COPPER_CHEST,
          WEATHERED_COPPER_CHEST, WAXED_WEATHERED_COPPER_CHEST,
          OXIDIZED_COPPER_CHEST, WAXED_OXIDIZED_COPPER_CHEST);
  public static final SpliceWeatheringCopperBlocks COPPER_CHESTS =
      new SpliceWeatheringCopperBlocks(
          COPPER_CHEST,
          EXPOSED_COPPER_CHEST,
          WEATHERED_COPPER_CHEST,
          OXIDIZED_COPPER_CHEST,
          WAXED_COPPER_CHEST,
          WAXED_EXPOSED_COPPER_CHEST,
          WAXED_WEATHERED_COPPER_CHEST,
          WAXED_OXIDIZED_COPPER_CHEST,
          CHEST_WEATHERING_CHAIN,
          CHEST_WAXED_MAP,
          ALL_CHEST_VARIANTS);
  private static final List<SpliceWeatheringCopperBlocks> COPPER_FAMILY =
      List.of(COPPER_BARS, COPPER_CHAIN, COPPER_LANTERN, COPPER_CHESTS);

  public static final DeferredBlock<Block> RESIN_BLOCK =
      BLOCKS.registerSimpleBlock(
          "resin_block",
          BlockBehaviour.Properties.of()
              .mapColor(MapColor.TERRACOTTA_ORANGE)
              .instrument(NoteBlockInstrument.BASEDRUM)
              .sound(SpliceSoundType.RESIN));

  public static final DeferredBlock<Block> RESIN_CLUMP =
      BLOCKS.registerBlock(
          "resin_clump",
          SpliceResinClumpBlock::new,
          BlockBehaviour.Properties.of()
              .mapColor(MapColor.TERRACOTTA_ORANGE)
              .replaceable()
              .noCollission()
              .sound(SpliceSoundType.RESIN)
              .ignitedByLava()
              .pushReaction(PushReaction.DESTROY));

  public static final DeferredBlock<Block> RESIN_BRICKS =
      BLOCKS.registerSimpleBlock("resin_bricks", getResinBricksProperties());

  public static DeferredBlock<StairBlock> RESIN_BRICK_STAIRS =
      BLOCKS.registerBlock(
          "resin_brick_stairs",
          props ->
              new StairBlock(RESIN_BRICKS.get().defaultBlockState(), getResinBricksProperties()));

  public static DeferredBlock<Block> CHISELED_RESIN_BRICKS =
      BLOCKS.registerSimpleBlock("chiseled_resin_bricks", getResinBricksProperties());

  public static DeferredBlock<SlabBlock> RESIN_BRICK_SLAB =
      BLOCKS.registerBlock("resin_brick_slab", props -> new SlabBlock(getResinBricksProperties()));

  public static DeferredBlock<WallBlock> RESIN_BRICK_WALL =
      BLOCKS.registerBlock("resin_brick_wall", props -> new WallBlock(getResinBricksProperties()));

  private static BlockBehaviour.Properties getResinBricksProperties() {
    return BlockBehaviour.Properties.of()
        .mapColor(MapColor.TERRACOTTA_ORANGE)
        .instrument(NoteBlockInstrument.BASEDRUM)
        .requiresCorrectToolForDrops()
        .sound(SpliceSoundType.RESIN_BRICKS)
        .strength(1.5f, 6.0f);
  }

  private static DeferredBlock<Block> registerChest(
      WeatheringCopper.WeatherState state, boolean isWaxed) {

    final String id =
        (isWaxed ? "waxed_" : "")
            + switch (state) {
              case UNAFFECTED -> "copper_chest";
              case EXPOSED -> "exposed_copper_chest";
              case WEATHERED -> "weathered_copper_chest";
              case OXIDIZED -> "oxidized_copper_chest";
            };

    final Holder<SoundEvent> openSound =
        switch (state) {
          case UNAFFECTED, EXPOSED -> SpliceSoundEvents.COPPER_CHEST_OPEN;
          case WEATHERED -> SpliceSoundEvents.COPPER_CHEST_WEATHERED_OPEN;
          case OXIDIZED -> SpliceSoundEvents.COPPER_CHEST_OXIDIZED_OPEN;
        };
    final Holder<SoundEvent> closeSound =
        switch (state) {
          case UNAFFECTED, EXPOSED -> SpliceSoundEvents.COPPER_CHEST_CLOSE;
          case WEATHERED -> SpliceSoundEvents.COPPER_CHEST_WEATHERED_CLOSE;
          case OXIDIZED -> SpliceSoundEvents.COPPER_CHEST_OXIDIZED_CLOSE;
        };

    final BlockBehaviour.Properties props = getChestProps(state);

    final Function<BlockBehaviour.Properties, ? extends Block> ctor =
        isWaxed
            ? (p) -> new SpliceCopperChestBlock(state, openSound, closeSound, p)
            : (p) -> new SpliceWeatheringCopperChestBlock(state, openSound, closeSound, p);

    return BLOCKS.registerBlock(id, ctor, props);
  }

  private static BlockBehaviour.Properties getChestProps(WeatheringCopper.WeatherState state) {
    final MapColor color =
        switch (state) {
          case UNAFFECTED -> Blocks.COPPER_BLOCK.defaultMapColor();
          case EXPOSED -> MapColor.TERRACOTTA_LIGHT_GRAY;
          case WEATHERED -> MapColor.WARPED_STEM;
          case OXIDIZED -> MapColor.WARPED_NYLIUM;
        };

    return BlockBehaviour.Properties.of()
        .mapColor(color)
        .strength(3.0f, 6.0f)
        .sound(SoundType.COPPER)
        .requiresCorrectToolForDrops();
  }

  public static List<SpliceWeatheringCopperBlocks> getCopperFamily() {
    return COPPER_FAMILY;
  }

  public static DeferredRegister.Blocks getBlocks() {
    return BLOCKS;
  }

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
