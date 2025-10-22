package com.github.unreference.splice.mixin.net.minecraft.client.renderer.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class SpliceMixinLivingEntityRenderer<T extends LivingEntity> {

  @ModifyReturnValue(method = "isEntityUpsideDown", at = @At("RETURN"))
  private static boolean splice$isEntityUpsideDown(boolean original, LivingEntity entity) {
    if (original) {
      return true;
    }

    boolean isUpsideDown = false;

    String stripped = ChatFormatting.stripFormatting(entity.getName().getString());
    if ("Unreference".equals(stripped)) {
      isUpsideDown =
          (!(entity instanceof Player))
              || ((Player) entity).isModelPartShown(PlayerModelPart.JACKET);
    }

    return isUpsideDown;
  }

  @ModifyReturnValue(
      method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z",
      at = @At("RETURN"))
  private boolean splice$shouldShowName(boolean original, T entity) {
    if (original) {
      return true;
    }

    final Minecraft MC = Minecraft.getInstance();

    boolean isNamesShown = Minecraft.renderNames();
    boolean isThirdPerson = !MC.options.getCameraType().isFirstPerson();
    boolean isGuiAllowed = (MC.screen == null) || (MC.screen instanceof ChatScreen);

    return isNamesShown && isThirdPerson && isGuiAllowed;
  }
}
