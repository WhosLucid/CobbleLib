package com.whoslucid.cobblelib.config;

import com.google.gson.Gson;
import com.whoslucid.cobblelib.CobbleLib;
import com.whoslucid.cobblelib.util.Utils;
import lombok.Data;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Data
public class Config {
    private boolean debug = false;
    private boolean useDefault = true;
    private String economySymbol = "Cobbletokens";

    public static Config load() {
        Gson gson = Utils.newGson();
        Config config = new Config();
        try {
            File dir = Utils.getAbsolutePath("/config/cobblelib");
            dir.mkdirs();
            File file = new File(dir, "config.json");
            if (file.exists()) {
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                config = gson.fromJson(content, Config.class);
                if (config == null) {
                    config = new Config();
                }
            }
            String json = gson.toJson(config);
            Files.writeString(file.toPath(), json, StandardCharsets.UTF_8);
        } catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to load config: " + e.getMessage());
        }
        return config;
    }

    public void save() {
        try {
            Gson gson = Utils.newGson();
            File dir = Utils.getAbsolutePath("/config/cobblelib");
            dir.mkdirs();
            File file = new File(dir, "config.json");
            String json = gson.toJson(this);
            Files.writeString(file.toPath(), json, StandardCharsets.UTF_8);
        } catch (Exception e) {
            CobbleLib.LOGGER.error("Failed to save config: " + e.getMessage());
        }
    }
}
