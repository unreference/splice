package com.github.unreference.splice.core.particles;

import com.github.unreference.splice.SpliceMain;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SpliceParticleTypes {
  private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
      DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SpliceMain.MOD_ID);

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> COPPER_FIRE_FLAME =
      PARTICLE_TYPES.register("copper_fire_flame", () -> new SimpleParticleType(false));

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PALE_OAK_LEAVES =
      PARTICLE_TYPES.register("pale_oak_leaves", () -> new SimpleParticleType(false));

  public static void register(IEventBus bus) {
    PARTICLE_TYPES.register(bus);
  }
}
