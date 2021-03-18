package com.skyternity.bottomless.blocks;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class BlockUtil {
    public static VoxelShape cube(Integer x1, Integer y1, Integer z1, Integer x2, Integer y2, Integer z2) {
        return VoxelShapes.cuboid(
                hexdec(x1), hexdec(y1), hexdec(z1),
                hexdec(x2), hexdec(y2), hexdec(z2)
        );
    }
    public static double hexdec(Integer n) {
        return n.doubleValue() / 16D;
    }
}
