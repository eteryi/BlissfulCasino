package dev.cross.casino.player;

import dev.cross.blissfulcore.ui.display.WindowDisplay;
import dev.cross.casino.Casino;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class CasinoPlayer {
    private static final HashMap<UUID, CasinoPlayer> players = new HashMap<>();

    public static CasinoPlayer from(Player player) {
        if (players.get(player.getUniqueId()) == null) players.put(player.getUniqueId(), new CasinoPlayer(player.getUniqueId()));
        return players.get(player.getUniqueId());
    }

    private PlayerRaycast raycast;
    private final UUID player;
    private WindowDisplay currentlyLooking;
    private final TokenManager tokenManager;
    private BukkitTask tickTask;

    private CasinoPlayer(UUID player) {
        this.player = player;
        this.tokenManager = TokenManager.getImpl(this.getPlayer());
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public int getCurrency() {
        return this.tokenManager.getTokens();
    }

    public void setCurrency(int i) {
        this.tokenManager.setTokens(i);
    }

    private void initRaycast(Player player) {
        this.raycast = new PlayerRaycast(player);
        this.raycast.addBehavior(entity -> {
            if (entity == null) {
                if (this.currentlyLooking != null) {
                    this.currentlyLooking.unLook(this.getPlayer());
                }
                return;
            }

            WindowDisplay display = WindowDisplay.from(entity);
            if (display == null) {
                if (this.currentlyLooking != null) {
                    this.currentlyLooking.unLook(this.getPlayer());
                }
                return;
            }
            if (display != this.currentlyLooking && this.currentlyLooking != null) {
                this.currentlyLooking.unLook(this.getPlayer());
            }
            display.onLookAt(this.getPlayer());
            this.currentlyLooking = display;
        });
    }
    public void start() {
        Player p = getPlayer();
        if (p == null || !p.isOnline()) return;

        initRaycast(p);
        this.tickTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (raycast != null) {
                    raycast.tick();
                }
            }
        }.runTaskTimer(Casino.getPlugin(), 0L, 1L);
    }

    public void stop() {
        if (this.currentlyLooking != null) {
            this.currentlyLooking.unLook(this.getPlayer());
        }
        if (this.tickTask != null) {
            this.tickTask.cancel();
        }

        this.tickTask = null;
    }
}
