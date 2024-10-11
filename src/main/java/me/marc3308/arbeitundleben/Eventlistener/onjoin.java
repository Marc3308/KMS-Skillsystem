package me.marc3308.arbeitundleben.Eventlistener;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class onjoin implements Listener {

    @EventHandler
    public void onjoin(PlayerJoinEvent e){

        Player p=e.getPlayer();

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)){
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);
        }

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)){
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,5.0);
        }
    }

    @EventHandler
    public void onleave(PlayerQuitEvent e){

        Player p=e.getPlayer();

        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                && !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)){

            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);

            ItemStack mainhand=p.getInventory().getItemInMainHand();
            ItemStack offhand=p.getInventory().getItemInOffHand();
            ItemStack kopf=p.getInventory().getHelmet();
            ItemStack brust=p.getInventory().getChestplate();
            ItemStack Hose=p.getInventory().getLeggings();
            ItemStack shuhe=p.getInventory().getBoots();

            p.getInventory().setContents(utilitys.hashas.get(p));
            utilitys.hashas.remove(p);

            //new inventory for the hero
            p.getInventory().setHelmet(kopf);
            p.getInventory().setChestplate(brust);
            p.getInventory().setLeggings(Hose);
            p.getInventory().setBoots(shuhe);
            p.getInventory().setItemInOffHand(offhand);
            p.getInventory().setHeldItemSlot(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"handinfo"), PersistentDataType.INTEGER));

        }

    }
}
