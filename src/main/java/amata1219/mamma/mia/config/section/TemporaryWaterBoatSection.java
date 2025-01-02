package amata1219.mamma.mia.config.section;

import amata1219.mamma.mia.config.MainConfig;
import com.google.common.collect.ImmutableSet;
import org.bukkit.configuration.ConfigurationSection;

public class TemporaryWaterBoatSection extends ConfigSection {

    private boolean enabledOrNot;
    private ImmutableSet<String> targetWorlds;

    public TemporaryWaterBoatSection(MainConfig config) {
        super(config);
    }

    @Override
    public void loadValues() {
        ConfigurationSection section = config.config().getConfigurationSection("Temporary boat on water");
        enabledOrNot = section.getBoolean("Enabled or not");
        targetWorlds = ImmutableSet.copyOf(section.getStringList("Target worlds"));
    }

    public boolean enabledOrNot() {
        return enabledOrNot;
    }

    public ImmutableSet<String> targetWorlds() {
        return targetWorlds;
    }

}