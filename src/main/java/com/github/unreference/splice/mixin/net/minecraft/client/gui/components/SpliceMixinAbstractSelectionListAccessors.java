package com.github.unreference.splice.mixin.net.minecraft.client.gui.components;

import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractSelectionList.class)
public interface SpliceMixinAbstractSelectionListAccessors {
  @Invoker("getScrollbarPosition")
  int splice$getScrollbarPosition();
}
