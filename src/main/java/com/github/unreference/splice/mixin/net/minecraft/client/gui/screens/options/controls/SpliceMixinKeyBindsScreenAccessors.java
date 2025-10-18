package com.github.unreference.splice.mixin.net.minecraft.client.gui.screens.options.controls;

import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@OnlyIn(Dist.CLIENT)
@Mixin(KeyBindsScreen.class)
public interface SpliceMixinKeyBindsScreenAccessors {
  @Accessor("keyBindsList")
  KeyBindsList splice$getKeyBindsList();
}
