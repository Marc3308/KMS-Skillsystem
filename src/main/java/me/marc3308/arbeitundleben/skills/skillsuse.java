package me.marc3308.arbeitundleben.skills;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R1.block.impl.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static me.marc3308.arbeitundleben.utilitys.getcon;
import static org.bukkit.Material.*;

public class skillsuse {

    public static void greaterteleportation(Player p){

        new BukkitRunnable() {

            @Override
            public void run() {

                //when not activ animor
                if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                        || !p.isOnline()) {
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,5.0);
                    cancel();
                    return;
                }

                //getting the location
                Location eyloc=p.getEyeLocation();
                Vector dir = eyloc.getDirection();

                //entfernungs calibrirung
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,
                        p.isSneaking() ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)+0.3
                        : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)-0.3<=5 ? 5.0
                        : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)-0.3);

                eyloc.add(dir.multiply(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)));


                //creating the block
                double kosten=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("greaterteleportation"+".Kostenart")+"now"), PersistentDataType.DOUBLE)-
                        (getcon(1).getInt(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)+".Kosten")
                                * p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE));

                p.sendBlockChange(eyloc,utilitys.clearsite(p.getEyeLocation(),eyloc) ?
                        kosten>=0 ? Material.GREEN_STAINED_GLASS.createBlockData()
                        : Material.RED_STAINED_GLASS.createBlockData()
                        : Material.RED_STAINED_GLASS.createBlockData());

                double test=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE);

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING,
                        (getcon(1).getString("greaterteleportation"+".AnzeigeName")+
                        (utilitys.clearsite(p.getEyeLocation(),eyloc) ?
                        kosten>=0 ? ChatColor.GREEN
                        : ChatColor.RED
                        : ChatColor.RED)
                        +" Blöcke: " +
                        (utilitys.clearsite(p.getEyeLocation(),eyloc) ?
                        kosten>=0 ? ChatColor.DARK_GREEN
                        : ChatColor.DARK_RED
                        : ChatColor.DARK_RED)
                        +(int) test));

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        Location testloc=p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)));

                        if(!p.getWorld().getBlockAt(testloc).equals(p.getWorld().getBlockAt(eyloc)) || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
                            p.sendBlockChange(eyloc,p.getWorld().getBlockData(eyloc));
                            cancel();
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),7,0);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);



    }

    public static void awayusmaximus(Player p){

        new BukkitRunnable() {

            @Override
            public void run() {

                //when not casting animor
                if(!p.isOnline() || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                        || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                    cancel();
                    return;
                }

                if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        && p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equals("awayusmaximusaktiv")){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING,"awayusmaximusaktiv");
                }

                //kosten
                double kosten=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("awayusmaximus"+".Kostenart")+"now"), PersistentDataType.DOUBLE)-
                        getcon(1).getInt("awayusmaximus"+".Kosten");
                if(kosten<=0){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                } else {
                    if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equals("awayusmaximusaktiv")){
                        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", getcon(1).getString("awayusmaximus"+".Kostenart")+"now"), PersistentDataType.DOUBLE,kosten);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,30,1,false,false));
                    }
                }

                //check how many hande
                switch (getcon(1).getString("awayusmaximus"+".Freiehande")){
                    case "onehand":
                        if(p.getInventory().getItemInMainHand().getType()!=Material.AIR && p.getInventory().getItemInOffHand().getType()!=Material.AIR)p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        break;
                    case "twohand":
                        if(p.getInventory().getItemInMainHand().getType()!=Material.AIR || p.getInventory().getItemInOffHand().getType()!=Material.AIR)p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        break;
                }

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);

    }

    public static void smite(Player p){

        new BukkitRunnable() {

            @Override
            public void run() {

                //when not activ animor
                if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                        || !p.isOnline()) {
                    cancel();
                    return;
                }
                //creating the block
                Location eyloc=p.getWorld().getHighestBlockAt(utilitys.nexthit(p, getcon(1).getInt("smite"+".Maxsichtweite")).getBlockX(),utilitys.nexthit(p, getcon(1).getInt("smite"+".Maxsichtweite")).getBlockZ()).getLocation();
                p.sendBlockChange(eyloc,Material.LIGHT_BLUE_STAINED_GLASS.createBlockData());


                new BukkitRunnable(){
                    @Override
                    public void run() {

                        Location testloc=p.getWorld().getHighestBlockAt(utilitys.nexthit(p, getcon(1).getInt("smite"+".Maxsichtweite")).getBlockX(),utilitys.nexthit(p, getcon(1).getInt("smite"+".Maxsichtweite")).getBlockZ()).getLocation();

                        if(!p.getWorld().getBlockAt(testloc).equals(p.getWorld().getBlockAt(eyloc)) || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
                            p.sendBlockChange(eyloc,p.getWorld().getBlockData(eyloc));
                            cancel();
                        }
                        if(p.getWorld().getBlockAt(testloc).equals(p.getWorld().getBlockAt(eyloc)))cancel();
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),7,0);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);



    }

    public static void blackhole(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,5.0);

        new BukkitRunnable() {

            @Override
            public void run() {

                //when not activ animor
                if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                        || !p.isOnline()) {
                    cancel();
                    return;
                }

                //entfernungs calibrirung
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,
                        p.isSneaking() ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)+0.3> getcon(1).getInt("blackhole"+".Maxsichtweite") ? Double.valueOf(getcon(1).getInt("blackhole"+".Maxsichtweite"))
                                : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)+0.3
                                : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)-0.3<=5 ? 5.0
                                : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)-0.3);


                //creating the block
                double test=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE);
                p.sendBlockChange(utilitys.nexthit(p,(int)test),Material.BLACK_STAINED_GLASS.createBlockData());

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING,
                        (getcon(1).getString("blackhole"+".AnzeigeName")+ChatColor.GRAY +" Blöcke: " +ChatColor.DARK_GRAY+(int) test));

                //getting the location
                Location eyloc=utilitys.nexthit(p,(int)test);

                new BukkitRunnable(){
                    @Override
                    public void run() {
                        double loc=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE);
                        Location testloc=utilitys.nexthit(p,(int)loc);


                        if(!p.getWorld().getBlockAt(testloc).equals(p.getWorld().getBlockAt(eyloc)) || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
                            p.sendBlockChange(eyloc,p.getWorld().getBlockData(eyloc));
                            cancel();
                            return;
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),7,0);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);
    }

    public static void fly(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"fly");

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("flyaktiv")) {
                    cancel();
                    return;
                }

                p.setAllowFlight(true);
                p.setFlying(true);

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //if cast is old
                        if(!p.isOnline() || p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            cancel();
                            return;
                        }

                        p.setAllowFlight(true);
                        p.setFlying(true);

                        if(!p.hasPotionEffect(PotionEffectType.GLOWING))p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,15,1,false,false));

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("fly"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow- getcon(1).getInt("fly"+".Kosten")>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", getcon(1).getString("fly"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow- getcon(1).getInt("fly"+".Kosten")));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        }

                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("fly"+".Dauer"))+3,20);
    }

    public static void findfamiliar(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"findfamiliar");


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("findfamiliaraktiv")) {
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));
                Inventory findfam= Bukkit.createInventory(p,27,"Begleiter");

                findfam.setItem(11,utilitys.builditem("PANDA_SPAWN_EGG",
                        p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftPandaname"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftPandaname"),PersistentDataType.STRING)
                        : "Panda",0,utilitys.getlore(p,"craftpanda",false)));

                findfam.setItem(13,utilitys.builditem("WOLF_SPAWN_EGG",
                        p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftWolfname"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftWolfname"),PersistentDataType.STRING)
                                : "Hund",0,utilitys.getlore(p,"craftwolf",false)));


                findfam.setItem(15,utilitys.builditem("SNIFFER_SPAWN_EGG",
                        p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftSniffername"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftSniffername"),PersistentDataType.STRING)
                                : "Sniffer",0,utilitys.getlore(p,"craftsniffer",false)));

                p.openInventory(findfam);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("findfamiliar"+".Dauer"))+3,20);
    }

    public static void creatundead(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"createundead");

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("createundeadaktiv")) {
                    cancel();
                    return;
                }

                Zombie zom = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                zom.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftZombiename"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftZombiename"),PersistentDataType.STRING)
                        : p.getName())+"'s Begleiter");
                zom.setCustomNameVisible(true);
                zom.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                zom.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));
                zom.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"CraftZombie");
                utilitys.entityfolow(p,zom);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("createundead"+".Dauer"))+3,20);
    }

    public static void deathwaria(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"deathwaria");

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("deathwariaaktiv")) {
                    cancel();
                    return;
                }

                Zombie zom = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                zom.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftZombiename"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftZombiename"),PersistentDataType.STRING)
                        : p.getName())+"'s Begleiter");
                zom.setCustomNameVisible(true);
                zom.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                zom.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                zom.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                zom.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                zom.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                zom.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
                zom.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"CraftZombie");
                utilitys.entityfolow(p,zom);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("deathwaria"+".Dauer"))+3,20);
    }

    public static void undeadmage(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"undeadmage");

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("undeadmageaktiv")) {
                    cancel();
                    return;
                }

                Zombie zom = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                zom.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftZombiename"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftZombiename"),PersistentDataType.STRING)
                        : p.getName())+"'s Begleiter");
                zom.setCustomNameVisible(true);
                zom.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                zom.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                zom.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                zom.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                zom.getEquipment().setItemInMainHand(new ItemStack(Material.STICK));
                zom.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                zom.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "mage"), PersistentDataType.BOOLEAN,true);
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"CraftZombie");
                utilitys.entityfolow(p,zom);

                //skills
                zom.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skilllv1"), PersistentDataType.BOOLEAN,true);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("undeadmage"+".Dauer"))+3,20);
    }

    public static void skeletarcher(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"skeletarcher");

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("skeletarcheraktiv")) {
                    cancel();
                    return;
                }

                Skeleton skel = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
                skel.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftSkeletonname"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftSkeletonname"),PersistentDataType.STRING)
                        : p.getName())+"'s Begleiter");
                skel.setCustomNameVisible(true);
                skel.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                skel.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"CraftSkeleton");
                utilitys.entityfolow(p,skel);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("skeletarcher"+".Dauer"))+3,20);
    }

    public static void maxmimismagicsummongiant(Player p){
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"maxmimismagicsummongiant");

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("maxmimismagicsummongiantaktiv")) {
                    cancel();
                    return;
                }

                Giant giant = (Giant) p.getWorld().spawnEntity(p.getLocation(), EntityType.GIANT);
                giant.setCustomName((p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftGiantname"),PersistentDataType.STRING) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "CraftGiantname"),PersistentDataType.STRING)
                        : p.getName())+"'s Begleiter");
                giant.setCustomNameVisible(true);
                giant.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                giant.addPassenger(p);
                giant.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING,p.getUniqueId().toString());
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING,"CraftGiant");
                utilitys.entityfolow(p,giant);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("maxmimismagicsummongiant"+".Dauer"))+3,20);
    }

    public static void coruption(Player p){
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"coruption");


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("coruptionaktiv")) {
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "curradius"), PersistentDataType.INTEGER,0);

                Material block=p.getLocation().add(0,-1,0).getBlock().getType();
                Location ploc=p.getLocation().add(0,-1,0);

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        if(!p.isOnline() || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
                            cancel();
                            return;
                        }

                        int radius=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "curradius"), PersistentDataType.INTEGER);
                        if(radius< getcon(1).getInt("coruption"+".Maxsichtweite"))p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "curradius"), PersistentDataType.INTEGER,radius+1);

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("coruption"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow- getcon(1).getInt("coruption"+".Kosten")>0) {
                            if(p.getLocation().distance(ploc)<= getcon(1).getInt("coruption"+".Maxsichtweite")){
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,25,0,false,false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,25,0,false,false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,25,0,false,false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,25,0,false,false));
                            }
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", getcon(1).getString("coruption"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow- getcon(1).getInt("coruption"+".Fallosten")));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        }

                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);

                for (Entity en : p.getWorld().getNearbyEntities(p.getLocation(), getcon(1).getInt("coruption"+".Maxsichtweite"), getcon(1).getInt("coruption"+".Maxsichtweite"), getcon(1).getInt("coruption"+".Maxsichtweite"))){
                    if(en instanceof Player){
                        new BukkitRunnable(){
                            @Override
                            public void run() {

                                if(!((Player) en).isOnline() ||p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "curradius"), PersistentDataType.INTEGER)>= getcon(1).getInt("coruption"+".Maxsichtweite")
                                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
                                    cancel();
                                    return;
                                }

                                int radius=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "curradius"), PersistentDataType.INTEGER);

                                //creating the block
                                int radiusSquared = radius * radius;
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            if (x * x + y * y + z * z <= radiusSquared) {
                                                Location blockLocation = ploc.clone().add(x, y, z);
                                                if (blockLocation.getBlock().getType() != Material.AIR) {
                                                    ((Player) en).sendBlockChange(blockLocation,Material.valueOf(getcon(1).getString("coruption"+".Wandlung")).createBlockData());
                                                    new BukkitRunnable(){
                                                        @Override
                                                        public void run() {
                                                            if(!((Player) en).isOnline() || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)){
                                                                ((Player) en).sendBlockChange(blockLocation,blockLocation.getBlock().getBlockData());
                                                                cancel();
                                                                return;
                                                            }
                                                        }
                                                    }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
                    }
                }

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("coruption"+".Dauer"))+3,20);
    }

    public static void  maximisemagiccallgreaterlighningstomrexplotion(Player p){


        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"maximisemagiccallgreaterlighningstomrexplotion");


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("maximisemagiccallgreaterlighningstomrexplotionaktiv")) {
                    cancel();
                    return;
                }

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //when not activ animor
                        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                                || !p.isOnline()) {
                            cancel();
                            return;
                        }

                        for(Entity en : p.getNearbyEntities(getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"))){
                            if(en instanceof LivingEntity){
                                p.getWorld().strikeLightningEffect(en.getLocation());
                                p.getWorld().playSound(en.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,1,1);
                                p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,en.getLocation(),10);
                                utilitys.hitapalyer(p,(LivingEntity) en,"maximisemagiccallgreaterlighningstomrexplotion");
                            }
                        }

                        if(p.getNearbyEntities(getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite")).size()<20){
                            for (int i = 0; i<=20-p.getNearbyEntities(getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite")).size(); i++){

                                double ran = ThreadLocalRandom.current().nextDouble(-getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"), getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Maxsichtweite"));
                                p.getWorld().strikeLightningEffect(p.getWorld().getHighestBlockAt(p.getLocation().add(ran,0,ran)).getLocation());
                            }
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("maximisemagiccallgreaterlighningstomrexplotion"+".Dauer"))+3,20);

    }

    public static void  seetrhoustone(Player p){


        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"seetrhoustone");


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("seetrhoustoneaktiv")) {
                    cancel();
                    return;
                }

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //when not activ animor
                        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                                || !p.isOnline()) {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                            cancel();
                            return;
                        }

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("seetrhoustone"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow- getcon(1).getInt("seetrhoustone"+".Kosten")>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", getcon(1).getString("seetrhoustone"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow- getcon(1).getInt("seetrhoustone"+".Kosten")));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                            cancel();
                            return;
                        }

                        int radius= getcon(1).getInt("seetrhoustone"+".Maxsichtweite");

                        ArrayList<Material> mat=new ArrayList<>();
                        mat.add(IRON_ORE);
                        mat.add(DEEPSLATE_IRON_ORE);
                        mat.add(DIAMOND_ORE);
                        mat.add(DEEPSLATE_DIAMOND_ORE);
                        mat.add(GOLD_ORE);
                        mat.add(DEEPSLATE_GOLD_ORE);
                        mat.add(COAL_ORE);
                        mat.add(DEEPSLATE_COAL_ORE);
                        mat.add(LAPIS_ORE);
                        mat.add(DEEPSLATE_LAPIS_ORE);

                        //creating the block
                        int radiusSquared = radius * radius;
                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    if (x * x + y * y + z * z <= radiusSquared) {
                                        Location blockLocation = p.getLocation().clone().add(x, y, z);
                                        if (blockLocation.getBlock().getType() != AIR && !mat.contains(blockLocation.getBlock().getType())) {

                                            p.sendBlockChange(blockLocation, GLASS.createBlockData());

                                            Location loc =p.getLocation().getBlock().getLocation();

                                            new BukkitRunnable(){
                                                @Override
                                                public void run() {
                                                    if(!loc.equals(p.getLocation().getBlock().getLocation())
                                                            || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                                                            || !p.isOnline()){
                                                        p.sendBlockChange(blockLocation,p.getWorld().getBlockData(blockLocation));
                                                        cancel();
                                                    }
                                                }
                                            }.runTaskTimer(Arbeitundleben.getPlugin(),0,39);
                                        }
                                    }
                                }
                            }
                        }


                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("seetrhoustone"+".Dauer"))+3,20);

    }

    public static void wachstum(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"wachstum");

        ArrayList<Material> wachblocks = new ArrayList<>();
        wachblocks.add(OAK_SAPLING);
        wachblocks.add(SPRUCE_SAPLING);
        wachblocks.add(BIRCH_SAPLING);
        wachblocks.add(JUNGLE_SAPLING);
        wachblocks.add(ACACIA_SAPLING);
        wachblocks.add(DARK_OAK_SAPLING);
        wachblocks.add(CHERRY_SAPLING);




        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("wachstumaktiv")) {
                    cancel();
                    return;
                }

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //when not activ animor
                        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                                || !p.isOnline()) {
                            cancel();
                            return;
                        }

                        int radius= getcon(1).getInt("wachstum"+".Maxsichtweite");

                        //creating the block
                        int radiusSquared = radius * radius;
                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    if (x * x + y * y + z * z <= radiusSquared) {
                                        Location blockLocation = p.getLocation().clone().add(x, y, z);

                                        if(wachblocks.contains(blockLocation.getBlock().getType())){
                                            CraftSapling sap=(CraftSapling) blockLocation.getBlock().getBlockData();
                                            if(ThreadLocalRandom.current().nextInt(1,101)>45)return;
                                            System.out.println("test");
                                            sap.setStage(sap.getMaximumStage());
                                            blockLocation.getBlock().setBlockData(sap);
                                        }

                                        switch (blockLocation.getBlock().getType()){
                                            case MANGROVE_PROPAGULE:
                                                CraftMangrovePropagule a=(CraftMangrovePropagule) blockLocation.getBlock().getBlockData();
                                                a.setAge( a.getAge()+1>a.getMaximumAge() ? a.getMaximumAge() : a.getAge()+1);
                                                blockLocation.getBlock().setBlockData(a);
                                                break;
                                            case BAMBOO:
                                                if(blockLocation.add(0,1,0).getBlock().getType().equals(AIR))blockLocation.getBlock().setType(BAMBOO);
                                                break;
                                            case SUGAR_CANE:
                                                if(blockLocation.add(0,1,0).getBlock().getType().equals(AIR))blockLocation.getBlock().setType(SUGAR_CANE);
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20* getcon(1).getInt("wachstum"+".Dauer"))+3,20);


    }

    public static  void meditation(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"meditation");

        //sit on armor stand
        p.sendTitle(ChatColor.GREEN+"Meditation",ChatColor.DARK_GREEN+"f to cancel");
        ArmorStand ar=p.getWorld().spawn(p.getLocation().add(0,-1,0),ArmorStand.class);
        ar.setMaxHealth(1000.0);
        ar.setCustomNameVisible(false);
        ar.setGravity(false);
        ar.setInvulnerable(true);
        ar.setVisible(false);
        ar.setPassenger(p);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("meditation")) {
                    p.getPersistentDataContainer().remove(new NamespacedKey("rassensystem", "infight"));
                    p.sendTitle("","");
                    ar.remove();
                    cancel();
                    return;
                }

                //positioning
                ar.setPassenger(p);
                p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "infight"), PersistentDataType.DOUBLE,1.0);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,35,10,false,false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,35,10,false,false));
                //manareg
                String num=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)==null ? "no"
                        : p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING);

                //werte
                int managrund= getcon(2).getInt("Grundwerte"+".mana");
                double manareggrund= getcon(2).getDouble("Grundwerte"+".manareg");
                double Manarasse= getcon(2).getDouble(num+".mana");
                double Manaregrasse= getcon(2).getDouble(num+".manareg");

                double Manareg = (manareggrund + p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", "manareg"), PersistentDataType.DOUBLE)) * ((100 + Manaregrasse) / 100);
                Manareg*= getcon(1).getDouble("meditation"+".Stärke");

                double Mananow = p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", "mananow"), PersistentDataType.DOUBLE) + Manareg;


                double Manamax = (managrund + p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", "mana"), PersistentDataType.DOUBLE)) * ((100 + Manarasse) / 100);
                if (Mananow > Manamax) Mananow = Manamax;
                p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "mananow"), PersistentDataType.DOUBLE, Mananow);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
    }

    public static void taunt(Player p){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"taunt");

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("tauntaktiv")) {
                    cancel();
                    return;
                }
                int range=getcon(1).getInt("taunt"+".Maxsichtweite");
                for (Entity e : p.getNearbyEntities(range,range,range)) {
                    if(e instanceof Monster){
                        ((Monster) e).setTarget(p);
                        Bukkit.getScheduler().runTaskLater(Arbeitundleben.getPlugin(), () -> ((Monster) e).setTarget(null), 20*getcon(1).getInt("taunt"+".SkillDauer"));
                    }
                }

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*getcon(1).getInt("taunt"+".Dauer"))+3,20);
    }
}
