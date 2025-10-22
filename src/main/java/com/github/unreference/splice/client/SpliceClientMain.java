package com.github.unreference.splice.client;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.client.renderer.entity.blockentity.SpliceCopperChestRenderer;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

public final class SpliceClientMain {
  private static final Material COPPER_CHEST_MATERIAL =
      new Material(
          Sheets.CHEST_SHEET,
          ResourceLocation.fromNamespaceAndPath(SpliceMain.MOD_ID, "entity/chest/copper"));

  @SubscribeEvent
  public static void onEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(
        SpliceBlockEntityType.COPPER_CHEST.get(), SpliceCopperChestRenderer::new);
  }

  @SubscribeEvent
  public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
    event.registerItem(
        new IClientItemExtensions() {
          private static final Minecraft MC = Minecraft.getInstance();
          private static final BlockEntityWithoutLevelRenderer BLOCK_ENTITY_RENDERER =
              new BlockEntityWithoutLevelRenderer(
                  MC.getBlockEntityRenderDispatcher(), MC.getEntityModels()) {
                private static final SpliceCopperChestBlockEntity COPPER_CHEST_SINGLE =
                    new SpliceCopperChestBlockEntity(
                        BlockPos.ZERO, SpliceBlocks.COPPER_CHEST.get().defaultBlockState());

                @Override
                public void renderByItem(
                    @NotNull ItemStack stack,
                    @NotNull ItemDisplayContext displayContext,
                    @NotNull PoseStack poseStack,
                    @NotNull MultiBufferSource buffer,
                    int packedLight,
                    int packedOverlay) {
                  MC.getBlockEntityRenderDispatcher()
                      .renderItem(
                          COPPER_CHEST_SINGLE, poseStack, buffer, packedLight, packedOverlay);
                }
              };

          @Override
          public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return BLOCK_ENTITY_RENDERER;
          }
        },
        SpliceBlocks.COPPER_CHEST.get().asItem());
  }
}
