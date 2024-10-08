package me.marc3308.arbeitundleben.Eventlistener;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class scorbordtoggle implements Listener {

    @EventHandler
    public void ondruck(PlayerSwapHandItemsEvent e){
        Player p=(Player) e.getPlayer();
        e.setCancelled(true);


        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                || p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
            return;
        }

        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)){
            if(!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",("skillslot"+1)), PersistentDataType.STRING))return;

            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,true);
            p.setScoreboard(utilitys.getBasebord(p));

            //chanched to standart
            if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)){
                utilitys.ramenkranz(p);
                return;
            }

            utilitys.hashas.put(p,p.getInventory().getContents());

            ItemStack mainhand=p.getInventory().getItemInMainHand();
            ItemStack offhand=p.getInventory().getItemInOffHand();
            ItemStack kopf=p.getInventory().getHelmet();
            ItemStack brust=p.getInventory().getChestplate();
            ItemStack Hose=p.getInventory().getLeggings();
            ItemStack shuhe=p.getInventory().getBoots();
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"handinfo"), PersistentDataType.INTEGER,p.getInventory().getHeldItemSlot());

            //new inventory for he hero
            p.getInventory().clear();
            p.getInventory().setHeldItemSlot(8);
            p.getInventory().setItem(8,mainhand);
            p.getInventory().setHelmet(kopf);
            p.getInventory().setChestplate(brust);
            p.getInventory().setLeggings(Hose);
            p.getInventory().setBoots(shuhe);
            p.getInventory().setItemInOffHand(offhand);

            //check if he has even a skill
            for (int j=0;j<=p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "howmannyskillslots"), PersistentDataType.INTEGER);j++){
                if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",("skillslot"+j) ), PersistentDataType.STRING)){
                    String skk=p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",("skillslot"+j) ), PersistentDataType.STRING);

                    ItemStack skill=new ItemStack(Material.valueOf(utilitys.getcon(1).getString(skk+".Block")));
                    ItemMeta skill_meta= skill.getItemMeta();
                    skill_meta.setCustomModelData(utilitys.getcon(1).getInt(skk+".Custemmoddeldatataken"));
                    ArrayList<String> skill_lore=new ArrayList<>();
                    skill_lore.add(utilitys.getcon(1).getString(skk+".Beschreibung"));
                    skill_lore.add(" ");
                    skill_lore.add(ChatColor.YELLOW+"CLICK to equipp");
                    skill_meta.setDisplayName(utilitys.getcon(1).getString(skk+".AnzeigeName"));
                    skill_meta.setLore(skill_lore);
                    skill.setItemMeta(skill_meta);
                    p.getInventory().setItem(p.getInventory().firstEmpty(),skill);
                }
            }


        } else {

            //main stat new
            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);

            if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)){
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
}
