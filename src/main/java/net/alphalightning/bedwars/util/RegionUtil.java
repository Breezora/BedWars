package net.alphalightning.bedwars.util;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class RegionUtil {

    public static void saveRegions(BedWarsPlugin plugin, GameMapSetup setup, RegionInformation information) {
        Path directory = plugin.getDataFolder().toPath().resolve("maps");

        try {
            Files.createDirectories(directory);
            Path regionFile = directory.resolve(setup.mapName() + ".bin");

            try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(
                    Files.newOutputStream(regionFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)))) {

                outputStream.write(1); // Version der Dateiformat-Version für den Fall, dass sich die Struktur mal ändert

                for(JacksonTeam team : setup.teams()) {
                    String teamName = team.name();

                    // Schreibe Name vom Team
                    byte[] teamBytes = teamName.getBytes(StandardCharsets.UTF_8);
                    outputStream.writeInt(teamBytes.length);
                    outputStream.write(teamBytes);

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

}
