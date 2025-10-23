package com.github.unreference.splice.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public final class SpliceItemRenderer<T extends BlockEntity>
    extends BlockEntityWithoutLevelRenderer {
  private final Function<Block, T> factory;
  private final BiConsumer<T, ItemStack> stateApplier;
  private final Map<Block, T> cache;

  public SpliceItemRenderer(
      BlockEntityRenderDispatcher dispatcher,
      EntityModelSet models,
      Function<Block, T> factory,
      BiConsumer<T, ItemStack> stateApplier,
      int expectedVariants) {
    super(dispatcher, models);
    this.factory = factory;
    this.stateApplier = stateApplier != null ? stateApplier : (be, s) -> {};
    this.cache = new HashMap<>(Math.max(1, expectedVariants));
  }

  public static <T extends BlockEntity> SpliceItemRenderer<T> create(
      Function<Block, T> factory, BiConsumer<T, ItemStack> stateApplier, int expectedVariants) {
    final Minecraft mc = Minecraft.getInstance();
    return new SpliceItemRenderer<>(
        mc.getBlockEntityRenderDispatcher(),
        mc.getEntityModels(),
        factory,
        stateApplier,
        expectedVariants);
  }

  private T getInstanceFor(Block block) {
    return cache.computeIfAbsent(block, factory);
  }

  @Override
  public void renderByItem(
      @NotNull ItemStack stack,
      @NotNull ItemDisplayContext displayContext,
      @NotNull PoseStack poseStack,
      @NotNull MultiBufferSource buffer,
      int packedLight,
      int packedOverlay) {

    if (!(stack.getItem() instanceof BlockItem blockItem)) return;

    T be = getInstanceFor(blockItem.getBlock());
    stateApplier.accept(be, stack);

    Minecraft.getInstance()
        .getBlockEntityRenderDispatcher()
        .renderItem(be, poseStack, buffer, packedLight, packedOverlay);
  }
}
