package com.github.unreference.splice;

import com.github.unreference.splice.client.SpliceClientMain;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import com.github.unreference.splice.data.SpliceDataGenerator;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.github.unreference.splice.world.item.SpliceCreativeModeTabs;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlockTypes;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.levelgen.feature.SpliceFeature;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(SpliceMain.MOD_ID)
public final class SpliceMain {
  public static final String MOD_ID = "splice";
  public static final Logger LOGGER = LogUtils.getLogger();

  public SpliceMain(IEventBus modEventBus) {
    modEventBus.addListener(SpliceDataGenerator::onGatherData);

    SpliceFeature.register(modEventBus);
    SpliceBlocks.register(modEventBus);
    SpliceBlockEntityType.register(modEventBus);
    SpliceBlockTypes.register(modEventBus);
    SpliceItems.register(modEventBus);
    SpliceParticleTypes.register(modEventBus);
    SpliceSoundEvents.register(modEventBus);

    if (FMLEnvironment.dist.isClient()) {
      modEventBus.register(SpliceClientMain.class);
      modEventBus.register(SpliceCreativeModeTabs.class);
    }

    modEventBus.addListener(this::onFmlCommonSetup);
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
  }

  private static void addPottedPlants() {
    final FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
    pot.addPlant(SpliceBlocks.PALE_OAK_SAPLING.getId(), SpliceBlocks.POTTED_PALE_OAK_SAPLING);
  }

  private void onFmlCommonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(
        () -> {
          addFlammables();
          addPottedPlants();
        });
  }
}
