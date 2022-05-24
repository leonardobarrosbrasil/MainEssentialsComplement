package lb.essentials.events;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class OnPortalEvent implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equalsIgnoreCase("serverBuilds")) {
            player.sendMessage("§e[l] §fDefensor infernal: §eO deus da guerra não permite intrusos.");
            player.getLocation().getBlock().setType(Material.AIR);
            Piglin piglin = (Piglin) player.getWorld().spawnEntity(player.getLocation(), EntityType.PIGLIN);
            piglin.setCanPickupItems(false);
            Objects.requireNonNull(piglin.getEquipment()).setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
            piglin.setImmuneToZombification(true);
            piglin.setTarget(player);
            piglin.setAdult();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPigZombieDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Piglin)) return;
        if (entity.getWorld().getName().equalsIgnoreCase("world2")) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
}
