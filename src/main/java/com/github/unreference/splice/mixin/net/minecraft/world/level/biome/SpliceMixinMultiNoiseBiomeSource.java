package com.github.unreference.splice.mixin.net.minecraft.world.level.biome;

import com.github.unreference.splice.data.worldgen.region.SpliceRegions;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class SpliceMixinMultiNoiseBiomeSource {
  @Final @Shadow
  private Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>>
      parameters;

  @Unique private Climate.ParameterList<Holder<Biome>> splice$extendedOverworldParameterList;

  @Unique
  private static Climate.ParameterList<Holder<Biome>> splice$buildExtendedParameterList(
      Climate.ParameterList<Holder<Biome>> baseList) {
    final List<Pair<Climate.ParameterPoint, Holder<Biome>>> values =
        new ArrayList<>(baseList.values());

    SpliceRegions.contribute(values::add);
    return new Climate.ParameterList<>(values);
  }

  @Inject(method = "parameters", at = @At("RETURN"), cancellable = true)
  private void splice$parameters(CallbackInfoReturnable<Climate.ParameterList<Holder<Biome>>> cir) {
    if (!this.splice$isOverworldPreset()) {
      return;
    }

    if (this.splice$extendedOverworldParameterList == null) {
      final Climate.ParameterList<Holder<Biome>> base = cir.getReturnValue();
      if (base == null) {
        return;
      }

      this.splice$extendedOverworldParameterList = splice$buildExtendedParameterList(base);
    }

    cir.setReturnValue(this.splice$extendedOverworldParameterList);
  }

  @Unique
  private boolean splice$isOverworldPreset() {
    return this.parameters
        .right()
        .map(preset -> preset.is(MultiNoiseBiomeSourceParameterLists.OVERWORLD))
        .orElse(false);
  }
}
