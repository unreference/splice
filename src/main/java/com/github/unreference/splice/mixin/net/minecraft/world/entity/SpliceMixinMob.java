package com.github.unreference.splice.mixin.net.minecraft.world.entity;

import com.github.unreference.splice.world.item.SpliceItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mob.class)
public abstract class SpliceMixinMob {
  @Redirect(
      method = "populateDefaultEquipmentSlots",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/world/entity/Mob;getEquipmentForSlot(Lnet/minecraft/world/entity/EquipmentSlot;I)Lnet/minecraft/world/item/Item;"))
  private static Item splice$getEquipmentForSlot(
      EquipmentSlot slot, int chance, @Local(argsOnly = true) RandomSource random) {
    if (chance == 1 && random.nextFloat() < 0.5f) {
      return switch (slot) {
        case HEAD -> SpliceItems.COPPER_HELMET.asItem();
        case CHEST -> SpliceItems.COPPER_CHESTPLATE.asItem();
        case LEGS -> SpliceItems.COPPER_LEGGINGS.asItem();
        case FEET -> SpliceItems.COPPER_BOOTS.asItem();
        default -> null;
      };
    }

    return Mob.getEquipmentForSlot(slot, chance);
  }
}
