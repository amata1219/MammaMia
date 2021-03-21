package amata1219.mamma.mia.task.monitor;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.config.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;

public abstract class TPSMonitor implements Runnable {

    private static final double[] RECENT_TPS;

    static {
        Server server = Bukkit.getServer();
        String version = server.getClass().getPackage().getName().replaceFirst(".*(\\d+_\\d+_R\\d+).*", "$1");

        Class<?> CraftServer = null;
        try {
            CraftServer = Class.forName("org.bukkit.craftbukkit.v" + version + "." + "CraftServer");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object craftServer = CraftServer.cast(server);

        Field console = null;
        try {
            console = CraftServer.getDeclaredField("console");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        console.setAccessible(true);

        Object consoleServer = null;
        try {
            consoleServer = console.get(craftServer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Class<?> MinecraftServer = null;
        try {
            MinecraftServer = Class.forName("net.minecraft.server.v" + version + "." + "MinecraftServer");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object minecraftServer = MinecraftServer.cast(consoleServer);

        Field field = null;
        try {
            field = MinecraftServer.getField("recentTps");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);

        double[] recentTps = null;
        try {
            recentTps = (double[]) field.get(minecraftServer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        RECENT_TPS = recentTps;
    }

    protected final MainConfig config = MammaMia.instance().config();
    private boolean isAtLowTPS;

    @Override
    public void run() {
        if (isAtLowTPS) {
            if (RECENT_TPS[0] > tpsThreshold()) {
                Bukkit.broadcastMessage(endMessage());
                isAtLowTPS = false;
            }
        } else {
            if (RECENT_TPS[0] <= tpsThreshold()) {
                Bukkit.broadcastMessage(startMessage());
                isAtLowTPS = true;
            }
        }
    }

	public boolean isAtLowTPS() {
        return isAtLowTPS;
    }

    protected abstract int tpsThreshold();

    protected abstract String startMessage();

    protected abstract String endMessage();

}
