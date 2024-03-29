package dev.cross.casino.game;

import dev.cross.blissfulcore.ui.BColors;
import dev.cross.blissfulcore.ui.BComponents;
import dev.cross.casino.Casino;
import dev.cross.casino.player.CasinoPlayer;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.BetInventory;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class RouletteGame {
    public static final int GAME_TIME_IN_SECONDS = 5;

    public enum Color {
        RED {
            @Override
            public ItemStack getItemStack() {

                return BetInventory.getItemStackWith(new ItemStack(BElements.RED_CHIP.first()), BElements.RED_CHIP.second(), Component.text("Red").color(BColors.RED));
            }
        }, BLACK {
            @Override
            public ItemStack getItemStack() {
                return BetInventory.getItemStackWith(new ItemStack(BElements.BLACK_CHIP.first()), BElements.BLACK_CHIP.second(), Component.text("Black").color(BColors.LIGHT_PURPLE));
            }
        };

        public abstract ItemStack getItemStack();
    }

    private final Player player;
    private final int betAmount;
    private final Inventory inventory;
    private final Color betColor;
    private Color winningColor;
    private BukkitTask gameTask;

    public RouletteGame(Player player, int betAmount, Color betColor, Inventory inventory) {
        this.player = player;
        this.betAmount = betAmount;
        this.betColor = betColor;
        this.inventory = inventory;
    }

    public void start() {
        CasinoPlayer casinoPlayer = CasinoPlayer.from(player);
        if (casinoPlayer.getCurrency() - betAmount < 0) {
            System.out.printf("%s shouldn't have a currency below 0 after betting... this is troublesome.%n", player.getName());
            return;
        }
        casinoPlayer.setCurrency(casinoPlayer.getCurrency() - betAmount);
        Random random = new Random();
        if (random.nextInt() % 2 == 0) {
            winningColor = Color.BLACK;
        } else {
            winningColor = Color.RED;
        }



        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                LegacyComponentSerializer componentSerializer = BukkitComponentSerializer.legacy();
                player.closeInventory();
                if (winningColor == betColor) {
                    Component message = BComponents.PREFIX.append(Component.text("You have won ").color(BColors.LIGHT_PURPLE).append(Component.text(betAmount * 2).color(BColors.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text(" tokens").color(BColors.LIGHT_PURPLE)));
                    player.sendMessage(componentSerializer.serialize(message));
                    casinoPlayer.setCurrency(casinoPlayer.getCurrency() + (betAmount * 2));
                } else {
                    Component message = BComponents.PREFIX.append(Component.text("You have lost ").color(BColors.LIGHT_PURPLE).append(Component.text(betAmount).color(BColors.RED).decorate(TextDecoration.BOLD)).append(Component.text(" tokens").color(BColors.LIGHT_PURPLE)));
                    player.sendMessage(componentSerializer.serialize(message));
                }
            }
        }.runTaskLater(Casino.getPlugin(), GAME_TIME_IN_SECONDS * 20);
    }
}
