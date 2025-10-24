package com.github.unreference.splice.client;

import com.github.unreference.splice.client.renderer.entity.SpliceItemRenderer;
import com.github.unreference.splice.client.renderer.entity.blockentity.SpliceCopperChestRenderer;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
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
    final IClientItemExtensions extension =
        new IClientItemExtensions() {
          private final BlockEntityWithoutLevelRenderer renderer =
              SpliceItemRenderer.create(
                  (Block block) ->
                      new SpliceCopperChestBlockEntity(BlockPos.ZERO, block.defaultBlockState()),
                  (entity, stack) -> {},
                  8);

          @Override
          public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return renderer;
          }
        };

    event.registerItem(
        extension,
        SpliceBlocks.COPPER_CHEST.asItem(),
        SpliceBlocks.EXPOSED_COPPER_CHEST.asItem(),
        SpliceBlocks.WEATHERED_COPPER_CHEST.asItem(),
        SpliceBlocks.OXIDIZED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST.asItem());
  }

  @SubscribeEvent
  public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(
        SpliceParticleTypes.COPPER_FIRE_FLAME.get(), FlameParticle.Provider::new);
  }
}
