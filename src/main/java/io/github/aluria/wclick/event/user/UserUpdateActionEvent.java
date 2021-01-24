package io.github.aluria.wclick.event.user;

import io.github.aluria.wclick.entity.User;
import io.github.aluria.wclick.entity.action.Action;
import io.github.aluria.wclick.event.AluriaEvent;

public class UserUpdateActionEvent extends AluriaEvent {

    private final User user;

    private Action action;

    public UserUpdateActionEvent(User user, Action action) {
        this.user = user;
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
