package com.github.unreference.splice.world.item;

import com.github.unreference.splice.tags.SpliceBlockTags;
import com.github.unreference.splice.tags.SpliceItemTags;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum SpliceTiers implements Tier {
  COPPER(
      SpliceBlockTags.INCORRECT_FOR_COPPER_TOOL,
      190,
      5.0f,
      1.0f,
      13,
      () -> Ingredient.of(SpliceItemTags.COPPER_TOOL_MATERIALS));

  private final TagKey<Block> incorrectBlocksForDrops;
  private final int uses;
  private final float speed;
  private final float damage;
  private final int enchantmentValue;
  private final Supplier<Ingredient> repairIngredient;

  SpliceTiers(
      TagKey<Block> incorrectBlockForDrops,
      int uses,
      float speed,
      float damage,
      int enchantmentValue,
      Supplier<Ingredient> repairIngredient) {
    this.incorrectBlocksForDrops = incorrectBlockForDrops;
    this.uses = uses;
    this.speed = speed;
    this.damage = damage;
    this.enchantmentValue = enchantmentValue;
    this.repairIngredient = Suppliers.memoize(repairIngredient::get);
  }

  @Override
  public int getUses() {
    return this.uses;
  }

  @Override
  public float getSpeed() {
    return this.speed;
  }

  @Override
  public float getAttackDamageBonus() {
    return this.damage;
  }

  @Override
  public TagKey<Block> getIncorrectBlocksForDrops() {
    return this.incorrectBlocksForDrops;
  }

  @Override
  public int getEnchantmentValue() {
    return this.enchantmentValue;
  }

  @Override
  public Ingredient getRepairIngredient() {
    return this.repairIngredient.get();
  }
}
