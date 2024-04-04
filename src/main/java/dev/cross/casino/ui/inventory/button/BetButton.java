package dev.cross.casino.ui.inventory.button;

import dev.cross.blissfulcore.ui.BSounds;
import dev.cross.blissfulcore.ui.inventory.button.AbstractClickableButton;
import dev.cross.casino.Casino;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.BetInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BetButton extends AbstractClickableButton {
    private final BetInventory inventory;
    private boolean isPressed;

    public BetButton(BetInventory inventory) {
        super(3, new Hitbox(BElements.BET_TEXT, List.of(Component.text("Click here to bet!"))), new Texture(BElements.BET_BUTTON.first(), BElements.BET_BUTTON.second()));
        this.inventory = inventory;
        this.isPressed = false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (this.isPressed) return;
        this.isPressed = true;
        assert event.getClickedInventory() != null;
        ItemStack stack = this.getTexture();
        stack.setType(BElements.SELECTED_BET_BUTTON.first());
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(BElements.SELECTED_BET_BUTTON.second());
        stack.setItemMeta(meta);
        int textureSlot = this.getTextureSlot();
        event.getClickedInventory().setItem(textureSlot, stack);

        if (event.getWhoClicked() instanceof Player p) {
            p.playSound(p, BSounds.BUTTON_CLICK, 1.0f, 0.65f);
        }

        if (!inventory.bet()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    isPressed = false;
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
