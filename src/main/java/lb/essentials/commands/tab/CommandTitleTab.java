package lb.essentials.commands.tab;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandTitleTab implements TabCompleter {

    private final List<String> args1 = Collections.singletonList("send");

    private final ArrayList<String> sounds = new ArrayList<>();
    private final ArrayList<String> players = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
        final List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return completions;
        }
        switch (args.length) {
            case 1:
                StringUtil.copyPartialMatches(args[0], args1, completions);
                break;
            case 2:
                players.clear();
                players.add("all");
                Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
                StringUtil.copyPartialMatches(args[1], players, completions);
                break;
            case 3:
                sounds.clear();
                for (Sound sound : Sound.values()) {
                    sounds.add(sound.toString());
                }
                StringUtil.copyPartialMatches(args[2], sounds, completions);
                break;
        }
        Collections.sort(completions);
        return completions;
    }
}