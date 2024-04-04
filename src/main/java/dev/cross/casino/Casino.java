package dev.cross.casino;

import dev.cross.casino.command.CasinoCommand;
import dev.cross.casino.command.TestCommand;
import dev.cross.casino.command.TokenCommand;
import dev.cross.casino.event.PlayerListener;
import dev.cross.casino.player.CasinoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Casino extends JavaPlugin {
    private static JavaPlugin plugin;
    public static JavaPlugin getPlugin() { return plugin; }

    public static class State {
        private boolean active;

        private State() {
            this.active = false;
        }

        public void start() {
            Bukkit.getOnlinePlayers().forEach(it -> CasinoPlayer.from(it).start());
            this.active = true;
        }

        public void stop() {
            Bukkit.getOnlinePlayers().forEach(it -> { CasinoPlayer.from(it).stop(); it.closeInventory(); });
            this.active = false;
        }

        public boolean isActive() {
            return this.active;
        }
    }

    public static final State CASINO_STATE = new State();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        CASINO_STATE.start();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        registerCommands();
    }

    public void registerCommand(String commandName, CommandExecutor executor) {
        Objects.requireNonNull(getCommand(commandName)).setExecutor(executor);
    }

    public void registerCommands() {
        registerCommand("spawn-roulette", new TestCommand());
        registerCommand("token", new TokenCommand());
        registerCommand("casino", new CasinoCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
