package amata1219.mamma.mia;

import amata1219.mamma.mia.command.MamiyaCommand;
import amata1219.mamma.mia.config.MainConfig;
import amata1219.mamma.mia.listener.*;
import amata1219.mamma.mia.task.monitor.BoostingElytraTPSMonitor;
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

    private Map<String, CommandExecutor> commands;

    private final ArrayList<BukkitTask> runningTasks = new ArrayList<>();
    private KickAFKerListener kickAFKerListener;

    @Override
    public void onEnable() {
        instance = this;

        config = new MainConfig();
        config.saveDefaultConfig();

        commands = ImmutableMap.of(
                "mamiya", new MamiyaCommand()
        );

        KickingAFKerTPSMonitor kickingAFKerTPSMonitor = new KickingAFKerTPSMonitor();
        runningTasks.add(kickingAFKerTPSMonitor.runTaskTimer(this, 1200, config().kickingAFKerSection().messagingIntervals() * 20));

        kickAFKerListener = new KickAFKerListener(kickingAFKerTPSMonitor);
        getServer().getOnlinePlayers().forEach(kickAFKerListener::startMonitoring);

        BoostingElytraTPSMonitor boostingElytraTPSMonitor = new BoostingElytraTPSMonitor();
        runningTasks.add(boostingElytraTPSMonitor.runTaskTimer(this, 1200, config.elytraBoosterDisablerSection().messagingIntervals() * 20));

        registerEventListeners(
                kickAFKerListener,
                new TemporaryIceBoatListener(),
                new CancelBoostingElytraListener(boostingElytraTPSMonitor),
                new TemporaryMinecartListener(),
                new TemporaryWaterBoatListener()
        );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        getServer().getOnlinePlayers().forEach(kickAFKerListener::stopMonitoring);
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
