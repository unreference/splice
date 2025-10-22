package com.github.unreference.splice.client;

import com.github.unreference.splice.client.renderer.entity.blockentity.SpliceCopperChestRenderer;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

public final class SpliceClientMain {
  @SubscribeEvent
  public static void onEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(
        SpliceBlockEntityType.COPPER_CHEST.get(), SpliceCopperChestRenderer::new);
  }

  @SubscribeEvent
  public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
    IClientItemExtensions extension =
        new IClientItemExtensions() {
          private static final Minecraft MC = Minecraft.getInstance();
          private static final BlockEntityWithoutLevelRenderer BLOCK_ENTITY_RENDERER =
              new BlockEntityWithoutLevelRenderer(
                  MC.getBlockEntityRenderDispatcher(), MC.getEntityModels()) {

                private final Map<Block, SpliceCopperChestBlockEntity> chests = new HashMap<>();

                private SpliceCopperChestBlockEntity getBlockEntityRendererFor(Block block) {
                  return chests.computeIfAbsent(
                      block,
                      b -> new SpliceCopperChestBlockEntity(BlockPos.ZERO, b.defaultBlockState()));
                }

                @Override
                public void renderByItem(
                    @NotNull ItemStack stack,
                    @NotNull ItemDisplayContext displayContext,
                    @NotNull PoseStack poseStack,
                    @NotNull MultiBufferSource buffer,
                    int packedLight,
                    int packedOverlay) {
                  if (stack.getItem() instanceof BlockItem blockItem) {
                    final SpliceCopperChestBlockEntity blockEntity =
                        getBlockEntityRendererFor(blockItem.getBlock());

                    MC.getBlockEntityRenderDispatcher()
                        .renderItem(blockEntity, poseStack, buffer, packedLight, packedOverlay);
                  }
                }
              };

          @Override
          public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return BLOCK_ENTITY_RENDERER;
          }
        };

    event.registerItem(extension, SpliceBlocks.COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.EXPOSED_COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.WEATHERED_COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.OXIDIZED_COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.WAXED_COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST.asItem());
    event.registerItem(extension, SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST.asItem());
  }
}
