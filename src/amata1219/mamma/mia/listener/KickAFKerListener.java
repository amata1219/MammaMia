package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.MainConfig;
import amata1219.mamma.mia.config.section.KickingAFKerSection;
import amata1219.mamma.mia.task.KickAFKerTask;
import amata1219.mamma.mia.task.monitor.KickingAFKerTPSMonitor;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class KickAFKerListener implements Listener {

    private final MammaMia plugin = MammaMia.instance();
    private final MainConfig config = plugin.config();

    private final KickingAFKerTPSMonitor activatedKickingAFKerTPSMonitor;
    private final HashMap<Player, BukkitTask> playersToMonitors = new HashMap<>();

    public KickAFKerListener(KickingAFKerTPSMonitor activatedKickingAFKerTPSMonitor) {
        this.activatedKickingAFKerTPSMonitor = activatedKickingAFKerTPSMonitor;
    }

    @EventHandler
    public void on(AfkStatusChangeEvent event) {
        KickingAFKerSection section = config.kickingAFKerSection();
        if (!section.enabledOrNot() || !(section.appliedOrNotRegardlessOfTPS() || activatedKickingAFKerTPSMonitor.isAtLowTPS())) return;

        Player player = event.getAffected().getBase();
        if (event.getValue()) playersToMonitors.put(player, new KickAFKerTask(player).runTaskTimer(plugin, 1200, 1200));
        else stopMonitoring(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        stopMonitoring(event.getPlayer());
    }

    private void stopMonitoring(Player player) {
        playersToMonitors.remove(player).cancel();
    }

}
