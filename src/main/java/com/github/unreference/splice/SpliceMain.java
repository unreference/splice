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
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(SpliceMain.MOD_ID)
public final class SpliceMain {
  public static final String MOD_ID = "splice";
  public static final Logger LOGGER = LogUtils.getLogger();

  public SpliceMain(IEventBus modEventBus) {
    modEventBus.addListener(SpliceDataGenerator::onGatherData);

    SpliceBlocks.register(modEventBus);
    SpliceBlockEntityType.register(modEventBus);
    SpliceBlockTypes.register(modEventBus);
    SpliceItems.register(modEventBus);
    SpliceParticleTypes.register(modEventBus);
    SpliceSoundEvents.register(modEventBus);

    modEventBus.addListener(SpliceCreativeModeTabs::onBuildCreativeModeTabContents);
    modEventBus.addListener(SpliceClientMain::onEntityRenderersEvent);
    modEventBus.addListener(SpliceClientMain::onRegisterClientExtensions);
    modEventBus.addListener(SpliceClientMain::onRegisterParticleProviders);
    modEventBus.addListener(this::onFmlCommonSetup);
  }

  private void onFmlCommonSetup(FMLCommonSetupEvent event) {
    final FireBlock fireBlock = (FireBlock) Blocks.FIRE;
    // Planks
    fireBlock.setFlammable(SpliceBlocks.PALE_OAK_PLANKS.get(), 5, 20);
    // Slab
    // Fence gate
    // Fence
    // Stairs
    // Log
    fireBlock.setFlammable(SpliceBlocks.PALE_OAK_LOG.get(), 5, 5);
    // Stripped log
    fireBlock.setFlammable(SpliceBlocks.STRIPPED_PALE_OAK_LOG.get(), 5, 5);
    // Stripped wood
    fireBlock.setFlammable(SpliceBlocks.STRIPPED_PALE_OAK_WOOD.get(), 5, 5);
    // Wood
    fireBlock.setFlammable(SpliceBlocks.PALE_OAK_WOOD.get(), 5, 5);
    // Leaves
  }
}
