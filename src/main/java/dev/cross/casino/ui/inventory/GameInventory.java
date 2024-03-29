package dev.cross.casino.ui.inventory;

import dev.cross.blissfulcore.ui.inventory.GUIInventory;
import org.bukkit.entity.Player;

public abstract class GameInventory extends GUIInventory {

    private final int betAmount;
    public GameInventory(Player player, int betAmount) {
        super(player, false);
        this.betAmount = betAmount;
    }

    public int getBetAmount() {
        return this.betAmount;
    }

    abstract public boolean startGame();
}
