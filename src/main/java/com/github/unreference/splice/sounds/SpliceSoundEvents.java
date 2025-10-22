package com.github.unreference.splice.sounds;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceSoundEvents {
  public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
      DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, SpliceMain.MOD_ID);

  public static final Holder<SoundEvent> ARMOR_EQUIP_COPPER = register("item.armor.equip_copper");

  private static Holder<SoundEvent> register(String key) {
    final var ID = ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key);
    return SOUND_EVENTS.register(key, () -> SoundEvent.createVariableRangeEvent(ID));
  }

  public static void register(IEventBus bus) {
    SOUND_EVENTS.register(bus);
  }
}
