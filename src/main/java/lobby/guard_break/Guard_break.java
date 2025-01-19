package lobby.guard_break;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.stream.events.Namespace;

public final class Guard_break extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
   /* @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        event.getPlayer().sendMessage("interact Event 1");
        ItemStack item = event.getItem();
        if(item != null && item.hasItemMeta()){
            event.getPlayer().sendMessage("interact Event 2");
            ItemMeta meta = item.getItemMeta();
            NamespacedKey key = new NamespacedKey(this,"guardBreak");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER,200);
            item.setItemMeta(meta);
            event.getPlayer().sendMessage("guardbreakが追加されました");
        }
    }
   */
    //give Xx_megante_xX minecraft:diamond_sword[minecraft:custom_data={PublicBukkitValues:{"guard_break:guardbreak": 100}}] 100の部分を変更することで武器の盾の破壊できる時間を変更できる





    @EventHandler
    public void onEntityDamegeByEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            ItemStack weapon = attacker.getInventory().getItemInMainHand();

            if(weapon != null && weapon.hasItemMeta());
            ItemMeta meta = weapon.getItemMeta();
            NamespacedKey key = new NamespacedKey(this,"guardBreak");

            if(meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER) && victim.isBlocking()){
                int guardBreak = meta.getPersistentDataContainer().get(key,PersistentDataType.INTEGER);
                victim.setCooldown(Material.SHIELD,guardBreak);


                if(victim.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
                        ItemStack shild = victim.getInventory().getItemInOffHand();
                        victim.getInventory().setItemInOffHand(null);
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                victim.getInventory().setItemInOffHand(shild);
                            }
                        }.runTaskLater(this,5L);
                }
                if(victim.getInventory().getItemInMainHand().getType() == Material.SHIELD) {
                    ItemStack shild = victim.getInventory().getItemInMainHand();
                    victim.getInventory().setItemInMainHand(null);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            victim.getInventory().setItemInMainHand(shild);
                        }
                    }.runTaskLater(this,5L);
                }
            }
        }
    }
}
