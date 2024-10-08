package me.marc3308.arbeitundleben.skills;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class summenskills {

    public static void feurhand(Monster e){

        e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(20* utilitys.getcon(1).getInt("firehands"+".Dauer")+35),10,false,false));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(e.isDead()) {
                    cancel();
                    return;
                }

                Fireball fireball = (Fireball) e.getLocation().getWorld().spawnEntity(e.getEyeLocation(), EntityType.FIREBALL);
                fireball.setShooter(e);
                fireball.setDirection(e.getLocation().getDirection().normalize().multiply(2.0));
                fireball.setInvulnerable(true);
                fireball.setIsIncendiary(false);
                fireball.setGravity(false);
                fireball.setIsIncendiary(false);
                fireball.setYield(0);
                fireball.setCustomNameVisible(false);
                fireball.setCustomName("firehands");
                e.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));

                e.setTarget(null);
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("firehands"+".Dauer"))+3,20);


    }

    public static void smite(Monster e){


        e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(20*utilitys.getcon(1).getInt("smite"+".Dauer")+35),10,false,false));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(e.isDead()) {
                    cancel();
                    return;
                }

                if(e.getTarget()==null)return;
                e.getWorld().strikeLightningEffect(e.getTarget().getLocation());
                for(Entity en : e.getWorld().getNearbyEntities(e.getTarget().getLocation(),1.5,1.5,1.5)){
                    if(en instanceof LivingEntity){
                        ((LivingEntity) en).damage(utilitys.getcon(1).getInt("smite"+".Schaden"));
                    }
                }

                e.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                e.setTarget(null);
                cancel();
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),(20*utilitys.getcon(1).getInt("smite"+".Dauer"))+3,20);
    }

}
