package me.marc3308.arbeitundleben.skills;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Findfamiliar implements Listener {

    @EventHandler
    public void onfamila(EntityTargetLivingEntityEvent e){

        //familia does not atack player or is passiv
        if(!e.getEntity().getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING))return;
        Player p=Bukkit.getPlayer(UUID.fromString(e.getEntity().getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING)));

        //familiar is agro
        if((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), e.getEntity()+"mod"), PersistentDataType.STRING)
                && p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), e.getEntity()+"mod"), PersistentDataType.STRING).equals("DIAMOND_SWORD"))) {
            for (Entity en : e.getEntity().getNearbyEntities( 20, 20, 20)) {
                if (en instanceof LivingEntity && !e.getEntity().getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING).equals(en.getUniqueId().toString())) {
                    utilitys.sumonatack((LivingEntity) e.getEntity(), (LivingEntity) en);
                }
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void rightcklicksummon(PlayerInteractEntityEvent e){
        Player p=e.getPlayer();
        if(!(e.getRightClicked() instanceof LivingEntity))return;
        if(!e.getRightClicked().getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING))return;
        if(!e.getRightClicked().getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING).equals(p.getUniqueId().toString()))return;
        if(!p.isSneaking())return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING))return;
        String a=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING);

        //CraftZombie

        Inventory findfam= Bukkit.createInventory(p,27,e.getRightClicked() instanceof Monster ? "Summon Auswahl" : "Begleiter Auswahl");

        if(e.getRightClicked() instanceof Monster){
            findfam.setItem(12, utilitys.builditem(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), a+"mod"), PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), a+"mod"), PersistentDataType.STRING)
                    : "STRUCTURE_VOID","Summon modie",0, utilitys.getlore(p,"ändere die einstellung deines summons anderen gegenüber",true)));
            findfam.setItem(14, utilitys.builditem("BARRIER","Wegschicken",0,utilitys.getlore(p,"Den Summon Wegschicken",true)));
        } else {

            if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), a+"angfiff"), PersistentDataType.BOOLEAN)){
                findfam.setItem(10, utilitys.builditem("NAME_TAG","Umbennenen",0,utilitys.getlore(p,"Bennene deinen begleiter um",true)));
                findfam.setItem(12, utilitys.builditem("DIAMOND","Skilltree",0,utilitys.getlore(p,"Der skilltree deines begleiters",true)));
                findfam.setItem(14, utilitys.builditem(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), a+"mod"), PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), a+"mod"), PersistentDataType.STRING)
                        : "STRUCTURE_VOID","Summon modie",0,utilitys.getlore(p,"ändere die einstellung deines summons anderen gegenüber",true)));
                findfam.setItem(16, utilitys.builditem("BARRIER","Wegschicken",0,utilitys.getlore(p,"Den begleiter Wegschicken",true)));
            } else if (p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), a+"inventar"), PersistentDataType.BOOLEAN)) {
                findfam.setItem(10, utilitys.builditem("NAME_TAG","Umbennenen",0,utilitys.getlore(p,"Bennene deinen begleiter um",true)));
                findfam.setItem(12, utilitys.builditem("DIAMOND","Skilltree",0,utilitys.getlore(p,"Der skilltree deines begleiters",true)));
                findfam.setItem(14,utilitys.builditem("CHEST","Inventar",0,utilitys.getlore(p,"Das inventar des begleiters",true)));
                findfam.setItem(16, utilitys.builditem("BARRIER","Wegschicken",0,utilitys.getlore(p,"Den begleiter Wegschicken",true)));
            } else if (p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), a+"reiten"), PersistentDataType.BOOLEAN)) {
                findfam.setItem(10, utilitys.builditem("NAME_TAG","Umbennenen",0,utilitys.getlore(p,"Bennene deinen begleiter um",true)));
                findfam.setItem(12, utilitys.builditem("DIAMOND","Skilltree",0,utilitys.getlore(p,"Der skilltree deines begleiters",true)));
                findfam.setItem(14, utilitys.builditem("SADDLE","Reiten",0,utilitys.getlore(p,"Den begleiter Reiten",true)));
                findfam.setItem(16, utilitys.builditem("BARRIER","Wegschicken",0,utilitys.getlore(p,"Den begleiter Wegschicken",true)));
            } else {
                findfam.setItem(11, utilitys.builditem("NAME_TAG","Umbennenen",0,utilitys.getlore(p,"Bennene deinen begleiter um",true)));
                findfam.setItem(13, utilitys.builditem("DIAMOND","Skilltree",0,utilitys.getlore(p,"Der skilltree deines begleiters",true)));
                findfam.setItem(15, utilitys.builditem("BARRIER","Wegschicken",0,utilitys.getlore(p,"Den begleiter Wegschicken",true)));
            }


        }

        p.openInventory(findfam);

    }

    @EventHandler
    public void passivgestellt(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player))return;
        if(!(e.getDamager() instanceof LivingEntity) && (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof LivingEntity))return;
        Player p= (Player) e.getEntity();
        LivingEntity liv=e.getDamager() instanceof Projectile ? (LivingEntity) ((Projectile) e.getDamager()).getShooter() : (LivingEntity) e.getDamager();

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING))return;
        if((!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING)+"mod"), PersistentDataType.STRING)
                || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING)+"mod"), PersistentDataType.STRING).equals("STRUCTURE_VOID")))return;


        for(Entity en : p.getWorld().getNearbyEntities(p.getLocation(),32,32,32)){
            if(en instanceof LivingEntity
                    && en.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING)
                    && en.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING).equals(p.getUniqueId().toString())){

                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(liv.isDead() || en.isDead()
                                || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING)+"mod"), PersistentDataType.STRING).equals("STRUCTURE_VOID")){
                            if(en instanceof Monster)((Monster) en).setTarget(null);
                            if(en instanceof Wolf)((Wolf) en).setTarget(null);
                            if(en instanceof Panda)((Panda) en).setTarget(null);
                            cancel();
                            return;
                        }
                        utilitys.sumonatack((LivingEntity) en,liv);
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);


            }
        }
    }

    @EventHandler
    public void donotdrop(EntityDeathEvent e){
        if(e.getEntity() instanceof Player)return;
        if(!(e.getEntity() instanceof LivingEntity))return;
        if(!e.getEntity().getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING))return;
        e.getDrops().clear();
    }

    @EventHandler
    public void familiainv(InventoryCloseEvent e){
        Player p=(Player) e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING))return;
        String familia=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING);
        if(!e.getView().getTitle().equalsIgnoreCase("Begleiter Auswahl > Inventory"))return;
        utilitys.begleiterinv.put(familia,e.getInventory().getContents());

    }

}
