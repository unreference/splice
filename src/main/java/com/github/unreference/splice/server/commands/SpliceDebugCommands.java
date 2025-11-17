package com.github.unreference.splice.server.commands;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.mixin.net.minecraft.world.level.biome.SpliceMixinMultiNoiseBiomeSourceAccessor;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class SpliceDebugCommands {
  public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
    final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
    dispatcher.register(
        Commands.literal(SpliceMain.MOD_ID)
            .requires(source -> source.hasPermission(2))
            .then(
                Commands.literal("biome_chunks")
                    .then(
                        Commands.argument(
                                "biome",
                                ResourceOrTagArgument.resourceOrTag(
                                    event.getBuildContext(), Registries.BIOME))
                            .then(
                                Commands.argument("radius", IntegerArgumentType.integer(1, 256))
                                    .executes(SpliceDebugCommands::biomeChunks)))));

    dispatcher.register(
        Commands.literal(SpliceMain.MOD_ID)
            .requires(source -> source.hasPermission(2))
            .then(
                Commands.literal("biome_climate")
                    .then(
                        Commands.argument(
                                "biome",
                                ResourceOrTagArgument.resourceOrTag(
                                    event.getBuildContext(), Registries.BIOME))
                            .executes(SpliceDebugCommands::biomeClimate))));
  }

  private static int biomeClimate(CommandContext<CommandSourceStack> context)
      throws CommandSyntaxException {
    final CommandSourceStack source = context.getSource();
    final ServerPlayer player = source.getPlayerOrException();
    final ServerLevel level = source.getLevel();

    final ResourceOrTagArgument.Result<Biome> biomeArgument =
        ResourceOrTagArgument.getResourceOrTag(context, "biome", Registries.BIOME);

    final BiomeSource biomeSource = level.getChunkSource().getGenerator().getBiomeSource();
    if (!(biomeSource instanceof MultiNoiseBiomeSource multiNoiseBiomeSource)) {
      source.sendFailure(
          Component.literal(
              String.format(
                  "Current dimension uses %s, not MultiNoiseBiomeSource",
                  biomeSource.getClass().getName())));
      return -1;
    }

    final Climate.ParameterList<Holder<Biome>> parameterList =
        ((SpliceMixinMultiNoiseBiomeSourceAccessor) multiNoiseBiomeSource).splice$parameters();
    final List<Pair<Climate.ParameterPoint, Holder<Biome>>> values = parameterList.values();

    int count = 0;
    for (Pair<Climate.ParameterPoint, Holder<Biome>> entry : values) {
      final Holder<Biome> biome = entry.getSecond();
      if (!biomeArgument.test(biome)) {
        continue;
      }

      final Climate.ParameterPoint point = entry.getFirst();
      final String line = formatParameterPoint(point);
      source.sendSuccess(() -> Component.literal(line), false);
      count++;
    }

    if (count == 0) {
      source.sendFailure(
          Component.literal(
              String.format(
                  Locale.ROOT, "No climate entries found for %s", biomeArgument.asPrintable())));
    } else {
      final int finalCount = count;
      source.sendSuccess(
          () ->
              Component.literal(
                  String.format(
                      Locale.ROOT,
                      "Found %d climate entries for %s",
                      finalCount,
                      biomeArgument.asPrintable())),
          false);
    }

    return count;
  }

  private static String formatParameterPoint(Climate.ParameterPoint point) {
    return String.format(
        Locale.ROOT,
        "temperature=%s, humidity=%s, continentalness=%s, erosion=%s, depth=%s, weirdness=%s, offset=%d",
        formatRange(point.temperature()),
        formatRange(point.humidity()),
        formatRange(point.continentalness()),
        formatRange(point.erosion()),
        formatRange(point.depth()),
        formatRange(point.weirdness()),
        point.offset());
  }

  private static String formatRange(Climate.Parameter parameter) {
    final float min = Climate.unquantizeCoord(parameter.min());
    final float max = Climate.unquantizeCoord(parameter.max());

    if (min == max) {
      return String.format(Locale.ROOT, "%.3f", min);
    }

    return String.format(Locale.ROOT, "[%.3f, %.3f]", min, max);
  }

  private static int biomeChunks(CommandContext<CommandSourceStack> context)
      throws CommandSyntaxException {
    final CommandSourceStack source = context.getSource();
    final ServerPlayer player = source.getPlayerOrException();
    final ServerLevel level = source.getLevel();
    final int centerChunkX = player.chunkPosition().x;
    final int centerChunkZ = player.chunkPosition().z;

    final ResourceOrTagArgument.Result<Biome> biomeArgument =
        ResourceOrTagArgument.getResourceOrTag(context, "biome", Registries.BIOME);
    final int radiusArgument = IntegerArgumentType.getInteger(context, "radius");

    int totalChunks = 0;
    int matchingChunks = 0;

    for (int x = -radiusArgument; x <= radiusArgument; x++) {
      for (int z = -radiusArgument; z <= radiusArgument; z++) {
        final int chunkX = centerChunkX + x;
        final int chunkZ = centerChunkZ + z;
        final int blockX = (chunkX << 4) + 8;
        final int blockZ = (chunkZ << 4) + 8;

        final int height = level.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
        final BlockPos pos = new BlockPos(blockX, height, blockZ);

        final Holder<Biome> biome = level.getBiome(pos);
        if (biomeArgument.test(biome)) {
          matchingChunks++;
        }

        totalChunks++;
      }
    }

    final double percent = totalChunks == 0 ? 0.0 : (matchingChunks * 100.0) / totalChunks;
    final double diameter = radiusArgument * 2 + 1;

    final Component component =
        Component.literal(
            String.format(
                Locale.ROOT,
                "%d/%d chunks matching %s in %.0f×%.0f radius (%.3f%%)",
                matchingChunks,
                totalChunks,
                biomeArgument.asPrintable(),
                diameter,
                diameter,
                percent));

    source.sendSuccess(() -> component, false);
    return matchingChunks;
  }
}
