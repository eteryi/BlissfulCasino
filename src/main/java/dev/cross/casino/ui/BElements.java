package dev.cross.casino.ui;

import dev.cross.blissfulcore.Pair;
import dev.cross.blissfulcore.ui.BColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

public class BElements {

    public static final Component NEGATIVE_SPACE = Component.text("\uE201");
    public static final Component TOKEN_UNICODE = Component.text("\uE207").color(NamedTextColor.WHITE);
    public static final Component BET_SCREEN = NEGATIVE_SPACE.append(Component.text("\uE200").color(NamedTextColor.WHITE));
    public static final Component ROULETTE_SCREEN = NEGATIVE_SPACE.append(Component.text("\uE205").color(NamedTextColor.WHITE));

    public static final Pair<Material, Integer> LOWER_BUTTON = Pair.of(Material.PAPER, 1);
    public static final Pair<Material, Integer> HIGHER_BUTTON = Pair.of(Material.PAPER, 2);
    public static final Pair<Material, Integer> RED_CHIP = Pair.of(Material.PAPER, 10);
    public static final Pair<Material, Integer> BLACK_CHIP = Pair.of(Material.PAPER, 11);
    public static final Pair<Material, Integer> TOKEN_ITEM = Pair.of(Material.PAPER, 3);
    public static final Pair<Material, Integer> BET_BUTTON = Pair.of(Material.GOLD_NUGGET, 1);
    public static final Pair<Material, Integer> SELECTED_BET_BUTTON = Pair.of(Material.GOLD_NUGGET, 2);
    public static final Pair<Material, Integer> SPIN_BUTTON = Pair.of(Material.GOLD_NUGGET, 3);
    public static final Pair<Material, Integer> SELECTED_SPIN_BUTTON = Pair.of(Material.GOLD_NUGGET, 4);

    public static final Pair<Material, Integer> ROULETTE_SPINNING_ITEM = Pair.of(Material.GOLD_NUGGET, 5);
    public static final Pair<Material, Integer> ROULETTE_RED_ITEM = Pair.of(Material.GOLD_NUGGET, 6);

    public static final Pair<Material, Integer> ROULETTE_DISPLAY = Pair.of(Material.PAPER, 7);
    public static final Pair<Material, Integer> START_BUTTON_DISPLAY = Pair.of(Material.PAPER, 8);
    public static final Pair<Material, Integer> SELECTED_START_BUTTON_DISPLAY = Pair.of(Material.PAPER, 9);

    public static final Component HIGHER_TEXT = Component.text("Click here to increase the Bet Amount! (Shift: +100)").color(BColors.GREEN).decoration(TextDecoration.ITALIC, false);
    public static final Component LOWER_TEXT = Component.text("Click here to decrease the Bet Amount! (Shift: -100)").color(BColors.RED).decoration(TextDecoration.ITALIC, false);
    public static final Component BET_TEXT = Component.text("Bet Button").color(BColors.YELLOW).decoration(TextDecoration.ITALIC, false);

    public static final Component TOKEN_SPACING = Component.text("\uE203");
    public static final Component HOTBAR_SPACING = Component.text("\uE204");
    public static final Component TOKEN_DISPLAY = HOTBAR_SPACING.append(Component.text("\uE202")).append(TOKEN_SPACING);
}
