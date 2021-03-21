package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.section.TemporaryBoatSection;
import amata1219.mamma.mia.config.section.TemporaryMinecartSection;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class TemporaryMinecartListener implements Listener {

    private static final String TEMPORARY_MINECART_METADATA = "mamma-mia-temp-minecart";

    private final MammaMia plugin = MammaMia.instance();

    @EventHandler
    public void on(PlayerInteractEvent event) {
        TemporaryBoatSection section = plugin.config().temporaryBoatSection();
        if (!section.enabledOrNot() || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.isBlockInHand()) return;

        Player player = event.getPlayer();
        if (player.isInsideVehicle() || player.isSneaking()) return;

        Block block = event.getClickedBlock();
        if (!block.getType().name().endsWith("RAIL") || event.getMaterial() == Material.MINECART) return;

        ImmutableSet<String> targetWorlds = section.targetWorlds();
        if (!(targetWorlds.contains(player.getWorld().getName()) || targetWorlds.contains("ALL"))) return;

        Location spawnPoint = block.getLocation().add(0.5, 0, 0.5);
        Entity minecart = player.getWorld().spawnEntity(spawnPoint, EntityType.MINECART);
        minecart.setMetadata(TEMPORARY_MINECART_METADATA, new FixedMetadataValue(plugin, true));
        minecart.addPassenger(player);

        event.setCancelled(true);
    }

    @EventHandler
    public void on(VehicleExitEvent event) {
        TemporaryMinecartSection section = plugin.config().temporaryMinecartSection();
        if (!section.enabledOrNot()) return;

        Vehicle vehicle = event.getVehicle();
        if (!(vehicle instanceof Minecart)) return;

        if (vehicle.hasMetadata(TEMPORARY_MINECART_METADATA)) vehicle.remove();
    }

}
