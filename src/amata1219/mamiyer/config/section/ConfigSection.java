package amata1219.mamiyer.config.section;

import amata1219.mamiyer.config.MainConfig;

public abstract class ConfigSection {

    protected MainConfig config;

    public ConfigSection(MainConfig config) {
        this.config = config;
        loadValues();
    }

    public abstract void loadValues();

}
