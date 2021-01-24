package io.github.aluria.wclick.entity.action;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Action {

    private final String name;
    private final int id, delay;

    private final ItemStack itemStack;

    public Action(String name, int id, int delay, ItemStack itemStack) {
        this.name = name;
        this.id = id;
        this.delay = delay;
        this.itemStack = itemStack;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getDelay() {
        return delay;
    }

    public ItemStack getStack() {
        return itemStack;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission("aluria.wclick." + id);
    }
}
