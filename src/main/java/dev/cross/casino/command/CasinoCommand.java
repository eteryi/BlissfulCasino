package dev.cross.casino.command;

import dev.cross.casino.Casino;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CasinoCommand implements CommandExecutor {

    //TODO
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.isOp()) return false;
        boolean bool = Boolean.parseBoolean(strings[0]);
        if (bool) {
            Casino.CASINO_STATE.start();
            commandSender.sendMessage("Started the Casino Mechanics!");
        } else {
            Casino.CASINO_STATE.stop();
            commandSender.sendMessage("Stopping the Casino!");
        }
        return true;
    }
}
