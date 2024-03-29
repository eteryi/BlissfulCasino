package dev.cross.casino.ui.inventory.button;

import dev.cross.blissfulcore.ui.inventory.button.AbstractClickableButton;
import dev.cross.casino.Casino;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.BetInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BetButton extends AbstractClickableButton {
    private final BetInventory inventory;
    public BetButton(BetInventory inventory) {
        super(3, new Hitbox(BElements.BET_TEXT, List.of(Component.text("Click here to bet!"))), new Texture(BElements.BET_BUTTON.first(), BElements.BET_BUTTON.second()));
        this.inventory = inventory;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        assert event.getClickedInventory() != null;
        ItemStack stack = this.getTexture();
        stack.setType(BElements.SELECTED_BET_BUTTON.first());
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(BElements.SELECTED_BET_BUTTON.second());
        stack.setItemMeta(meta);
        int textureSlot = this.getTextureSlot();
        event.getClickedInventory().setItem(textureSlot, stack);

        if (!inventory.bet()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    stack.setType(BElements.BET_BUTTON.first());
                    ItemMeta meta = stack.getItemMeta();
                    meta.setCustomModelData(BElements.BET_BUTTON.second());
                    stack.setItemMeta(meta);
                    event.getClickedInventory().setItem(textureSlot, stack);
                }
            }.runTaskLater(Casino.getPlugin(), 4L);
        }
    }
}
