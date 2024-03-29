package dev.cross.casino.ui.display;

import dev.cross.blissfulcore.ui.display.WindowDisplay;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.BetInventory;
import dev.cross.casino.ui.inventory.RouletteInventory;
import dev.cross.casino.ui.inventory.BetInventory;
import dev.cross.casino.ui.inventory.RouletteInventory;
import org.bukkit.Location;

public class RouletteDisplay extends WindowDisplay {
    private final StartButton startButton;
    public RouletteDisplay() {
        super(BElements.ROULETTE_DISPLAY.second());
        this.startButton = new StartButton(player -> {
            BetInventory inventory = new BetInventory(player, pair -> new RouletteInventory(pair.getLeft(), pair.getRight()));
            inventory.open();
        });
    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);
        startButton.spawn(location.add(0, -0.5, 0));
    }

    @Override
    protected void onSeen() {

    }

    @Override
    protected void unSeen() {

    }
}
