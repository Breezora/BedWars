package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class RegionUtil {

    public static void saveRegions(BedWarsPlugin plugin, GameMapSetup setup, RegionInformation information) {
        Path directory = plugin.getDataFolder().toPath()
                .resolve("maps")
                .resolve("bin");

        try {
            Files.createDirectories(directory);
            Path regionFile = directory.resolve(setup.mapName() + ".bin");

            try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(
                    Files.newOutputStream(regionFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)))) {

                outputStream.write(1); // Version der Dateiformat-Version für den Fall, dass sich die Struktur mal ändert

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
