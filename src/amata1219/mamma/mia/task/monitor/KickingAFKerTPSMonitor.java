package amata1219.mamma.mia.task.monitor;

public class KickingAFKerTPSMonitor extends TPSMonitor {

    @Override
    protected int tpsThreshold() {
        return config.kickingAFKerSection().tpsThresholdToWhichRuleApplies();
    }

    @Override
    protected String startMessage() {
        return config.kickingAFKerSection().startedApplyingRuleMessage();
    }

    @Override
    protected String endMessage() {
        return config.kickingAFKerSection().stoppedApplyingRuleMessage();
    }

}
