package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowerCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowerCountResponse;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends GetCountTask {
    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }
    @Override
    protected int runCountTask() {
        try {
            ServerFacade facade = new ServerFacade();
            User targetUser = getTargetUser() == null ? null : getTargetUser();
            GetFollowerCountRequest request = new GetFollowerCountRequest(getAuthToken(), targetUser);
            GetFollowerCountResponse response = facade.getFollowerCount(request, "get-follower-count");

            if (response.isSuccess()) {
                return response.getCount();
            }
            else {
                sendFailedMessage(response.getMessage());
            }
        }
        catch (Exception ex) {
            sendExceptionMessage(ex);
        }
        return -1;    }
}
