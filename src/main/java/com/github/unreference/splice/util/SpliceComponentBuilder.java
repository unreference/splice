package com.github.unreference.splice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class SpliceComponentBuilder {
  private final List<Component> parts = new ArrayList<>();

  public SpliceComponentBuilder() {}

  public SpliceComponentBuilder(String text) {
    this.append(text);
  }

  public SpliceComponentBuilder(Component component) {
    this.append(component);
  }

  public static SpliceComponentBuilder create() {
    return new SpliceComponentBuilder();
  }

  public SpliceComponentBuilder append(Component component) {
    this.parts.add(component);
    return this;
  }

  public SpliceComponentBuilder append(String text) {
    this.parts.add(Component.literal(text));
    return this;
  }

  public SpliceComponentBuilder append(String text, ChatFormatting... formatting) {
    this.parts.add(Component.literal(text).withStyle(formatting));
    return this;
  }

  public SpliceComponentBuilder append(String text, Style style) {
    this.parts.add(Component.literal(text).withStyle(style));
    return this;
  }

  public SpliceComponentBuilder newLine() {
    return this.append("\n");
  }

  public SpliceComponentBuilder appendIf(boolean condition, String text) {
    if (condition) {
      this.append(text);
    }

    return this;
  }

  public SpliceComponentBuilder appendIf(
      boolean condition, String text, ChatFormatting... formatting) {
    if (condition) {
      this.append(text, formatting);
    }

    return this;
  }

  public SpliceComponentBuilder appendIf(boolean condition, Supplier<Component> supplier) {
    if (condition) {
      this.append(supplier.get());
    }

    return this;
  }

  public MutableComponent build() {
    final MutableComponent root = Component.literal("");
    for (Component part : this.parts) {
      root.append(part);
    }

    return root;
  }
}
