package lb.essentials.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class OnCraftItemEvent implements Listener {

    private final List<Material> items = Arrays.asList(Material.ARMOR_STAND, Material.SHULKER_BOX, Material.TNT, Material.TNT_MINECART, Material.RAIL, Material.MINECART, Material.TRIPWIRE_HOOK, Material.COMPARATOR, Material.REPEATER, Material.LEVER, Material.REDSTONE_TORCH, Material.PISTON, Material.STICKY_PISTON, Material.TRIPWIRE_HOOK, Material.DROPPER, Material.DISPENSER, Material.HOPPER, Material.REDSTONE_BLOCK, Material.REDSTONE_WIRE);

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (item == null) return;
        for (Material material : items) {
            if (item.getType().equals(material)) {
                event.setCancelled(true);
                player.sendMessage("§cA criação desse item foi proibida.");
                player.closeInventory();
            }
        }
    }
}
