package amata1219.mamma.mia.config.section;

import amata1219.mamma.mia.config.Config;

public abstract class ConfigSection {

    protected Config config;

    public ConfigSection(Config config) {
        this.config = config;
        loadValues();
    }

    public abstract void loadValues();

}
