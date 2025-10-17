package com.github.unreference.splice.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public final class SpliceClientMain {
  @SubscribeEvent
  public static void onFmlClientSetup(FMLClientSetupEvent event) {}
}
