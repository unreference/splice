package com.github.unreference.splice.server.commands;

import com.github.unreference.splice.SpliceMain;
import com.github.unreference.splice.mixin.net.minecraft.world.level.biome.SpliceMixinMultiNoiseBiomeSourceAccessor;
import com.github.unreference.splice.util.SpliceComponentBuilder;
import com.google.common.base.Stopwatch;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class SpliceDebugCommands {
  private static final int MAX_BIOME_CHUNK_RADIUS = 256;
  private static final int MAX_BIOME_SEARCH_RADIUS = 6400;
  private static final int BIOME_SAMPLE_RESOLUTION_HORIZONTAL = 32;
  private static final int BIOME_SAMPLE_RESOLUTION_VERTICAL = 64;
  private static final int MAX_STRUCTURE_SEARCH_RADIUS = 100;

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
                                Commands.argument(
                                        "radius",
                                        IntegerArgumentType.integer(1, MAX_BIOME_CHUNK_RADIUS))
                                    .executes(
                                        context ->
                                            biomeChunks(
                                                context.getSource(),
                                                ResourceOrTagArgument.getResourceOrTag(
                                                    context, "biome", Registries.BIOME),
                                                IntegerArgumentType.getInteger(
                                                    context, "radius"))))))
            .then(
                Commands.literal("biome_climate")
                    .then(
                        Commands.argument(
                                "biome",
                                ResourceOrTagArgument.resourceOrTag(
                                    event.getBuildContext(), Registries.BIOME))
                            .executes(SpliceDebugCommands::biomeClimate)))
            .then(
                Commands.literal("locate")
                    .then(
                        Commands.literal("biome")
                            .then(
                                Commands.argument(
                                        "biome",
                                        ResourceOrTagArgument.resourceOrTag(
                                            event.getBuildContext(), Registries.BIOME))
                                    .executes(
                                        context ->
                                            locateBiome(
                                                context.getSource(),
                                                ResourceOrTagArgument.getResourceOrTag(
                                                    context, "biome", Registries.BIOME)))))));
  }

  private static int locateBiome(
      CommandSourceStack source, ResourceOrTagArgument.Result<Biome> biome) {
    final BlockPos playerPos = BlockPos.containing(source.getPosition());
    final ServerLevel level = source.getLevel();
    final String biomePrintable = biome.asPrintable();

    final Supplier<Component> searching =
        () ->
            Component.literal(
                String.format("Searching for biome of type \"%s\"...", biomePrintable));

    final Supplier<LocateBiomeResult> task =
        () -> {
          final Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
          final Pair<BlockPos, Holder<Biome>> pair =
              level.findClosestBiome3d(
                  biome,
                  playerPos,
                  MAX_BIOME_SEARCH_RADIUS,
                  BIOME_SAMPLE_RESOLUTION_HORIZONTAL,
                  BIOME_SAMPLE_RESOLUTION_VERTICAL);

          stopwatch.stop();
          return new LocateBiomeResult(pair, stopwatch.elapsed());
        };

    final Consumer<LocateBiomeResult> callback =
        result -> {
          if (result.pair() == null) {
            source.sendFailure(
                Component.translatable("commands.locate.biome.not_found", biomePrintable));
          } else {
            showLocateResult(
                source,
                biome,
                playerPos,
                result.pair(),
                "commands.locate.biome.success",
                true,
                result.duration());
          }
        };

    return runAsyncCommand(source, searching, task, callback);
  }

  private static void showLocateResult(
      CommandSourceStack source,
      ResourceOrTagArgument.Result<?> result,
      BlockPos pos,
      Pair<BlockPos, Holder<Biome>> resultPos,
      String translationKey,
      boolean isAbsoluteY,
      Duration duration) {
    final SpliceComponentBuilder builder = new SpliceComponentBuilder(result.asPrintable());
    result
        .unwrap()
        .ifRight(
            holders ->
                builder.append(" (").append(resultPos.getSecond().getRegisteredName()).append(")"));

    final Component element = builder.build();
    showLocateResult(source, pos, resultPos, translationKey, isAbsoluteY, element, duration);
  }

  private static void showLocateResult(
      CommandSourceStack source,
      BlockPos pos,
      Pair<BlockPos, Holder<Biome>> resultPos,
      String translationKey,
      boolean isAbsoluteY,
      Component elementName,
      Duration duration) {
    final BlockPos blockPos = resultPos.getFirst();
    final int distance =
        isAbsoluteY
            ? Mth.floor(Mth.sqrt((float) pos.distSqr(blockPos)))
            : Mth.floor(getDistance(pos.getX(), pos.getZ(), blockPos.getX(), blockPos.getZ()));
    final String location = isAbsoluteY ? String.valueOf(blockPos.getY()) : "~";

    final Component coordinates =
        ComponentUtils.wrapInSquareBrackets(
                Component.translatable(
                    "chat.coordinates", blockPos.getX(), location, blockPos.getZ()))
            .withStyle(
                style ->
                    style
                        .withColor(ChatFormatting.GREEN)
                        .withClickEvent(
                            new ClickEvent(
                                ClickEvent.Action.SUGGEST_COMMAND,
                                "/teleport @s "
                                    + blockPos.getX()
                                    + ' '
                                    + location
                                    + ' '
                                    + blockPos.getZ()))
                        .withHoverEvent(
                            new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("chat.coordinates.tooltip"))));

    final MutableComponent success =
        Component.translatable(translationKey, elementName, coordinates, distance);

    source.sendSuccess(() -> success, false);
    SpliceMain.LOGGER.info(
        "Locating biome {} took {} ms", elementName.getString(), duration.toMillis());
  }

  private static float getDistance(int x, int z, int x2, int z2) {
    final int i = x2 - x;
    final int j = z2 - z;
    return Mth.sqrt((float) (i * i + j * j));
  }

  private static int biomeClimate(CommandContext<CommandSourceStack> context)
      throws CommandSyntaxException {
    final CommandSourceStack source = context.getSource();
    final ServerLevel level = source.getLevel();

    final ResourceOrTagArgument.Result<Biome> biomeArgument =
        ResourceOrTagArgument.getResourceOrTag(context, "biome", Registries.BIOME);

    final BiomeSource biomeSource = level.getChunkSource().getGenerator().getBiomeSource();
    final Climate.ParameterList<Holder<Biome>> parameterList =
        ((SpliceMixinMultiNoiseBiomeSourceAccessor) biomeSource).splice$parameters();
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

  private static int biomeChunks(
      CommandSourceStack source, ResourceOrTagArgument.Result<Biome> biome, int radius)
      throws CommandSyntaxException {
    final ServerPlayer player = source.getPlayerOrException();
    final ServerLevel level = source.getLevel();
    final int centerChunkX = player.chunkPosition().x;
    final int centerChunkZ = player.chunkPosition().z;
    final String biomePrintable = biome.asPrintable();

    final Supplier<Component> searching =
        () ->
            Component.literal(
                String.format(
                    "Scanning %d chunk/chunks for \"%s\"...",
                    (radius * 2 + 1) * (radius * 2 + 1), biomePrintable));

    final Stopwatch[] stopwatch = new Stopwatch[1];
    final Supplier<BiomeChunkResult> task =
        () -> {
          stopwatch[0] = Stopwatch.createStarted(Util.TICKER);

          int totalChunks = 0;
          int matchingChunks = 0;

          for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
              final int chunkX = centerChunkX + x;
              final int chunkZ = centerChunkZ + z;
              final int blockX = (chunkX << 4) + 8;
              final int blockZ = (chunkZ << 4) + 8;

              final int height = level.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
              final BlockPos pos = new BlockPos(blockX, height, blockZ);

              final Holder<Biome> biomeHolder = level.getBiome(pos);
              if (biome.test(biomeHolder)) {
                matchingChunks++;
              }

              totalChunks++;
            }
          }

          final double percent = totalChunks == 0 ? 0.0 : (matchingChunks * 100.0) / totalChunks;

          stopwatch[0].stop();
          return new BiomeChunkResult(matchingChunks, totalChunks, percent, stopwatch[0].elapsed());
        };

    final Consumer<BiomeChunkResult> callback =
        result -> {
          final double diameter = radius * 2 + 1;
          final Component component =
              Component.literal(
                  String.format(
                      Locale.ROOT,
                      "%d/%d chunks matching %s in %.0f×%.0f radius (%.3f%%)",
                      result.matching(),
                      result.total(),
                      biomePrintable,
                      diameter,
                      diameter,
                      result.percent()));

          source.sendSuccess(() -> component, false);
          SpliceMain.LOGGER.info(
              "Locating {} matching chunks took {} ms",
              result.total(),
              stopwatch[0].elapsed().toMillis());
        };

    return runAsyncCommand(source, searching, task, callback);
  }

  private static <T> int runAsyncCommand(
      CommandSourceStack source,
      Supplier<Component> searchMessage,
      Supplier<T> task,
      Consumer<T> callback) {
    final MinecraftServer server = source.getServer();
    source.sendSuccess(searchMessage, false);

    CompletableFuture.supplyAsync(task, Util.backgroundExecutor())
        .thenAcceptAsync(callback, server);

    return 1;
  }

  private record LocateBiomeResult(Pair<BlockPos, Holder<Biome>> pair, Duration duration) {}

  private record BiomeChunkResult(int matching, int total, double percent, Duration duration) {}
}
