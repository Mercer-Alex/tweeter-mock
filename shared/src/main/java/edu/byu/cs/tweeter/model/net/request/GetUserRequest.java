package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest extends AuthenticatedRequest{
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public GetUserRequest() {}

    public GetUserRequest(AuthToken token, String user) {
        this.authToken = token;
        this.user = user;
    }
}
