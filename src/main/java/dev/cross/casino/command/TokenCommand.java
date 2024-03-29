package dev.cross.casino.command;

import dev.cross.casino.player.CasinoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TokenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // /token 0 -- set your tokens
        // token etery 0

        if (!commandSender.isOp()) return true;

        if (args.length == 0)  {
            commandSender.sendMessage("Correct usage: /token <player?> <int>");
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            if (!(commandSender instanceof Player)) return true;
            player = (Player) commandSender;
        }
        int tokens = Integer.parseInt(args[args.length - 1]);
        setPlayerToken(player, tokens);
        commandSender.sendMessage("Tokens were successfully set");
        return true;
    }

    private void setPlayerToken(Player player, int token) {
        player.sendMessage("Your token amount was set to " + token) ;
        CasinoPlayer.from(player).setCurrency(token);
    }
}
