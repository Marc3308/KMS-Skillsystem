package me.marc3308.arbeitundleben;

import me.marc3308.arbeitundleben.skills.skillsuse;
import me.marc3308.arbeitundleben.skills.summenskills;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftSniffer;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class utilitys {
    public static HashMap<Player, ItemStack[]> hashas=new HashMap<>();

    public static HashMap<String, ItemStack[]> begleiterinv=new HashMap<>();

    public static HashMap<Player, Location> refmep=new HashMap<>();

    public static HashMap<Location, BlockData> locmap=new HashMap<>();

    public static void activateskills(Player p,String name){

        switch (name){
            case "greaterteleportation":
                utilitys.bar(p,name);
                skillsuse.greaterteleportation(p);
                break;
            case "awayusmaximus":
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"awayusmaximus");
                utilitys.iscastin(p,name,1.0);
                utilitys.bar(p,name);
                skillsuse.awayusmaximus(p);
                break;
            case "firehands":
                utilitys.bar(p,name);
                break;
            case "smite":
                skillsuse.smite(p);
                utilitys.bar(p,name);
                break;
            case "spy":
                utilitys.bar(p,name);
                break;
            case "lightningstrike":
                utilitys.bar(p,name);
                break;
            case "blackhole":
                skillsuse.blackhole(p);
                utilitys.bar(p,name);
                break;
            case "maximisemagicgardenofeden":
                utilitys.bar(p,name);
                break;
            case "doorway":
                utilitys.bar(p,name);
                break;
            case "healingworld":
                utilitys.bar(p,name);
                break;
            case "curewounds":
                utilitys.bar(p,name);
                break;
            case "sturmangriff":
                utilitys.bar(p,name);
                break;
            case "greaterhalnextlove":
                utilitys.bar(p,name);
                break;
            case "findfamiliar":
                utilitys.bar(p,name);
                skillsuse.findfamiliar(p);
                utilitys.iscastin(p,"findfamiliar",1.0);
                break;
            case "fly":
                utilitys.bar(p,name);
                skillsuse.fly(p);
                utilitys.iscastin(p,"fly",0.2);
                break;
            case "wansin":
                utilitys.bar(p,name);
                break;
            case "createundead":
                utilitys.bar(p,name);
                skillsuse.creatundead(p);
                utilitys.iscastin(p,"createundead",1.0);
                break;
            case "deathwaria":
                utilitys.bar(p,name);
                skillsuse.deathwaria(p);
                utilitys.iscastin(p,"deathwaria",1.0);
                break;
            case "undeadmage":
                utilitys.bar(p,name);
                skillsuse.undeadmage(p);
                utilitys.iscastin(p,"undeadmage",1.0);
                break;
            case "skeletarcher":
                utilitys.bar(p,name);
                skillsuse.skeletarcher(p);
                utilitys.iscastin(p,"skeletarcher",1.0);
                break;
            case "maxmimismagicsummongiant":
                utilitys.bar(p,name);
                skillsuse.maxmimismagicsummongiant(p);
                utilitys.iscastin(p,"maxmimismagicsummongiant",1.0);
                break;
            case "rundumschlag":
                utilitys.bar(p,name);
                utilitys.iscastin(p,"rundumschlag",1.0);
                break;
            case "coruption":
                utilitys.bar(p,name);
                skillsuse.coruption(p);
                utilitys.iscastin(p,"coruption",1.0);
                break;
            case "maximisemagiccallgreaterlighningstomrexplotion":
                utilitys.bar(p,name);
                skillsuse.maximisemagiccallgreaterlighningstomrexplotion(p);
                utilitys.iscastin(p,"maximisemagiccallgreaterlighningstomrexplotion",1.0);
                break;
            case "seetrhoustone":
                utilitys.bar(p,name);
                skillsuse.seetrhoustone(p);
                utilitys.iscastin(p,"seetrhoustone",1.0);
                break;
            case "thunderwave":
                utilitys.bar(p,name);
                break;
            case "wachstum":
                utilitys.bar(p,name);
                skillsuse.wachstum(p);
                utilitys.iscastin(p,"wachstum",1.0);
                break;
            case "meditation":
                utilitys.bar(p,name);
                skillsuse.meditation(p);
                break;
            case "schwebekissen":
                utilitys.bar(p,name);
                break;
            case "icebold":
                utilitys.bar(p,name);
                break;
            case "firebold":
                utilitys.bar(p,name);
                break;
            case "pandanerv":
                utilitys.bar(p,name);
                break;
            case "selbstschutz":
                utilitys.bar(p,name);
                break;
            case "anderschwache":
                utilitys.bar(p,name);
                break;
            case "schlechtewelle":
                utilitys.bar(p,name);
                break;
            case "nafernwelle":
                utilitys.bar(p,name);
                break;
            case "magicmisial":
                utilitys.bar(p,name);
                break;
            case "schildwal":
                utilitys.bar(p,name);
                break;
            case "parieren":
                utilitys.bar(p,name);
                break;
            case "erdolchung":
                utilitys.bar(p,name);
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"erdolchung");
                utilitys.iscastin(p,"erdolchung",1.0);
                break;
            case "knockbackpfeil":
                utilitys.bar(p,name);
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"knockbackpfeil");
                utilitys.iscastin(p,"knockbackpfeil",1.0);
                break;
            case "taunt":
                utilitys.bar(p,name);
                skillsuse.taunt(p);
                utilitys.iscastin(p,"taunt",1.0);
                break;
            default:
                return;
        }
    }

    public static Scoreboard getBasebord(Player p){

        Scoreboard skillbord = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective oj=skillbord.registerNewObjective("main","main", ChatColor.GREEN+"Skillslots");
        oj.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(int i=1;i<=p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "howmannyskillslots"), PersistentDataType.INTEGER);i++){

            String skillslot="skillslot"+(i);

            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",skillslot), PersistentDataType.STRING)){
                oj.getScore(i+": "+utilitys.getcon(1).getString(p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",skillslot), PersistentDataType.STRING)+".AnzeigeName")).setScore(
                        0);//utilitys.getcon(1).getInt(p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",skillslot), PersistentDataType.STRING)+".Kosten")
            }
        }



        return skillbord;
    }

    public static void ramenkranz(Player p){

        ArrayList<String> skilllist=new ArrayList<>();


        //get how manny skills are equipped
        for(int i=0;i<=p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "howmannyskillslots"), PersistentDataType.INTEGER);i++){
            String skillslot="skillslot"+(i);

            if(p.getPersistentDataContainer().has(new NamespacedKey("arbeitundleben",skillslot), PersistentDataType.STRING)){
                skilllist.add(p.getPersistentDataContainer().get(new NamespacedKey("arbeitundleben",skillslot), PersistentDataType.STRING));
            }
        }

        int si=skilllist.size();
        for(int i=0;i<si;i++){

            ArmorStand ar=p.getWorld().spawn(p.getLocation().add(0,-1,0),ArmorStand.class);

            ar.setMaxHealth(1000.0);
            ar.setCustomName(String.valueOf(i+1));
            ar.setCustomNameVisible(true);
            ar.setGravity(false);
            ar.setVisible(false);

            ar.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(),"Besitzer"), PersistentDataType.STRING,p.getUniqueId().toString());

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

            String a=p.getUniqueId().toString();
            switch (i+1){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
            }

            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(a)));
            head.setItemMeta(skullMeta);

            ar.setHelmet(head);


            for(Player player : Bukkit.getOnlinePlayers()){
                if(!player.equals(p)){
                    player.hideEntity(Arbeitundleben.getPlugin(),ar);
                }
            }


            int finalI = i+1;
            new BukkitRunnable() {

                @Override
                public void run() {

                    if(!p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN) || !p.isOnline()){
                        ar.remove();
                        cancel();
                        return;
                    }

                    // armor stand loc
                    Location loc=p.getLocation();

                    double abstand=1;

                    double a=    si==1 ? 0
                            : si==2 ? finalI==abstand ? -abstand : abstand
                            : si==3 ? finalI==abstand ? -abstand : finalI==2 ? 0 : abstand
                            : si==4 ? finalI==abstand ? -abstand : finalI==2 ? 0 : finalI==3 ? abstand : 0
                            : si==5 ? finalI==abstand ? -abstand : finalI==2 ? 0 : finalI==3 ? abstand : finalI==4 ? -abstand : abstand
                            : si==6 ? finalI==abstand ? -abstand : finalI==2 ? 0 : finalI==3 ? abstand : finalI==4 ? -abstand : finalI==5 ? 0 : abstand
                            : si==7 ? finalI==abstand ? -abstand : finalI==2 ? 0 : finalI==3 ? abstand : finalI==4 ? -abstand : finalI==5 ? 0 : finalI==6 ? abstand : 0
                            : si==8 ? finalI==abstand ? -abstand : finalI==2 ? 0 : finalI==3 ? abstand : finalI==4 ? -abstand : finalI==5 ? 0 : finalI==6 ? abstand : finalI==7 ? -abstand : abstand
                            : finalI==1 ? -abstand : finalI==2 ? 0 : finalI==3 ? abstand : finalI==4 ? -abstand : finalI==5 ? 0 : finalI==6 ? abstand : finalI==7 ? -abstand : finalI==8 ? 0 : abstand;

                    double b = ((int) Math.ceil(finalI/3.0))-0.2;

                    double x=p.getLocation().getYaw()<-45 && p.getLocation().getYaw()>-135 ? b
                            : p.getLocation().getYaw()>45 && p.getLocation().getYaw()<135 ? -b
                            : p.getLocation().getYaw()>135 || p.getLocation().getYaw()<-135 ? -a : a;

                    double z=p.getLocation().getYaw()>-45 && p.getLocation().getYaw()<45 ? b
                            : p.getLocation().getYaw()>135 || p.getLocation().getYaw()<-135 ? -b
                            : p.getLocation().getYaw()>45 && p.getLocation().getYaw()<135 ? -a : a;


                    float yaws=p.getLocation().getYaw()<-45 && p.getLocation().getYaw()>-135 ? -90.0f
                            : p.getLocation().getYaw()>45 && p.getLocation().getYaw()<135 ? 90.0f
                            : p.getLocation().getYaw()>135 || p.getLocation().getYaw()<-135 ? -180.0f : 0.0f;;

                    loc.setYaw(yaws);
                    loc.add(x,-1.3,z);
                    ar.teleport(loc);


                }
            }.runTaskTimer(Arbeitundleben.getPlugin(), 0, 1);

        }
    }

    public static void particels(Player p, Location loc, Integer num){

        switch (num){
            case 1:
                p.getWorld().spawnParticle(Particle.DRIP_LAVA,p.getLocation().add(ThreadLocalRandom.current().nextDouble(0,1.1), 1,ThreadLocalRandom.current().nextDouble(0,1.1)),10);
                p.getWorld().spawnParticle(Particle.DRIP_LAVA,p.getLocation().add(-ThreadLocalRandom.current().nextDouble(0,1.1), 1,-ThreadLocalRandom.current().nextDouble(0,1.1)),10);

                break;
            case 2:

                for(int i=0;i<=100;i++){
                    Location set=loc;
                    p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,set.add(ThreadLocalRandom.current().nextDouble(0,2),ThreadLocalRandom.current().nextDouble(0,2),ThreadLocalRandom.current().nextDouble(0,2)),10);
                }
                break;
        }




    }

    public static boolean clearsite(Location loc1, Location loc2){

        World world = loc1.getWorld();

        // Get the block coordinates of the two locations
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();

        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();

        // Calculate the minimum and maximum coordinates for iteration
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);

        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        // Iterate through all blocks between the two locations
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location currentLocation = new Location(world, x, y, z);
                    Block block = currentLocation.getBlock();

                    // Check if the block is not air
                    if (block.getType() != Material.AIR) {
                        //todo so that see trhou blocks ar not includet maby clear with aiwen
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static Location nexthit(Player p,Integer max){

        return  p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),max)==null ? p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(max))
                :p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),max).getHitBlock()==null ? p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(max))
                :p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),max).getHitBlock().getLocation();


    }

    public static void iscastin(Player p,String spell,Double extrakosten){

        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"), PersistentDataType.INTEGER,0);

        new BukkitRunnable(){
            @Override
            public void run() {

                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING,utilitys.getcon(1).getString(spell+".AnzeigeName"));
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barcolor"), PersistentDataType.STRING,"RED");
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barprogress"), PersistentDataType.DOUBLE,0.0);
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barstyle"), PersistentDataType.STRING,"SEGMENTED_10");

                //when not casting animor
                if(!p.isOnline() || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                        || (!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                        && !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))){

                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"));
                    cancel();
                    return;
                }

                //casting time
                int progess=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"), PersistentDataType.INTEGER)+1;
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"), PersistentDataType.INTEGER,progess);
                int castingtime=utilitys.getcon(1).getInt(spell+".Dauer");

                //check how many hande
                switch (utilitys.getcon(1).getString(spell+".Freiehande")){
                    case "onehand":
                        if(p.getInventory().getItemInMainHand().getType()!=Material.AIR && p.getInventory().getItemInOffHand().getType()!=Material.AIR){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"));
                            p.sendMessage(ChatColor.RED+"Du brauchst eine freie hand");
                            cancel();
                            return;
                        }
                        break;
                    case "twohand":
                        if(p.getInventory().getItemInMainHand().getType()!=Material.AIR || p.getInventory().getItemInOffHand().getType()!=Material.AIR){
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"));
                            p.sendMessage(ChatColor.RED+"Du brauchst zwei freie hände");
                            cancel();
                            return;
                        }
                        break;
                }

                //check if genug wert
                double mana=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", utilitys.getcon(1).getString(spell+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                double geskosten=(utilitys.getcon(1).getInt(spell+".Kosten")*extrakosten);

                //check if not 0
                if(utilitys.getcon(1).getInt(spell+".Dauer")<=0){
                    if(mana-geskosten<0)return;
                    p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", utilitys.getcon(1).getString(spell+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mana-geskosten));
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,"firehandsaktiv");
                    cancel();
                    return;
                }

                double kosten=geskosten/castingtime;
                if((mana-kosten)<0){
                    p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", utilitys.getcon(1).getString(spell+".Kostenart")+"now"), PersistentDataType.DOUBLE,0.0);
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"));
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"));
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT,2,1);
                    cancel();
                    return;
                }

                //set mana
                p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", utilitys.getcon(1).getString(spell+".Kostenart")+"now"), PersistentDataType.DOUBLE,(mana-kosten));

                //bar progress
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barcolor"), PersistentDataType.STRING,progess<=((castingtime/100.0)*33) ? "RED"
                        : progess<=((castingtime/100.0)*66) ? "YELLOW"
                        : "GREEN");
                p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "barprogress"), PersistentDataType.DOUBLE,progess<=((castingtime/100.0)*10) ? 0.1
                        : progess<=((castingtime/100.0)*20) ? 0.2
                        : progess<=((castingtime/100.0)*30) ? 0.3
                        : progess<=((castingtime/100.0)*40) ? 0.4
                        : progess<=((castingtime/100.0)*50) ? 0.5
                        : progess<=((castingtime/100.0)*60) ? 0.6
                        : progess<=((castingtime/100.0)*70) ? 0.7
                        : progess<=((castingtime/100.0)*80) ? 0.8
                        : progess<=((castingtime/100.0)*90) ? 0.9
                        : 1.0);

                //slow for casting
                if(utilitys.getcon(1).getString(spell+".Kostenart").equals("mana"))p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20,3,false,false));

                //when cast erfolgreich
                if(progess>=castingtime){
                    p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING,(spell+"aktiv"));
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "castingtime"));
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
    }

    public static void bar(Player p, String name){
        //setup bar
        BossBar bar= Bukkit.createBossBar(utilitys.getcon(1).getString(name+".AnzeigeName"), BarColor.RED, BarStyle.SEGMENTED_10);
        bar.setProgress(0.0);
        bar.addPlayer(p);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline() || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
                        || (!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "isactiv"), PersistentDataType.STRING)
                        && !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING))){
                    bar.setVisible(false);
                    bar.removePlayer(p);
                    cancel();
                    return;
                }
                if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.STRING)){
                    bar.setColor(BarColor.valueOf(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "barcolor"), PersistentDataType.STRING)));
                    bar.setProgress(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "barprogress"), PersistentDataType.DOUBLE));
                    bar.setTitle(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING));
                    bar.setStyle(BarStyle.valueOf(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "barstyle"), PersistentDataType.STRING)));
                } else {

                    //barüberarbeiten
                    double now=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", utilitys.getcon(1).getString(name+".Kostenart")+"now"), PersistentDataType.DOUBLE);
                    int num=utilitys.getcon(2).getInt("Name"+"."+p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)+".custemmoddeldata");
                    int managrund=utilitys.getcon(2).getInt("Grundwerte"+"."+utilitys.getcon(1).getString(name+".Kostenart"));
                    double Manarasse=utilitys.getcon(2).getDouble("Nummer"+"."+num+"."+utilitys.getcon(1).getString(name+".Kostenart"));
                    double Manamax = (managrund + p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", utilitys.getcon(1).getString(name+".Kostenart")), PersistentDataType.DOUBLE)) * ((100 + Manarasse) / 100);
                    bar.setColor(now<=((Manamax/100.0)*33) ? BarColor.RED
                            : now<=((Manamax/100.0)*66) ? BarColor.YELLOW
                            : BarColor.GREEN);
                    bar.setProgress(now<=((Manamax/100.0)*10) ? 0.1
                            : now<=((Manamax/100.0)*20) ? 0.2
                            : now<=((Manamax/100.0)*30) ? 0.3
                            : now<=((Manamax/100.0)*40) ? 0.4
                            : now<=((Manamax/100.0)*50) ? 0.5
                            : now<=((Manamax/100.0)*60) ? 0.6
                            : now<=((Manamax/100.0)*70) ? 0.7
                            : now<=((Manamax/100.0)*80) ? 0.8
                            : now<=((Manamax/100.0)*90) ? 0.9
                            : 1.0);
                    bar.setTitle(p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "bartitle"), PersistentDataType.STRING));
                }
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);
    }

    public static void entityfolow(Player p,LivingEntity e){

        new BukkitRunnable(){
            @Override
            public void run() {

                if(!p.isOnline() || e.isDead() || p.isDead() || !p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING)){
                    p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"));
                    e.setHealth(0.0);
                    cancel();
                    return;
                }

                if(p.getLocation().distance(e.getLocation())>=32)e.teleport(p.getLocation());


            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
    }

    public static ItemStack builditem(String mat,String name,Integer cus,ArrayList<String> lore){

        ItemStack item=new ItemStack(Material.valueOf(mat));
        ItemMeta item_meta= item.getItemMeta();
        item_meta.setDisplayName(name);
        item_meta.setCustomModelData(cus);
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);

        return item;
    }

    public static void movebackwhilecast(Monster e){

        new BukkitRunnable(){
            @Override
            public void run() {

                if(e.isDead() || !e.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.BOOLEAN)){
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),0,1);
    }

    public static void iskamp(Player p,Double dmg){

        if(!p.getPersistentDataContainer().has(new NamespacedKey("rassensystem", "fightdmg"), PersistentDataType.DOUBLE))return;  //todo test if goes

        //check to fight
        double gerade = p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", "fightdmg"), PersistentDataType.DOUBLE)+dmg;
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fightdmg"), PersistentDataType.DOUBLE,gerade);

        //when in fight
        if(gerade>=utilitys.getcon(2).getInt("Grundwerte"+".kampfstartschaden")){
            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fightdmg"), PersistentDataType.DOUBLE,0.0);
            p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "infight"), PersistentDataType.DOUBLE,0.0);

            //still in fight?
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(!p.isOnline() || (System.currentTimeMillis()-p.getPersistentDataContainer().get(new NamespacedKey("rassensystem", "lastdmghit"),PersistentDataType.LONG))>(utilitys.getcon(2).getInt("Grundwerte"+".kampfende")*1000)
                            || !p.getPersistentDataContainer().has(new NamespacedKey("rassensystem", "infight"), PersistentDataType.DOUBLE)
                            || p.getHealth()<=0){
                        p.getPersistentDataContainer().remove(new NamespacedKey("rassensystem", "infight"));
                        cancel();
                        return;
                    }
                    p.closeInventory();
                }
            }.runTaskTimer(Arbeitundleben.getPlugin(),0,20);
            return;
        }

        //cooldown so not always add
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline() || p.getPersistentDataContainer().has(new NamespacedKey("rassensystem", "infight"), PersistentDataType.DOUBLE)){
                    p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fightdmg"), PersistentDataType.DOUBLE,0.0);
                    cancel();
                    return;
                }
                p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "fightdmg"), PersistentDataType.DOUBLE,gerade-dmg);
                cancel();
                return;
            }
        }.runTaskTimer(Arbeitundleben.getPlugin(),10*20,20);
    }

    public static void hitapalyer(Player p,LivingEntity en, String name){

        double dmg=utilitys.getcon(1).getInt(name+".Schaden");
        p.getPersistentDataContainer().set(new NamespacedKey("rassensystem", "lastdmghit"), PersistentDataType.LONG,System.currentTimeMillis());

        //added schadensarten
        if(utilitys.getcon(1).get(name+".Schadenstyp")!=null){

            String zusatzschadensart=utilitys.getcon(1).getString(name+".Schadenstyp");
            String prasse=p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING)==null ? "no"
                    : p.getPersistentDataContainer().get(new NamespacedKey("rassensystem","rasse"), PersistentDataType.STRING);

            //resistenzen und schaden
            if(!prasse.equals("no")){
                int k=0;
                while (k<1000){
                    k++;
                    if(utilitys.getcon(2).getString(prasse+".resistzenzen"+"."+k)==null)break;
                    String resi=utilitys.getcon(2).getString(prasse+".resistzenzen"+"."+k);
                    dmg/=2;
                }

                k=0;
                while (k<1000){
                    k++;
                    if(utilitys.getcon(2).getString(prasse+".schwächen"+"."+k)==null)break;
                    String resi=utilitys.getcon(2).getString(prasse+".schwächen"+"."+k);
                    dmg*=2;
                }
            }

            switch (zusatzschadensart){
                case "Nekrotisch":
                    //make untot posible
                    en.getPersistentDataContainer().set(new NamespacedKey("rassensystem","nekro"), PersistentDataType.BOOLEAN,true);
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            en.getPersistentDataContainer().set(new NamespacedKey("rassensystem","nekro"), PersistentDataType.BOOLEAN,false);
                            cancel();
                        }
                    }.runTaskTimer(Arbeitundleben.getPlugin(),8*20,20);
                    break;
                case "True":
                    en.setHealth(en.getHealth()-dmg<=0 ? 0.0 : en.getHealth()-dmg);
                    iskamp(p,dmg);
                    return;
            }
        }

        if(!(en instanceof Player))dmg*=utilitys.getcon(1).get(name+".schadengegenmobs")==null ? utilitys.getcon(1).getDouble("schadengegenmobs") : utilitys.getcon(1).getDouble(name+".schadengegenmobs");
        if(en.getPersistentDataContainer().has(new NamespacedKey("rassensystem", "infight"), PersistentDataType.DOUBLE))dmg*=(getcon(2).getDouble("Grundkosten"+".Schadenimkampf")/100);
        ((LivingEntity) en).damage(dmg);


        iskamp(p,Double.valueOf(utilitys.getcon(1).getInt(name+".Schaden")));

    }

    public static void sumonatack(LivingEntity e, LivingEntity en){

        if(e instanceof Monster)((Monster) e).setTarget(en);
        if(e instanceof Wolf)((Wolf) e).setTarget(en);
        if(e instanceof Panda)((Panda) e).setTarget(en);
        if(e instanceof CraftSniffer)((CraftSniffer) e).setTarget(en);


        //mage
        if(!e.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "mage"), PersistentDataType.BOOLEAN))return;
        if(e.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.BOOLEAN))return;

        e.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "iscasting"), PersistentDataType.BOOLEAN,true);

        int ran= e.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "skilllv1"), PersistentDataType.BOOLEAN) ? 2
                : e.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "skilllv2"), PersistentDataType.BOOLEAN) ? 4
                : 5;

        Location pointToLookAt=en.getLocation();
        // Calculate the pitch and yaw angles to look at the point
        double deltaX = pointToLookAt.getX() - e.getLocation().getX();
        double deltaY = pointToLookAt.getY() - e.getLocation().getY();
        double deltaZ = pointToLookAt.getZ() - e.getLocation().getZ();
        double angleY = Math.toDegrees(Math.atan2(deltaX, deltaZ));
        double angleX = Math.toDegrees(Math.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ)));

        // Teleport the entity to adjust its orientation
        Location newLocation = e.getLocation().clone();
        newLocation.setPitch((float) -angleX);
        newLocation.setYaw((float) angleY);

        e.teleport(newLocation);


        switch (ThreadLocalRandom.current().nextInt(1,(ran+1))){
            case 1:
                utilitys.movebackwhilecast((Monster) e);
                summenskills.feurhand((Monster) e);
                break;
            case 2:
                utilitys.movebackwhilecast((Monster) e);
                summenskills.smite((Monster) e);
                break;
        }
    }

    public static FileConfiguration getcon(Integer num){

        File file = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        file = new File("plugins/KMS Plugins/Rassensystem","Rassen.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file);

        file = new File("plugins/KMS Plugins/Klassensysteem","begleiterskilltree.yml");
        FileConfiguration con3= YamlConfiguration.loadConfiguration(file);

        file = new File("plugins/KMS Plugins/Klassensysteem","custemmodelds.yml");
        FileConfiguration con4= YamlConfiguration.loadConfiguration(file);


        return num==1 ? con : num==2 ? con2 : num==3 ? con3 : con4;

    }

    public static LivingEntity getfamilia(Player p){

        for(Entity en : p.getWorld().getNearbyEntities(p.getLocation(),32,32,32)){
            if(en instanceof LivingEntity
                    && en.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING)
                    && en.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "owner"), PersistentDataType.STRING).equals(p.getUniqueId().toString())){
                    return (LivingEntity) en;
            }
        }

        return null;
    }

    public static ArrayList<String> getlore(Player p, String familia, boolean loreelei){

        //lore
        ArrayList<String> lore=new ArrayList<>();
        if(loreelei){
            lore.add(familia);
            return lore;
        }

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"leben"), PersistentDataType.DOUBLE)){
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"leben"), PersistentDataType.DOUBLE, 100.0);
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"angrifsdamage"), PersistentDataType.DOUBLE,100.0);
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"bewegungsgeschwindigkeit"), PersistentDataType.DOUBLE,100.0);
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"invgrose"), PersistentDataType.INTEGER,9);
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"lv"), PersistentDataType.INTEGER,0);
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER,0);
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"xp"), PersistentDataType.DOUBLE,0.0);
        }

        double leben=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"leben"), PersistentDataType.DOUBLE);
        double speed=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"bewegungsgeschwindigkeit"), PersistentDataType.DOUBLE);
        double atk=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"angrifsdamage"), PersistentDataType.DOUBLE);
        int infgröße=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"invgrose"), PersistentDataType.INTEGER);

        int lv=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"lv"), PersistentDataType.INTEGER);
        int sk=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER);
        double xp=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"xp"), PersistentDataType.DOUBLE);

        int xpneaded=utilitys.getcon(3).getInt("Lvkosten"+"."+0+".xpneaded");

        double xpinccrese=(utilitys.getcon(3).getInt("Lvkosten"+".persentrise")+100);
        xpinccrese/=100;

        if(utilitys.getcon(3).get("Lvkosten"+"."+lv+".xpneaded")==null){
            for(int i=0;i<=lv;i++){
                xpneaded*=xpinccrese;
            }
        } else {
            xpneaded=utilitys.getcon(3).getInt("Lvkosten"+"."+lv+".xpneaded");
        }


        lore.add("Level: "+lv);
        lore.add("Skillpunkte: "+sk);
        lore.add("xp :"+(int) xp+"/"+xpneaded);
        lore.add("Leben: "+leben+"%");
        lore.add("Geschwindigkeit: "+speed+"%");
        if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"angfiff"), PersistentDataType.BOOLEAN)){
            lore.add("Schaden: "+atk+"%");
        } else if (p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"inventar"), PersistentDataType.BOOLEAN)) {
            lore.add("Inventar Größe: "+infgröße);
        }

        return lore;
    }
}
