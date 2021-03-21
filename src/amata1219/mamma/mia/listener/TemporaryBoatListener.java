package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.section.TemporaryBoatSection;
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
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class TemporaryBoatListener implements Listener {

    private static final Random RANDOM = new Random();
    private static final String TEMPORARY_BOAT_METADATA = "mamma-mia-temp-boat";
    private static final List<TreeSpecies> BOAT_MATERIAL_TYPES = Arrays.asList(TreeSpecies.values());

    private final MammaMia plugin = MammaMia.instance();

    @EventHandler
    public void on(PlayerInteractEvent event) {
        TemporaryBoatSection section = plugin.config().temporaryBoatSection();
        if (!section.enabledOrNot() || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.isBlockInHand()) return;

        Player player = event.getPlayer();
        if (player.isInsideVehicle() || player.isSneaking() || player.hasMetadata("race") || player.hasMetadata("race-team")) return;

        Block block = event.getClickedBlock();
        if (!block.getType().name().endsWith("ICE")) return;

        if (event.getMaterial().name().endsWith("_BOAT")) return;

        ImmutableSet<String> targetWorlds = section.targetWorlds();
        if (!(targetWorlds.contains(player.getWorld().getName()) || targetWorlds.contains("ALL"))) return;

        Location spawnPoint = block.getLocation().add(0.5, 1, 0.5);
        spawnPoint.setYaw(player.getLocation().getYaw());

        Boat boat = (Boat) player.getWorld().spawnEntity(spawnPoint, EntityType.BOAT);
        boat.setMetadata(TEMPORARY_BOAT_METADATA, new FixedMetadataValue(plugin, true));
        boat.setWoodType(BOAT_MATERIAL_TYPES.get(RANDOM.nextInt(BOAT_MATERIAL_TYPES.size())));
        boat.addPassenger(player);

        event.setCancelled(true);
    }

    @EventHandler
    public void on(VehicleExitEvent event) {
        TemporaryBoatSection section = plugin.config().temporaryBoatSection();
        if (!section.enabledOrNot()) return;

        Vehicle vehicle = event.getVehicle();
        if (!(vehicle instanceof Boat)) return;

        if (vehicle.hasMetadata(TEMPORARY_BOAT_METADATA)) vehicle.remove();
    }

}
