package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.gson.JsonObject;
import net.alphalightning.bedwars.util.JSONUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

@CommandAlias("setspawnpoint")
@Description("Set the Spawnpoints based on further Arguments")
public class SetSpawnPointCommand extends BaseCommand {

    @Subcommand("lobby")
    @CommandPermission("bedwars.admin")
    public void onSetSpawnPoint(CommandSender sender) throws Exception {

        if(sender instanceof Player p) {
            try {
                String filePath = "lobbyconfig.json";

                // Neues JSON-Objekt erstellen
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("x", p.getX());
                jsonObject.addProperty("y", p.getY());
                jsonObject.addProperty("z", p.getZ());
                jsonObject.addProperty("pitch", p.getPitch());
                jsonObject.addProperty("yaw", p.getYaw());
                // In Datei schreiben
                JSONUtil.writeJsonToFile(filePath, jsonObject);
                System.out.println("JSON-Datei wurde geschrieben.");
            } catch (IOException e) {
                throw new Exception("Fehler beim Schreiben der JSON Datei");
            }
        }
    }
}
