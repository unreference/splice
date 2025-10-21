package com.github.unreference.splice.world.item;

import com.github.unreference.splice.tags.SpliceBlockTags;
import com.github.unreference.splice.tags.SpliceItemTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public final class SpliceTiers {
  public static Tier COPPER =
      new SimpleTier(
          SpliceBlockTags.INCORRECT_FOR_COPPER_TOOL,
          190,
          5.0f,
          1.0f,
          13,
          () -> Ingredient.of(SpliceItemTags.COPPER_TOOL_MATERIALS));
}
