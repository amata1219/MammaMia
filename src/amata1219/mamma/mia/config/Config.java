package amata1219.mamma.mia.config;

import amata1219.mamma.mia.MammaMia;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class Config {

    private final MammaMia plugin = MammaMia.instance();

    private FileConfiguration config = null;
    private final File file;
    private final String fileName;

    public Config(String fileName) {
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), this.fileName);
        loadSections();
    }

    public void saveDefaultConfig() {
        if (!file.exists()) plugin.saveResource(fileName, false);
    }

    public void updateConfig() {
        saveConfig();
        reloadConfig();
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream == null) return;
        config.setDefaults( YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
        loadSections();
    }

    public FileConfiguration config() {
        if(config == null) reloadConfig();
        return config;
    }

    public void saveConfig() {
        if (config == null) return;

        try {
            config().save(file);
        } catch (IOException ignored) {

        }
    }

    public abstract void loadSections();

}
