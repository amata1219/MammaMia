package amata1219.mamma.mia;

import amata1219.mamma.mia.command.MamiyaCommand;
import amata1219.mamma.mia.config.MainConfig;
import amata1219.mamma.mia.listener.KickAFKerListener;
import amata1219.mamma.mia.task.monitor.KickingAFKerTPSMonitor;
import com.google.common.collect.ImmutableMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Map;

public class MammaMia extends JavaPlugin {

    private static MammaMia instance;

    private MainConfig config;

    private final Map<String, CommandExecutor> commands = ImmutableMap.of(
            "mamiya", new MamiyaCommand()
    );

    private final ArrayList<BukkitTask> runningTasks = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        config = new MainConfig();
        config.saveDefaultConfig();

        KickingAFKerTPSMonitor kickingAFKerTPSMonitor = new KickingAFKerTPSMonitor();
        runningTasks.add(kickingAFKerTPSMonitor.runTaskTimer(this, 1200, config().kickingAFKerSection().messagingIntervals() * 20));


        registerEventListeners(
                new KickAFKerListener(kickingAFKerTPSMonitor)
        );
        /*
        int intervals = plugin.config().getInt("Kicking AFKer.Messaging intervals");
		return observer.runTaskTimer(plugin, 1200, intervals * 20);
         */
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        runningTasks.forEach(BukkitTask::cancel);
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

    private void registerEventListeners(Listener... listeners) {
        for (Listener listener : listeners) getServer().getPluginManager().registerEvents(listener, this);
    }

}
