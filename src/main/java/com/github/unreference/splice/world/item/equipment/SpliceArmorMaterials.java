package com.github.unreference.splice.world.item.equipment;

import com.github.unreference.splice.SpliceMain;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public final class SpliceArmorMaterials {
  public static final Holder<ArmorMaterial> COPPER =
      register(
          "copper",
          makeDefense(1, 3, 4, 2, 4),
          8,
          SoundEvents.ARMOR_EQUIP_GENERIC,
          0.0f,
          0.0f,
          () -> Ingredient.of(Items.COPPER_INGOT));

  private static EnumMap<ArmorItem.Type, Integer> makeDefense(
      int boots, int leggings, int chestplate, int helmet, int body) {
    return Maps.newEnumMap(
        Map.of(
            ArmorItem.Type.BOOTS,
            boots,
            ArmorItem.Type.LEGGINGS,
            leggings,
            ArmorItem.Type.CHESTPLATE,
            chestplate,
            ArmorItem.Type.HELMET,
            helmet,
            ArmorItem.Type.BODY,
            body));
  }

  private static Holder<ArmorMaterial> register(
      String key,
      EnumMap<ArmorItem.Type, Integer> defense,
      int enchantmentValue,
      Holder<SoundEvent> equipSound,
      float toughness,
      float knockbackResistance,
      Supplier<Ingredient> repairIngredient) {
    List<ArmorMaterial.Layer> list =
        List.of(
            new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key)));
    return register(
        key,
        defense,
        enchantmentValue,
        equipSound,
        toughness,
        knockbackResistance,
        repairIngredient,
        list);
  }

  private static Holder<ArmorMaterial> register(
      String key,
      EnumMap<ArmorItem.Type, Integer> defense,
      int enchantmentValue,
      Holder<SoundEvent> equipSound,
      float toughness,
      float knockbackResistance,
      Supplier<Ingredient> makeIngredient,
      List<ArmorMaterial.Layer> layers) {
    EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);

    for (ArmorItem.Type armoritem$Type : ArmorItem.Type.values()) {
      enumMap.put(armoritem$Type, defense.get(armoritem$Type));
    }

    return Registry.registerForHolder(
        BuiltInRegistries.ARMOR_MATERIAL,
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, key),
        new ArmorMaterial(
            enumMap,
            enchantmentValue,
            equipSound,
            makeIngredient,
            layers,
            toughness,
            knockbackResistance));
  }
}
