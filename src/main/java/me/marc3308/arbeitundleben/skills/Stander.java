package me.marc3308.arbeitundleben.skills;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class Stander implements Listener {

    @EventHandler
    public void ondmg(EntityDamageByEntityEvent e){

        if(!(e.getDamager() instanceof Player))return;
        if(!(e.getEntity() instanceof ArmorStand))return;
        Player p=(Player) e.getDamager();
        ArmorStand ar=(ArmorStand) e.getEntity();

        //wenn der spieler lieber das andere will
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN))return;

        ArrayList<String> names=new ArrayList<>();
        for(int i=1;i<=p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "howmannyskillslots"), PersistentDataType.INTEGER);i++){
            names.add(String.valueOf(i));
        }


        if(!names.contains(ar.getName().toString()))return;


        String a="skillslot"+ar.getName().toString();
        if(!p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",a), PersistentDataType.STRING))return;
        if(!ar.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING))return;
        if(!ar.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING).equals(p.getUniqueId().toString())){
            Player pp =Bukkit.getPlayer(UUID.fromString(ar.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING)));
            pp.damage(e.getFinalDamage());
            e.setCancelled(true);
            return;
        }

        String name=p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",a), PersistentDataType.STRING);

        //set eferithing up
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING,name);

        //barstyles
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING,utilitys.getcon(1).getString(name+".AnzeigeName"));
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barcolor"), PersistentDataType.STRING,"RED");
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barprogress"), PersistentDataType.DOUBLE,0.0);
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barstyle"), PersistentDataType.STRING,"SEGMENTED_10");

        utilitys.activateskills(p,name);
        e.setCancelled(true);
    }

    @EventHandler
    public void oninteract(PlayerInteractAtEntityEvent e){

        if(!(e.getRightClicked() instanceof ArmorStand))return;
        ArmorStand ar=(ArmorStand) e.getRightClicked();
        if(!ar.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING))return;
        e.setCancelled(true);
    }
}
