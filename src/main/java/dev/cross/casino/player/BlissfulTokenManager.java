package dev.cross.casino.player;

import dev.cross.blissfulcore.api.BlissfulAPI;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

public class BlissfulTokenManager implements TokenManager {
    private final Player player;

    public BlissfulTokenManager(Player player) {
        this.player = player;
    }

    @Override
    public void setTokens(int i) {
        BlissfulAPI.getImpl().setTokensFor(player, i);
    }

    @Override
    public int getTokens() {
        return BlissfulAPI.getImpl().getTokens(player);
    }
}
