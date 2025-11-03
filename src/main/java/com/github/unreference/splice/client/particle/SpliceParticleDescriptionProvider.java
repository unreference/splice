package com.github.unreference.splice.client.particle;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public final class SpliceParticleDescriptionProvider extends ParticleDescriptionProvider {
  public SpliceParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper);
  }

  @Override
  protected void addDescriptions() {
    this.sprite(
        SpliceParticleTypes.COPPER_FIRE_FLAME.get(),
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "copper_fire_flame"));

    this.spriteSet(
        SpliceParticleTypes.PALE_OAK_LEAVES.get(),
        ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "pale_oak"),
        12,
        false);
  }
}
