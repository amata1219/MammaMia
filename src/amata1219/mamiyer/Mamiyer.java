package amata1219.mamiyer;

import com.google.common.collect.ImmutableMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class Mamiyer extends JavaPlugin {

    private static Mamiyer instance;

    private final Map<String, CommandExecutor> commands = ImmutableMap.of();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commands.get(label).onCommand(sender, command, label, args);
    }

}
