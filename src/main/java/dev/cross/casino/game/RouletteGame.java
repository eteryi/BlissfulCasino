package dev.cross.casino.game;

import dev.cross.blissfulcore.ui.BColors;
import dev.cross.casino.Casino;
import dev.cross.casino.player.CasinoPlayer;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.BetInventory;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;

public class RouletteGame {
    public static final int GAME_TIME_IN_SECONDS = 5;

    public enum Color {
        RED {
            @Override
            public ItemStack getItemStack() {

                return BetInventory.getItemStackWith(new ItemStack(BElements.RED_CHIP.first()), BElements.RED_CHIP.second(), Component.text("Red").color(BColors.RED));
            }

            @Override
            public Color getReverse() {
                return Color.BLACK;
            }
        }, BLACK {
            @Override
            public ItemStack getItemStack() {
                return BetInventory.getItemStackWith(new ItemStack(BElements.BLACK_CHIP.first()), BElements.BLACK_CHIP.second(), Component.text("Black").color(BColors.LIGHT_PURPLE));
            }

            @Override
            public Color getReverse() {
                return Color.RED;
            }
        };

        public abstract ItemStack getItemStack();
        public abstract Color getReverse();
    }

    private final Player player;
    private final int betAmount;
    private final Color betColor;
    private Color winningColor;

    public RouletteGame(Player player, int betAmount, Color betColor) {
        this.player = player;
        this.betAmount = betAmount;
        this.betColor = betColor;
    }

    public void start() {
        CasinoPlayer casinoPlayer = CasinoPlayer.from(player);
        if (casinoPlayer.getCurrency() - betAmount < 0) {
            System.out.printf("%s shouldn't have a currency below 0 after betting... this is troublesome.%n", player.getName());
            return;
        }
        casinoPlayer.setCurrency(casinoPlayer.getCurrency() - betAmount);

        Random random = new Random();

        if (random.nextInt(0, 101) <= 40) {
            winningColor = this.betColor;
        } else {
            winningColor = this.betColor.getReverse();
        }

        // Component message = BComponents.PREFIX.append(Component.text("You have won ").color(BColors.LIGHT_PURPLE).append(Component.text(betAmount * 2).color(BColors.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text(" tokens").color(BColors.LIGHT_PURPLE)));
        //Component message = BComponents.PREFIX.append(Component.text("You have lost ").color(BColors.LIGHT_PURPLE).append(Component.text(betAmount).color(BColors.RED).decorate(TextDecoration.BOLD)).append(Component.text(" tokens").color(BColors.LIGHT_PURPLE)));
        new BukkitRunnable() {
            @Override
            public void run() {
                LegacyComponentSerializer componentSerializer = BukkitComponentSerializer.legacy();
                player.closeInventory();
                if (winningColor == betColor) {
                    // Component message = BComponents.PREFIX.append(Component.text("You have won ").color(BColors.LIGHT_PURPLE).append(Component.text(betAmount * 2).color(BColors.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text(" tokens").color(BColors.LIGHT_PURPLE)));
                    Component message = Component.text("[ ").color(BColors.LIGHT_PURPLE).append(Component.text("+").color(BColors.YELLOW)).append(Component.text(betAmount * 2).color(BColors.YELLOW)).append(BElements.TOKEN_UNICODE).append(Component.text(" ]"));
                    player.sendTitle("", componentSerializer.serialize(message), 20, 100, 20);
                    player.playSound(player, Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
                    casinoPlayer.setCurrency(casinoPlayer.getCurrency() + (betAmount * 2));
                } else {
                    //Component message = BComponents.PREFIX.append(Component.text("You have lost ").color(BColors.LIGHT_PURPLE).append(Component.text(betAmount).color(BColors.RED).decorate(TextDecoration.BOLD)).append(Component.text(" tokens").color(BColors.LIGHT_PURPLE)));
                    Component message = Component.text("[ ").color(BColors.LIGHT_PURPLE).append(Component.text("-").color(BColors.RED)).append(Component.text(betAmount).color(BColors.RED)).append(BElements.TOKEN_UNICODE).append(Component.text(" ]"));
                    player.playSound(player, Sound.ITEM_SHIELD_BREAK, 1.0f, 0.5F);
                    player.sendTitle("", componentSerializer.serialize(message), 20, 100, 20);
                }
            }
        }.runTaskLater(Casino.getPlugin(), GAME_TIME_IN_SECONDS * 20);
    }
}
