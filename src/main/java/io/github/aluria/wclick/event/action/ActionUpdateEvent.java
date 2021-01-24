package io.github.aluria.wclick.event.action;

import io.github.aluria.wclick.entity.User;
import io.github.aluria.wclick.entity.action.Action;
import io.github.aluria.wclick.event.AluriaEvent;

public class ActionUpdateEvent extends AluriaEvent {

    private final Action action;
    private final User user;
    private final long millis;

    public ActionUpdateEvent(Action action, User user, long millis) {
        this.action = action;
        this.user = user;
        this.millis = millis;
    }

    public Action getAction() {
        return action;
    }

    public long getMillis() {
        return millis;
    }

    public User getUser() {
        return user;
    }


}
