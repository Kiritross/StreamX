package com.kiritokirigami5;

import com.kiritokirigami5.commands.CommandManager;
import com.kiritokirigami5.events.CheckTwitch;
import com.kiritokirigami5.files.Config;
import com.kiritokirigami5.utils.Colorize;
import com.kiritokirigami5.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StreamX extends JavaPlugin {

    private static StreamX plugin;

    @Override
    public void onEnable() {
        plugin = this;

        registerConfig();
        getCommand("Stream").setExecutor(new CommandManager());
        this.reloadConfig();

        Metrics metrics = new Metrics(this,14802);

        new UpdateChecker(this, 97346).getLatestVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(Colorize.colorize("&fA new version of StreamX is available! &c" + version));
            }else{
                Bukkit.getConsoleSender().sendMessage(Colorize.colorize("&fYou are running the latest version of StreamX!"));
            }
        });

        if (config.getBoolean("TwitchApi.Enable")){
            CheckTwitch check = new CheckTwitch(this, config.getInt("TwitchApi.DelayCheck") * 20L);
            check.execution();
        }
    }

    @Override
    public void onDisable() {
        this.reloadConfig();
    }

    public void registerConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Config.setup();
        Config.get().options().copyDefaults(true);
        Config.save();
    }

    FileConfiguration config = this.getConfig();
}
