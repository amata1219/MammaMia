package amata1219.mamiyer.config.section;

import amata1219.mamiyer.config.MainConfig;
import com.google.common.collect.ImmutableSet;
import org.bukkit.configuration.ConfigurationSection;

public class TemporaryBoatSection extends ConfigSection {

    private boolean enabledOrNot;
    private ImmutableSet<String> targetWorlds;

    public TemporaryBoatSection(MainConfig config) {
        super(config);
    }

    @Override
    public void loadValues() {
        ConfigurationSection section = config.config().getConfigurationSection("Temporary boat");
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
