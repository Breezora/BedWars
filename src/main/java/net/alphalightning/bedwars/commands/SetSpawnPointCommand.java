package net.alphalightning.bedwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.google.gson.JsonObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("setspawnpoint")
@Description("Set the Spawnpoints based on further Arguments")
public class SetSpawnPointCommand extends BaseCommand {

    @Subcommand("lobby")
    @CommandPermission("bedwars.admin")
    public void onSetSpawnPoint(CommandSender sender) throws Exception {

        if (sender instanceof Player p) {
//            try {
            String filePath = "plugins/BedWars/config/lobbyconfig.json";

            // Neues JSON-Objekt erstellen
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("x", p.getX());
            jsonObject.addProperty("y", p.getY());
            jsonObject.addProperty("z", p.getZ());
            jsonObject.addProperty("pitch", p.getPitch());
            jsonObject.addProperty("yaw", p.getYaw());
            jsonObject.addProperty("world", p.getWorld().getName());
            // In Datei schreiben
            //    JSONUtil.writeJsonToFile(filePath, jsonObject);
            System.out.println("JSON-Datei wurde geschrieben.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
