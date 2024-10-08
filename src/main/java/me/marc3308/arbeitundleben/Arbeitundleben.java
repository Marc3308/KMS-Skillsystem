package me.marc3308.arbeitundleben;

import me.marc3308.arbeitundleben.Eventlistener.gui;
import me.marc3308.arbeitundleben.Eventlistener.onjoin;
import me.marc3308.arbeitundleben.Eventlistener.scorbordtoggle;
import me.marc3308.arbeitundleben.commands.familiaskilltree;
import me.marc3308.arbeitundleben.commands.interfacechanche;
import me.marc3308.arbeitundleben.skills.Activate;
import me.marc3308.arbeitundleben.skills.Findfamiliar;
import me.marc3308.arbeitundleben.skills.Invswitch;
import me.marc3308.arbeitundleben.skills.Stander;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Arbeitundleben extends JavaPlugin {


    public static Arbeitundleben plugin;
    @Override
    public void onEnable() {

        plugin=this;

        //todo alle skills testen für server performens
        //todo familia tp passiert was komisches
        //todo sniffer macht wirde dinge

        //Plugin startup logic
        File file = new File("plugins/KMS Plugins/Arbeitundleben","Skills.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        Bukkit.getPluginManager().registerEvents(new gui(),this);
        Bukkit.getPluginManager().registerEvents(new onjoin(),this);
        Bukkit.getPluginManager().registerEvents(new Stander(),this);
        Bukkit.getPluginManager().registerEvents(new Activate(),this);
        Bukkit.getPluginManager().registerEvents(new Invswitch(),this);
        Bukkit.getPluginManager().registerEvents(new Findfamiliar(),this);
        Bukkit.getPluginManager().registerEvents(new scorbordtoggle(),this);
        getCommand("switchskillgui").setExecutor(new interfacechanche());
        getCommand("familiatree").setExecutor(new familiaskilltree());


        //listen
        List<String> infos=new ArrayList<>();
        infos.add("This yml is for the rassen, there are two sections to this, the names and the numbers");
        infos.add("The name is for the item creation, what is that rase, how is it called etc...");
        infos.add("The numbers is for activation, there we nead the stats");
        infos.add("For correckt use the name neads: name, beschreibung, custemmoddeldata");
        infos.add("For correckt use the nummbers nead: custemmoddeldata, leben, lebenreg, ausdauer, ausdauerreg, mana, manareg, name");
        infos.add("wichtig noch die grundwerte sind die grundwerte jedes spielers auf den dann die boni der klassen und rassen draufgerechnet werden");

        if(con.get("skills")==null){

            con.set("schadengegenmobs",2.0);
            con.set("skills"+".1","smite");
            con.set("skills"+".2","lightningstrike");
            con.set("skills"+".3","spy");
            con.set("skills"+".4","awayusmaximus");
            con.set("skills"+".5","doorway");
            con.set("skills"+".6","greaterteleportation");
            con.set("skills"+".7","firehands");
            con.set("skills"+".8","blackhole");
            con.set("skills"+".9","curewounds");
            con.set("skills"+".10","healingworld");
            con.set("skills"+".11","greaterhalnextlove");
            con.set("skills"+".12","sturmangriff");
            con.set("skills"+".13","findfamiliar");
            con.set("skills"+".14","maximisemagicgardenofeden");
            con.set("skills"+".15","fly");
            con.set("skills"+".16","wansin");
            con.set("skills"+".17","createundead");
            con.set("skills"+".18","deathwaria");
            con.set("skills"+".19","undeadmage");
            con.set("skills"+".20","skeletarcher");
            con.set("skills"+".21","maxmimismagicsummongiant");
            con.set("skills"+".22","rundumschlag");
            con.set("skills"+".23","coruption");
            con.set("skills"+".24","maximisemagiccallgreaterlighningstomrexplotion");
            con.set("skills"+".25","seetrhoustone");
            con.set("skills"+".26","thunderwave");
            con.set("skills"+".27","wachstum");
            con.set("skills"+".28","meditation");
            con.set("skills"+".29","schwebekissen");
            con.set("skills"+".30","icebold");
            con.set("skills"+".31","firebold");
            con.set("skills"+".32","pandanerv");
            con.set("skills"+".33","selbstschutz");
            con.set("skills"+".34","anderschwache");
            con.set("skills"+".35","schlechtewelle");
            con.set("skills"+".36","nafernwelle");
            con.set("skills"+".37","magicmisial");
            con.set("skills"+".38","schildwal");
            con.set("skills"+".39","parieren");
            con.set("skills"+".40","erdolchung");
            con.set("skills"+".41","knockbackpfeil");
            con.set("skills"+".42","taunt");


            //not skills but passiven

            con.set("greaterteleportation"+".Block","DIAMOND");
            con.set("greaterteleportation"+".Custemmoddeldatataken",3);
            con.set("greaterteleportation"+".AnzeigeName","Greater Teleportation");
            con.set("greaterteleportation"+".Beschreibung","Du tpst dich wohin du willst");
            con.set("greaterteleportation"+".Kosten",1);
            con.set("greaterteleportation"+".Dauer",5);
            con.set("greaterteleportation"+".Kostenart","mana");
            con.set("greaterteleportation"+".Freiehande","twohand");

        }


        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        //begleiter skill tree
        file = new File("plugins/KMS Plugins/Klassensysteem","begleiterskilltree.yml");
        con= YamlConfiguration.loadConfiguration(file);
        infos.clear();
        infos.add("This is vor skilltree vrom tha doge work liks nurml skil tree");

        if(con.get("Grundtrees")==null){

            con.set("Grundtrees"+".Anzahlentscheidungen",3);
            con.set("Grundtrees"+".1"+".AnzeigeName","Angriff");
            con.set("Grundtrees"+".1"+".Beschreibung","Dein tier kann nun angreifen");
            con.set("Grundtrees"+".1"+".Block","DIAMOND");
            con.set("Grundtrees"+".1"+".Custemmoddeldatafresh",100);
            con.set("Grundtrees"+".1"+".Custemmoddeldatataken",101);
            con.set("Grundtrees"+".1"+".Lastskill",0);
            con.set("Grundtrees"+".1"+".Nextskill",11);
            con.set("Grundtrees"+".1"+".Bonianzahl",1);
            con.set("Grundtrees"+".1"+".Boni"+".1"+".Name","skill");
            con.set("Grundtrees"+".1"+".Boni"+".1"+".Wirkung","angfiff");

            con.set("Grundtrees"+".2"+".AnzeigeName","Reiten");
            con.set("Grundtrees"+".2"+".Beschreibung","Du kannst dein tier nun reiten");
            con.set("Grundtrees"+".2"+".Block","DIAMOND");
            con.set("Grundtrees"+".2"+".Custemmoddeldatafresh",100);
            con.set("Grundtrees"+".2"+".Custemmoddeldatataken",101);
            con.set("Grundtrees"+".2"+".Lastskill",0);
            con.set("Grundtrees"+".2"+".Nextskill",11);
            con.set("Grundtrees"+".2"+".Bonianzahl",1);
            con.set("Grundtrees"+".2"+".Boni"+".1"+".Name","skill");
            con.set("Grundtrees"+".2"+".Boni"+".1"+".Wirkung","reiten");

            con.set("Grundtrees"+".3"+".AnzeigeName","Inventar");
            con.set("Grundtrees"+".3"+".Beschreibung","Dein tier hat nun ein inventar");
            con.set("Grundtrees"+".3"+".Block","DIAMOND");
            con.set("Grundtrees"+".3"+".Custemmoddeldatafresh",100);
            con.set("Grundtrees"+".3"+".Custemmoddeldatataken",101);
            con.set("Grundtrees"+".3"+".Lastskill",0);
            con.set("Grundtrees"+".3"+".Nextskill",11);
            con.set("Grundtrees"+".3"+".Bonianzahl",1);
            con.set("Grundtrees"+".3"+".Boni"+".1"+".Name","skill");
            con.set("Grundtrees"+".3"+".Boni"+".1"+".Wirkung","inventar");

        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }


    }

    @Override
    public void onDisable() {
        System.out.println("Doing stuff");
    }

    public static Arbeitundleben getPlugin() {
        return plugin;
    }
}
