package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LogoutResponse extends Response{
    public LogoutResponse(boolean success, String message) {
        super(success, message);
    }

    public LogoutResponse(boolean success) {
        super(success);
    }
}
