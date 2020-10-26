package com.sarinsa;

import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchRunnable extends BukkitRunnable {

    private final JsonWriter<DataHolder> jsonWriter;

    public FetchRunnable(JsonWriter<DataHolder> jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    @Override
    public void run() {
        try {
            jsonWriter.getData().update();
            String data = jsonWriter.write();

            String urlString = DataFetcher.instance.getConfiguraion().getString("jsonbin-url");
            String masterKey = DataFetcher.instance.getConfiguraion().getString("jsonbin-key");

            DataFetcher.instance.getLogger().info("Checking json bin auth.");

            if (urlString.isEmpty() || masterKey.isEmpty()) {
                DataFetcher.instance.getLogger().warning("Invalid or missing jsonbin URL or master key. Make sure to specify them in the config.");
                return;
            }

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Wait 10 seconds before giving up
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-Master-Key", masterKey);

            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setDoOutput(true);

            connection.connect();

            if (connection.getResponseCode() == 200) {
                DataFetcher.instance.getLogger().info("Connect request accepted! Writing data...");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.close();
            }
            else {
                DataFetcher.instance.getLogger().warning("Connect request denied. Returned response code: " + connection.getResponseCode());
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
