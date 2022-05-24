package lb.essentials.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.inventory.InventoryView;

import java.util.Arrays;
import java.util.List;

public class OnSendCommandEvent implements Listener {

    private final List<String> questCommands = Arrays.asList("quests take", "qs take");
    private final List<String> blockedCommands = Arrays.asList("quests list", "quests stats", "qs list", "qs stats", "bukkit:version", "bukkit:?", "bukkit:about", "bukkit:help", "bukkit:pl", "bukkit:plugins", "bukkit:reload", "bukkit:rl", "bukkit:timings", "bukkit:ver", "bukkit:version", "?", "about", "help", "pl", "plugins", "reload", "rl", "timings", "ver", "version", "minecraft:me", "me");
    private final List<String> appearCommands = Arrays.asList("discord", "trash", "enderchest", "explore", "spawn", "clan", "tpa", "home", "combat", "lantern", "site", "tpaccept", "tpdeny", "sethome", "home", "delhome", "g", "l", "global", "local", "tell", "ally", "c", "pay", "balancetop");

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        for (String commands : blockedCommands) {
            if (event.getMessage().contains("/" + commands)) {
                player.sendMessage("§cComando não encontrado.");
                event.setCancelled(true);
            }
        }
        for (String commands : questCommands) {
            if (event.getMessage().contains("" + commands)) {
                InventoryView inventoryView = player.getOpenInventory();
                if (!inventoryView.getTitle().startsWith("Missões")) {
                    player.sendMessage("§cComando não encontrado.");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTab(PlayerCommandSendEvent event) {
        if (event.getPlayer().hasPermission("lb.admin.hidedCommands")) return;
        event.getCommands().removeIf(string -> !appearCommands.contains(string));
    }
}
