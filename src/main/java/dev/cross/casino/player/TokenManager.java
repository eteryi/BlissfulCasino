package dev.cross.casino.player;

import org.bukkit.entity.Player;

public interface TokenManager {
    void setTokens(int i);
    int getTokens();

    class SimpleTokenManager implements TokenManager {
        private int tokens;

        public SimpleTokenManager() {
            this.tokens = 0;
        }

        @Override
        public void setTokens(int i) {
            this.tokens = i;
        }

        @Override
        public int getTokens() {
            return this.tokens;
        }
    }

    static TokenManager getImpl(Player player) {
        return new BlissfulTokenManager(player);
    }
}
