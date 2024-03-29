package dev.cross.casino.event;

import dev.cross.casino.Casino;
import dev.cross.casino.player.CasinoPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        CasinoPlayer player = CasinoPlayer.from(e.getPlayer());
        if (Casino.CASINO_STATE.isActive()) player.start();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        CasinoPlayer player = CasinoPlayer.from(e.getPlayer());
        if (Casino.CASINO_STATE.isActive()) player.stop();
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
        event.setCancelled(true);
    }
}
