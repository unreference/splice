package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomes;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public final class SpliceBiomeTagsProvider extends BiomeTagsProvider {
  public SpliceBiomeTagsProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> provider,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, provider, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    this.tag(BiomeTags.IS_FOREST).add(SpliceBiomes.PALE_GARDEN);
    this.tag(BiomeTags.HAS_WOODLAND_MANSION).add(SpliceBiomes.PALE_GARDEN);
    this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(SpliceBiomes.PALE_GARDEN);
  }
}
