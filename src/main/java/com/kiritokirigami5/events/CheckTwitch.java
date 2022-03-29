package com.kiritokirigami5.events;

import com.kiritokirigami5.StreamX;
import com.kiritokirigami5.utils.Colorize;
import com.kiritokirigami5.utils.StreamAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public class CheckTwitch {

    int taskID;

    private StreamX plugin;
    long ticks;

    public CheckTwitch(StreamX plugin, long ticks) {
        this.plugin = plugin;
        this.ticks = ticks;
    }

    public void execution() {
        BukkitScheduler sh = Bukkit.getScheduler();

        taskID = sh.scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                action();
            }
        }, 0L, ticks);
    }

    public void action() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            FileConfiguration config = plugin.getConfig();

            List<String> players = config.getStringList("TwitchApi.Streamers");


            for (int i = 0; i < players.size(); i++) {
                StreamAPI streamarg = new StreamAPI(players.get(i));

                List<String> msg = config.getStringList("TwitchApi.Message");

                String path = String.valueOf(streamarg.getUrl().getPath());
                String idStr = path.substring(path.lastIndexOf("/") + 1);

                if (streamarg.isOnline()) {

                    for (int g = 0; g < msg.size(); g++) {

                        p.sendMessage(Colorize.unColorize(msg.get(g))
                                .replaceAll("%player%", idStr)
                                .replaceAll("%link%", "twitch.tv/" + idStr)
                        );
                    }

                }
            }
        }
    }
}
