package com.github.unreference.splice.server;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@OnlyIn(Dist.DEDICATED_SERVER)
public final class SpliceServerMain {
  @SubscribeEvent
  public static void onServerStarting(ServerStartingEvent event) {}
}
