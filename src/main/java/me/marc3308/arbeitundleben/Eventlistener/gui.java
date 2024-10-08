package me.marc3308.arbeitundleben.Eventlistener;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftSniffer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;


public class gui implements Listener {

    @EventHandler
    public void on(InventoryClickEvent e){

        Player p=(Player) e.getWhoClicked();
        String title=e.getView().getTitle();

        //find familia
        if(title.equalsIgnoreCase("Begleiter")){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            p.closeInventory();

            switch (e.getCurrentItem().getType()){

                case PANDA_SPAWN_EGG:
                    Panda pan= (Panda) p.getWorld().spawnEntity(p.getLocation(), EntityType.PANDA);
                    pan.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "craftpandaname"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "craftpandaname"),PersistentDataType.STRING)
                            :p.getName())+"'s Begleiter");
                    pan.setCustomNameVisible(true);
                    pan.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"craftpanda");
                    utilitys.entityfolow(p,pan);
                    break;
                case WOLF_SPAWN_EGG:
                    Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
                    wolf.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "craftwolfname"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "craftwolfname"),PersistentDataType.STRING)
                            :p.getName())+"'s Begleiter");
                    wolf.setCustomNameVisible(true);
                    wolf.setOwner(p);
                    wolf.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"craftwolf");
                    utilitys.entityfolow(p,wolf);
                    break;
                case SNIFFER_SPAWN_EGG:
                    CraftSniffer snif = (CraftSniffer) p.getWorld().spawnEntity(p.getLocation(), EntityType.SNIFFER);
                    snif.setTarget(p);
                    snif.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "craftsniffername"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "craftsniffername"),PersistentDataType.STRING)
                            :p.getName())+"'s Begleiter");
                    snif.setCustomNameVisible(true);
                    snif.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"craftsniffer");
                    utilitys.entityfolow(p,snif);
                    break;
            }

        }

        //familiar inventory
        if(title.equalsIgnoreCase("Summon Auswahl") || title.equalsIgnoreCase("Begleiter Auswahl")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING))return;
            String familia=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING);

            if(e.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)
                    || e.getCurrentItem().getType().equals(Material.SHIELD)
                    || e.getCurrentItem().getType().equals(Material.STRUCTURE_VOID)){

                Inventory findfam= Bukkit.createInventory(p,27,title.equalsIgnoreCase("Summon Auswahl") ? "Summon Auswahl > Modi" : "Begleiter Auswahl > Modi");

                findfam.setItem(11, utilitys.builditem("DIAMOND_SWORD","Agressiv",0,utilitys.getlore(p,"Dein summen greift alle feindlich gesinten kreaturen an",true)));
                findfam.setItem(13, utilitys.builditem("SHIELD","Devensiv",0,utilitys.getlore(p,"Dein summen greift alle kreaturen an die dich angreifen",true)));
                findfam.setItem(15, utilitys.builditem("STRUCTURE_VOID","Passiv",0,utilitys.getlore(p,"Dein summen hält sich aus kämpfen raus",true)));
                p.closeInventory();
                p.openInventory(findfam);
                return;
            }


            switch (e.getCurrentItem().getType()){
                case NAME_TAG:
                    Inventory test=Bukkit.createInventory(null, InventoryType.ANVIL,"Suche:");
                    p.openInventory(test);
                    break;
                case DIAMOND:
                    Bukkit.getServer().dispatchCommand(p,"familiatree");
                    break;
                case BARRIER:
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));
                    p.closeInventory();
                    break;
                case CHEST:
                    int größe=9* (p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"invgröße"), PersistentDataType.INTEGER)
                            ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"invgröße"), PersistentDataType.INTEGER) : 1);
                    Inventory auswahl= Bukkit.createInventory(p,größe,"Begleiter Auswahl > Inventory");
                    if(utilitys.begleiterinv.get(familia)!=null)auswahl.setContents(utilitys.begleiterinv.get(familia));
                    p.openInventory(auswahl);
                default:
                    break;
            }

        }

        //familiar mod
        if(title.equalsIgnoreCase("Summon Auswahl > Modi") || title.equalsIgnoreCase("Begleiter Auswahl > Modi")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getCurrentItem().getType()){
                case DIAMOND_SWORD:
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),
                            p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"hasfamilia"), PersistentDataType.STRING)+"mod"), PersistentDataType.STRING, "DIAMOND_SWORD");
                    p.closeInventory();
                    break;
                case SHIELD:
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),
                            p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"hasfamilia"), PersistentDataType.STRING)+"mod"), PersistentDataType.STRING, "SHIELD");
                    for(Entity en : p.getWorld().getNearbyEntities(p.getLocation(),32,32,32)){
                        if(en instanceof LivingEntity && en.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING)
                                && en.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING).equals(p.getUniqueId().toString())){

                            if(en instanceof Monster)((Monster)en).setTarget(null);
                            if(en instanceof Wolf)((Wolf) en).setTarget(null);

                        }
                    }
                    p.closeInventory();
                    break;
                case STRUCTURE_VOID:
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),
                            p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),"hasfamilia"), PersistentDataType.STRING)+"mod"), PersistentDataType.STRING, "STRUCTURE_VOID");

                    for(Entity en : p.getWorld().getNearbyEntities(p.getLocation(),32,32,32)){
                        if(en instanceof LivingEntity && en.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING)
                            && en.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING).equals(p.getUniqueId().toString())){

                            if(en instanceof Monster)((Monster)en).setTarget(null);
                            if(en instanceof Wolf)((Wolf) en).setTarget(null);

                        }
                    }
                    p.closeInventory();
                    break;
            }


        }

        //familiar skilltree
        if(title.equalsIgnoreCase("Begleiter Auswahl > Skilltree")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(!e.getCurrentItem().getType().equals(Material.DIAMOND))return;
            if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING))return;
            String familia=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING);
            if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER)==0)return;
            int skill = p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"skillnow"), PersistentDataType.INTEGER) ?
                    p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"skillnow"), PersistentDataType.INTEGER)
                    : 0;
            if(skill==0 && e.getSlot()<9 && e.getSlot()>19)return;
            if(skill!=0 && e.getSlot()<27 && e.getSlot()>35)return;
            int sk=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER);
            if(sk==0)return;
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER,(sk-1));
            String skillnow = skill==0 ? "Grundtrees"
                    : skill<=9 ? "Grundtrees"+"."+skill
                    : utilitys.getcon(3).get(skill+".Anzahlentscheidungen")!=null ? String.valueOf(skill)
                    : String.valueOf(skill) +"."+p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), String.valueOf(skill)), PersistentDataType.INTEGER);

            String nextskill = skill==0 ? skillnow : String.valueOf(utilitys.getcon(3).getInt(skillnow+".Nextskill"));
            if(utilitys.getcon(3).getString(nextskill+".Anzahlentscheidungen")!=null){
                int test=0;
                for (int i=1;i<=utilitys.getcon(3).getInt(nextskill+".Anzahlentscheidungen");i++){
                    String testskill=nextskill+"."+i;
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals(utilitys.getcon(3).getString(testskill+",AnzeigeName")))test=i;
                }
                if(test==0)return;
                nextskill=nextskill+"."+test;
            } else {
                if(!e.getCurrentItem().getItemMeta().getDisplayName().equals(utilitys.getcon(3).getString(nextskill+",AnzeigeName")))return;
            }

            //addet the boni
            int bonianzahl=utilitys.getcon(3).getInt(nextskill+".Bonianzahl");

            for(int i=1;i<bonianzahl+1;i++){

                String boniname = utilitys.getcon(3).getString(nextskill+".Boni"+"."+i+".Name");
                double boniwirkung = utilitys.getcon(3).getDouble(nextskill+".Boni"+"."+i+".Wirkung");

                if(boniname.equalsIgnoreCase("skill")){

                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+utilitys.getcon(3).getString(nextskill + ".Boni" + "." + i + ".Wirkung")), PersistentDataType.BOOLEAN, true);

                } else {

                    double boniadd =p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),familia+boniname), PersistentDataType.DOUBLE);
                    boniadd+=boniwirkung;
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),familia+boniname), PersistentDataType.DOUBLE,boniadd);

                }
            }

            Bukkit.getServer().dispatchCommand(p,"familiatree");
        }
    }
}
