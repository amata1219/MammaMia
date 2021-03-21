package amata1219.mamma.mia.task;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.MainConfig;
import amata1219.mamma.mia.config.section.KickingAFKerSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KickAFKerTask extends BukkitRunnable {

    private final MainConfig config = MammaMia.instance().config();

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
