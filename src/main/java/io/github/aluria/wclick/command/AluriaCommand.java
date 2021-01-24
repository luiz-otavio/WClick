package io.github.aluria.wclick.command;

import io.github.aluria.wclick.WClick;
import io.github.aluria.wclick.composite.Manager;
import io.github.aluria.wclick.container.UserMenu;
import io.github.aluria.wclick.entity.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AluriaCommand extends Command {

    private final Manager<User> userManager = WClick.getManager(User.class);

    public AluriaCommand(String... aliases) {
        super("autoclicker", "Auto-clicker command.", "/autoclicker", Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        final Player player = (Player) sender;

        final User user = userManager.searchBy(target -> target.getUniqueId().compareTo(player.getUniqueId()) == 0);

        new UserMenu(user, player, 0).open(player);

        return true;
    }
}
