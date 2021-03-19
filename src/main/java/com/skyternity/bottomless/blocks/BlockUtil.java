package com.skyternity.bottomless.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class BlockUtil {
    public static VoxelShape cube(Integer x1, Integer y1, Integer z1, Integer x2, Integer y2, Integer z2) {
        return VoxelShapes.cuboid(
                hexdec(x1), hexdec(y1), hexdec(z1),
                hexdec(x2), hexdec(y2), hexdec(z2)
        );
    }

    public static Vec3d offset(BlockPos pos, double x, double y, double z) {
        return new Vec3d(
                (double) pos.getX() + hexdec(x),
                (double) pos.getY() + hexdec(y),
                (double) pos.getZ() + hexdec(z)
        );
    }

    public static double hexdec(int n) {
        return hexdec((double) n);
    }
    public static double hexdec(double n) {
        return n / 16D;
    }
}
