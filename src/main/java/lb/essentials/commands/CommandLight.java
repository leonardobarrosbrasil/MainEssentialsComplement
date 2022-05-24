package lb.essentials.commands;

import lb.essentials.commands.tab.CommandPlayerTab;
import lb.essentials.main.MainEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class CommandLight implements CommandExecutor {

    public CommandLight(MainEssentials essentials, String command) {
        Objects.requireNonNull(essentials.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(essentials.getCommand(command)).setTabCompleter(new CommandPlayerTab());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (args.length == 0) {
            args0(sender, args);
            return true;
        }
        if (args.length == 1) {
            args1(sender, args);
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return true;
    }

    private void args0(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVocê nâo pode usar este comando no console.");
            return;
        }
        Player player = (Player) sender;
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.sendMessage("§aVocê desativou sua lanterna.");
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        } else {
            player.sendMessage("§aVocê ativou sua lanterna.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0));
        }
    }

    private void args1(CommandSender sender, String[] args) {
        if (!sender.hasPermission("lb.admin.light")) {
            sender.sendMessage("§cVocê não tem permissão para fazer isto.");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return;
        }
        if (target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            sender.sendMessage("§aVocê desativou a lanterna de " + target.getName() + ".");
            target.removePotionEffect(PotionEffectType.NIGHT_VISION);
        } else {
            sender.sendMessage("§aVocê ativou a lanterna de " + target.getName() + ".");
            target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0));
        }
    }
}
