package dev.cross.casino.ui.display;

import dev.cross.blissfulcore.ui.display.InteractWindowDisplay;
import dev.cross.casino.ui.BElements;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public class StartButton extends InteractWindowDisplay {
    private final Consumer<Player> startFunction;
    public StartButton(Consumer<Player> startFunction) {
        super(BElements.START_BUTTON_DISPLAY.second());
        this.startFunction = startFunction;
        this.setInteractionOffset(new Vector(0, -0.5, 0));
    }

    public static ItemStack getItemStackWithModelData(ItemStack stack, int customModelData) {
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(customModelData);
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    protected void onSeen() {
        this.getDisplayEntity().setGlowing(true);
        ItemStack stack = this.getDisplayEntity().getItemStack();
        if (stack == null) return;
        this.getDisplayEntity().setItemStack(getItemStackWithModelData(stack, BElements.SELECTED_START_BUTTON_DISPLAY.second()));
    }

    @Override
    protected void unSeen() {
        this.getDisplayEntity().setGlowing(false);
        ItemStack stack = this.getDisplayEntity().getItemStack();
        if (stack == null) return;
        this.getDisplayEntity().setItemStack(getItemStackWithModelData(stack, BElements.START_BUTTON_DISPLAY.second()));
    }

    @Override
    public void onInteraction(Player player) {
        this.startFunction.accept(player);
    }
}
