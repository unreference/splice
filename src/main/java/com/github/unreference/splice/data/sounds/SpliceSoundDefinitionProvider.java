package com.github.unreference.splice.data.sounds;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public final class SpliceSoundDefinitionProvider extends SoundDefinitionsProvider {
  public SpliceSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
    super(output, SpliceMain.MOD_ID, helper);
  }

  private static ResourceLocation getResourceLocation(String path) {
    return ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, path);
  }

  @Override
  public void registerSounds() {
    this.add(
        SpliceSoundEvents.ARMOR_EQUIP_COPPER,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("item/armor/equip_copper1")),
                sound(getResourceLocation("item/armor/equip_copper2")),
                sound(getResourceLocation("item/armor/equip_copper3")),
                sound(getResourceLocation("item/armor/equip_copper4")),
                sound(getResourceLocation("item/armor/equip_copper5")),
                sound(getResourceLocation("item/armor/equip_copper6")))
            .subtitle("subtitles.splice.item.armor.equip_copper"));

    this.add(
        SpliceSoundEvents.COPPER_CHEST_OPEN,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/copper_chest/copper_chest_open1")),
                sound(getResourceLocation("block/copper_chest/copper_chest_open2")),
                sound(getResourceLocation("block/copper_chest/copper_chest_open3")))
            .subtitle("subtitles.block.chest.open"));

    this.add(
        SpliceSoundEvents.COPPER_CHEST_CLOSE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/copper_chest/copper_chest_close1")),
                sound(getResourceLocation("block/copper_chest/copper_chest_close2")),
                sound(getResourceLocation("block/copper_chest/copper_chest_close3")))
            .subtitle("subtitles.block.chest.close"));

    this.add(
        SpliceSoundEvents.COPPER_CHEST_WEATHERED_OPEN,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/copper_chest/copper_chest_weathered_open1")),
                sound(getResourceLocation("block/copper_chest/copper_chest_weathered_open2")),
                sound(getResourceLocation("block/copper_chest/copper_chest_weathered_open3")))
            .subtitle("subtitles.block.chest.open"));

    this.add(
        SpliceSoundEvents.COPPER_CHEST_WEATHERED_CLOSE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/copper_chest/copper_chest_weathered_close1")),
                sound(getResourceLocation("block/copper_chest/copper_chest_weathered_close2")),
                sound(getResourceLocation("block/copper_chest/copper_chest_weathered_close3")))
            .subtitle("subtitles.block.chest.close"));

    this.add(
        SpliceSoundEvents.COPPER_CHEST_OXIDIZED_OPEN,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/copper_chest/copper_chest_oxidized_open1")),
                sound(getResourceLocation("block/copper_chest/copper_chest_oxidized_open2")),
                sound(getResourceLocation("block/copper_chest/copper_chest_oxidized_open3")))
            .subtitle("subtitles.block.chest.open"));

    this.add(
        SpliceSoundEvents.COPPER_CHEST_OXIDIZED_CLOSE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/copper_chest/copper_chest_oxidized_close1")),
                sound(getResourceLocation("block/copper_chest/copper_chest_oxidized_close2")),
                sound(getResourceLocation("block/copper_chest/copper_chest_oxidized_close3")))
            .subtitle("subtitles.block.chest.close"));

    this.add(
        SpliceSoundEvents.MUSIC_DISC_TEARS,
        SoundDefinition.definition().with(sound(getResourceLocation("records/tears")).stream()));

    this.add(
        SpliceSoundEvents.MUSIC_DISC_LAVA_CHICKEN,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("records/lava_chicken")).stream()));
  }
}
