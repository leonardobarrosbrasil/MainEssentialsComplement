package lb.essentials.commands;

import lb.essentials.commands.tab.CommandTitleTab;
import lb.essentials.main.MainEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandTitle implements CommandExecutor {

    public CommandTitle(MainEssentials essentials, String command) {
        Objects.requireNonNull(essentials.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(essentials.getCommand(command)).setTabCompleter(new CommandTitleTab());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (args.length == 0) {
            //
            return true;
        }
        if (args.length >= 2) {
            args2(sender, args);
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return false;
    }

    private void args2(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayerExact(args[1]);
        switch (args[0]) {
            case "send":
                if (!args[1].equalsIgnoreCase("all") && target == null) {
                    sender.sendMessage("§cJogador não encontrado.");
                    return;
                }
                if (!isSound(args[2])) {
                    sender.sendMessage("§cSom inválido.");
                    return;
                }
                Sound sound = Sound.valueOf(args[2]);
                boolean bool = args[1].equalsIgnoreCase("all");
                String title = MainEssentials.getPlugin().getFunctions().hex(args[3]);
                String subtitle = MainEssentials.getPlugin().getFunctions().hex(getArgs(4, args));
                if (bool) {
                    Bukkit.getOnlinePlayers().forEach(players -> {
                        players.playSound(players.getLocation(), sound, 1.0F, 1.0F);
                        players.sendTitle(title, subtitle, 10, 30, 10);
                    });
                    sender.sendMessage("§aVocê enviou um titulo para todos os jogadores.");
                } else {
                    assert target != null;
                    target.playSound(target.getLocation(), sound, 1.0F, 1.0F);
                    target.sendTitle(title, subtitle, 10, 30, 10);
                    sender.sendMessage("§aVocê enviou um titulo para " + target.getName() + ".");
                }
                break;
            default:
                sender.sendMessage("§cArgumentos inválidos.");
                break;
        }
    }

    public String getArgs(int value, String[] args) {
        StringBuilder message = new StringBuilder();
        for (int i = 4; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }
        return message.toString().trim();
    }

    public boolean isSound(String string) {
        try {
            Sound sound = Sound.valueOf(string);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
