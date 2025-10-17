package com.github.unreference.splice;

import com.github.unreference.splice.common.SpliceCommonMain;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(SpliceMain.MOD_ID)
public final class SpliceMain {
  public static final String MOD_ID = "splice";
  private static final Logger LOGGER = LogUtils.getLogger();

  public SpliceMain(IEventBus modEventBus, ModContainer modContainer) {
    modEventBus.addListener(SpliceCommonMain::onFmlCommonSetup);
  }
}
