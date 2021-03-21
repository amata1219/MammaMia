package amata1219.mamiyer.config.section;

import amata1219.mamiyer.config.MainConfig;
import org.bukkit.configuration.ConfigurationSection;

public class KickingAFKerSection extends ConfigSection {

    private boolean enabledOrNot;
    private int messagingIntervals;
    private boolean appliedOrNotRegardlessOfTPS;
    private int tpsThresholdToWhichRuleApplies;
    private int afkedTimeRequiredForKicks;
    private String startedApplyingRuleMessage;
    private String stoppedApplyingRuleMessage;
    private String kickedAFKerMessage;

    public KickingAFKerSection(MainConfig config) {
        super(config);
    }

    @Override
    public void loadValues() {
        ConfigurationSection section = config.config().getConfigurationSection("Kicking AFKer");
        enabledOrNot = section.getBoolean("Enabled or not");
        messagingIntervals = section.getInt("Messaging intervals");
        appliedOrNotRegardlessOfTPS = section.getBoolean("Applied or not regardless of TPS");
        tpsThresholdToWhichRuleApplies = section.getInt("TPS Threshold to which the rule applies");
        afkedTimeRequiredForKicks = section.getInt("AFKed time required for kicks");

        ConfigurationSection message = section.getConfigurationSection("Message");
        startedApplyingRuleMessage = message.getString("When the plugin started applying the rule");
        stoppedApplyingRuleMessage = message.getString("When the plugin stopped applying the rule");
        kickedAFKerMessage = message.getString("When the plugin kicked AFKer");
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

    public int tpsThresholdToWhichRuleApplies() {
        return tpsThresholdToWhichRuleApplies;
    }

    public int afkedTimeRequiredForKicks() {
        return afkedTimeRequiredForKicks;
    }

    public String startedApplyingRuleMessage() {
        return startedApplyingRuleMessage;
    }

    public String stoppedApplyingRuleMessage() {
        return stoppedApplyingRuleMessage;
    }

    public String kickedAFKerMessage() {
        return kickedAFKerMessage;
    }

}
