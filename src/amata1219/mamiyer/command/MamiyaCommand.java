package amata1219.mamiyer.command;

import amata1219.mamiyer.Mamiyer;
import amata1219.mamiyer.bryionake.dsl.BukkitCommandExecutor;
import amata1219.mamiyer.bryionake.dsl.context.CommandContext;
import amata1219.mamiyer.sound.SoundEffects;
import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class MamiyaCommand implements BukkitCommandExecutor {

    private final CommandContext<CommandSender> executor;

    {
        Mamiyer plugin = Mamiyer.instance();

        CommandContext<CommandSender> reload = (sender, unparsedArguments, parsedArguments) -> {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.AQUA + "コンフィグの再読み込みを行いました。");
            if (sender instanceof Player) SoundEffects.SUCCEEDED.play((Player) sender);
        };

        PluginDescriptionFile description = plugin.getDescription();

        executor = define(
                () -> Joiner.on('\n').join(
                        ChatColor.AQUA + "MAMIYER INFORMATION",
                        ChatColor.GRAY + "　PLUGIN VERSION -> " + ChatColor.AQUA + description.getVersion(),
                        ChatColor.GRAY + "　SPIGOT VERSION -> " + ChatColor.AQUA + description.getAPIVersion()
                ),
                bind("reload", reload)
        );

    }

    @Override
    public CommandContext<CommandSender> executor() {
        return executor;
    }

}
