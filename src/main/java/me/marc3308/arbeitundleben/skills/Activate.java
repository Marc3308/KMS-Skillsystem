package me.marc3308.arbeitundleben.skills;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static me.marc3308.arbeitundleben.utilitys.*;

public class Activate implements Listener {

    @EventHandler
    public void greaterteleportation(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("greaterteleportation"))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR))return;


        Location eyloc=p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)));
        if(!clearsite(p.getEyeLocation(),eyloc))return;

        //casting
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"greaterteleportation");
        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
        utilitys.iscastin(p,"greaterteleportation",p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE));

        new BukkitRunnable(){
            @Override
            public void run() {

                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("greaterteleportationaktiv")){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                    cancel();
                    return;
                }
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,5.0);
                p.teleport(eyloc);
                p.getWorld().playSound(p.getLocation(),Sound.ENTITY_FOX_TELEPORT,1,1);

                //slowfall
                new BukkitRunnable(){
                    @Override
                    public void run() {

                        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"greaterteleportationaktiv");
                        if(!p.isOnline() || p.getLocation().subtract(0,1,0).getBlock().getType()!=Material.AIR){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                            cancel();
                            return;
                        }


                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", utilitys.getcon(1).getString("greaterteleportation"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow-utilitys.getcon(1).getInt("greaterteleportation"+".Fallosten")>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", utilitys.getcon(1).getString("greaterteleportation"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow-utilitys.getcon(1).getInt("greaterteleportation"+".Fallosten")));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20,1,false,false));
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),10,3);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("greaterteleportation"+".Dauer"))+3,1);
    }

    @EventHandler
    public void firehands(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("firehands"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"firehands");
        utilitys.iscastin(p,"firehands",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("firehandsaktiv")) {
                    cancel();
                    return;
                }

                Fireball fireball = (Fireball) p.getLocation().getWorld().spawnEntity(p.getEyeLocation(), EntityType.FIREBALL);
                fireball.setShooter(p);
                fireball.setDirection(p.getLocation().getDirection().normalize().multiply(2.0));
                fireball.setInvulnerable(true);
                fireball.setIsIncendiary(false);
                fireball.setGravity(false);
                fireball.setIsIncendiary(false);
                fireball.setYield(0);
                fireball.setCustomNameVisible(false);
                fireball.setCustomName("firehands");
                //todo noch was machen was den erkennbar macht persiston data kontainer halt
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("firehands"+".Dauer"))+3,20);
    }

    @EventHandler
    public void smite(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("smite"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"smite");
        utilitys.iscastin(p,"smite",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("smiteaktiv")) {
                    cancel();
                    return;
                }

                Location loc=utilitys.nexthit(p,utilitys.getcon(1).getInt("smite"+".Maxsichtweite"));
                loc.setY(p.getWorld().getHighestBlockYAt(loc.getBlockX(),loc.getBlockZ()));
                p.getWorld().strikeLightningEffect(loc);
                for(Entity en : p.getWorld().getNearbyEntities(loc,2,2,2)){
                    if(en instanceof LivingEntity){
                        utilitys.hitapalyer(p, (LivingEntity) en,"smite");
                    }
                }

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("smite"+".Dauer"))+3,20);
    }

    @EventHandler
    public void spy(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("spy"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"spy");
        utilitys.iscastin(p,"spy",1.0);


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("spyaktiv")) {
                    cancel();
                    return;
                }

                //denie casting
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                //creating the block
                for(int x=0;x<=3;x++){
                    for(int y=0;y<=3;y++){
                        for(int z=0;z<=3;z++){
                            Location loc=e.getClickedBlock().getLocation();
                            loc.setX(loc.getBlockX()+x-1);
                            loc.setY(loc.getBlockY()+y-1);
                            loc.setZ(loc.getBlockZ()+z-1);
                            if(loc.getBlock().getType()!=Material.AIR)p.sendBlockChange(loc,Material.BARRIER.createBlockData());
                        }
                    }
                }


                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //if cast is old
                        if(!p.isOnline() || p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)) {
                            //breaking
                            for(int x=0;x<=3;x++){
                                for(int y=0;y<=3;y++){
                                    for(int z=0;z<=3;z++){
                                        Location loc=e.getClickedBlock().getLocation();
                                        loc.setX(loc.getBlockX()+x-1);
                                        loc.setY(loc.getBlockY()+y-1);
                                        loc.setZ(loc.getBlockZ()+z-1);
                                        p.sendBlockChange(loc,loc.getWorld().getBlockData(loc));
                                    }
                                }
                            }
                            cancel();
                            return;
                        }

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", utilitys.getcon(1).getString("spy"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow-utilitys.getcon(1).getInt("spy"+".Kosten")>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", utilitys.getcon(1).getString("spy"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow-utilitys.getcon(1).getInt("spy"+".Kosten")));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        }

                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("spy"+".Dauer"))+3,20);
    }

    @EventHandler
    public void lightningstrike(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("lightningstrike"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"), PersistentDataType.INTEGER))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"lightningstrike");
        utilitys.iscastin(p,"lightningstrike",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {

                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("lightningstrikeaktiv")) {
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"), PersistentDataType.INTEGER,3);
                //strike
                new BukkitRunnable(){
                    @Override
                    public void run() {


                        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"), PersistentDataType.INTEGER)>=utilitys.getcon(1).getInt("lightningstrike"+".Maxsichtweite")){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"));
                            cancel();
                            return;
                        }

                        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"), PersistentDataType.INTEGER,
                                p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"), PersistentDataType.INTEGER)+1);


                        Location loc=p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "strikeer"), PersistentDataType.INTEGER)));
                        loc.setY(p.getWorld().getHighestBlockYAt(loc.getBlockX(),loc.getBlockZ()));
                        p.getWorld().strikeLightningEffect(loc);
                        for(Entity en : p.getWorld().getNearbyEntities(loc,2,2,2)){
                            if(en instanceof LivingEntity){
                                utilitys.hitapalyer(p, (LivingEntity) en,"lightningstrike");
                            }
                        }

                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,5);

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("lightningstrike"+".Dauer"))+3,20);
    }

    @EventHandler
    public void blackhole(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("blackhole"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"), PersistentDataType.INTEGER))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;


        //casting
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"blackhole");
        utilitys.iscastin(p,"blackhole",1.0);

        double ent=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE);
        Location loc=utilitys.nexthit(p,(int)ent);
        p.sendBlockChange(utilitys.nexthit(p,(int)ent),Material.BLACK_CONCRETE.createBlockData());


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("blackholeaktiv")) {
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,5.0);
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"));
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"), PersistentDataType.INTEGER,0);
                p.setAllowFlight(true);
                new BukkitRunnable(){
                    @Override
                    public void run() {

                        if(!p.isOnline() || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"), PersistentDataType.INTEGER)>=ent*20){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"));
                            p.setAllowFlight(false);
                            for(Player pp : Bukkit.getOnlinePlayers()){
                                if(utilitys.refmep.get(p)!=null){
                                    pp.sendBlockChange(utilitys.refmep.get(p),p .getWorld().getBlockData(utilitys.refmep.get(p)));
                                    utilitys.refmep.remove(p);
                                    pp.setAllowFlight(false);
                                }
                            }
                            cancel();
                            return;
                        }

                        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"), PersistentDataType.INTEGER,
                                p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "blackholetime"), PersistentDataType.INTEGER)+1);

                        for(Entity pp : loc.getWorld().getNearbyEntities(loc,(int)ent,(int)ent,(int)ent)){
                            if(pp instanceof Player){
                                ((Player) pp).sendBlockChange(loc,Material.BLACK_CONCRETE.createBlockData());
                                utilitys.refmep.put(p,loc);
                                ((Player) pp).setAllowFlight(true);
                            }

                            if(!(pp instanceof Player && ((Player)pp).equals(p))){
                                Location playerLocation = pp.getLocation();
                                @NotNull Vector direction = loc.toVector().subtract(playerLocation.toVector()).normalize();
                                double distance = playerLocation.distance(loc);
                                double pullStrength = 0.1; // Adjust as needed
                                @NotNull Vector pullVector = direction.multiply(pullStrength);
                                pp.setVelocity(pullVector);
                                if(pp instanceof LivingEntity)utilitys.hitapalyer(p, (LivingEntity) pp,"blackhole");

                            }
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("blackhole"+".Dauer"))+3,20);
    }

    @EventHandler
    public void maximisemagicgardenofeden(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("maximisemagicgardenofeden"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "maxgaren"), PersistentDataType.INTEGER))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"maximisemagicgardenofeden");
        utilitys.iscastin(p,"maximisemagicgardenofeden",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("maximisemagicgardenofedenaktiv")) {
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "maxgaren"), PersistentDataType.INTEGER,0);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(!p.isOnline() || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "maxgaren"), PersistentDataType.INTEGER)>=utilitys.getcon(1).getInt("maximisemagicgardenofeden"+".Maxsichtweite")){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "maxgaren"));
                            cancel();
                            return;
                        }

                        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "maxgaren"), PersistentDataType.INTEGER,
                                p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "maxgaren"), PersistentDataType.INTEGER)+1);

                        int radius=utilitys.getcon(1).getInt("maximisemagicgardenofeden"+".Maxsichtweite");

                        for(Entity en : p.getWorld().getNearbyEntities(p.getLocation(),radius,radius,radius)){
                            if(en instanceof Player && !((Player)en).equals(p)){
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -radius; y <= radius; y++) {
                                        for (int z = -radius; z <= radius; z++) {

                                            Location location = p.getLocation().clone().add(x, y, z);
                                            if(location.getBlock().getType().equals(Material.AIR))((Player) en).sendBlockChange(location,Material.BAMBOO.createBlockData());

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
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("maximisemagicgardenofeden"+".Dauer"))+3,20);
    }

    @EventHandler
    public void doorway(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("doorway"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"doorway");
        utilitys.iscastin(p,"doorway",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("doorwayaktiv")) {
                    cancel();
                    return;
                }

                //denie casting
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                //blocks
                ArrayList<Material> mat=new ArrayList<>();
                mat.add(Material.BARREL);
                mat.add(Material.BEEHIVE);
                mat.add(Material.BLAST_FURNACE);
                mat.add(Material.BREWING_STAND);
                mat.add(Material.CHEST);
                mat.add(Material.CHISELED_BOOKSHELF);
                mat.add(Material.DISPENSER);
                mat.add(Material.DROPPER);
                mat.add(Material.ENDER_CHEST);
                mat.add(Material.FLOWER_POT);
                mat.add(Material.FURNACE);
                mat.add(Material.HOPPER);
                mat.add(Material.ITEM_FRAME);
                mat.add(Material.JUKEBOX);
                mat.add(Material.LECTERN);
                mat.add(Material.SMOKER);
                mat.add(Material.TRAPPED_CHEST);
                mat.add(Material.SHULKER_BOX);
                mat.add(Material.WHITE_SHULKER_BOX);
                mat.add(Material.ORANGE_SHULKER_BOX);
                mat.add(Material.MAGENTA_SHULKER_BOX);
                mat.add(Material.LIGHT_BLUE_SHULKER_BOX);
                mat.add(Material.YELLOW_SHULKER_BOX);
                mat.add(Material.LIME_SHULKER_BOX);
                mat.add(Material.PINK_SHULKER_BOX);
                mat.add(Material.GRAY_SHULKER_BOX);
                mat.add(Material.LIGHT_GRAY_SHULKER_BOX);
                mat.add(Material.CYAN_SHULKER_BOX);
                mat.add(Material.PURPLE_SHULKER_BOX);
                mat.add(Material.BLUE_SHULKER_BOX);
                mat.add(Material.BROWN_SHULKER_BOX);
                mat.add(Material.GREEN_SHULKER_BOX);
                mat.add(Material.RED_SHULKER_BOX);
                mat.add(Material.BLACK_SHULKER_BOX);


                //creating the block
                for(int x=0;x<=3;x++){
                    for(int y=0;y<=3;y++){
                        for(int z=0;z<=3;z++){
                            Location loc=e.getClickedBlock().getLocation();
                            loc.setX(loc.getBlockX()+x-1);
                            loc.setY(loc.getBlockY()+y-1);
                            loc.setZ(loc.getBlockZ()+z-1);
                            if(utilitys.locmap.get(loc)==null && loc.getBlock().getType()!=Material.AIR && !mat.contains(loc.getBlock().getType())){
                                utilitys.locmap.put(loc,loc.getBlock().getBlockData());
                                p.getWorld().setBlockData(loc,Material.AIR.createBlockData());
                            }
                        }
                    }
                }


                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //if cast is old
                        if(!p.isOnline() || p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)) {
                            //breaking
                            for(int x=0;x<=3;x++){
                                for(int y=0;y<=3;y++){
                                    for(int z=0;z<=3;z++){
                                        Location loc=e.getClickedBlock().getLocation();
                                        loc.setX(loc.getBlockX()+x-1);
                                        loc.setY(loc.getBlockY()+y-1);
                                        loc.setZ(loc.getBlockZ()+z-1);
                                        if(utilitys.locmap.get(loc)!=null){
                                            p.getWorld().setBlockData(loc,utilitys.locmap.get(loc));
                                            utilitys.locmap.remove(loc);
                                        }

                                    }
                                }
                            }
                            cancel();
                            return;
                        }

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", utilitys.getcon(1).getString("doorway"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow-utilitys.getcon(1).getInt("doorway"+".Kosten")>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", utilitys.getcon(1).getString("doorway"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow-utilitys.getcon(1).getInt("doorway"+".Kosten")));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        }

                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("doorway"+".Dauer"))+3,20);
    }

    @EventHandler
    public void healingworld(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("healingworld"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"healingworld");
        utilitys.iscastin(p,"healingworld",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("healingworldaktiv")) {
                    cancel();
                    return;
                }


                int max=utilitys.getcon(1).getInt("healingworld"+".Maxsichtweite");
                for(Entity pp : p.getWorld().getNearbyEntities(p.getLocation(),max,max,max)){
                    if(pp instanceof LivingEntity){
                        ((LivingEntity) pp).setHealth(((LivingEntity) pp).getHealth()+utilitys.getcon(1).getInt("healingworld"+".Heal")>((LivingEntity) pp).getMaxHealth() ?
                                ((LivingEntity) pp).getMaxHealth()
                                :((LivingEntity) pp).getHealth()+utilitys.getcon(1).getInt("healingworld"+".Heal"));
                        utilitys.particels(p,pp.getLocation(),2);
                    }
                }


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("healingworld"+".Dauer"))+3,20);
    }

    @EventHandler
    public void curewounds(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("curewounds"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"curewounds");
        utilitys.iscastin(p,"curewounds",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("curewoundsaktiv")) {
                    cancel();
                    return;
                }

                utilitys.particels(p,p.getLocation(),2);
                p.getWorld().playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1,1);
                p.setHealth(p.getHealth()+utilitys.getcon(1).getInt("healingworld"+".Heal")>p.getMaxHealth() ?
                        p.getMaxHealth()
                        :p.getHealth()+utilitys.getcon(1).getInt("healingworld"+".Heal"));


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("curewounds"+".Dauer"))+3,20);
    }

    @EventHandler
    public void curewoundshits(EntityDamageByEntityEvent e){

        if(!(e.getEntity() instanceof LivingEntity))return;
        if(!(e.getDamager() instanceof Player))return;
        Player p= (Player) e.getDamager();
        LivingEntity pp = (LivingEntity) e.getEntity();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("curewounds"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        e.setCancelled(true);


        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"curewounds");
        utilitys.iscastin(p,"curewounds",1.0);

        //damit man sehen kann das er gehielt wird
        pp.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20*utilitys.getcon(1).getInt("curewounds"+".Dauer"),1,false,false));


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("curewoundsaktiv")) {
                    cancel();
                    return;
                }

                utilitys.particels(p,pp.getLocation(),2);
                pp.getWorld().playSound(pp.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1,1);
                pp.setHealth(pp.getHealth()+utilitys.getcon(1).getInt("healingworld"+".Heal")>pp.getMaxHealth() ?
                        pp.getMaxHealth()
                        :pp.getHealth()+utilitys.getcon(1).getInt("healingworld"+".Heal"));

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("curewounds"+".Dauer"))+3,20);
    }

    @EventHandler
    public void sturmangriff(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("sturmangriff"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"), PersistentDataType.INTEGER))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"sturmangriff");
        utilitys.iscastin(p,"sturmangriff",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("sturmangriffaktiv")) {
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"),PersistentDataType.INTEGER,0);

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        if(!p.isOnline()
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"), PersistentDataType.INTEGER)
                                || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"), PersistentDataType.INTEGER)>=(utilitys.getcon(1).getInt("sturmangriff"+".Maxsichtweite"))){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"));
                            cancel();
                            return;
                        }

                        int a=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"), PersistentDataType.INTEGER);
                        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"), PersistentDataType.INTEGER, a+1);

                        Location loc=utilitys.nexthit(p,utilitys.getcon(1).getInt("sturmangriff"+".Maxsichtweite"));
                        Location playerLocation = p.getLocation();
                        @NotNull Vector direction = loc.add(0,-100,0).toVector().subtract(playerLocation.toVector()).normalize();
                        double distance = playerLocation.distance(loc);
                        double pullStrength = 5.0; // Adjust as needed
                        @NotNull Vector pullVector = direction.multiply(pullStrength);
                        for(Entity pp : p.getWorld().getNearbyEntities(p.getLocation(),2,2,2)){
                            if(pp instanceof LivingEntity){
                                if(!(pp instanceof Player && ((Player)pp).equals(p))){
                                    ((LivingEntity) pp).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"), PersistentDataType.INTEGER),2,false,false));
                                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "runtime"));
                                    p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,p.getLocation(),7);
                                    utilitys.hitapalyer(p, (LivingEntity) pp,"sturmangriff");
                                }
                            }
                        }
                        p.setVelocity(pullVector);


                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("sturmangriff"+".Dauer"))+3,20);
    }

    @EventHandler
    public void greaterhalnextlove(EntityDamageByEntityEvent e){

        if(!(e.getEntity() instanceof LivingEntity))return;
        if(!(e.getDamager() instanceof Player))return;
        Player p= (Player) e.getDamager();
        LivingEntity pp = (LivingEntity) e.getEntity();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("greaterhalnextlove"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        e.setCancelled(true);


        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"greaterhalnextlove");
        utilitys.iscastin(p,"greaterhalnextlove",1.0);

        //damit man sehen kann das er gehielt wird
        pp.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20*utilitys.getcon(1).getInt("greaterhalnextlove"+".Dauer"),1,false,false));


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("greaterhalnextloveaktiv")) {
                    cancel();
                    return;
                }

                utilitys.particels(p,pp.getLocation(),2);
                pp.getWorld().playSound(pp.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,1,1);
                pp.setHealth(pp.getHealth()+p.getMaxHealth()>pp.getMaxHealth() ?
                        pp.getMaxHealth()
                        :pp.getHealth()+p.getMaxHealth());

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("greaterhalnextlove"+".Dauer"))+3,20);
    }

    @EventHandler
    public void wansin(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("wansin"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;


        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"wansin");
        utilitys.iscastin(p,"wansin",1.0);

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "wansin2"), PersistentDataType.INTEGER,utilitys.getcon(1).getInt("wansin"+".Dauer"));

        //spawn heads while casting
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("wansin")) {
                    cancel();
                    return;
                }

                Location loc=p.getLocation().add(ThreadLocalRandom.current().nextDouble(-2,2),ThreadLocalRandom.current().nextDouble(0,2),ThreadLocalRandom.current().nextDouble(-2,2));
                ArmorStand ar=p.getWorld().spawn(loc,ArmorStand.class);
                ar.setCustomName("Das ist wahnsin");
                ar.setCustomNameVisible(false);
                ar.setGravity(false);
                ar.setVisible(false);
                ar.setInvulnerable(true);
                ar.setCollidable(false);
                ar.setRemoveWhenFarAway(true);
                ar.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,p.getUniqueId().toString());

                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                skullMeta.setOwningPlayer(Bukkit.getPlayer(p.getUniqueId()));
                head.setItemMeta(skullMeta);

                ar.setHelmet(head);

                int test=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "wansin2"), PersistentDataType.INTEGER);

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        if(!p.isOnline()
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                                || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("wansinaktiv")) {
                            ar.remove();
                            cancel();
                            return;
                        }

                        Location loc=utilitys.nexthit(p,utilitys.getcon(1).getInt("wansin"+".Maxsichtweite"));
                        Location playerLocation = p.getLocation();
                        @NotNull Vector direction = loc.add(0,0,0).toVector().subtract(playerLocation.toVector()).normalize();
                        double distance = playerLocation.distance(loc);
                        double pullStrength = 0.6; // Adjust as needed
                        @NotNull Vector pullVector = direction.multiply(pullStrength);

                        if(!ar.getLocation().add(0,-1,0).getBlock().getType().equals(Material.AIR)){

                            Vector direc = loc.toVector().subtract(ar.getLocation().toVector());
                            direc.normalize();
                            double dis = 0.1;
                            Location newLocation = ar.getLocation().add(direc.multiply(dis));
                            ar.teleport(newLocation);
                        }

                        ar.setGravity(true);
                        ar.setVelocity(pullVector);

                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),((20*test)+5),1);

                test-=(test/3);
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "wansin2"), PersistentDataType.INTEGER,test);

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,(16*utilitys.getcon(1).getInt("wansin"+".Dauer")/3));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("wansinaktiv")) {
                    cancel();
                    return;
                }

                for(Entity ar : p.getWorld().getNearbyEntities(p.getLocation(),utilitys.getcon(1).getInt("wansin"+".Maxsichtweite")+1,utilitys.getcon(1).getInt("wansin"+".Maxsichtweite")+1,utilitys.getcon(1).getInt("wansin"+".Maxsichtweite")+1)){
                    if(ar instanceof ArmorStand
                    && ar.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "Besitzer"), PersistentDataType.STRING)
                    && ar.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "Besitzer"), PersistentDataType.STRING).equalsIgnoreCase(p.getUniqueId().toString())){
                        Location arloc=ar.getLocation();

                        for(Entity en : p.getWorld().getNearbyEntities(arloc,2,2,2)){
                            if(en instanceof LivingEntity && !(en instanceof ArmorStand))utilitys.hitapalyer(p, (LivingEntity) en,"wansin");
                        }
                        p.getWorld().playSound(arloc,Sound.ENTITY_GENERIC_EXPLODE,1,1);
                        p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,arloc,10);

                    }
                }


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("wansin"+".Dauer"))+40,20);
    }

    @EventHandler
    public void rundumschlag(EntityDamageByEntityEvent e){

        if(!(e.getEntity() instanceof LivingEntity))return;
        if(!(e.getDamager() instanceof Player))return;
        Player p= (Player) e.getDamager();
        LivingEntity pp = (LivingEntity) e.getEntity();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("rundumschlag"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))utilitys.iscastin(p,"rundumschlag",1.0);
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("rundumschlagaktiv"))return;
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"rundumschlag");
        utilitys.iscastin(p,"rundumschlag",1.0);

        //setup
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "rundspin"), PersistentDataType.INTEGER,0);


        new BukkitRunnable(){
            @Override
            public void run() {

                if(!p.isOnline() || p.isDead() || utilitys.getcon(1).getInt("rundumschlag"+".Dauer")<=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "rundspin"), PersistentDataType.INTEGER)){
                    cancel();
                    return;
                }

                for(Entity en : p.getNearbyEntities(2,2,2)){
                    if(en instanceof LivingEntity){
                        utilitys.hitapalyer(p,(LivingEntity) en,"rundumschlag");
                    }
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "rundspin"), PersistentDataType.INTEGER,
                        p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "rundspin"), PersistentDataType.INTEGER)+1);
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
    }

    @EventHandler
    public void thunderwave(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("thunderwave"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"thunderwave");
        utilitys.iscastin(p,"thunderwave",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("thunderwaveaktiv")) {
                    cancel();
                    return;
                }
                int maxrange=utilitys.getcon(1).getInt("thunderwave"+".Maxsichtweite");
                p.getWorld().playSound(p.getLocation(),Sound.ENTITY_LIGHTNING_BOLT_THUNDER,1,1);
                for(Entity en : p.getNearbyEntities(maxrange,maxrange,maxrange)){
                    if(en instanceof LivingEntity){
                        double ff=maxrange/100.0;
                        Vector vector = en.getLocation().toVector().subtract(p.getLocation().toVector());
                        double force = Math.abs((30 - en.getLocation().distance(p.getLocation())) * ff);
                        /*Here is a small math problem, we calculate the force to push based on distance
                        So: 30 - distance between player and entity, multiply by 0.125
                        You can play with these values or set a fixed force.*/
                        vector.normalize();
                        vector.multiply(force); //if you want to push the entities away just set to "-force"
                        en.setVelocity(vector);
                        ((LivingEntity) en).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,60,1,false,false));
                        ((LivingEntity) en).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60,3,false,false));
                        utilitys.hitapalyer(p, (LivingEntity) en,"thunderwave");
                    }
                }



                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("thunderwave"+".Dauer"))+3,20);




    }

    @EventHandler
    public void schwebekissen(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("schwebekissen")
                && !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("schwebekissenaktiv"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;
        double adove=0.1;
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE,
                p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE)==null ? 0.4
                        : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE)==0.0 ? 0.4
                        : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE)+adove>=1.0 ? 1.0
                        : p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE)+adove);
        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("schwebekissenaktiv"))return;


        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"schwebekissen");
        utilitys.iscastin(p,"schwebekissen",1.0);

        ArmorStand ar=p.getWorld().spawn(p.getLocation().add(0,-1,0),ArmorStand.class);
        ar.setMaxHealth(1000.0);
        ar.setCustomNameVisible(false);
        ar.setInvulnerable(true);
        ar.setVisible(false);
        ar.setPassenger(p);
        p.setAllowFlight(true);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("schwebekissenaktiv")) {
                    ar.remove();
                    cancel();
                    return;
                }

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING,"schwebekissenaktiv");

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        //when not activ animor
                        if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                                || !p.isOnline()
                                || p.isDead()) {
                            p.setAllowFlight(false);
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                            ar.remove();
                            cancel();
                            return;
                        }

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("schwebekissen"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow- (getcon(1).getInt("schwebekissen"+".Kosten")/4.0)>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", getcon(1).getString("schwebekissen"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow-(getcon(1).getInt("schwebekissen"+".Kosten")/4.0)));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                            ar.remove();
                            cancel();
                            return;
                        }

                        //flowflow
                        if(p.getVehicle()==null || !p.getVehicle().equals(ar)){
                            float yaw=p.getLocation().getYaw();
                            float pitch=p.getLocation().getPitch();
                            ar.setPassenger(p);
                            p.getLocation().setPitch(pitch);
                            p.getLocation().setYaw(yaw);
                            if(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE)==0.0){
                                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                                ar.remove();
                            }
                            ar.setGravity(false);
                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    if(ar.isDead() || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE)!=0.0){
                                        ar.setGravity(true);
                                        ar.remove();
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Arbeitundleben.getPlugin(),0,3);
                            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE,0.0);
                        }


                        p.getWorld().spawnParticle(Particle.CLOUD,p.getLocation(),7,0.5,0.5,0.5,0.05);

                        Location arLocation = ar.getLocation();
                        @NotNull Vector direction = nexthit(p,10).toVector().subtract(arLocation.toVector()).normalize();
                        double distance = arLocation.distance(nexthit(p,10));
                        double pullStrength =p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "goinfast"), PersistentDataType.DOUBLE); // Adjust as needed
                        @NotNull Vector pullVector = direction.multiply(pullStrength);
                        ar.setVelocity(pullVector);


                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,5);




                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("schwebekissen"+".Dauer"))+3,20);
    }

    @EventHandler
    public void icebold(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("icebold"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"icebold");
        utilitys.iscastin(p,"icebold",1.0);


        final int[] i = {0};
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("iceboldaktiv")) {
                    cancel();
                    return;
                }


                Snowball snowball = (Snowball) p.getLocation().getWorld().spawnEntity(p.getEyeLocation().add(0,0.5,0), EntityType.SNOWBALL);
                snowball.setShooter(p);
                snowball.setVelocity(p.getLocation().getDirection().normalize().multiply(2.0));
                snowball.setInvulnerable(true);
                snowball.setGravity(false);
                snowball.setCustomNameVisible(false);
                snowball.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "spell"), PersistentDataType.STRING, "icebold");
                snowball.setCustomName("icebold");

                i[0]++;
                if(i[0] == 3){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                    cancel();
                }
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("icebold"+".Dauer"))+3,10);
    }

    @EventHandler
    public void firebold(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("firebold"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"firebold");
        utilitys.iscastin(p,"firebold",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("fireboldaktiv")) {
                    cancel();
                    return;
                }

                SmallFireball smball=(SmallFireball) p.getLocation().getWorld().spawnEntity(p.getEyeLocation().add(0,0.5,0), EntityType.SMALL_FIREBALL);
                smball.setShooter(p);
                smball.setVelocity(p.getLocation().getDirection().normalize().multiply(2.0));
                smball.setInvulnerable(true);
                smball.setGravity(false);
                smball.setCustomNameVisible(false);
                smball.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "spell"), PersistentDataType.STRING, "firebold");
                smball.setCustomName("firebold");

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("firebold"+".Dauer"))+3,20);
    }

    @EventHandler
    public void pandanerv(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player))return;
        if(!(e.getDamager() instanceof Player))return;
        Player p= (Player) e.getDamager();
        Player pp = (Player) e.getEntity();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("pandanerv"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        e.setCancelled(true);

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"pandanerv");
        utilitys.iscastin(p,"pandanerv",1.0);

        //damit man sehen kann das er gehielt wird
        pp.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20*utilitys.getcon(1).getInt("pandanerv"+".Stärke"),1,false,false));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("pandanervaktiv")) {
                    cancel();
                    return;
                }

                pp.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*utilitys.getcon(1).getInt("pandanerv"+".Stärke"),0,false,false));

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("pandanerv"+".Dauer"))+3,20);
    }

    @EventHandler
    public void selbstschutz(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("selbstschutz"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"selbstschutz");
        utilitys.iscastin(p,"selbstschutz",1.0);


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("selbstschutzaktiv")) {
                    cancel();
                    return;
                }

                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20*utilitys.getcon(1).getInt("selbstschutz"+".Stärke"),0,false,false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*utilitys.getcon(1).getInt("selbstschutz"+".Stärke"),0,false,false));


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("selbstschutz"+".Dauer"))+3,20);



    }

    @EventHandler
    public void anderschwache(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("anderschwache"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"anderschwache");
        utilitys.iscastin(p,"anderschwache",1.0);


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("anderschwacheaktiv")) {
                    cancel();
                    return;
                }


                Snowball snowball = (Snowball) p.getLocation().getWorld().spawnEntity(p.getEyeLocation().add(0,0.5,0), EntityType.SNOWBALL);
                snowball.setShooter(p);
                snowball.setVelocity(p.getLocation().getDirection().normalize().multiply(2.0));
                snowball.setInvulnerable(true);
                snowball.setGravity(false);
                snowball.setCustomNameVisible(false);
                snowball.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "spell"), PersistentDataType.STRING, "anderschwache");
                snowball.setCustomName("anderschwache");
                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("anderschwache"+".Dauer"))+3,10);
    }

    @EventHandler
    public void schlechtewelle(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("schlechtewelle"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"schlechtewelle");
        utilitys.iscastin(p,"schlechtewelle",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("schlechtewelleaktiv")) {
                    cancel();
                    return;
                }
                int maxrange=utilitys.getcon(1).getInt("schlechtewelle"+".Maxsichtweite");
                for(Entity en : p.getNearbyEntities(maxrange,maxrange,maxrange)){
                    if(en instanceof Player && en!=p){
                        utilitys.hitapalyer(p, (LivingEntity) en,"schlechtewelle");
                        ((Player) en).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,20*utilitys.getcon(1).getInt("schlechtewelle"+".Stärke"),0,false,false));
                    }
                }

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("schlechtewelle"+".Dauer"))+3,20);
    }

    @EventHandler
    public void nafernwelle(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("nafernwelle"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isRightClick() && !e.getAction().isLeftClick())return;
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"nafernwelle");
        utilitys.iscastin(p,"nafernwelle",1.0);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("nafernwelleaktiv")) {
                    cancel();
                    return;
                }
                int maxrange=utilitys.getcon(1).getInt("nafernwelle"+".Maxsichtweite");
                for(Entity en : p.getNearbyEntities(maxrange,maxrange,maxrange)){
                    if(en instanceof LivingEntity){

                        double ff=maxrange/100.0;
                        Vector vector = e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)
                                ? en.getLocation().add(0,-3,0).toVector().subtract(p.getLocation().toVector())
                                : en.getLocation().toVector().subtract(p.getLocation().toVector());
                        double force = Math.abs((utilitys.getcon(1).getInt("nafernwelle"+".Stärke") - en.getLocation().distance(p.getLocation())) * ff);
                        /*Here is a small math problem, we calculate the force to push based on distance
                        So: 30 - distance between player and entity, multiply by 0.125
                        You can play with these values or set a fixed force.*/
                        vector.normalize();
                        vector.multiply(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK) ? force : -force); //if you want to push the entities away just set to "-force"
                        en.setVelocity(vector);


                        utilitys.hitapalyer(p, (LivingEntity) en,"nafernwelle");
                    }
                }



                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("nafernwelle"+".Dauer"))+3,20);




    }

    @EventHandler
    public void magicmisial(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("magicmisial"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"magicmisial");
        utilitys.iscastin(p,"magicmisial",1.0);


        final int[] i = {0};
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("magicmisialaktiv")) {
                    cancel();
                    return;
                }

                Entity en=p;
                Snowball snowball = (Snowball) p.getLocation().getWorld().spawnEntity(p.getEyeLocation().add(0,0.5,0), EntityType.SNOWBALL);

                for(Entity ett : p.getNearbyEntities(utilitys.getcon(1).getInt("magicmisial"+".Maxsichtweite")
                        ,utilitys.getcon(1).getInt("magicmisial"+".Maxsichtweite")
                        ,utilitys.getcon(1).getInt("magicmisial"+".Maxsichtweite"))){
                    if(ett instanceof LivingEntity && (en==p || (en.getLocation().distance(p.getLocation())>ett.getLocation().distance(p.getLocation()) && ett!=snowball)))en=ett;
                }

                snowball.setShooter(p);
                snowball.setVelocity(p.getLocation().getDirection().normalize().multiply(2.0));
                snowball.setInvulnerable(true);
                snowball.setGravity(false);
                snowball.setCustomNameVisible(false);
                snowball.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "spell"), PersistentDataType.STRING, "magicmisial");
                snowball.setCustomName("magicmisial");

                if(en==p)en=snowball;
                Entity finalEn = en;
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(snowball.isDead() || !snowball.isValid() || !finalEn.isValid() || finalEn.isDead()){
                            snowball.remove();
                            cancel();
                        }

                        Location etloc = snowball.getLocation();
                        @NotNull Vector direction = finalEn.getLocation().clone().add(0,1,0).toVector().subtract(etloc.toVector()).normalize();
                        double distance = etloc.distance(finalEn.getLocation());
                        double pullStrength = 1.0; // Adjust as needed
                        @NotNull Vector pullVector = direction.multiply(pullStrength);
                        snowball.setVelocity(pullVector);

                        if(finalEn.getLocation().distance(etloc)>=100)snowball.remove();
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);

                i[0]++;
                if(i[0] == 2){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                    cancel();
                }
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("magicmisial"+".Dauer"))+3,10);
    }

    @EventHandler
    public void schildwal(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("schildwal"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"schildwal");
        utilitys.iscastin(p,"schildwal",1.0);


        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("schildwalaktiv")) {
                    cancel();
                    return;
                }

                //armor stand
                ArmorStand ar=p.getWorld().spawn(p.getLocation().add(-1,-1,0),ArmorStand.class);
                ArmorStand ar2=p.getWorld().spawn(p.getLocation().add(1,-1,0),ArmorStand.class);
                ArmorStand ar3=p.getWorld().spawn(p.getLocation().add(0,-1,-1),ArmorStand.class);
                ArmorStand ar4=p.getWorld().spawn(p.getLocation().add(0,-1,1),ArmorStand.class);

                ArmorStand ar5=p.getWorld().spawn(p.getLocation().add(-2,-1,0),ArmorStand.class);
                ArmorStand ar6=p.getWorld().spawn(p.getLocation().add(2,-1,0),ArmorStand.class);
                ArmorStand ar7=p.getWorld().spawn(p.getLocation().add(0,-1,-2),ArmorStand.class);
                ArmorStand ar8=p.getWorld().spawn(p.getLocation().add(0,-1,2),ArmorStand.class);



                ar.setInvisible(true);
                ar.setInvulnerable(true);
                ar.setGravity(false);
                ar.setArms(true);
                ar.setBasePlate(false);
                ar.setItemInHand(new ItemStack(Material.SHIELD));
                ar.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                ar2.setInvisible(true);
                ar2.setInvulnerable(true);
                ar2.setGravity(false);
                ar2.setArms(true);
                ar2.setBasePlate(false);
                ar2.setItemInHand(new ItemStack(Material.SHIELD));
                ar2.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar2.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));;

                ar3.setInvisible(true);
                ar3.setInvulnerable(true);
                ar3.setGravity(false);
                ar3.setArms(true);
                ar3.setBasePlate(false);
                ar3.setItemInHand(new ItemStack(Material.SHIELD));
                ar3.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar3.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                ar4.setInvisible(true);
                ar4.setInvulnerable(true);
                ar4.setGravity(false);
                ar4.setArms(true);
                ar4.setBasePlate(false);
                ar4.setItemInHand(new ItemStack(Material.SHIELD));
                ar4.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar4.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                ar5.setInvisible(true);
                ar5.setInvulnerable(true);
                ar5.setGravity(false);
                ar5.setArms(true);
                ar5.setBasePlate(false);
                ar5.setItemInHand(new ItemStack(Material.SHIELD));
                ar5.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar5.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                ar6.setInvisible(true);
                ar6.setInvulnerable(true);
                ar6.setGravity(false);
                ar6.setArms(true);
                ar6.setBasePlate(false);
                ar6.setItemInHand(new ItemStack(Material.SHIELD));
                ar6.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar6.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                ar7.setInvisible(true);
                ar7.setInvulnerable(true);
                ar7.setGravity(false);
                ar7.setArms(true);
                ar7.setBasePlate(false);
                ar7.setItemInHand(new ItemStack(Material.SHIELD));
                ar7.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar7.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                ar8.setInvisible(true);
                ar8.setInvulnerable(true);
                ar8.setGravity(false);
                ar8.setArms(true);
                ar8.setBasePlate(false);
                ar8.setItemInHand(new ItemStack(Material.SHIELD));
                ar8.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,"nono");
                ar8.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(90), Math.toRadians(0)));

                Location loc1=p.getLocation().add(1,0,0);
                loc1.setYaw(90f);
                ar.teleport(loc1);

                loc1=p.getLocation().add(-1,0,0);
                loc1.setYaw(-90f);
                ar2.teleport(loc1);

                loc1=p.getLocation().add(0,0,1);
                loc1.setYaw(180f);
                ar3.teleport(loc1);

                loc1=p.getLocation().add(0,0,-1);
                loc1.setYaw(0f);
                ar4.teleport(loc1);

                loc1=p.getLocation().add(-1,0,1);
                loc1.setYaw(225f);
                ar5.teleport(loc1);

                loc1=p.getLocation().add(1,0,-1);
                loc1.setYaw(45f);
                ar6.teleport(loc1);

                loc1=p.getLocation().add(-1,0,-1);
                loc1.setYaw(315f);
                ar7.teleport(loc1);

                loc1=p.getLocation().add(1,0,1);
                loc1.setYaw(135f);
                ar8.teleport(loc1);

                new BukkitRunnable(){
                    @Override
                    public void run() {

                        if(!p.isOnline() || p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                                || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)) {
                            ar.remove();
                            ar2.remove();
                            ar3.remove();
                            ar4.remove();
                            ar5.remove();
                            ar6.remove();
                            ar7.remove();
                            ar8.remove();
                            return;
                        }

                        double mananow=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", getcon(1).getString("schildwal"+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                        if(mananow- getcon(1).getInt("schildwal"+".Kosten")>0) {
                            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", getcon(1).getString("schildwal"+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mananow- getcon(1).getInt("schildwal"+".Kosten")));
                        } else {
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
                        }
                    }
                }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);


                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("schildwal"+".Dauer"))+3,10);
    }

    @EventHandler
    public void parieren(PlayerInteractEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING).equalsIgnoreCase("parieren"))return;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!e.getAction().isLeftClick() || !p.isSneaking())return;

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"parieren");
        utilitys.iscastin(p,"parieren",1.0);

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,(20*utilitys.getcon(1).getInt("parieren"+".Dauer"))+3,100,false,false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,(20*utilitys.getcon(1).getInt("parieren"+".Dauer"))+3,1,false,false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,(20*utilitys.getcon(1).getInt("parieren"+".Dauer"))+3,utilitys.getcon(1).getInt("parieren"+".Stärke"),false,false));
        double leb=p.getHealth();

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)
                        || !p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("parierenaktiv")) {
                    cancel();
                    return;
                }

                if(p.getHealth()<leb)p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,(20*utilitys.getcon(1).getInt("parieren"+".Stärke"))+3,0,false,false));

                p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("parieren"+".Dauer"))+3,10);
    }

    @EventHandler
    public void erdolchung(EntityDamageByEntityEvent e){

        if(!(e.getEntity() instanceof LivingEntity))return;
        if(!(e.getDamager() instanceof Player))return;
        Player p= (Player) e.getDamager();
        LivingEntity pp = (LivingEntity) e.getEntity();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("erdolchungaktiv"))return;
        if(p.getLocation().getDirection().angle(pp.getLocation().getDirection())>0.75)return;
        double ddamge=e.getDamage()*utilitys.getcon(1).getDouble("erdolchung"+".Stärke");
        if(!(pp instanceof Player))ddamge*=utilitys.getcon(1).get("erdolchung"+".schadengegenmobs")==null ? utilitys.getcon(1).getDouble("schadengegenmobs") : utilitys.getcon(1).getDouble("erdolchung"+".schadengegenmobs");
        e.setDamage(ddamge);
        utilitys.iskamp(p,ddamge);

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));

    }

    @EventHandler
    public void knockbackpfeil(EntityDamageByEntityEvent e){

        if(!(e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player))return;
        if(!(e.getEntity() instanceof LivingEntity))return;

        LivingEntity pp = (LivingEntity) e.getEntity();
        Player p= (Player) (((Arrow) e.getDamager()).getShooter());
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))return;
        if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING).equalsIgnoreCase("knockbackpfeilaktiv"))return;

        Vector direction = p.getLocation().getDirection();
        direction.normalize();
        direction.multiply(utilitys.getcon(1).getInt("knockbackpfeil"+".Stärke"));
        pp.setVelocity(direction);

        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
        p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"));
    }



    @EventHandler
    public void playerhit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Projectile) || !e.getDamager().getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "spell"), PersistentDataType.STRING) || !(e.getEntity() instanceof LivingEntity))return;

        Player p =Bukkit.getPlayer(((Projectile) e.getDamager()).getOwnerUniqueId());
        LivingEntity opfer= (LivingEntity) e.getEntity();
        String name=e.getDamager().getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "spell"), PersistentDataType.STRING);
        if(!p.isOnline())return;
        e.setDamage(0.0);
        hitapalyer(p,opfer,name);

        switch (name){
            case "icebold":
                if(opfer.getFreezeTicks()>1){
                    opfer.setFreezeTicks(opfer.getFreezeTicks()+(opfer.getMaxFreezeTicks()/2));
                } else {
                    opfer.setFreezeTicks(opfer.getFreezeTicks()+(opfer.getMaxFreezeTicks()/2));
                    final int[] i = {0};
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            opfer.setFreezeTicks(opfer.getFreezeTicks()+3);
                            i[0]++;
                            if(i[0]>utilitys.getcon(1).getInt(name+".Dauer")*20)cancel();
                        }
                    }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);
                }

                break;
            case "anderschwache":
                opfer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,20*utilitys.getcon(1).getInt("anderschwache"+".Stärke"),0,false,false));
                iskamp(p,0.0);
                break;
        }


    }

    @EventHandler
    public void platack(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player))return;
        iskamp((Player) e.getEntity(),0.0);
    }

}
