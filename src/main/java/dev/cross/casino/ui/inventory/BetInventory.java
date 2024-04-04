package dev.cross.casino.ui.inventory;

import dev.cross.blissfulcore.ui.BColors;
import dev.cross.blissfulcore.ui.BSounds;
import dev.cross.blissfulcore.ui.inventory.GUIInventory;
import dev.cross.casino.Casino;
import dev.cross.casino.player.CasinoPlayer;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.button.BetButton;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Function;

public class BetInventory extends GUIInventory {
    public static class Pair<T, R> {
        private final T value;
        private final R sec;
        public Pair(T value, R sec) {
            this.value = value;
            this.sec = sec;
        }

        public T getLeft() {
            return this.value;
        }

        public R getRight() {
            return this.sec;
        }
    }

    private int betAmount;
    private final Function<Pair<Player, Integer>, ? extends GameInventory> gameInventorySupplier;

    public BetInventory(Player player, Function<Pair<Player, Integer>, ? extends GameInventory> function) {
        super(player, false);
        this.betAmount = 0;
        this.gameInventorySupplier = function;
        addButton(new BetButton(this), 14);
    }

    @Override
    public void open() {
        super.open();
        getPlayer().playSound(getPlayer(), BSounds.INVENTORY_OPEN, 1.0f, 1.0f);
    }

    private ItemStack getAmountAsPaper() {
        ItemStack paper = new ItemStack(BElements.TOKEN_ITEM.first());
        ItemMeta meta = paper.getItemMeta();
        if (meta == null) throw new RuntimeException("welp...");
        LegacyComponentSerializer componentSerializer = BukkitComponentSerializer.legacy();
        meta.setDisplayName(componentSerializer.serialize(Component.text("Bet Amount: ").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false).append(Component.text(betAmount).color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))));
        meta.setCustomModelData(BElements.TOKEN_ITEM.second());
        paper.setItemMeta(meta);
        return paper;
    }

    public static ItemStack getItemStackWith(ItemStack stack, int customModelData, Component text) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) throw new RuntimeException("welp...");
        meta.setCustomModelData(customModelData);
        LegacyComponentSerializer componentSerializer = BukkitComponentSerializer.legacy();
        meta.setDisplayName(componentSerializer.serialize(text));
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    protected Inventory inventorySupplier() {
        // TODO Fix
        Inventory inventory = Bukkit.createInventory(getPlayer(), 27, BukkitComponentSerializer.legacy().serialize(BElements.BET_SCREEN));
        inventory.setItem(11, getAmountAsPaper());

        inventory.setItem(2, getItemStackWith(new ItemStack(BElements.HIGHER_BUTTON.first()), BElements.HIGHER_BUTTON.second(), BElements.HIGHER_TEXT));
        inventory.setItem(20, getItemStackWith(new ItemStack(BElements.LOWER_BUTTON.first()), BElements.LOWER_BUTTON.second(), BElements.LOWER_TEXT));
        return inventory;
    }

    private void update(Inventory inventory) {
        getPlayer().playSound(getPlayer(), BSounds.BUTTON_CLICK, 1.0f, 1.0f);
        inventory.setItem(11, getAmountAsPaper());
    }

    public boolean bet() {
        if (betAmount <= 0) {
            LegacyComponentSerializer componentSerializer = BukkitComponentSerializer.legacy();
            getPlayer().sendMessage(componentSerializer.serialize(Component.text(" ⚠ You can't bet 0 tokens buddy").color(BColors.RED)));
            return false;
        }
        GameInventory inventory = gameInventorySupplier.apply(new Pair<>(getPlayer(), this.betAmount));
        BetInventory betInventory = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                betInventory.close();
                inventory.open();
            }
        }.runTaskLater(Casino.getPlugin(), 8L);
        return true;
    }

    private static final int HARD_BETTING_LIMIT = 1500;
    @Override
    protected void onInteract(InventoryClickEvent event) {
        int amount = event.isShiftClick() ? 100 : 10;
        if (event.getSlot() == 20) {
            this.betAmount = Math.max(0, betAmount - amount);
            if (event.getClickedInventory() != null) {
                update(event.getClickedInventory());
            }
        }
        if (event.getSlot() == 2) {
            this.betAmount = Math.min(HARD_BETTING_LIMIT, Math.min(CasinoPlayer.from(getPlayer()).getCurrency(), betAmount + amount));
            if (event.getClickedInventory() != null) {
                update(event.getClickedInventory());
            }
        }
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }
}
