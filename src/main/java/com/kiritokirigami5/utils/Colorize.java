package com.kiritokirigami5.utils;

import org.bukkit.ChatColor;

public class Colorize {
    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', "&7[&bStreamX&7] " + msg);
    }

    public static String unColorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
