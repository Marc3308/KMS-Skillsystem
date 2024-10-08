package me.marc3308.arbeitundleben.commands;

import me.marc3308.arbeitundleben.Arbeitundleben;
import me.marc3308.arbeitundleben.utilitys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class familiaskilltree implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING))return false;
        String familia=p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "hasfamilia"), PersistentDataType.STRING);

        //creat skill points
        ItemStack xpstack=new ItemStack(Material.valueOf(utilitys.getcon(4).getString("Verbleibendeskillpunkte"+".Block")));
        int sk=p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER) ? p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"skillpunkte"), PersistentDataType.INTEGER) : 0;
        p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"sk"), PersistentDataType.INTEGER,sk);
        ItemMeta xp_meta = xpstack.getItemMeta();
        xp_meta.setCustomModelData(utilitys.getcon(4).getInt("Verbleibendeskillpunkte"+".Custemmoddeldatataken"));
        xp_meta.setDisplayName(ChatColor.DARK_PURPLE+"Skillpunkte: "+sk);
        xpstack.setItemMeta(xp_meta);

        //create link
        ItemStack link=new ItemStack(Material.valueOf(utilitys.getcon(4).getString("skilltreelink"+".Block")));
        ItemMeta link_meta = link.getItemMeta();
        link_meta.setDisplayName(" ");

        int skill = p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"skillnow"), PersistentDataType.INTEGER) ?
                p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"skillnow"), PersistentDataType.INTEGER)
                : 0;

        //create the gui
        int größe=skill==0 ? 27 : 54;
        Inventory auswahl= Bukkit.createInventory(p,größe,"Begleiter Auswahl > Skilltree");


        String skillnow=skill<=9 ? skill==0 ? "Grundtrees" : "Grundtrees"+"."+skill : String.valueOf(skill);
        int auflauf=skill==0 ? 1 : 3;

        try {

            for(int i=0;i<auflauf;i++){

                //how mutch is the fish
                int auswahlmöglichkeiten =utilitys.getcon(3).getString(skillnow+".Anzahlentscheidungen")!=null
                        ? p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+String.valueOf(skillnow)), PersistentDataType.INTEGER)
                        ? 1
                        : utilitys.getcon(3).getInt(skillnow+".Anzahlentscheidungen")
                        : 1 ;

                if(p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), familia+String.valueOf(skillnow)), PersistentDataType.INTEGER)
                        && p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+String.valueOf(skillnow)), PersistentDataType.INTEGER)!=0){
                    skillnow=skillnow+"."+p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(),familia+String.valueOf(skillnow)), PersistentDataType.INTEGER);
                }


                for(int j=1;j<=auswahlmöglichkeiten;j++){

                    if(auswahlmöglichkeiten!=1){

                        String auswahlskill = skillnow;

                        if(j!=1){

                            String cudata =".Custemmoddeldatasideways";

                            if((j-1)==(Math.ceil((auswahlmöglichkeiten/2)))){
                                cudata=".Custemmoddeldatamiddel";
                            }

                            link_meta.setCustomModelData(utilitys.getcon(4).getInt("skilltreelink"+cudata));
                            link.setItemMeta(link_meta);
                            auswahl.setItem((skill==0 ? 11 :(47-(i*18)))-auswahlmöglichkeiten+(j*2),link);

                        }

                        auswahlskill=skillnow+"."+j;
                        ItemStack grundklasse =build(auswahlskill,j,utilitys.getcon(3));
                        auswahl.setItem((skill==0 ? 12 :(48-(i*18)))-auswahlmöglichkeiten+(j*2),grundklasse);


                    } else {

                        ItemStack grundklasse =build(skillnow,i,utilitys.getcon(3));
                        auswahl.setItem((skill==0 ? 13 : 49-(i*18)),grundklasse);
                        skillnow=String.valueOf(utilitys.getcon(3).getInt(skillnow+".Nextskill"));

                    }

                    //check if next skill exist
                    if(utilitys.getcon(3).getString(skillnow)==null) {
                        auswahl.setItem(0,xpstack);
                        p.openInventory(auswahl);
                        return true;
                    }

                    link_meta.setCustomModelData(utilitys.getcon(4).getInt("skilltreelink"+".Custemmoddeldatadown"));
                    link.setItemMeta(link_meta);
                    auswahl.setItem(skill==0 ? 4 : 49-(i*18)-9,link);

                }



            }
        } catch (NullPointerException e){
            System.out.println(ChatColor.DARK_RED+"There is a problem with the Begleiterskill Nr.: "+p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), familia+"skillpunkte"), PersistentDataType.INTEGER));
        }

        auswahl.setItem(0,xpstack);
        p.openInventory(auswahl);
        return false;
    }

    public ItemStack build(String s,int cus, FileConfiguration con){


        //die grundaten
        String name= con.getString(s+".AnzeigeName");
        String beschreibung= con.getString(s+".Beschreibung");
        String block = con.getString(s+".Block");
        int bonimal = con.getInt(s+".Bonianzahl");
        double bonimun;
        String boniname;
        int custemmodeldata = 1;


        //check if used or not
        if(cus==0){
            custemmodeldata = con.getInt(s+".Custemmoddeldatataken");
        } else {
            custemmodeldata = con.getInt(s+".Custemmoddeldatafresh");
        }

        //create the block
        ItemStack grundklasse =new ItemStack(Material.valueOf(block));
        ItemMeta grund_meta = grundklasse.getItemMeta();
        grund_meta.setCustomModelData(custemmodeldata);
        grund_meta.setDisplayName(name);
        ArrayList<String> grund_story =new ArrayList<>();
        grund_story.add(beschreibung);
        if(con.getString(s+".Titel")!=null)grund_story.add("Titel: "+ con.getString(s+".Titel"));
        for(int k=1;k<bonimal+1;k++){
            boniname = con.getString(s+".Boni"+"."+k+".Name");
            bonimun = con.getDouble(s+".Boni"+"."+k+".Wirkung");
            //boni
            switch (boniname){
                case "skill":
                    String ss=con.getString(s+".Boni"+"."+k+".Wirkung");
                    grund_story.add(ChatColor.LIGHT_PURPLE+"Neuer Skill: "+ utilitys.getcon(1).getString(ss+".AnzeigeName")!=null ? utilitys.getcon(1).getString(ss+".AnzeigeName") : ss);
                    grund_story.add(ChatColor.YELLOW+"RECHTS CLICK für mehr infos");
                    break;
                case "bewegungsgeschwindigkeit":
                    grund_story.add("+"+bonimun+"% Bewegungsgeschwindigkeit");
                    break;
                case "fahigkeitsgeschwindigkeit":
                    grund_story.add("-"+bonimun+"% Fähigkeits Abklingzeit");
                    break;
                case "fahigkeitscritchance":
                    grund_story.add("+"+bonimun+"% Fähigkeits Critische Treffer Chance");
                    break;
                case "fahigkeitscritdmg":
                    grund_story.add("+"+bonimun+"% Fähigkeits Critischer Treffer Schaden");
                    break;
                case "fahigkeitsschaden":
                    grund_story.add("+"+bonimun+"% Fähigkeitsschaden");
                    break;
                case "waffengeschwindigkeit":
                    grund_story.add("-"+bonimun+"% Waffen Abklingzeit");
                    break;
                case "waffencritchance":
                    grund_story.add("+"+bonimun+"% Waffen Critische Treffer Chance");
                    break;
                case "waffencritdmg":
                    grund_story.add("+"+bonimun+"% Waffen Critischer Treffer Schaden");
                    break;
                case "waffenschaden":
                    grund_story.add("+"+bonimun+"% Waffenschaden");
                    break;
                case "leben":
                    grund_story.add("+"+bonimun+" Leben");
                    break;
                case "lebenreg":
                    grund_story.add("+"+bonimun+" Lebensregeneration");
                    break;
                case "ausdauer":
                    grund_story.add("+"+bonimun+" Ausdauer");
                    break;
                case "ausdauerreg":
                    grund_story.add("+"+bonimun+" Ausdauerregeneration");
                    break;
                case "mana":
                    grund_story.add("+"+bonimun+" Mana");
                    break;
                case "manareg":
                    grund_story.add("+"+bonimun+" Manaregeneration");
                    break;
                default:
                    grund_story.add("+"+bonimun+" "+boniname);
                    break;
            }
        }
        grund_meta.setLore(grund_story);
        grundklasse.setItemMeta(grund_meta);

        return grundklasse;
    }
}
