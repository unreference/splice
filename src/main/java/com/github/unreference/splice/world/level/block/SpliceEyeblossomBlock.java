package com.github.unreference.splice.world.level.block;

import com.github.unreference.splice.core.particles.SpliceTrailParticleOption;
import com.github.unreference.splice.sounds.SpliceSoundEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public final class SpliceEyeblossomBlock extends FlowerBlock {
  public static final MapCodec<SpliceEyeblossomBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      Codec.BOOL.fieldOf("open").forGetter(eyeblossom -> eyeblossom.type.isOpen),
                      propertiesCodec())
                  .apply(instance, SpliceEyeblossomBlock::new));

  private static final int XZ_RANGE = 3;
  private static final int Y_RANGE = 2;

  private final SpliceEyeblossomBlock.Type type;

  public SpliceEyeblossomBlock(
      SpliceEyeblossomBlock.Type type, BlockBehaviour.Properties properties) {
    super(type.effect, type.effectDuration, properties);
    this.type = type;
  }

  public SpliceEyeblossomBlock(boolean isOpen, BlockBehaviour.Properties properties) {
    super(Type.fromBoolean(isOpen).effect, Type.fromBoolean(isOpen).effectDuration, properties);
    this.type = Type.fromBoolean(isOpen);
  }

  public static boolean isMoonVisible(Level level) {
    if (!level.dimensionType().natural()) {
      return false;
    }

    final int ticks = (int) (level.getDayTime() % 24000L);
    return ticks >= 12600 && ticks <= 23400;
  }

  @Override
  public void animateTick(
      @NotNull BlockState state,
      @NotNull Level level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (this.type.emitSounds() && random.nextInt(700) == 0) {
      final BlockState below = level.getBlockState(pos.below());
      if (below.is(SpliceBlocks.PALE_MOSS_BLOCK)) {
        level.playLocalSound(
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            SpliceSoundEvents.EYEBLOSSOM_IDLE.get(),
            SoundSource.AMBIENT,
            1.0f,
            1.0f,
            false);
      }
    }
  }

  @Override
  protected void randomTick(
      @NotNull BlockState state,
      @NotNull ServerLevel level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (this.isAbleToChangeState(state, level, pos, random)) {
      level.playSound(null, pos, this.type.transform().longSwitchSound.get(), SoundSource.BLOCKS);
    }

    super.randomTick(state, level, pos, random);
  }

  @Override
  protected void tick(
      @NotNull BlockState state,
      @NotNull ServerLevel level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (this.isAbleToChangeState(state, level, pos, random)) {
      level.playSound(null, pos, this.type.transform().shortSwitchSound.get(), SoundSource.BLOCKS);
    }

    super.tick(state, level, pos, random);
  }

  private boolean isAbleToChangeState(
      @NotNull BlockState state,
      @NotNull ServerLevel level,
      @NotNull BlockPos pos,
      @NotNull RandomSource random) {
    if (!level.dimensionType().natural() || isMoonVisible(level) == this.type.isOpen) {
      return false;
    }

    final Type type = this.type.transform();
    level.setBlock(pos, type.getState(), Block.UPDATE_ALL);
    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
    type.spawnTransformParticle(level, pos, random);

    BlockPos.betweenClosed(
            pos.offset(-XZ_RANGE, -Y_RANGE, -XZ_RANGE), pos.offset(XZ_RANGE, Y_RANGE, XZ_RANGE))
        .forEach(
            neighborPos -> {
              final BlockState neighbor = level.getBlockState(neighborPos);
              if (neighbor == state) {
                final double d = Math.sqrt(pos.distSqr(neighborPos));
                final int delay = random.nextIntBetweenInclusive((int) (d * 5.0), (int) (d * 10.0));
                level.scheduleTick(neighborPos, state.getBlock(), delay);
              }
            });

    return true;
  }

  @Override
  protected void entityInside(
      @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
    if (!level.isClientSide()
        && level.getDifficulty() != Difficulty.PEACEFUL
        && entity instanceof Bee b
        && !b.hasEffect(MobEffects.POISON)) {
      b.addEffect(this.getBeeInteractionEffect());
    }
  }

  private MobEffectInstance getBeeInteractionEffect() {
    return new MobEffectInstance(MobEffects.POISON, 25);
  }

  public enum Type {
    OPEN(
        true,
        MobEffects.BLINDNESS,
        11.0f,
        SpliceSoundEvents.EYEBLOSSOM_OPEN_LONG,
        SpliceSoundEvents.EYEBLOSSOM_OPEN,
        16545810),
    CLOSED(
        false,
        MobEffects.CONFUSION,
        7.0f,
        SpliceSoundEvents.EYEBLOSSOM_CLOSE_LONG,
        SpliceSoundEvents.EYEBLOSSOM_CLOSE,
        6250335);

    private final boolean isOpen;
    private final Holder<MobEffect> effect;
    private final float effectDuration;
    private final DeferredHolder<SoundEvent, SoundEvent> longSwitchSound;
    private final DeferredHolder<SoundEvent, SoundEvent> shortSwitchSound;
    private final int particleColor;

    Type(
        boolean isOpen,
        Holder<MobEffect> effect,
        float effectDuration,
        DeferredHolder<SoundEvent, SoundEvent> longSwitchSound,
        DeferredHolder<SoundEvent, SoundEvent> shortSwitchSound,
        int particleColor) {
      this.isOpen = isOpen;
      this.effect = effect;
      this.effectDuration = effectDuration;
      this.longSwitchSound = longSwitchSound;
      this.shortSwitchSound = shortSwitchSound;
      this.particleColor = particleColor;
    }

    public static Type fromBoolean(boolean isOpen) {
      return isOpen ? OPEN : CLOSED;
    }

    private Block getBlock() {
      return this.isOpen
          ? SpliceBlocks.OPEN_EYEBLOSSOM.get()
          : SpliceBlocks.CLOSED_EYEBLOSSOM.get();
    }

    private BlockState getState() {
      return this.getBlock().defaultBlockState();
    }

    public Type transform() {
      return fromBoolean(!this.isOpen);
    }

    private boolean emitSounds() {
      return this.isOpen;
    }

    public void spawnTransformParticle(ServerLevel level, BlockPos pos, RandomSource random) {
      final Vec3 center = pos.getCenter();
      final double randomDouble = 0.5 + random.nextDouble();
      final Vec3 location =
          new Vec3(random.nextDouble() - 0.5, random.nextDouble() + 1.0, random.nextDouble() - 0.5);
      final Vec3 scaled = center.add(location.scale(randomDouble));
      final SpliceTrailParticleOption particle =
          new SpliceTrailParticleOption(scaled, this.particleColor, (int) (20.0 * randomDouble));
      level.sendParticles(particle, center.x(), center.y(), center.z(), 1, 0.0, 0.0, 0.0, 0.0);
    }

    public DeferredHolder<SoundEvent, SoundEvent> getLongSwitchSound() {
      return longSwitchSound;
    }
  }
}
