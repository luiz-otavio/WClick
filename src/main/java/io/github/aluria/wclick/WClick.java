package io.github.aluria.wclick;

import com.google.common.collect.ImmutableMap;
import io.github.aluria.wclick.adapter.configuration.ConfigurationAdapter;
import io.github.aluria.wclick.command.AluriaCommand;
import io.github.aluria.wclick.composite.Manager;
import io.github.aluria.wclick.composite.linked.LinkedManager;
import io.github.aluria.wclick.entity.User;
import io.github.aluria.wclick.entity.action.Action;
import io.github.aluria.wclick.listener.AluriaHandler;
import io.github.aluria.wclick.task.AluriaTask;
import io.github.aluria.wclick.tooling.listener.ToolingHandler;
import io.github.aluria.wclick.tooling.util.Try;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public final class WClick extends JavaPlugin {

    private static final Map<Class<?>, Manager<?>> IMMUTABLE_MAP = ImmutableMap.<Class<?>, Manager<?>>builder()
            .put(User.class, new LinkedManager<User>())
            .put(Action.class, new LinkedManager<Action>())
            .build();

    public static WClick getInstance() {
        return getPlugin(WClick.class);
    }

    private final BukkitScheduler scheduler = Bukkit.getScheduler();
    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private final ConfigurationAdapter configurationAdapter = new ConfigurationAdapter(this);

    @Override
    public void onLoad() {
        if(!getDataFolder().exists()) getDataFolder().mkdirs();

        saveDefaultConfig();

        final List<Action> actions = configurationAdapter.accept("config.yml");

        getManager(Action.class).putAll(actions);
    }

    @Override
    public void onEnable() {
        scheduler.runTaskTimerAsynchronously(this, new AluriaTask(), 20, 20);

        pluginManager.registerEvents(new AluriaHandler(), this);
        pluginManager.registerEvents(new ToolingHandler(), this);

        setCommand("autoclicker", new AluriaCommand("clicker", "autoclickers", "autoclicks"));
    }

    @Override
    public void onDisable() {
        scheduler.cancelTasks(this);
    }

    public static <T> Manager<T> getManager(Class<T> clazz) {
        return (Manager<T>) IMMUTABLE_MAP.get(clazz);
    }

    public void setCommand(String name, Command command) {
        Try.of(() -> {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);

            final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(name, command);
        });
    }
}
