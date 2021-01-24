package io.github.aluria.wclick.container;

import io.github.aluria.wclick.WClick;
import io.github.aluria.wclick.composite.Manager;
import io.github.aluria.wclick.entity.User;
import io.github.aluria.wclick.entity.action.Action;
import io.github.aluria.wclick.event.user.UserUpdateActionEvent;
import io.github.aluria.wclick.tooling.container.Container;
import io.github.aluria.wclick.tooling.container.icon.Icon;
import io.github.aluria.wclick.tooling.container.size.Size;
import io.github.aluria.wclick.tooling.item.ItemBuilder;
import io.github.aluria.wclick.tooling.util.paginator.Paginator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UserMenu extends Container {

    private static final Manager<Action> ACTION_MANAGER = WClick.getManager(Action.class);

    private static final int[] FILL = {
            13, 14, 15, 16,
            22, 23, 24, 25,
            31, 32, 33, 34
    };

    public UserMenu(User user, Player player, int page) {
        super("Auto Clicker", Size.FIVE_LINES);

        final List<Action> parent = ACTION_MANAGER.getCollection(),
                actions = Paginator.getPage(12, page, parent);

        if(actions == null || actions.isEmpty()) {
            Icon.of(24)
                    .stack(() -> new ItemBuilder(Material.BARRIER)
                            .name("§cOops! Não há auto-clickers disponiveis!")
                            .build())
                    .build(this);

            return;
        }

        int index = 0;

        for (Action action : actions) {
            if(!action.hasPermission(player)) continue;

            Icon.of(FILL[index++])
                    .stack(() -> toCurrent(user, action))
                    .handle((icon, event) -> new UserUpdateActionEvent(user, user.isAction(action) ? null : action).call())
                    .build(this);
        }

        final ItemStack enough = new ItemBuilder(Material.INK_SACK, 1, 8)
                .name("§cIndisponivel.")
                .build();

        for (int available : FILL) {
            if(with(available) == null) {
                Icon.of(available)
                        .stack(() -> enough)
                        .build(this);
            }
        }

        if(Paginator.hasPage(12, page + 1, parent)) {
            Icon.of(26)
                    .stack(() -> new ItemBuilder(Material.ARROW)
                            .name("§aClique para acessar a proxima pagina!")
                            .build())
                    .handle((icon, event) -> new UserMenu(user, player, page + 1).open(player))
                    .build(this);
        }

        if(Paginator.hasPage(12, page - 1, parent)) {
            Icon.of(21)
                    .stack(() -> new ItemBuilder(Material.ARROW)
                            .name("§aClique para acessar a pagina anterior!")
                            .build())
                    .handle((icon, event) -> new UserMenu(user, player, page - 1).open(player))
                    .build(this);
        }
    }

    private ItemStack toCurrent(User user, Action action) {
        final ItemStack itemStack = action.getStack();
        final ItemMeta itemMeta = itemStack.getItemMeta();

        final List<String> lore = itemMeta.getLore();

        if(user.isAction(action)) {
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 0, true);

            lore.add("§cClique para remover.");
        } else {
            lore.add("§aClique para selecionar.");
        }

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
