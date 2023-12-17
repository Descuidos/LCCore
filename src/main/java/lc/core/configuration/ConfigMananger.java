package lc.core.configuration;

import lc.core.LCCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class ConfigMananger {
    private LCCore core = LCCore.getInstance();
    public FileConfiguration config = null;
    private File configFile = null;


    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }
        return this.config;
    }

    public void reloadConfig() {
        if (this.config == null) {
            this.configFile = new File(this.core.getDataFolder(), "config.yml");
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);

        try {
            Reader defConfigStream = new InputStreamReader(this.core.getResource("config.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.config.setDefaults(defConfig);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void registerConfig() {
        this.configFile = new File(this.core.getDataFolder(), "config.yml");
        if (!this.configFile.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}
