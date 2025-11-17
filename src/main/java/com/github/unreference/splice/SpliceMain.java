package com.github.unreference.splice;

import com.github.unreference.splice.client.SpliceClientMain;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import com.github.unreference.splice.data.SpliceDataGenerator;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomeData;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomes;
import com.github.unreference.splice.data.worldgen.region.SpliceOverworldRegion;
import com.github.unreference.splice.data.worldgen.region.SpliceRegions;
import com.github.unreference.splice.server.commands.SpliceDebugCommands;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.github.unreference.splice.world.item.SpliceCreativeModeTabs;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlockTypes;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.levelgen.feature.SpliceFeature;
import com.github.unreference.splice.world.level.levelgen.feature.treedecorators.SpliceTreeDecoratorType;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.slf4j.Logger;

@Mod(SpliceMain.MOD_ID)
public final class SpliceMain {
  public static final String MOD_ID = "splice";
  public static final Logger LOGGER = LogUtils.getLogger();

  public SpliceMain(IEventBus modEventBus) {
    modEventBus.addListener(SpliceDataGenerator::onGatherData);

    SpliceFeature.register(modEventBus);
    SpliceTreeDecoratorType.register(modEventBus);
    SpliceBlocks.register(modEventBus);
    SpliceBlockEntityType.register(modEventBus);
    SpliceBlockTypes.register(modEventBus);
    SpliceItems.register(modEventBus);
    SpliceParticleTypes.register(modEventBus);
    SpliceSoundEvents.register(modEventBus);

    if (FMLEnvironment.dist.isClient()) {
      modEventBus.addListener(SpliceClientMain::onEntityRenderersEvent);
      modEventBus.addListener(SpliceClientMain::onRegisterParticleProviders);
      modEventBus.addListener(SpliceClientMain::onRegisterClientExtensions);
      modEventBus.register(SpliceCreativeModeTabs.class);

      NeoForge.EVENT_BUS.addListener(SpliceClientMain::onSelectMusic);
    }

    modEventBus.addListener(SpliceMain::onFmlCommonSetup);

    NeoForge.EVENT_BUS.addListener(SpliceMain::onServerAboutToStart);
    NeoForge.EVENT_BUS.addListener(SpliceDebugCommands::onRegisterCommandsEvent);
  }

  private static void addFlammables() {
    final FireBlock block = (FireBlock) Blocks.FIRE;
    block.setFlammable(SpliceBlocks.PALE_OAK_PLANKS.get(), 5, 20);
    block.setFlammable(SpliceBlocks.PALE_OAK_FENCE_GATE.get(), 5, 20);
    block.setFlammable(SpliceBlocks.PALE_OAK_FENCE.get(), 5, 20);
    block.setFlammable(SpliceBlocks.PALE_OAK_STAIRS.get(), 5, 20);
    block.setFlammable(SpliceBlocks.PALE_OAK_LOG.get(), 5, 5);
    block.setFlammable(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get(), 5, 5);
    block.setFlammable(SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get(), 5, 5);
    block.setFlammable(SpliceBlocks.PALE_OAK_WOOD.get(), 5, 5);
    block.setFlammable(SpliceBlocks.PALE_OAK_LEAVES.get(), 30, 60);
    block.setFlammable(SpliceBlocks.PALE_MOSS_CARPET.get(), 5, 100);
    block.setFlammable(SpliceBlocks.PALE_MOSS_BLOCK.get(), 5, 100);
    block.setFlammable(SpliceBlocks.PALE_HANGING_MOSS.get(), 5, 100);
    block.setFlammable(SpliceBlocks.CLOSED_EYEBLOSSOM.get(), 60, 100);
    block.setFlammable(SpliceBlocks.OPEN_EYEBLOSSOM.get(), 60, 100);
  }

  private static void addPottedPlants() {
    final FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
    pot.addPlant(SpliceBlocks.PALE_OAK_SAPLING.getId(), SpliceBlocks.POTTED_PALE_OAK_SAPLING);
    pot.addPlant(SpliceBlocks.CLOSED_EYEBLOSSOM.getId(), SpliceBlocks.POTTED_CLOSED_EYEBLOSSOM);
    pot.addPlant(SpliceBlocks.OPEN_EYEBLOSSOM.getId(), SpliceBlocks.POTTED_OPEN_EYEBLOSSOM);
  }

  private static void addBiomes() {
    SpliceRegions.register(new SpliceOverworldRegion());
  }

  private static void onFmlCommonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(
        () -> {
          addFlammables();
          addPottedPlants();
          addBiomes();
        });
  }

  private static void onServerAboutToStart(ServerAboutToStartEvent event) {
    final RegistryAccess registryAccess = event.getServer().registryAccess();
    final Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
    SpliceBiomeData.PALE_GARDEN = biomeRegistry.getHolderOrThrow(SpliceBiomes.PALE_GARDEN);
  }
}
