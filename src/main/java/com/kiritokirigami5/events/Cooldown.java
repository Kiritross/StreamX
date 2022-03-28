package com.kiritokirigami5.events;

import com.kiritokirigami5.StreamX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Cooldown {
    private StreamX plugin;

    public Cooldown(StreamX plugin){
        this.plugin = plugin;
    }

    public String getCooldown(Player player){
        FileConfiguration config = plugin.getConfig();
        String pathtime = "Players."+player.getUniqueId()+".cooldown";
        if(config.contains(pathtime)){
            String timecooldownString = config.getString(pathtime);
            long timecooldown = Long.valueOf(timecooldownString);
            long millis = System.currentTimeMillis();
            long cooldown = config.getInt("Config.Cooldown");
            long cooldownmil = cooldown*1000;

            long espera = millis - timecooldown;
            long esperaDiv = espera/1000;
            long esperatotalseg = cooldown - esperaDiv;
            long esperatotalmin = esperatotalseg/60;
            long esperatotalhour = esperatotalmin/60;
            if(((timecooldown + cooldownmil) > millis) && (timecooldown != 0)){
                if(esperatotalseg > 59){
                    esperatotalseg = esperatotalseg - 60*esperatotalmin;
                }
                String time = "";
                if(esperatotalseg != 0){
                    time = esperatotalseg+"s";
                }

                if(esperatotalmin > 59){
                    esperatotalmin = esperatotalmin - 60*esperatotalhour;
                }
                if(esperatotalmin > 0){
                    time = esperatotalmin+"min"+" "+time;
                }

                if(esperatotalhour > 0){
                    time = esperatotalhour+ "h"+" " + time;
                }
                return time;
            }else{
                //Ya se termino el cooldown
                return "-1";
            }
        }else{
            //Usa el comando por primera vez, ya que no existe el path en la config
            return "-1";
        }
    }
}
