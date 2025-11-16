package com.github.unreference.splice.client;

import com.github.unreference.splice.client.particle.SpliceFallingLeavesParticle;
import com.github.unreference.splice.client.particle.SpliceTrailParticle;
import com.github.unreference.splice.client.renderer.entity.SpliceItemRenderer;
import com.github.unreference.splice.client.renderer.entity.blockentity.SpliceCopperChestRenderer;
import com.github.unreference.splice.core.particles.SpliceParticleTypes;
import com.github.unreference.splice.data.worldgen.biome.SpliceBiomes;
import com.github.unreference.splice.world.level.block.SpliceBlocks;
import com.github.unreference.splice.world.level.block.entity.SpliceBlockEntityType;
import com.github.unreference.splice.world.level.block.entity.SpliceCopperChestBlockEntity;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

public final class SpliceClientMain {
  @SubscribeEvent
  public static void onEntityRenderersEvent(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(
        SpliceBlockEntityType.COPPER_CHEST.get(), SpliceCopperChestRenderer::new);
    event.registerBlockEntityRenderer(SpliceBlockEntityType.SIGN.get(), SignRenderer::new);
    event.registerBlockEntityRenderer(
        SpliceBlockEntityType.HANGING_SIGN.get(), HangingSignRenderer::new);
  }

  @SubscribeEvent
  public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(
        SpliceParticleTypes.COPPER_FIRE_FLAME.get(), FlameParticle.Provider::new);
    event.registerSpriteSet(
        SpliceParticleTypes.PALE_OAK_LEAVES.get(),
        SpliceFallingLeavesParticle.PaleOakProvider::new);
    event.registerSpriteSet(SpliceParticleTypes.TRAIL.get(), SpliceTrailParticle.Provider::new);
  }

  @SubscribeEvent
  public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
    copperChestsRenderer(event);
  }

  private static void copperChestsRenderer(RegisterClientExtensionsEvent event) {
    event.registerItem(
        itemRenderer(
            (block) -> new SpliceCopperChestBlockEntity(BlockPos.ZERO, block.defaultBlockState()),
            8),
        SpliceBlocks.COPPER_CHEST.asItem(),
        SpliceBlocks.EXPOSED_COPPER_CHEST.asItem(),
        SpliceBlocks.WEATHERED_COPPER_CHEST.asItem(),
        SpliceBlocks.OXIDIZED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_EXPOSED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_WEATHERED_COPPER_CHEST.asItem(),
        SpliceBlocks.WAXED_OXIDIZED_COPPER_CHEST.asItem());
  }

  private static <T extends BlockEntity> IClientItemExtensions itemRenderer(
      Function<Block, T> factory, int variants) {
    return itemRenderer(factory, (entity, stack) -> {}, variants);
  }

  private static <T extends BlockEntity> IClientItemExtensions itemRenderer(
      Function<Block, T> factory, BiConsumer<T, ItemStack> stateApplier, int variants) {
    return new IClientItemExtensions() {
      private BlockEntityWithoutLevelRenderer renderer;

      @Override
      public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
        if (this.renderer == null) {
          this.renderer = SpliceItemRenderer.create(factory, stateApplier, variants);
        }

        return this.renderer;
      }
    };
  }

  @SubscribeEvent
  public static void onSelectMusic(SelectMusicEvent event) {
    final Minecraft mc = Minecraft.getInstance();
    final LocalPlayer player = mc.player;
    final ClientLevel level = mc.level;

    if (player == null || level == null) {
      return;
    }

    final BlockPos pos = player.blockPosition();
    final Holder<Biome> biome = level.getBiome(pos);

    if (biome.is(SpliceBiomes.PALE_GARDEN)) {
      event.setMusic(null);
    }
  }
}
