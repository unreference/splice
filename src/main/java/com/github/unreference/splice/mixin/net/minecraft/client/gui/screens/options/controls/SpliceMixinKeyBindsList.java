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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(KeyBindsList.class)
public abstract class SpliceMixinKeyBindsList {

  @OnlyIn(Dist.CLIENT)
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
                  Component.translatable("splice.controls.unbind"), button -> splice$unbindNow())
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
      final int PAD = PADDING;
      final int BETWIXT = PAD / 2;

      final int UNBIND_WIDTH = splice$computeUnbindWidth();
      if (this.splice$unbind.getWidth() != UNBIND_WIDTH) {
        this.splice$unbind.setWidth(UNBIND_WIDTH);
      }

      final int GUTTER_RIGHT =
          ((SpliceMixinAbstractSelectionListAccessors) this.this$0).splice$getScrollbarPosition();
      final int ROW_LEFT = left + PADDING;

      int unbindLeft = GUTTER_RIGHT - UNBIND_WIDTH - PADDING;
      int resetLeft = unbindLeft - BETWIXT - this.resetButton.getWidth();

      int changeLeft = resetLeft - BETWIXT - this.changeButton.getWidth();
      if (changeLeft < ROW_LEFT) {
        final int FIX = ROW_LEFT - changeLeft;
        changeLeft += FIX;
        resetLeft += FIX;
        unbindLeft += FIX;
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
      final int MARKER = PADDING + 5;
      final int MAX_X = Math.max(x, this.changeButton.getX() - MARKER);

      final int LINE_Y_TOP = y - font.lineHeight / 2;
      AbstractWidget.renderScrollingString(
          guiGraphics, font, text, x, LINE_Y_TOP, MAX_X, LINE_Y_TOP + font.lineHeight, color);
      return Math.min(font.width(text), MAX_X - x);
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
      final KeyMapping MAPPING = this.key;
      MAPPING.setKeyModifierAndCode(KeyModifier.NONE, InputConstants.UNKNOWN);

      final Minecraft MC = Minecraft.getInstance();
      MC.options.setKey(MAPPING, InputConstants.UNKNOWN);

      KeyBindsScreen keyBindsScreen = (MC.screen instanceof KeyBindsScreen kbs) ? kbs : null;
      if (keyBindsScreen != null && keyBindsScreen.selectedKey == MAPPING) {
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

        final int PAD = PADDING;
        final int MIN = 50;
        final int MAX = MIN * 2;
        final int TEXT = splice$mc.font.width(this.splice$unbind.getMessage());

        this.splice$unbindCachedWidth = Mth.clamp(TEXT + PAD, MIN, MAX);
      }

      return this.splice$unbindCachedWidth;
    }
  }
}
