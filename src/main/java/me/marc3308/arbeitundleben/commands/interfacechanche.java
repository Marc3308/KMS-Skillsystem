package me.marc3308.arbeitundleben.commands;

import me.marc3308.arbeitundleben.Arbeitundleben;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class interfacechanche implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)
            || p.getPersistentDataContainer().get(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN)) {

            p.sendMessage("Bitte schließe zuerst dein Skill Gui");
         return false;
        }
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN)){
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"), PersistentDataType.BOOLEAN,true);
        } else {
            p.getPersistentDataContainer().remove(new NamespacedKey(Arbeitundleben.getPlugin(), "diffrentcastingmethode"));
        }
        p.sendMessage("Dein Skill Gui wurde erfolgreich gewechselt");
        return false;
    }
}
