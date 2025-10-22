package com.github.unreference.splice.mixin.net.minecraft.client.gui.screens.options.controls;

import com.github.unreference.splice.client.gui.screens.options.controls.SpliceMixinKeyBindsScreenHelper;
import com.github.unreference.splice.mixin.net.minecraft.client.gui.components.SpliceMixinAbstractSelectionListAccessors;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBindsList.class)
public abstract class SpliceMixinKeyBindsList {
  @Mixin(KeyBindsList.KeyEntry.class)
  public abstract static class MixinKeyBindsListKeyEntry
      implements SpliceMixinKeyBindsScreenHelper {
    @Shadow @Final private static int PADDING;
    @Shadow @Final KeyBindsList this$0;

    @Shadow @Final private Button changeButton;
    @Shadow @Final private Button resetButton;
    @Shadow @Final private KeyMapping key;

    @Unique private Button splice$unbind;

    @Unique private int splice$unbindCachedWidth = -1;
    @Unique private Language splice$lastLang;
    @Unique private Minecraft splice$mc;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void splice$ctor(
        KeyBindsList this$0,
        KeyMapping key,
        net.minecraft.network.chat.Component name,
        CallbackInfo callback) {
      this.splice$unbind =
          Button.builder(
                  Component.translatable("controls.splice.unbind"), button -> splice$unbindNow())
              .bounds(0, 0, splice$unbindCachedWidth, 20)
              .build();

      this.splice$unbind.active = false;
      this.splice$unbind.visible = true;
      this.splice$lastLang = Language.getInstance();
      this.splice$mc = Minecraft.getInstance();
    }

    @Inject(method = "children", at = @At("RETURN"), cancellable = true)
    private void splice$children(
        CallbackInfoReturnable<List<? extends GuiEventListener>> callback) {
      List<GuiEventListener> list = new ArrayList<>(callback.getReturnValue());
      list.add(this.splice$unbind);
      callback.setReturnValue(list);
    }

    @Inject(method = "narratables", at = @At("RETURN"), cancellable = true)
    private void splice$narratables(
        CallbackInfoReturnable<List<? extends NarratableEntry>> callback) {
      List<NarratableEntry> list = new ArrayList<>(callback.getReturnValue());
      list.add(this.splice$unbind);
      callback.setReturnValue(list);
    }

    @Inject(
        method = "render",
        at =
            @At(
                value = "INVOKE",
                target =
                    "Lnet/minecraft/client/gui/components/Button;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void splice$preRender(
        GuiGraphics guiGraphics,
        int index,
        int top,
        int left,
        int width,
        int height,
        int mouseX,
        int mouseY,
        boolean hovering,
        float partialTick,
        CallbackInfo callback) {
      final int pad = PADDING;
      final int betwixt = pad / 2;

      final int unbindWidth = splice$computeUnbindWidth();
      if (this.splice$unbind.getWidth() != unbindWidth) {
        this.splice$unbind.setWidth(unbindWidth);
      }

      final int gutterRight =
          ((SpliceMixinAbstractSelectionListAccessors) this.this$0).splice$getScrollbarPosition();
      final int rowLeft = left + pad;

      int unbindLeft = gutterRight - unbindWidth - pad;
      int resetLeft = unbindLeft - betwixt - this.resetButton.getWidth();

      int changeLeft = resetLeft - betwixt - this.changeButton.getWidth();
      if (changeLeft < rowLeft) {
        final int fix = rowLeft - changeLeft;
        changeLeft += fix;
        resetLeft += fix;
        unbindLeft += fix;
      }

      this.changeButton.setX(changeLeft);
      this.resetButton.setX(resetLeft);
      this.splice$unbind.setX(unbindLeft);
      this.splice$unbind.setY(this.resetButton.getY());
    }

    @WrapOperation(
        method = "render",
        at =
            @At(
                value = "INVOKE",
                target =
                    "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I",
                ordinal = 0))
    private int splice$renderScrollingString(
        GuiGraphics guiGraphics,
        Font font,
        Component text,
        int x,
        int y,
        int color,
        Operation<Integer> original) {
      final int marker = PADDING + 5;
      final int maxX = Math.max(x, this.changeButton.getX() - marker);

      final int lineYTop = y - font.lineHeight / 2;
      AbstractWidget.renderScrollingString(
          guiGraphics, font, text, x, lineYTop, maxX, lineYTop + font.lineHeight, color);
      return Math.min(font.width(text), maxX - x);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void splice$postRender(
        GuiGraphics guiGraphics,
        int index,
        int top,
        int left,
        int width,
        int height,
        int mouseX,
        int mouseY,
        boolean hovering,
        float partialTick,
        CallbackInfo callback) {
      this.splice$unbind.active = !this.key.isUnbound();
      this.splice$unbind.visible = this.resetButton.visible;
      this.splice$unbind.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void splice$unbindNow() {
      final KeyMapping mapping = this.key;
      mapping.setKeyModifierAndCode(KeyModifier.NONE, InputConstants.UNKNOWN);

      final Minecraft mc = Minecraft.getInstance();
      mc.options.setKey(mapping, InputConstants.UNKNOWN);

      final KeyBindsScreen keyBindsScreen = (mc.screen instanceof KeyBindsScreen kbs) ? kbs : null;
      if (keyBindsScreen != null && keyBindsScreen.selectedKey == mapping) {
        keyBindsScreen.selectedKey = null;
        keyBindsScreen.lastKeySelection = Util.getMillis();
      }

      if (keyBindsScreen != null) {
        var list = ((SpliceMixinKeyBindsScreenAccessors) keyBindsScreen).splice$getKeyBindsList();
        list.resetMappingAndUpdateButtons();
      }
    }

    @Override
    public Button splice$getUnbindButton() {
      return this.splice$unbind;
    }

    @Unique
    private int splice$computeUnbindWidth() {
      final var LANG = Language.getInstance();
      if (this.splice$unbindCachedWidth < 0 || LANG != this.splice$lastLang) {
        this.splice$lastLang = LANG;

        final int pad = PADDING;
        final int min = 50;
        final int max = min * 2;
        final int text = splice$mc.font.width(this.splice$unbind.getMessage());

        this.splice$unbindCachedWidth = Mth.clamp(text + pad, min, max);
      }

      return this.splice$unbindCachedWidth;
    }
  }
}
