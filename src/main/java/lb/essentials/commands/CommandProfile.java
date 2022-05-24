package lb.essentials.commands;

import lb.engines.main.MainEngines;
import lb.engines.utils.LBPlayer;
import lb.essentials.commands.tab.CommandPlayerTab;
import lb.essentials.main.MainEssentials;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class CommandProfile implements CommandExecutor {

    BukkitScheduler scheduler = Bukkit.getScheduler();

    public CommandProfile(MainEssentials essentials, String command) {
        Objects.requireNonNull(essentials.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(essentials.getCommand(command)).setTabCompleter(new CommandPlayerTab());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVocê nâo pode usar este comando no console.");
            return false;
        }
        if (args.length == 1) {
            args1(sender, args);
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return true;
    }

    private void args1(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (MainEngines.getPlugin().getManager().hasCache(target.getUniqueId())) {
            LBPlayer data = MainEngines.getPlugin().getManager().getCached(target.getUniqueId());
            scheduler.runTask(MainEssentials.getPlugin(), () -> player.openInventory(getInventory(target, data)));
        } else {
            if (!player.hasPermission("lb.admin.profile")) {
                player.sendMessage("§cVocê não tem permissão para fazer isto.");
                return;
            }
            try {
                LBPlayer data = MainEngines.getPlugin().getMySQL().getData(target.getUniqueId());
                scheduler.runTask(MainEssentials.getPlugin(), () -> player.openInventory(getInventory(target, data)));
            } catch (NullPointerException exception) {
                player.sendMessage("§cJogador não encontrado.");
            }
        }
    }

    public Inventory getInventory(OfflinePlayer player, LBPlayer data) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Perfil de " + player.getName());
        inventory.setItem(11, MainEssentials.getPlugin().getFunctions().createHead((byte) 1, player, "§7Informação de " + player.getName(), "§fSem descrição."));
        inventory.setItem(13, MainEssentials.getPlugin().getFunctions().createItem(Material.GOLD_BLOCK, (byte) 1, ChatColor.of("#FFA92D") + "Informações de economia", ChatColor.of("#cc8724") +"$ Coroas: §7" + data.getMoney() + " coroas"));
        inventory.setItem(14, MainEssentials.getPlugin().getFunctions().createItem(Material.DIAMOND_BLOCK, (byte) 1, ChatColor.of("#00ff8b") + "Informações de nível", ChatColor.of("#00cc6f") +"★ Nível: §7Nível " + data.getLevel(), ChatColor.of("#00cc6f") +"♦ Experiência: §7" + data.getExp() + " de experiência"));
        inventory.setItem(15, MainEssentials.getPlugin().getFunctions().createItem(Material.REDSTONE_BLOCK, (byte) 1, ChatColor.of("#E83737") + "Informações de combate", ChatColor.of("#ba2c2c") + "☠ Matou: §7" + data.getKills() + " jogadores", ChatColor.of("#ba2c2c") +"☠ Morreu: §7" + data.getDeaths() + " vezes", "", ChatColor.of("#ba2c2c") +"⚔ Combates vencidos: §7" +data.getFightWins() + " combates", ChatColor.of("#ba2c2c") +"⚔ Combates perdidos: §7" + data.getFightDefeats() + " combates"));
        return inventory;
    }
}
