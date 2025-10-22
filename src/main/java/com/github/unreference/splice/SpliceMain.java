package com.github.unreference.splice;

import com.github.unreference.splice.client.SpliceClientMain;
import com.github.unreference.splice.data.SpliceDataGenerator;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.github.unreference.splice.world.item.SpliceCreativeModeTabs;
import com.github.unreference.splice.world.item.SpliceItems;
import com.github.unreference.splice.world.level.block.SpliceBlockTypes;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(SpliceMain.MOD_ID)
public final class SpliceMain {
  public static final String MOD_ID = "splice";
  public static final Logger LOGGER = LogUtils.getLogger();

  public SpliceMain(IEventBus modEventBus) {
    modEventBus.addListener(SpliceDataGenerator::onGatherData);

    SpliceBlocks.register(modEventBus);
    SpliceItems.register(modEventBus);
    SpliceSoundEvents.register(modEventBus);
    SpliceBlockEntityType.register(modEventBus);
    SpliceBlockTypes.register(modEventBus);

    modEventBus.addListener(SpliceClientMain::onEntityRenderersEvent);
    modEventBus.addListener(SpliceClientMain::onRegisterClientExtensions);

    modEventBus.addListener(SpliceCreativeModeTabs::onBuildCreativeModeTabContents);
  }
}
