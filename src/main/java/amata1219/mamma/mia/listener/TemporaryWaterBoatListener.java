package amata1219.mamma.mia.listener;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.section.TemporaryIceBoatSection;
import amata1219.mamma.mia.config.section.TemporaryWaterBoatSection;
import amata1219.mamma.mia.reflect.APIPackage;
import amata1219.mamma.mia.vehicles.BoatTypes;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.joor.Reflect;

public class TemporaryWaterBoatListener  implements Listener {

    private static final String TEMPORARY_BOAT_METADATA = "mamma-mia-temp-boat";

    private final MammaMia plugin = MammaMia.instance();

    @EventHandler(ignoreCancelled = true)
    public void on(PlayerSwapHandItemsEvent event) {
        TemporaryWaterBoatSection section = plugin.config().temporaryWaterBoatSection();
        if (!section.enabledOrNot()) return;

        Player player = event.getPlayer();
        if (player.isInsideVehicle() || player.isSneaking()) return;

        ImmutableSet<String> targetWorlds = section.targetWorlds();
        if (!(targetWorlds.contains(player.getWorld().getName()) || targetWorlds.contains("ALL"))) return;

        if (player.hasMetadata("race") || player.hasMetadata("race-team")) return;

        Block waterClicked = waterClicked(player.getWorld(), player);
        if (waterClicked == null) return;

        Location spawnPoint = waterClicked.getLocation().add(0.5, 1.0, 0.5);
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