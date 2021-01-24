package io.github.aluria.wclick.entity;

import com.google.common.collect.Lists;
import io.github.aluria.wclick.entity.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;

    private Action action;
    private long latest;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public long getLatest() {
        return latest;
    }

    public void setLatest(long latest) {
        this.latest = latest;
    }

    public boolean isPlaying() {
        return getPlayer() != null && action != null;
    }

    public boolean isAvailable() {
        return System.currentTimeMillis() >= latest;
    }

    public boolean isAction(Action action) {
        return this.action != null && this.action.getId() == action.getId();
    }
}
