package com.sarinsa;

import org.bukkit.Bukkit;

public class DataHolder {

    private int onlinePlayers = 0;
    private int tps = 0;

    public void update() {
        this.onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
    }

    public int getPlayerCount() {
        return onlinePlayers;
    }

    public int getTps() {
        return tps;
    }
}
