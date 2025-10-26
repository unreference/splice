package com.github.unreference.splice.world.item;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public final class SpliceJukeboxSongs {
  public static ResourceKey<JukeboxSong> TEARS = create("tears");
  public static ResourceKey<JukeboxSong> LAVA_CHICKEN = create("lava_chicken");

  private static ResourceKey<JukeboxSong> create(String key) {
    return ResourceKey.create(
        Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key));
  }

  private static void register(
      BootstrapContext<JukeboxSong> context,
      ResourceKey<JukeboxSong> key,
      Holder<SoundEvent> sound,
      float length,
      int comparator) {
    context.register(
        key,
        new JukeboxSong(
            sound,
            Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())),
            length,
            comparator));
  }

  public static void bootstrap(BootstrapContext<JukeboxSong> context) {
    register(context, TEARS, SpliceSoundEvents.MUSIC_DISC_TEARS, 175, 10);
    register(context, LAVA_CHICKEN, SpliceSoundEvents.MUSIC_DISC_LAVA_CHICKEN, 134, 9);
  }
}
