package me.marc3308.arbeitundleben.Eventlistener;

import me.marc3308.arbeitundleben.Arbeitundleben;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class onjoin implements Listener {

    @EventHandler
    public void onjoin(PlayerJoinEvent e){

        Player p=e.getPlayer();

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.DOUBLE)){
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "skillf"), PersistentDataType.BOOLEAN,false);
        }

        if(!p.getPersistentDataContainer().has(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE)){
            p.getPersistentDataContainer().set(new NamespacedKey(Arbeitundleben.getPlugin(), "entfernung"), PersistentDataType.DOUBLE,5.0);
        }
    }
}
