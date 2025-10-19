package com.github.unreference.splice.mixin.net.minecraft.client.gui.screens.options.controls;

import com.github.unreference.splice.client.gui.screens.options.controls.SpliceMixinKeyBindsScreenHelper;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBindsScreen.class)
public abstract class SpliceMixinKeyBindsScreen {
  @Shadow public KeyMapping selectedKey;
  @Shadow private KeyBindsList keyBindsList;

  @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
  private void splice$mouseClicked(
      double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> callback) {
    if (this.selectedKey == null) {
      return;
    }

    List<KeyBindsList.Entry> children = this.keyBindsList.children();
    for (KeyBindsList.Entry entry : children) {
      if (entry instanceof KeyBindsList.KeyEntry key) {
        Button unbind = ((SpliceMixinKeyBindsScreenHelper) key).splice$getUnbindButton();
        if (unbind != null && unbind.active && unbind.isMouseOver(mouseX, mouseY)) {
          ((SpliceMixinKeyBindsScreenHelper) key).splice$unbindNow();
          callback.setReturnValue(true);
          return;
        }
      }
    }
  }
}
