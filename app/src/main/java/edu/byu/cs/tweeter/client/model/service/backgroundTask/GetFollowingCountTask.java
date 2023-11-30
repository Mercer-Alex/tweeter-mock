package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask {

    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected int runCountTask() {
        try {
            ServerFacade facade = new ServerFacade();
            User targetUser = getTargetUser() == null ? null : getTargetUser();
            assert targetUser != null;
            GetFollowingCountRequest request = new GetFollowingCountRequest(getAuthToken(), targetUser.getAlias());
            GetFollowingCountResponse response = facade.getFollowingCount(request, "get-following-count");

            if (response.isSuccess()) {
                return response.getFollowingCount();
            }
            else {
                sendFailedMessage(response.getMessage());
            }
        }
        catch (Exception ex) {
            sendExceptionMessage(ex);
        }
        return -1;
    }
}
