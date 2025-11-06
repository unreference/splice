package com.github.unreference.splice.sounds;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceSoundEvents {
  private static final DeferredRegister<SoundEvent> SOUND_EVENTS =
      DeferredRegister.create(Registries.SOUND_EVENT, SpliceMain.MOD_ID);

  public static final DeferredHolder<SoundEvent, SoundEvent> ARMOR_EQUIP_COPPER =
      register("item.armor.equip_copper");

  public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_CHEST_OPEN =
      register("block.copper_chest.open");
  public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_CHEST_CLOSE =
      register("block.copper_chest.close");
  public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_CHEST_WEATHERED_OPEN =
      register("block.copper_chest.weathered_open");
  public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_CHEST_WEATHERED_CLOSE =
      register("block.copper_chest.weathered_close");
  public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_CHEST_OXIDIZED_OPEN =
      register("block.copper_chest.oxidized_open");
  public static final DeferredHolder<SoundEvent, SoundEvent> COPPER_CHEST_OXIDIZED_CLOSE =
      register("block.copper_chest.oxidized_close");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_BREAK =
      register("block.resin.break");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_STEP =
      register("block.resin.step");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_PLACE =
      register("block.resin.place");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_FALL =
      register("block.resin.fall");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_BRICKS_BREAK =
      register("block.resin_bricks.break");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_BRICKS_STEP =
      register("block.resin_bricks.step");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_BRICKS_PLACE =
      register("block.resin_bricks.place");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_BRICKS_HIT =
      register("block.resin_bricks.hit");
  public static final DeferredHolder<SoundEvent, SoundEvent> RESIN_BRICKS_FALL =
      register("block.resin_bricks.fall");
  public static final DeferredHolder<SoundEvent, SoundEvent> PALE_HANGING_MOSS_IDLE =
      register("block.pale_hanging_moss.idle");

  public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_TEARS =
      register("music_disc.tears");
  public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_LAVA_CHICKEN =
      register("music_disc.lava_chicken");
  public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_COFFEE_MACHINE =
      register("music_disc.coffee_machine");

  private static DeferredHolder<SoundEvent, SoundEvent> register(String key) {
    final ResourceLocation id = ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key);
    return SOUND_EVENTS.register(key, () -> SoundEvent.createVariableRangeEvent(id));
  }

  public static void register(IEventBus bus) {
    SOUND_EVENTS.register(bus);
  }
}
