package io.github.aluria.wclick.listener;

import io.github.aluria.wclick.WClick;
import io.github.aluria.wclick.composite.Manager;
import io.github.aluria.wclick.entity.User;
import io.github.aluria.wclick.entity.action.Action;
import io.github.aluria.wclick.event.action.ActionUpdateEvent;
import io.github.aluria.wclick.event.user.UserUpdateActionEvent;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class AluriaHandler implements Listener {

    private final Manager<User> userManager = WClick.getManager(User.class);

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onActionUpdate(ActionUpdateEvent event) {
        final User user = event.getUser();
        final long millis = event.getMillis();

        final Player player = user.getPlayer();

        final List<Entity> entities = player.getNearbyEntities(5, 5, 5);

        final World world = player.getWorld();
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        user.setLatest(millis);

        final PacketPlayOutAnimation animation = new PacketPlayOutAnimation(entityPlayer, 0);

        for (Player target : world.getPlayers()) {
            final CraftPlayer craftPlayer = (CraftPlayer) target;

            final EntityPlayer entity = craftPlayer.getHandle();

            entity.playerConnection.sendPacket(animation);
        }

        for (Entity entity : entities) {
            if(!(entity instanceof LivingEntity)) continue;

            final LivingEntity livingEntity = (LivingEntity) entity;

            if(livingEntity.isDead() || livingEntity instanceof Player) continue;

            livingEntity.damage(4, player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onUserUpdateAction(UserUpdateActionEvent event) {
        final User user = event.getUser();
        final Action action = event.getAction();

        user.setAction(action);

        final Player player = user.getPlayer();

        if(action == null) {
            player.sendMessage("§aVocê removeu com sucesso o auto clicker!");
        } else {
            player.sendMessage("§aVocê selecionou com sucesso o auto clicker!");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final User user = userManager.searchBy(any -> any.getUniqueId().compareTo(player.getUniqueId()) == 0);

        if(user != null) return;

        final User entity = new User(player.getUniqueId());

        userManager.put(entity);
    }
}
