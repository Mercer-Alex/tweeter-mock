package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask {

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        try {
            ServerFacade facade = new ServerFacade();
            String targetUserAlias = getTargetUser() == null ? null : getTargetUser().getAlias();
            String lastFolloweeAlias = getLastItem() == null ? "" : getLastItem().getAlias();

            GetFollowersRequest request = new GetFollowersRequest(this.getAuthToken(), targetUserAlias, this.getLimit(), lastFolloweeAlias);
            GetFollowersResponse response = facade.getFollowers(request, "get-followers");

            if (response.isSuccess()) {
                setLastItem(response.getFollowList().get(response.getFollowList().size() - 1));
                return new Pair<>(response.getFollowList(), response.getHasMorePages());
            }
            else {
                sendFailedMessage(response.getMessage());
            }
        }
        catch (Exception ex) {
            sendExceptionMessage(ex);
        }

        return null;
    }
}
