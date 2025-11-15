package com.github.unreference.splice.world.phys;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class SpliceVec3 {
  public static final StreamCodec<ByteBuf, Vec3> STREAM_CODEC =
      new StreamCodec<>() {
        public @NotNull Vec3 decode(@NotNull ByteBuf byteBuf) {
          return new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble());
        }

        public void encode(@NotNull ByteBuf byteBuf, @NotNull Vec3 vec3) {
          byteBuf.writeDouble(vec3.x());
          byteBuf.writeDouble(vec3.y());
          byteBuf.writeDouble(vec3.z());
        }
      };
}
