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
        SpliceSoundEvents.RESIN_BREAK,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/resin/resin_break1")),
                sound(getResourceLocation("block/resin/resin_break2")),
                sound(getResourceLocation("block/resin/resin_break3")),
                sound(getResourceLocation("block/resin/resin_break4")),
                sound(getResourceLocation("block/resin/resin_break5")))
            .subtitle("subtitles.block.generic.break"));

    this.add(
        SpliceSoundEvents.RESIN_STEP,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/resin/resin_step1")),
                sound(getResourceLocation("block/resin/resin_step2")),
                sound(getResourceLocation("block/resin/resin_step3")),
                sound(getResourceLocation("block/resin/resin_step4")),
                sound(getResourceLocation("block/resin/resin_step5")))
            .subtitle("subtitles.block.generic.footsteps"));

    this.add(
        SpliceSoundEvents.RESIN_PLACE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/resin/resin_place1")),
                sound(getResourceLocation("block/resin/resin_place2")),
                sound(getResourceLocation("block/resin/resin_place3")),
                sound(getResourceLocation("block/resin/resin_place4")))
            .subtitle("subtitles.block.generic.place"));

    this.add(
        SpliceSoundEvents.RESIN_FALL,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("block/resin/resin_fall")))
            .subtitle("subtitles.splice.block.generic.fall"));

    this.add(
        SpliceSoundEvents.RESIN_BRICKS_BREAK,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("block/resin_bricks/resin_brick_break")))
            .subtitle("subtitles.block.generic.break"));

    this.add(
        SpliceSoundEvents.RESIN_BRICKS_STEP,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/resin_bricks/resin_brick_step1")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_step2")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_step3")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_step4")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_step5")))
            .subtitle("subtitles.block.generic.footsteps"));

    this.add(
        SpliceSoundEvents.RESIN_BRICKS_PLACE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/resin_bricks/resin_brick_place1")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_place2")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_place3")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_place4")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_place5")))
            .subtitle("subtitles.block.generic.place"));

    this.add(
        SpliceSoundEvents.RESIN_BRICKS_HIT,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/resin_bricks/resin_brick_hit1")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_hit2")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_hit3")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_hit4")),
                sound(getResourceLocation("block/resin_bricks/resin_brick_hit5")))
            .subtitle("subtitles.block.generic.hit"));

    this.add(
        SpliceSoundEvents.RESIN_BRICKS_FALL,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("block/resin_bricks/resin_brick_fall")))
            .subtitle("subtitles.splice.block.generic.fall"));

    this.add(
        SpliceSoundEvents.PALE_HANGING_MOSS_IDLE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss1")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss2")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss3")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss4")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss5")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss6")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss7")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss8")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss9")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss10")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss11")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss12")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss13")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss14")),
                sound(getResourceLocation("block/pale_hanging_moss/pale_hanging_moss15")))
            .subtitle("subtitles.splice.block.pale_hanging_moss.idle"));

    this.add(
        SpliceSoundEvents.EYEBLOSSOM_OPEN_LONG,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("block/eyeblossom/eyeblossom_open_long")))
            .subtitle("subtitles.splice.block.eyeblossom.open"));

    this.add(
        SpliceSoundEvents.EYEBLOSSOM_OPEN,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/eyeblossom/eyeblossom_open1")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_open2")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_open3")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_open4")))
            .subtitle("subtitles.splice.block.eyeblossom.open"));

    this.add(
        SpliceSoundEvents.EYEBLOSSOM_CLOSE_LONG,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("block/eyeblossom/eyeblossom_close_long")))
            .subtitle("subtitles.splice.block.eyeblossom.close"));

    this.add(
        SpliceSoundEvents.EYEBLOSSOM_CLOSE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/eyeblossom/eyeblossom_close1")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_close2")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_close3")))
            .subtitle("subtitles.splice.block.eyeblossom.close"));

    this.add(
        SpliceSoundEvents.EYEBLOSSOM_IDLE,
        SoundDefinition.definition()
            .with(
                sound(getResourceLocation("block/eyeblossom/eyeblossom_idle1")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_idle2")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_idle3")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_idle4")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_idle5")),
                sound(getResourceLocation("block/eyeblossom/eyeblossom_idle6")))
            .subtitle("subtitles.splice.block.eyeblossom.idle"));

    this.add(
        SpliceSoundEvents.MUSIC_DISC_TEARS,
        SoundDefinition.definition().with(sound(getResourceLocation("records/tears")).stream()));

    this.add(
        SpliceSoundEvents.MUSIC_DISC_LAVA_CHICKEN,
        SoundDefinition.definition()
            .with(sound(getResourceLocation("records/lava_chicken")).stream()));
  }
}
