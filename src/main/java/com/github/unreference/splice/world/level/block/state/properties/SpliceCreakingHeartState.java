package com.github.unreference.splice.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum SpliceCreakingHeartState implements StringRepresentable {
  UPROOTED("uprooted"),
  DORMANT("dormant"),
  AWAKE("awake");

  private final String name;

  SpliceCreakingHeartState(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public @NotNull String getSerializedName() {
    return this.name;
  }
}
