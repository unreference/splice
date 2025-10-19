package com.github.unreference.splice.data.tags;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.tags.SpliceBannerPatternTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public final class SpliceBannerPatternTagsProvider extends BannerPatternTagsProvider {
  public SpliceBannerPatternTagsProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> provider,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, provider, SpliceMain.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    this.replaceVanilla();

    this.tag(SpliceBannerPatternTags.PATTERN_ITEM_FIELD_MASONED).add(BannerPatterns.BRICKS);
    this.tag(SpliceBannerPatternTags.PATTERN_ITEM_BORDURE_INDENTED)
        .add(BannerPatterns.CURLY_BORDER);
  }

  private void replaceVanilla() {
    this.tag(BannerPatternTags.NO_ITEM_REQUIRED)
        .replace(true)
        .add(
            BannerPatterns.SQUARE_BOTTOM_LEFT,
            BannerPatterns.SQUARE_BOTTOM_RIGHT,
            BannerPatterns.SQUARE_TOP_LEFT,
            BannerPatterns.SQUARE_TOP_RIGHT,
            BannerPatterns.STRIPE_BOTTOM,
            BannerPatterns.STRIPE_TOP,
            BannerPatterns.STRIPE_LEFT,
            BannerPatterns.STRIPE_RIGHT,
            BannerPatterns.STRIPE_CENTER,
            BannerPatterns.STRIPE_MIDDLE,
            BannerPatterns.STRIPE_DOWNRIGHT,
            BannerPatterns.STRIPE_DOWNLEFT,
            BannerPatterns.STRIPE_SMALL,
            BannerPatterns.CROSS,
            BannerPatterns.STRAIGHT_CROSS,
            BannerPatterns.TRIANGLE_BOTTOM,
            BannerPatterns.TRIANGLE_TOP,
            BannerPatterns.TRIANGLES_BOTTOM,
            BannerPatterns.TRIANGLES_TOP,
            BannerPatterns.DIAGONAL_LEFT,
            BannerPatterns.DIAGONAL_RIGHT,
            BannerPatterns.DIAGONAL_LEFT_MIRROR,
            BannerPatterns.DIAGONAL_RIGHT_MIRROR,
            BannerPatterns.CIRCLE_MIDDLE,
            BannerPatterns.RHOMBUS_MIDDLE,
            BannerPatterns.HALF_VERTICAL,
            BannerPatterns.HALF_HORIZONTAL,
            BannerPatterns.HALF_VERTICAL_MIRROR,
            BannerPatterns.HALF_HORIZONTAL_MIRROR,
            BannerPatterns.BORDER,
            BannerPatterns.GRADIENT,
            BannerPatterns.GRADIENT_UP);
  }
}
