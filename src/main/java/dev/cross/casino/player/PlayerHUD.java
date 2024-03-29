package dev.cross.casino.player;

import dev.cross.blissfulcore.ui.BColors;
import dev.cross.casino.ui.BElements;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;

public class PlayerHUD {
    private final Player player;
    private final CasinoPlayer casinoPlayer;

    public PlayerHUD(Player player) {
        this.casinoPlayer = CasinoPlayer.from(player);
        this.player = player;
    }

    public void tick() {
        BungeeComponentSerializer componentSerializer = BungeeComponentSerializer.get();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, componentSerializer.serialize(BElements.TOKEN_DISPLAY.append(Component.text(casinoPlayer.getCurrency()).decoration(TextDecoration.BOLD, true).color(BColors.YELLOW))));
    }
}
