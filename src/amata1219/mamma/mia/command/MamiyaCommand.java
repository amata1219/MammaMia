package amata1219.mamma.mia.command;

import amata1219.mamma.mia.MammaMia;
import amata1219.mamma.mia.bryionake.dsl.BukkitCommandExecutor;
import amata1219.mamma.mia.bryionake.dsl.context.CommandContext;
import amata1219.mamma.mia.sound.SoundEffects;
import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class MamiyaCommand implements BukkitCommandExecutor {

    private final CommandContext<CommandSender> executor;

    {
        MammaMia plugin = MammaMia.instance();

        CommandContext<CommandSender> reload = (sender, unparsedArguments, parsedArguments) -> {
            plugin.config().reloadConfig();
            sender.sendMessage(ChatColor.AQUA + "コンフィグの再読み込みを行いました。");
            if (sender instanceof Player) SoundEffects.SUCCEEDED.play((Player) sender);
        };

        PluginDescriptionFile description = plugin.getDescription();

        executor = define(
                () -> Joiner.on('\n').join(
                        ChatColor.AQUA + "MAMMA-MIA INFORMATION",
                        ChatColor.AQUA + "    PLUGIN VERSION -> " + ChatColor.WHITE + description.getVersion(),
                        ChatColor.AQUA + "  SPIGOT VERSION -> " + ChatColor.WHITE + description.getAPIVersion(),
                        ChatColor.AQUA + "  COMMAND -> " + ChatColor.WHITE + "/mamiya reload - コンフィグを再読み込みします。"
                ),
                bind("reload", reload)
        );

    }

    @Override
    public CommandContext<CommandSender> executor() {
        return executor;
    }

}
