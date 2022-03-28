package com.kiritokirigami5.commands.subcommands;

import com.kiritokirigami5.StreamX;
import com.kiritokirigami5.commands.SubCommand;
import com.kiritokirigami5.events.Cooldown;
import com.kiritokirigami5.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class StreamCommand extends SubCommand {

    int NO = 0;

    @Override
    public String getName() {
        return "stream";
    }

    @Override
    public String getDescription() {
        return "Reload plugin config";
    }

    @Override
    public String getSyntax() {
        return "/stream stream <link>";
    }

    @Override
    public void perform(Player player, String[] args) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("StreamX");
        FileConfiguration config = plugin.getConfig();

        String NoLink = config.getString("Messages.NoLink");
        String NoRecognized = config.getString("Messages.NoRecognized");
        String StreamPerm = config.getString("Config.StreamPermission");
        String NoPerms = config.getString("Messages.NoPerms");

        if (player.hasPermission(StreamPerm)){
            if (args.length >= 2) {
                for (String str : config.getConfigurationSection("Platforms").getKeys(false)) {
                    if (args[1].contains(config.getString("Platforms." + str + ".domain"))) {

                        Cooldown c = new Cooldown((StreamX) plugin);
                        String pathtime = "Players." + player.getUniqueId() + ".cooldown";

                        if (c.getCooldown(player).equals("-1")) {
                            long millis = System.currentTimeMillis();
                            config.set(pathtime, millis);
                            plugin.saveConfig();

                            List<String> msg = config.getStringList("Platforms." + str + ".message");

                            for (int i = 0; i < msg.size(); i++) {

                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(Colorize.unColorize(msg.get(i))
                                            .replaceAll("%player%", player.getName())
                                            .replaceAll("%link%", args[1])
                                            .replaceAll("https://", "")
                                            .replaceAll("http://", "")
                                    );
                                }
                            }
                            NO = 0;
                            break;
                        } else {
                            String inCooldown = config.getString("Messages.InCooldown");
                            player.sendMessage(Colorize.colorize(inCooldown).replaceAll("%cooldown%", c.getCooldown(player)));
                            break;
                        }

                    } else if (args[1].equalsIgnoreCase("<link>")) {
                        player.sendMessage(Colorize.colorize("&7Use &e/stream &7help for help"));
                        break;
                    }else {
                        NO += 1;
                    }
                }
                if (NO > 0){
                    player.sendMessage(Colorize.colorize(NoRecognized));
                }
            } else {
                player.sendMessage(Colorize.colorize(NoLink));
            }
        }else{
            player.sendMessage(Colorize.colorize(NoPerms));
        }
    }
}
