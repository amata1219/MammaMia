package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.section.ElytraBoosterDisablerSection;
import amata1219.mamma.mia.sound.SoundEffects;
import amata1219.mamma.mia.task.monitor.BoostingElytraTPSMonitor;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CancelBoostingElytraListener implements Listener {

    private final MammaMia plugin = MammaMia.instance();

    private final BoostingElytraTPSMonitor activatedBoostingElytraTPSMonitor;

    public CancelBoostingElytraListener(BoostingElytraTPSMonitor activatedBoostingElytraTPSMonitor) {
        this.activatedBoostingElytraTPSMonitor = activatedBoostingElytraTPSMonitor;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ElytraBoosterDisablerSection section = plugin.config().elytraBoosterDisablerSection();
        if (!section.enabledOrNot()) return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        if (!player.isGliding()) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.FIREWORK_ROCKET) return;

        if (!(section.appliedOrNotRegardlessOfTPS() || activatedBoostingElytraTPSMonitor.isAtLowTPS())) return;

        ImmutableSet<String> targetWorlds = section.targetWorlds();
        if (!(targetWorlds.contains(player.getWorld().getName()) || targetWorlds.contains("ALL"))) return;

        player.sendMessage(section.blocedElytraBoostingMessage());
        SoundEffects.FAILED.play(player);

        event.setCancelled(true);
    }

}
