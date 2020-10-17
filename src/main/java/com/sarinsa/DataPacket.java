package com.sarinsa;

import org.bukkit.Bukkit;

public class DataPacket {

    private int onlinePlayers = 0;
    private int tps = 0;

    public void updateTps() {

    }

    public void updateOnlinePlayers() {
        this.onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
    }

    public int getPlayerCount() {
        return onlinePlayers;
    }

    public int getTps() {
        return tps;
    }
}
