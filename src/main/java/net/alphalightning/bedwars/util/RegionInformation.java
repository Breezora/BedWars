package net.alphalightning.bedwars.util;

import java.util.BitSet;

public record RegionInformation(int width, int height, int depth, int minX, int minY, int minZ, BitSet bitSet) {
}
