package amata1219.mamma.mia.reflect;

import org.bukkit.Bukkit;

public class APIPackage {

    public static final String ORG_BUKKIT_CRAFTBUKKIT;
    public static final String NET_MINECRAFT_SERVER;

    static {
        String version = Bukkit.getServer()
                .getClass()
                .getPackage()
                .getName()
                .replaceFirst(".*(\\d+_\\d+_R\\d+).*", "$1");

        ORG_BUKKIT_CRAFTBUKKIT = "org.bukkit.craftbukkit.v" + version + ".";
        NET_MINECRAFT_SERVER = "net.minecraft.server.v" + version + ".";
    }

}
