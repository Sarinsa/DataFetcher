package com.sarinsa;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DataFetcher extends JavaPlugin {

    public static DataFetcher instance;
    private FileConfiguration CONFIG;
    private JsonWriter<DataPacket> jsonWriter = new JsonWriter<>(DataPacket.class);

    private FetchRunnable task = new FetchRunnable();
    // 2 minutes of server ticks.
    private final long defaultTaskDelay = 20 * 60 * 2;

    public DataFetcher() {
        instance = this;
    }

    @Override
    public void onEnable() {
        createDir();
        createConfig();

        CONFIG = getConfiguraion();

        task.runTaskLater(this, CONFIG.getLong("task-delay"));
    }

    private void createDir() {
        try {
            if (!getDataFolder().exists()) {
                if (!getDataFolder().mkdirs())
                    getLogger().warning("Failed to create config directory.");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void createConfig() {
        try {
            File main = new File(getDataFolder(), "cfg.yml");

            if (!main.exists()) {
                getLogger().info("Creating plugin configuration...");

                CONFIG = YamlConfiguration.loadConfiguration(main);
                CONFIG.set("task-delay", defaultTaskDelay);
                CONFIG.save(main);
            }
        } catch (Exception e) {
            getLogger().severe("Failed to create config file! This ain't good, chief!");
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfiguraion() {
        File config = new File(getDataFolder(), "cfg.yml");
        return YamlConfiguration.loadConfiguration(config);
    }
}
