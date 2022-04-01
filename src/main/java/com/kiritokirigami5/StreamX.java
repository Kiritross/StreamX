package com.kiritokirigami5;

import com.kiritokirigami5.commands.CommandManager;
import com.kiritokirigami5.events.CheckTwitch;
import com.kiritokirigami5.files.Config;
import com.kiritokirigami5.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class StreamX extends JavaPlugin {

    private static StreamX plugin;

    @Override
    public void onEnable() {
        plugin = this;

        registerConfig();
        getCommand("Stream").setExecutor(new CommandManager());
        this.reloadConfig();
        updateChecker();

        Metrics metrics = new Metrics(this,14802);

        //---------------------------------------------------------------------------------------------------------------

        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String javaVersion = System.getProperty("java.version");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (javaVersion.startsWith("1.7")) {
                map.put("Java 1.7", entry);
            } else if (javaVersion.startsWith("1.8")) {
                map.put("Java 1.8", entry);
            } else if (javaVersion.startsWith("1.9")) {
                map.put("Java 1.9", entry);
            } else {
                map.put("Other", entry);
            }
            return map;
        }));

        //---------------------------------------------------------------------------------------------------------------
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

    PluginDescriptionFile pdffile = this.getDescription();

    public String latestversion;

    public String version = pdffile.getVersion();

    public void updateChecker() {
        try {
            HttpURLConnection con = (HttpURLConnection)(new URL("https://api.spigotmc.org/legacy/update.php?resource=97346/")).openConnection();
            int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            this.latestversion = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
            if (this.latestversion.length() <= 7) {
                if (!this.version.equals(this.latestversion)) {
                    Bukkit.getConsoleSender().sendMessage(Colorize.colorize("&cThere is a new version available. &e(&8"+this.latestversion+"&e)"));
                    Bukkit.getConsoleSender().sendMessage(Colorize.colorize("&fYou can download it at: https://www.spigotmc.org/resources/97346/"));
                } else {
                    Bukkit.getConsoleSender().sendMessage(Colorize.colorize("&aYou have the latest version of the plugin!"));
                }
            }
        } catch (Exception var3) {
            Bukkit.getConsoleSender().sendMessage(Colorize.colorize("&cError while checking update."));
        }
    }

}
