package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.section.TemporaryIceBoatSection;
import amata1219.mamma.vehicles.BoatTypes;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class TemporaryIceBoatListener implements Listener {

    private static final Random RANDOM = new Random();
    private static final String TEMPORARY_BOAT_METADATA = "mamma-mia-temp-boat";

    private final MammaMia plugin = MammaMia.instance();

    @EventHandler
    public void on(PlayerInteractEvent event) {
        TemporaryIceBoatSection section = plugin.config().temporaryIceBoatSection();
        Action action = event.getAction();
        if (event.getHand() != EquipmentSlot.HAND || !section.enabledOrNot() || event.isBlockInHand() || (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) || isBoat(event.getMaterial())) return;

        Player player = event.getPlayer();
        if (player.isInsideVehicle() || player.isSneaking() || isBoat(player.getInventory().getItemInOffHand().getType())) return;

        ImmutableSet<String> targetWorlds = section.targetWorlds();
        if (!(targetWorlds.contains(player.getWorld().getName()) || targetWorlds.contains("ALL"))) return;

        if (player.hasMetadata("race") || player.hasMetadata("race-team")) return;

        Block blockClicked = event.getClickedBlock();
        if (blockClicked == null || !blockClicked.getType().name().endsWith("ICE")) return;

        Location spawnPoint = blockClicked.getLocation().add(0.5, 1.0, 0.5);
        if (spawnPoint.getBlock().getType() != Material.AIR) return;

        spawnPoint.setYaw(player.getLocation().getYaw());

        Boat boat = (Boat) player.getWorld().spawnEntity(spawnPoint, BoatTypes.getRandomTypeBoatEntityType());
        boat.setMetadata(TEMPORARY_BOAT_METADATA, new FixedMetadataValue(plugin, true));
        boat.addPassenger(player);

        event.setCancelled(true);
    }

    @EventHandler
    public void on(VehicleExitEvent event) {
        TemporaryIceBoatSection section = plugin.config().temporaryIceBoatSection();
        if (!section.enabledOrNot()) return;

        Vehicle vehicle = event.getVehicle();
        if (!(vehicle instanceof Boat)) return;

        if (vehicle.hasMetadata(TEMPORARY_BOAT_METADATA)) vehicle.remove();
    }

    private static boolean isBoat(Material type) {
        return type.name().endsWith("_BOAT");
    }

}
