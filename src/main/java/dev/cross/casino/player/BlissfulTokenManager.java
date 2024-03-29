package dev.cross.casino.player;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

public class BlissfulTokenManager implements TokenManager {
    private static final String TOKEN_OBJECTIVE = "TokensThisEvent";
    private final Player player;

    public BlissfulTokenManager(Player player) {
        this.player = player;
    }

    @Override
    public void setTokens(int i) {
        Objective tokenObjective = this.player.getScoreboard().getObjective(TOKEN_OBJECTIVE);
        if (tokenObjective == null) return;
        tokenObjective.getScore(player).setScore(i);
    }

    @Override
    public int getTokens() {
        Objective tokenObjective = this.player.getScoreboard().getObjective(TOKEN_OBJECTIVE);
        if (tokenObjective == null) throw new RuntimeException("Token Objective couldn't be reached");
        return tokenObjective.getScore(player).getScore();
    }
}
