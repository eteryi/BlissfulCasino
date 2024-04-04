package dev.cross.casino.ui.inventory.button;

import dev.cross.blissfulcore.ui.BColors;
import dev.cross.blissfulcore.ui.inventory.button.AbstractClickableButton;
import dev.cross.casino.Casino;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.GameInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SpinButton extends AbstractClickableButton {
    private final GameInventory inventory;
    private boolean isPressed;

    public SpinButton(GameInventory inventory) {
        super(3, new Hitbox(Component.text("Start the Game").color(BColors.BLUE), List.of()), new Texture(BElements.SPIN_BUTTON.first(), BElements.SPIN_BUTTON.second()));
        this.inventory =  inventory;
        this.isPressed = false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        assert event.getClickedInventory() != null;
        if (this.isPressed) return;
        this.isPressed = true;
        ItemStack stack = this.getTexture();
        stack.setType(BElements.SELECTED_SPIN_BUTTON.first());
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(BElements.SELECTED_SPIN_BUTTON.second());
        stack.setItemMeta(meta);
        int textureSlot = this.getTextureSlot();
        event.getClickedInventory().setItem(textureSlot, stack);

        if (event.getWhoClicked() instanceof Player p) p.playSound(p, Sound.ENTITY_ARROW_HIT_PLAYER, 0.5f, 0.5f);

        if (!inventory.startGame()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    isPressed = false;
                    stack.setType(BElements.SPIN_BUTTON.first());
                    ItemMeta meta = stack.getItemMeta();
                    meta.setCustomModelData(BElements.SPIN_BUTTON.second());
                    stack.setItemMeta(meta);
                    event.getClickedInventory().setItem(textureSlot, stack);
                }
            }.runTaskLater(Casino.getPlugin(), 4L);
        }
    }
}
