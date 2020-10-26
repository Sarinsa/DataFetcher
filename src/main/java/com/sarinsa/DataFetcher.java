package com.sarinsa;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DataFetcher extends JavaPlugin {

    public static DataFetcher instance;
    private FileConfiguration CONFIG;
    private JsonWriter<DataHolder> jsonWriter;
    private FetchRunnable task;
    // 2 minutes of server ticks.
    private final long defaultTaskDelay = 20 * 60 * 2;


    @Override
    public void onEnable() {
        instance = this;

        createDir();
        createConfig();

        this.getCommand("dfreload").setExecutor(new ConfigReloadExecutor());

        this.jsonWriter = new JsonWriter<>(DataHolder.class);
        this.task = new FetchRunnable(jsonWriter);

        reloadConfig();

        long period = CONFIG.getLong("task-period");
        task.runTaskTimer(this, 0, period);
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
            File file = new File(getDataFolder(), "config.yml");

            if (!file.exists()) {
                getLogger().info("Creating plugin configuration...");

                CONFIG = YamlConfiguration.loadConfiguration(file);
                // Default values
                CONFIG.set("task-period", defaultTaskDelay);
                CONFIG.set("jsonbin-url", "");
                CONFIG.set("jsonbin-key", "");

                CONFIG.save(file);
            }
        } catch (Exception e) {
            getLogger().severe("Failed to create config file! This ain't good, chief!");
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfiguraion() {
        return this.CONFIG;
    }

    @Override
    public void reloadConfig() {
        File config = new File(getDataFolder(), "config.yml");
        this.CONFIG = YamlConfiguration.loadConfiguration(config);
    }
}
