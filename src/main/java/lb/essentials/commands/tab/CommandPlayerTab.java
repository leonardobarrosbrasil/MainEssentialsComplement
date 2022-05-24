package lb.essentials.commands.tab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandPlayerTab implements TabCompleter {

    private final ArrayList<String> players = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
        final List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return completions;
        }
        if (args.length == 1) {
            players.clear();
            Bukkit.getOnlinePlayers().forEach(p -> {
                players.add(p.getName());
            });
            StringUtil.copyPartialMatches(args[0], players, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}