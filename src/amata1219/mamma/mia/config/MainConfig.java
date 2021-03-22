package amata1219.mamma.mia.config;

import amata1219.mamma.mia.config.section.*;

public class MainConfig extends Config {

    private ElytraBoosterDisablerSection elytraBoosterDisablerSection;
    private TemporaryIceBoatSection temporaryIceBoatSection;
    private KickingAFKerSection kickingAFKerSection;
    private TemporaryMinecartSection temporaryMinecartSection;
    private TemporaryWaterBoatSection temporaryWaterBoatSection;

    public MainConfig() {
        super("config.yml");
    }

    @Override
    public void loadSections() {
        elytraBoosterDisablerSection = new ElytraBoosterDisablerSection(this);
        temporaryIceBoatSection = new TemporaryIceBoatSection(this);
        kickingAFKerSection = new KickingAFKerSection(this);
        temporaryMinecartSection = new TemporaryMinecartSection(this);
        temporaryWaterBoatSection = new TemporaryWaterBoatSection(this);
    }

    public ElytraBoosterDisablerSection elytraBoosterDisablerSection() {
        return elytraBoosterDisablerSection;
    }

    public TemporaryIceBoatSection temporaryIceBoatSection() {
        return temporaryIceBoatSection;
    }

    public KickingAFKerSection kickingAFKerSection() {
        return kickingAFKerSection;
    }

    public TemporaryMinecartSection temporaryMinecartSection() {
        return temporaryMinecartSection;
    }

    public TemporaryWaterBoatSection temporaryWaterBoatSection() {
        return temporaryWaterBoatSection;
    }

}
