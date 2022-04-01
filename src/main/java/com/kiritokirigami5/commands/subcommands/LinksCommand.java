package com.kiritokirigami5.commands.subcommands;

import com.kiritokirigami5.commands.SubCommand;
import com.kiritokirigami5.utils.Colorize;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LinksCommand extends SubCommand {

    @Override
    public String getName() {
        return "links";
    }

    @Override
    public String getDescription() {
        return "Displays you the allowed links";
    }

    @Override
    public String getSyntax() {
        return "/stream links";
    }

    @Override
    public void perform(Player player, String[] args) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("StreamX");
        FileConfiguration config = plugin.getConfig();

        String StreamPerm = config.getString("Config.AdminPermission");
        String NoPerms = config.getString("Messages.NoPerms");
        NoPerms = PlaceholderAPI.setPlaceholders(player, NoPerms);

        String Method = config.getString("Messages.Method");

        if (player.hasPermission(StreamPerm)) {
            player.sendMessage(Colorize.colorize(Method));
            player.sendMessage(Colorize.unColorize("&f&m-----------------------------------"));
            for (String str : config.getConfigurationSection("Platforms").getKeys(false)) {
                String method = config.getString("Platforms." + str + ".domain");
                method = PlaceholderAPI.setPlaceholders(player, method);
                player.sendMessage(Colorize.unColorize("&a" + str + " &f> " + "&b" + method));
            }
            player.sendMessage(Colorize.unColorize("&f&m-----------------------------------"));
        } else {
            player.sendMessage(Colorize.colorize(NoPerms));
        }
    }
}
