package io.github.aluria.wclick.adapter.configuration;

import com.google.common.collect.Lists;
import io.github.aluria.wclick.adapter.item.ItemAdapter;
import io.github.aluria.wclick.entity.action.Action;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class ConfigurationAdapter {

    private final Plugin plugin;

    public ConfigurationAdapter(Plugin plugin) {
        this.plugin = plugin;
    }

    public List<Action> accept(String file) {
        final File target = new File(plugin.getDataFolder(), file);
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(target);
        final ConfigurationSection section = configuration.getConfigurationSection("Actions");

        final List<Action> actions = Lists.newArrayList();

        for (String key : section.getKeys(false)) {
            final ConfigurationSection any = section.getConfigurationSection(key);

            final ItemStack icon = ItemAdapter.toItem(any);

            final int id = any.getInt("Id"),
                    delay = any.getInt("Delay");
            final String name = any.getString("Name");

            actions.add(new Action(name, id, delay, icon));
        }

        return actions;
    }

}
