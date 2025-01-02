package amata1219.mamma.mia.task.monitor;

public class BoostingElytraTPSMonitor extends TPSMonitor {

    @Override
    protected int tpsThreshold() {
        return config.elytraBoosterDisablerSection().tpsThresholdToWhichRestrictionApplied();
    }

    @Override
    protected String startMessage() {
        return config.elytraBoosterDisablerSection().startedApplyingRestrictionMessage();
    }

    @Override
    protected String endMessage() {
        return config.elytraBoosterDisablerSection().stoppedApplyingRestrictionMessage();
    }

}
