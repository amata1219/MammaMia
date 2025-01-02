package amata1219.mamma.vehicles;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.EntityType;

public class BoatTypes {

    private static final Random RANDOM = new Random();
    public static List<EntityType> boatTypes = Arrays.asList(EntityType.OAK_BOAT, EntityType.SPRUCE_BOAT, EntityType.BIRCH_BOAT,
        EntityType.JUNGLE_BOAT, EntityType.ACACIA_BOAT, EntityType.DARK_OAK_BOAT, EntityType.MANGROVE_BOAT,
        EntityType.CHERRY_BOAT, EntityType.PALE_OAK_BOAT, EntityType.BAMBOO_RAFT);

    public static final EntityType getRandomTypeBoatEntityType() {
        int randomIndex = RANDOM.nextInt(boatTypes.size());
        return boatTypes.get(randomIndex);
    }
    
}
