package amata1219.mamiyer.config.section;

import amata1219.mamiyer.config.MainConfig;
import com.google.common.collect.ImmutableSet;
import org.bukkit.configuration.ConfigurationSection;

public class ElytraBoosterDisablerSection {

    private final MainConfig config;

    private boolean enabledOrNot;
    private int messagingIntervals;
    private boolean appliedOrNotRegardlessOfTPS;
    private ImmutableSet<String> targetWorlds;
    private int tpsThresholdToWhichRestrictionApplies;
    private String startedApplyingRestrictionMessage;
    private String stoppedApplyingRestrictionMessage;
    private String blockedElytraBoostingMessage;

    public ElytraBoosterDisablerSection(MainConfig config) {
        this.config = config;
        loadValues();
    }

    public void loadValues() {
        ConfigurationSection section = config.config().getConfigurationSection("Restriction on elytra boosts by fireworks");
        enabledOrNot = section.getBoolean("Enabled or not");
        messagingIntervals = section.getInt("Messaging intervals");
        appliedOrNotRegardlessOfTPS = section.getBoolean("Applied or not regardless of TPS");
        targetWorlds = ImmutableSet.copyOf(section.getStringList("Target worlds"));
        tpsThresholdToWhichRestrictionApplies = section.getInt("TPS Threshold to which the restriction applies");
        startedApplyingRestrictionMessage = section.getString("When the plugin started applying the restriction");
        stoppedApplyingRestrictionMessage = section.getString("When the plugin stopped applying the restriction");
        blockedElytraBoostingMessage = section.getString("When the plugin blocked elytra boosting");
    }

    public boolean enabledOrNot() {
        return enabledOrNot;
    }

    public int messagingIntervals() {
        return messagingIntervals;
    }

    public boolean appliedOrNotRegardlessOfTPS() {
        return appliedOrNotRegardlessOfTPS;
    }

    public ImmutableSet<String> targetWorlds() {
        return targetWorlds;
    }

    public int tpsThresholdToWhichRestrictionApplied() {
        return tpsThresholdToWhichRestrictionApplies;
    }

    public String startedApplyingRestrictionMessage() {
        return startedApplyingRestrictionMessage;
    }

    public String stoppedApplyingRestrictionMessage() {
        return stoppedApplyingRestrictionMessage;
    }

    public String blocedElytraBoostingMessage() {
        return blockedElytraBoostingMessage;
    }

}
