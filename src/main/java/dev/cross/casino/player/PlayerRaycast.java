package dev.cross.casino.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class PlayerRaycast {
    public static final int MAX_RAY_DISTANCE = 5;

    private final Player player;
    private @Nullable Entity currentlyLooking;
    private final List<Consumer<Entity>> behavior;

    public PlayerRaycast(Player player) {
        this.player = player;
        this.currentlyLooking = null;
        this.behavior = new ArrayList<>();
    }

    protected void tick() {
        if (player == null || !player.isOnline()) return;
        RayTraceResult result = player.getWorld().rayTraceEntities(player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection()), player.getLocation().getDirection(), MAX_RAY_DISTANCE);
        if (result == null) {
            if (getSightEntity().isPresent()) {
                setEntity(null);
            }
            return;
        }

        if (getSightEntity().orElse(null) != result.getHitEntity()) {
            setEntity(result.getHitEntity());
        }
    }

    public void addBehavior(Consumer<Entity> consumer) {
        this.behavior.add(consumer);
    }

    public boolean hasBehavior(Consumer<Entity> entityConsumer) {
        return behavior.contains(entityConsumer);
    }

    private void setEntity(Entity entity) {
        this.behavior.forEach(it -> it.accept(entity));
        this.currentlyLooking = entity;
    }

    public Optional<Entity> getSightEntity() {
        return Optional.ofNullable(currentlyLooking);
    }
}
