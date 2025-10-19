package com.github.unreference.splice.mixin.net.minecraft.client.gui.screens.options.controls;

import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBindsScreen.class)
public interface SpliceMixinKeyBindsScreenAccessors {
  @Accessor("keyBindsList")
  KeyBindsList splice$getKeyBindsList();
}
