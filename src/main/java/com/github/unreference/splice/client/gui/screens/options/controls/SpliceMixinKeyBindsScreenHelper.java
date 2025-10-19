package com.github.unreference.splice.client.gui.screens.options.controls;

import net.minecraft.client.gui.components.Button;

public interface SpliceMixinKeyBindsScreenHelper {
  Button splice$getUnbindButton();

  void splice$unbindNow();
}
