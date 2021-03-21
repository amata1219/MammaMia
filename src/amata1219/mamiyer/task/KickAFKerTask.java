package amata1219.mamiyer.task;

import amata1219.mamiyer.Mamiyer;
import amata1219.mamiyer.config.MainConfig;
import amata1219.mamiyer.config.section.KickingAFKerSection;
import org.bukkit.entity.Player;

public class KickAFKerTask implements Runnable {

    private final MainConfig config = Mamiyer.instance().config();

    private final Player monitoredPlayer;
    private int idleMinutes;

    public KickAFKerTask(Player player) {
        this.monitoredPlayer = player;
    }

    @Override
    public void run() {
        KickingAFKerSection section = config.kickingAFKerSection();
        if (++idleMinutes >= section.afkedTimeRequiredForKicks()) monitoredPlayer.kickPlayer(section.kickedAFKerMessage());
    }

}
