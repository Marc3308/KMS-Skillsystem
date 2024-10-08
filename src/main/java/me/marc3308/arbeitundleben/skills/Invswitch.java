package me.marc3308.arbeitundleben.skills;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Invswitch implements Listener {


    @EventHandler
    public void onswitch(PlayerItemHeldEvent e){
        Player p=e.getPlayer();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)
                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return;
        e.setCancelled(true);



        String a="skillslot"+(e.getNewSlot()+1);
        if(!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",a), PersistentDataType.STRING))return;
        String name=p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",a), PersistentDataType.STRING);


        //set eferithing up
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING,name);

        p.getInventory().setContents(utilitys.hashas.get(p));
        p.getInventory().setHeldItemSlot(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"handinfo"), PersistentDataType.INTEGER));

        //barstyles
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING,utilitys.getcon(1).getString(name+".AnzeigeName"));
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barcolor"), PersistentDataType.STRING,"RED");
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barprogress"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barstyle"), PersistentDataType.STRING,"SEGMENTED_10");

        utilitys.activateskills(p,name);

    }

    @EventHandler
    public void oninv(InventoryClickEvent e){
        Player p=(Player) e.getWhoClicked();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)
                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onaufsammeln(PlayerPickupItemEvent e){
        Player p=e.getPlayer();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)
                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return;
        e.setCancelled(true);
    }

    @EventHandler
    public void ondrop(PlayerDropItemEvent e){
        Player p=e.getPlayer();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)
                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onplace(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)
                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return;
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        e.setCancelled(true);
    }

    @EventHandler
    public void ondeath(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))return;
        Player p=(Player) e.getEntity();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return; //todo test if goes
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)
                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN))return;
        if(p.getHealth()-e.getFinalDamage()>0)return;
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);

        ItemStack mainhand=p.getInventory().getItemInMainHand();
        ItemStack offhand=p.getInventory().getItemInOffHand();
        ItemStack kopf=p.getInventory().getHelmet();
        ItemStack brust=p.getInventory().getChestplate();
        ItemStack Hose=p.getInventory().getLeggings();
        ItemStack shuhe=p.getInventory().getBoots();

        p.getInventory().setContents(utilitys.hashas.get(p));

        //new inventory for he hero
        p.getInventory().setHelmet(kopf);
        p.getInventory().setChestplate(brust);
        p.getInventory().setLeggings(Hose);
        p.getInventory().setBoots(shuhe);
        p.getInventory().setItemInOffHand(offhand);

        for(int i=0;i<9;i++){
            if(mainhand.getType().equals(Material.AIR)){
                p.getInventory().setHeldItemSlot(p.getInventory().firstEmpty());
                return;
            }
            if(p.getInventory().getItem(i)!=null && p.getInventory().getItem(i).equals(mainhand)){
                p.getInventory().setHeldItemSlot(i);
                break;
            }
        }

    }


}
