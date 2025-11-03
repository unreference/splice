package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.github.unreference.splice.sounds.SpliceSoundType;
import com.github.unreference.splice.world.level.block.state.properties.SpliceBlockSetType;
import com.github.unreference.splice.world.level.block.state.properties.SpliceWoodType;
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
  public static final DeferredRegister.Blocks BLOCKS =
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
      BLOCKS.register(
          "copper_torch",
          () ->
              new SpliceDeferredTorchBlock(
                  SpliceParticleTypes.COPPER_FIRE_FLAME, getCopperTorchProperties()));

  public static final DeferredBlock<Block> COPPER_WALL_TORCH =
      BLOCKS.register(
          "copper_wall_torch",
          () ->
              new SpliceDeferredWallTorchBlock(
                  SpliceParticleTypes.COPPER_FIRE_FLAME,
                  getCopperTorchProperties().lootFrom(COPPER_TORCH)));

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

  public static final List<SpliceWeatheringCopperBlocks> COPPER_FAMILY =
      List.of(COPPER_BARS, COPPER_CHAIN, COPPER_LANTERN, COPPER_CHESTS);

  public static final DeferredBlock<Block> RESIN_BLOCK =
      BLOCKS.registerSimpleBlock("resin_block", getResinProperties());

  public static final DeferredBlock<Block> RESIN_CLUMP =
      BLOCKS.registerBlock(
          "resin_clump",
          SpliceResinClumpBlock::new,
          getResinProperties()
              .replaceable()
              .noCollission()
              .ignitedByLava()
              .pushReaction(PushReaction.DESTROY));

  public static final DeferredBlock<Block> RESIN_BRICKS =
      BLOCKS.registerSimpleBlock("resin_bricks", getResinBricksProperties());

  public static DeferredBlock<StairBlock> RESIN_BRICK_STAIRS =
      BLOCKS.registerBlock(
          "resin_brick_stairs",
          props ->
              new StairBlock(RESIN_BRICKS.get().defaultBlockState(), getResinBricksProperties()));

  public static final DeferredBlock<RotatedPillarBlock> STRIPPED_PALE_OAK_LOG =
      BLOCKS.registerBlock(
          "stripped_pale_oak_log", props -> new RotatedPillarBlock(getPaleOakPlanksProperties()));

  public static final DeferredBlock<RotatedPillarBlock> PALE_OAK_LOG =
      BLOCKS.register(
          "pale_oak_log",
          () -> new SpliceStrippableLogBlock(STRIPPED_PALE_OAK_LOG, getPaleOakPlanksProperties()));

  public static final DeferredBlock<RotatedPillarBlock> STRIPPED_PALE_OAK_WOOD =
      BLOCKS.registerBlock(
          "stripped_pale_oak_wood",
          RotatedPillarBlock::new,
          getLogProperties().mapColor(MapColor.STONE));

  public static final DeferredBlock<RotatedPillarBlock> PALE_OAK_WOOD =
      BLOCKS.register(
          "pale_oak_wood",
          () -> new SpliceStrippableLogBlock(STRIPPED_PALE_OAK_WOOD, getLogProperties()));

  public static final DeferredBlock<Block> PALE_OAK_PLANKS =
      BLOCKS.registerSimpleBlock("pale_oak_planks", getPaleOakPlanksProperties());

  public static final DeferredBlock<StairBlock> PALE_OAK_STAIRS =
      BLOCKS.registerBlock(
          "pale_oak_stairs",
          props ->
              new StairBlock(
                  PALE_OAK_PLANKS.get().defaultBlockState(), getPaleOakPlanksProperties()));

  public static final DeferredBlock<ButtonBlock> PALE_OAK_BUTTON =
      BLOCKS.registerBlock(
          "pale_oak_button",
          props ->
              new ButtonBlock(
                  SpliceBlockSetType.PALE_OAK,
                  30,
                  getPaleOakPlanksProperties()
                      .noCollission()
                      .strength(0.5f)
                      .pushReaction(PushReaction.DESTROY)));

  public static final DeferredBlock<FenceBlock> PALE_OAK_FENCE =
      BLOCKS.registerBlock("pale_oak_fence", props -> new FenceBlock(getPaleOakPlanksProperties()));

  public static final DeferredBlock<FenceGateBlock> PALE_OAK_FENCE_GATE =
      BLOCKS.registerBlock(
          "pale_oak_fence_gate",
          props ->
              new FenceGateBlock(
                  SpliceWoodType.PALE_OAK, getPaleOakPlanksProperties().forceSolidOn()));

  public static final DeferredBlock<PressurePlateBlock> PALE_OAK_PRESSURE_PLATE =
      BLOCKS.registerBlock(
          "pale_oak_pressure_plate",
          props ->
              new PressurePlateBlock(
                  SpliceBlockSetType.PALE_OAK,
                  getPaleOakPlanksProperties()
                      .forceSolidOn()
                      .noCollission()
                      .strength(0.5f)
                      .pushReaction(PushReaction.DESTROY)));

  public static final DeferredBlock<SpliceStandingSignBlock> PALE_OAK_SIGN =
      BLOCKS.registerBlock(
          "pale_oak_sign",
          props -> new SpliceStandingSignBlock(SpliceWoodType.PALE_OAK, props),
          getPaleOakPlanksProperties().forceSolidOn().noCollission().strength(1.0f));

  public static final DeferredBlock<SpliceWallSignBlock> PALE_OAK_WALL_SIGN =
      BLOCKS.registerBlock(
          "pale_oak_wall_sign",
          props -> new SpliceWallSignBlock(SpliceWoodType.PALE_OAK, props),
          getPaleOakPlanksProperties()
              .lootFrom(PALE_OAK_SIGN)
              .forceSolidOn()
              .noCollission()
              .strength(1.0f));

  public static final DeferredBlock<SlabBlock> PALE_OAK_SLAB =
      BLOCKS.registerBlock("pale_oak_slab", SlabBlock::new, getPaleOakPlanksProperties());

  public static final DeferredBlock<DoorBlock> PALE_OAK_DOOR =
      BLOCKS.register(
          "pale_oak_door",
          () ->
              new DoorBlock(
                  SpliceBlockSetType.PALE_OAK,
                  getPaleOakPlanksProperties()
                      .strength(3.0f)
                      .noOcclusion()
                      .pushReaction(PushReaction.DESTROY)));

  public static final DeferredBlock<TrapDoorBlock> PALE_OAK_TRAPDOOR =
      BLOCKS.register(
          "pale_oak_trapdoor",
          () ->
              new TrapDoorBlock(
                  SpliceBlockSetType.PALE_OAK,
                  getPaleOakPlanksProperties()
                      .strength(3.0f)
                      .noOcclusion()
                      .isValidSpawn(Blocks::never)
                      .pushReaction(PushReaction.DESTROY)));

  public static DeferredBlock<Block> CHISELED_RESIN_BRICKS =
      BLOCKS.registerSimpleBlock("chiseled_resin_bricks", getResinBricksProperties());

  public static DeferredBlock<SlabBlock> RESIN_BRICK_SLAB =
      BLOCKS.registerBlock("resin_brick_slab", props -> new SlabBlock(getResinBricksProperties()));

  public static DeferredBlock<WallBlock> RESIN_BRICK_WALL =
      BLOCKS.registerBlock("resin_brick_wall", props -> new WallBlock(getResinBricksProperties()));

  // ---- Helpers

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

    final BlockBehaviour.Properties chestProps = getChestProps(state);

    final Function<BlockBehaviour.Properties, ? extends Block> ctor =
        isWaxed
            ? (p) -> new SpliceCopperChestBlock(state, openSound, closeSound, p)
            : (p) -> new SpliceWeatheringCopperChestBlock(state, openSound, closeSound, p);

    return BLOCKS.registerBlock(id, ctor, chestProps);
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

  private static BlockBehaviour.Properties getCopperTorchProperties() {
    return BlockBehaviour.Properties.of()
        .noCollission()
        .instabreak()
        .lightLevel(emission -> 14)
        .sound(SoundType.WOOD)
        .pushReaction(PushReaction.DESTROY);
  }

  private static BlockBehaviour.Properties getResinProperties() {
    return BlockBehaviour.Properties.of()
        .mapColor(MapColor.TERRACOTTA_ORANGE)
        .instrument(NoteBlockInstrument.BASEDRUM)
        .sound(SpliceSoundType.RESIN);
  }

  private static BlockBehaviour.Properties getResinBricksProperties() {
    return getResinProperties()
        .requiresCorrectToolForDrops()
        .sound(SpliceSoundType.RESIN_BRICKS)
        .strength(1.5f, 6.0f);
  }

  private static BlockBehaviour.Properties getLogProperties() {
    return BlockBehaviour.Properties.of()
        .mapColor(MapColor.WOOD)
        .instrument(NoteBlockInstrument.BASS)
        .strength(2.0f)
        .sound(SoundType.WOOD)
        .ignitedByLava();
  }

  private static BlockBehaviour.Properties getPaleOakPlanksProperties() {
    return getLogProperties().mapColor(MapColor.QUARTZ).strength(2.0f, 3.0f);
  }

  public static void register(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
