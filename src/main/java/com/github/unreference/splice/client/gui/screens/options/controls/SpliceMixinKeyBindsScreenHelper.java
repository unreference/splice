package com.github.unreference.splice.client.gui.screens.options.controls;

import net.minecraft.client.gui.components.Button;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface SpliceMixinKeyBindsScreenHelper {
  Button splice$getUnbindButton();

  void splice$unbindNow();
}
