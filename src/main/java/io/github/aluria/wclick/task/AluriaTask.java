package io.github.aluria.wclick.task;

import io.github.aluria.wclick.WClick;
import io.github.aluria.wclick.composite.Manager;
import io.github.aluria.wclick.entity.User;
import io.github.aluria.wclick.entity.action.Action;
import io.github.aluria.wclick.event.action.ActionUpdateEvent;

import java.util.concurrent.TimeUnit;

public class AluriaTask implements Runnable {

    private final Manager<User> userManager = WClick.getManager(User.class);

    @Override
    public void run() {
        for (User user : userManager.fill(User::isPlaying)) {
            if(!user.isAvailable()) continue;

            final Action action = user.getAction();

            final long millis = TimeUnit.SECONDS.toMillis(action.getDelay()) + System.currentTimeMillis();

            new ActionUpdateEvent(action, user, millis).call();
        }
    }
}
