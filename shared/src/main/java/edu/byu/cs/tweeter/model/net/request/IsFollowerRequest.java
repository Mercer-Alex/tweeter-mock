package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class IsFollowerRequest extends AuthenticatedRequest {
    private User user;
    private User targetUser;

    public IsFollowerRequest() {
    }

    public IsFollowerRequest(AuthToken token, User user, User targetUser) {
        this.authToken = token;
        this.user = user;
        this.targetUser = targetUser;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTargetUser() {
        return targetUser;
    }
    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
}
