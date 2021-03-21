package amata1219.mamiyer.config;

import amata1219.mamiyer.Mamiyer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Config {

    private final Mamiyer plugin = Mamiyer.instance();

    private FileConfiguration config = null;
    private final File configFile;
    private final String file;

    public Config(String fileName) {
        this.file = fileName;
        configFile = new File(plugin.getDataFolder(), file);
    }

    public void saveDefaultConfig() {
        if(!configFile.exists()) plugin.saveResource(file, false);
    }

    public void updateConfig() {
        saveConfig();
        reloadConfig();
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = plugin.getResource(file);
        if (defConfigStream == null) return;
        config.setDefaults( YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
    }

    public FileConfiguration config() {
        if(config == null) reloadConfig();
        return config;
    }

    public void saveConfig() {
        if(config == null) return;

        try {
            config().save(configFile);
        } catch (IOException ignored) {

        }
    }

}
