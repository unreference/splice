package com.github.unreference.splice.world.level.levelgen.feature.treedecorators;

import com.github.unreference.splice.SpliceMain;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public record SpliceTreeDecoratorType<T extends TreeDecorator>(MapCodec<T> codec) {
  private static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES =
      DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, SpliceMain.MOD_ID);

  public static final DeferredHolder<
          TreeDecoratorType<?>, TreeDecoratorType<SplicePaleMossDecorator>>
      PALE_MOSS = register("pale_moss", SplicePaleMossDecorator.CODEC);

  public static final DeferredHolder<
          TreeDecoratorType<?>, TreeDecoratorType<SpliceCreakingHeartDecorator>>
      CREAKING_HEART = register("creaking_heart", SpliceCreakingHeartDecorator.CODEC);

  private static <T extends TreeDecorator>
      DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<T>> register(
          String name, MapCodec<T> codec) {
    return TREE_DECORATOR_TYPES.register(name, () -> new TreeDecoratorType<>(codec));
  }

  public static void register(IEventBus bus) {
    TREE_DECORATOR_TYPES.register(bus);
  }
}
