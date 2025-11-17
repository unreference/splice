package com.github.unreference.splice.data.worldgen.region;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

public interface SpliceRegion {
  void addBiomes(Consumer<Pair<Climate.ParameterPoint, Holder<Biome>>> sink);
}
