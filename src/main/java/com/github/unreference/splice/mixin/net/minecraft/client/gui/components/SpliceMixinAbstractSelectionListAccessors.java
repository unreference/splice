package com.github.unreference.splice.mixin.net.minecraft.client.gui.components;

import net.minecraft.client.gui.components.AbstractSelectionList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@OnlyIn(Dist.CLIENT)
@Mixin(AbstractSelectionList.class)
public interface SpliceMixinAbstractSelectionListAccessors {
  @Invoker("getScrollbarPosition")
  int splice$getScrollbarPosition();
}
