package com.github.unreference.splice.world.level.levelgen.feature;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SpliceFeature {
  public static final DeferredRegister<Feature<?>> FEATURES =
      DeferredRegister.create(Registries.FEATURE, SpliceMain.MOD_ID);

  public static final DeferredHolder<Feature<?>, SpliceSimpleBlockFeature> SIMPLE_BLOCK =
      register("simple_block");

  private static DeferredHolder<Feature<?>, SpliceSimpleBlockFeature> register(String name) {
    return FEATURES.register(
        name, () -> new SpliceSimpleBlockFeature(SimpleBlockConfiguration.CODEC));
  }

  public static void register(IEventBus bus) {
    FEATURES.register(bus);
  }
}
