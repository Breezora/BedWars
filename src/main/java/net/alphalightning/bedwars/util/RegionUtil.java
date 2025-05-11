package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import org.bukkit.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public final class RegionUtil {

    public static RegionInformation createRegionInformation(Set<Location> floodFillLocations, CuboidSelection selection) {
        return fromLists(selection, floodFillLocations);
    }

    public static RegionInformation createRegionInformation(CuboidSelection selection) {
        return fromLists(selection, new HashSet<>(selection.allBetween()));
    }

    private static RegionInformation fromLists(CuboidSelection selection, Set<Location> locations) {
        // Berechne die Dimensionen der Region
        int minX = Math.min(selection.first().getBlockX(), selection.second().getBlockX());
        int maxX = Math.max(selection.first().getBlockX(), selection.second().getBlockX());
        int minY = Math.min(selection.first().getBlockY(), selection.second().getBlockY());
        int maxY = Math.max(selection.first().getBlockY(), selection.second().getBlockY());
        int minZ = Math.min(selection.first().getBlockZ(), selection.second().getBlockZ());
        int maxZ = Math.max(selection.first().getBlockZ(), selection.second().getBlockZ());

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        int depth = maxZ - minZ + 1;

        BitSet bitSet = new BitSet(width * height * depth);

        for (Location location : locations) {
            int x = location.getBlockX() - minX;
            int y = location.getBlockY() - minY;
            int z = location.getBlockZ() - minZ;

            int index = x + (y * width) + (z * width * height);
            bitSet.set(index, true);
        }

        return new RegionInformation(width, height, depth, minX, minY, minZ, bitSet);
    }

    public static void saveRegions(BedWarsPlugin plugin, GameMapSetup setup, List<RegionInformation> informationList) {
        Path directory = plugin.getDataFolder().toPath()
                .resolve("maps")
                .resolve("bin");

        try {
            Files.createDirectories(directory);
            Path regionFile = directory.resolve(setup.mapName() + ".bin");

            try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(
                    Files.newOutputStream(regionFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)))) {

                outputStream.write(1); // Version der Dateiformat-Version für den Fall, dass sich die Struktur mal ändert

                for (RegionInformation information : informationList) {
                    for (JacksonTeam _ : setup.teams()) {
                        // Schreibe Informationen über die Dimensionen des Quaders
                        outputStream.writeInt(information.width());
                        outputStream.writeInt(information.height());
                        outputStream.writeInt(information.depth());
                        outputStream.writeInt(information.minX());
                        outputStream.writeInt(information.minY());
                        outputStream.writeInt(information.minZ());

                        // Schreibe BitSet
                        byte[] bits = information.bitSet().toByteArray();
                        outputStream.writeInt(bits.length);
                        outputStream.write(bits);
                    }
                }
            }

        } catch (IOException exception) {
            plugin.getLogger().severe("Could not save region file: " + exception.getMessage());
        }
    }

    public List<RegionInformation> loadRegions(BedWarsPlugin plugin, String mapName) {
        List<RegionInformation> regions = new ArrayList<>();
        Path path = plugin.getDataFolder().toPath()
                .resolve("maps")
                .resolve("bin")
                .resolve(mapName + ".bin");

        if (!Files.exists(path)) return regions;

        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            while (inputStream.available() > 0) {
                int version = inputStream.readInt();
                if (version != 1) {
                    plugin.getLogger().warning("Unknown region file version: " + version);
                    continue;
                }

                // Lese Dimensionen
                int width = inputStream.readInt();
                int height = inputStream.readInt();
                int depth = inputStream.readInt();
                int minX = inputStream.readInt();
                int minY = inputStream.readInt();
                int minZ = inputStream.readInt();

                // Lese BitSet
                int bitSetLength = inputStream.readInt();
                byte[] bitSetBytes = new byte[bitSetLength];
                inputStream.readFully(bitSetBytes);
                BitSet bitSet = BitSet.valueOf(bitSetBytes);

                regions.add(new RegionInformation(width, height, depth, minX, minY, minZ, bitSet));
            }

        } catch (IOException exception) {
            plugin.getLogger().severe("Could not read region file: " + exception.getMessage());
        }
        return regions;
    }

}
