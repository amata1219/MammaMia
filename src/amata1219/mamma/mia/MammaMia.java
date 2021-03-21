package amata1219.mamma.mia;

import amata1219.mamma.mia.command.MamiyaCommand;
import amata1219.mamma.mia.config.MainConfig;
import com.google.common.collect.ImmutableMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class MammaMia extends JavaPlugin {

    private static MammaMia instance;

    private MainConfig config;

    private final Map<String, CommandExecutor> commands = ImmutableMap.of(
            "mamiya", new MamiyaCommand()
    );

    @Override
    public void onEnable() {
        instance = this;

        config = new MainConfig();
        config.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commands.get(label).onCommand(sender, command, label, args);
    }

    public static MammaMia instance() {
        return instance;
    }

    public MainConfig config() {
        return config;
    }

}
