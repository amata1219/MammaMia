package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.section.TemporaryBoatSection;
import amata1219.mamma.mia.reflect.APIPackage;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.joor.Reflect;

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
        Action action = event.getAction();
        if (event.getHand() != EquipmentSlot.HAND || !section.enabledOrNot() || event.isBlockInHand() || (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) || isBoat(event.getMaterial())) return;

        Player player = event.getPlayer();
        if (player.isInsideVehicle() || player.isSneaking() || isBoat(player.getInventory().getItemInOffHand().getType())) return;

        ImmutableSet<String> targetWorlds = section.targetWorlds();
        if (!(targetWorlds.contains(player.getWorld().getName()) || targetWorlds.contains("ALL"))) return;

        if (player.hasMetadata("race") || player.hasMetadata("race-team")) return;

        Block waterClicked = waterClicked(player.getWorld(), player);
        if (waterClicked == null && !(event.hasBlock() && event.getClickedBlock().getType().name().endsWith("ICE"))) return;

        Location spawnPoint = (waterClicked != null ? waterClicked : event.getClickedBlock()).getLocation().add(0.5, 1.0, 0.5);
        if (spawnPoint.getBlock().getType() != Material.AIR) return;

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

    private static boolean isBoat(Material type) {
        return type.name().endsWith("_BOAT");
    }

    private static Block waterClicked(World world, Player player) {
        Object nmsWorld = Reflect.on(world)
                .call("getHandle")
                .get();
        Object entityHuman = Reflect.on(player)
                .call("getHandle")
                .get();

        Object fluidCollisionOption = Reflect.onClass(APIPackage.NET_MINECRAFT_SERVER + "RayTrace$FluidCollisionOption")
                .get("ANY");

        Object blockClicked = Reflect.onClass(APIPackage.NET_MINECRAFT_SERVER + "Item")
                .call("a", nmsWorld, entityHuman, fluidCollisionOption)
                .get();

        Object clickedBlockType = Reflect.on(blockClicked)
                .call("getType")
                .get();
        Object movingObjectType = Reflect.onClass(APIPackage.NET_MINECRAFT_SERVER + "MovingObjectPosition$EnumMovingObjectType")
                .get("BLOCK");

        if (clickedBlockType != movingObjectType) return null;

        Object blockPosition = Reflect.on(blockClicked)
                .call("getBlockPosition")
                .get();

        boolean result = Reflect.on(nmsWorld)
                .call("a", entityHuman, blockPosition)
                .get();

        if (!result) return null;

        boolean isFluid = Reflect.on(nmsWorld)
                .call("getType", blockPosition)
                .call("getFluid")
                .call("isEmpty")
                .get();

        if (isFluid) return null;

        int x = Reflect.on(blockPosition)
                .call("getX")
                .get();
        int y = Reflect.on(blockPosition)
                .call("getY")
                .get();
        int z = Reflect.on(blockPosition)
                .call("getZ")
                .get();

        return world.getBlockAt(x, y, z);
    }

}
