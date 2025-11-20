package com.github.unreference.splice.world.level.block.state.properties;

import net.minecraft.world.level.block.state.properties.*;

public final class SpliceBlockStateProperties {
  public static final BooleanProperty IS_JUST_THE_TIP = BooleanProperty.create("tip");
  public static final EnumProperty<SpliceCreakingHeartState> CREAKING_HEART_STATE =
      EnumProperty.create("creaking_heart_state", SpliceCreakingHeartState.class);
  public static final BooleanProperty IS_NATURAL = BooleanProperty.create("natural");
}
