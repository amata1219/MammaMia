package amata1219.mamiyer.config;

import amata1219.mamiyer.config.section.ElytraBoosterDisablerSection;
import amata1219.mamiyer.config.section.KickingAFKerSection;
import amata1219.mamiyer.config.section.TemporaryBoatSection;
import amata1219.mamiyer.config.section.TemporaryMinecartSection;

public class MainConfig extends Config {

    private ElytraBoosterDisablerSection elytraBoosterDisablerSection;
    private TemporaryBoatSection temporaryBoatSection;
    private KickingAFKerSection kickingAFKerSection;
    private TemporaryMinecartSection temporaryMinecartSection;

    public MainConfig() {
        super("config.yml");
    }

    @Override
    public void loadSections() {
        elytraBoosterDisablerSection = new ElytraBoosterDisablerSection(this);
        temporaryBoatSection = new TemporaryBoatSection(this);
        kickingAFKerSection = new KickingAFKerSection(this);
        temporaryMinecartSection = new TemporaryMinecartSection(this);
    }

    public ElytraBoosterDisablerSection elytraBoosterDisablerSection() {
        return elytraBoosterDisablerSection;
    }

    public TemporaryBoatSection temporaryBoatSection() {
        return temporaryBoatSection;
    }

    public KickingAFKerSection kickingAFKerSection() {
        return kickingAFKerSection;
    }

    public TemporaryMinecartSection temporaryMinecartSection() {
        return temporaryMinecartSection;
    }

}
