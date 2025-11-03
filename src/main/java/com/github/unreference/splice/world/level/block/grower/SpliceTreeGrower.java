package com.github.unreference.splice.world.level.block.grower;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.worldgen.features.SpliceTreeFeatures;
import java.util.Optional;
import net.minecraft.world.level.block.grower.TreeGrower;

public final class SpliceTreeGrower {
  public static final TreeGrower PALE_OAK =
      new TreeGrower(
          SpliceMain.MOD_ID + ":pale_oak",
          Optional.of(SpliceTreeFeatures.PALE_OAK_BONE_MEAL),
          Optional.empty(),
          Optional.empty());
}
