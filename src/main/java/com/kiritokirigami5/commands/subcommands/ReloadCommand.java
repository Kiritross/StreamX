package com.kiritokirigami5.commands.subcommands;

import com.kiritokirigami5.StreamX;
import com.kiritokirigami5.commands.SubCommand;
import com.kiritokirigami5.events.CheckTwitch;
import com.kiritokirigami5.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ReloadCommand extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload plugin config";
    }

    @Override
    public String getSyntax() {
        return "/stream reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("StreamX");
        FileConfiguration config = plugin.getConfig();

        String StreamPerm = config.getString("Config.AdminPermission");
        String NoPerms = config.getString("Messages.NoPerms");

        if (player.hasPermission(StreamPerm)) {
            plugin.reloadConfig();
            if (config.getBoolean("TwitchApi.Enable")){
                CheckTwitch check = new CheckTwitch((StreamX) plugin, config.getInt("TwitchApi.DelayCheck") * 20L);
                check.execution();
            }
            player.sendMessage(Colorize.colorize(config.getString("Messages.Reload")));
        }else{
            player.sendMessage(Colorize.colorize(NoPerms));
        }
    }
}
