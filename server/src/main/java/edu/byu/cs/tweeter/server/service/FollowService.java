package edu.byu.cs.tweeter.server.service;

import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowerCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowerCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.SuccessResponse;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

        Pair<List<User>, Boolean> pair = getFollowingDAO().getFollowees(request.getFollowerAlias(), request.getLimit(), request.getLastFolloweeAlias());
        return new FollowingResponse(pair.getFirst(), pair.getSecond());
    }

    public SuccessResponse followUser(FollowRequest request) {
        if(request.getSelectedUserAlias() == null) {
            throw new RuntimeException("[Bad Request] missing selected user alias");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] missing auth token");
        }
        return getFollowingDAO().followUser(request);
    }

    public GetFollowerCountResponse getFollowerCount(GetFollowerCountRequest request) {
        if(request.getTargetUser() == null) {
            throw new RuntimeException("[Bad Request] missing target user");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] missing auth token");
        }
        int count = getFollowingDAO().getFollowerCount(request.getTargetUser());
        return new GetFollowerCountResponse(count);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request) {
        if(request.getTargetUser() == null) {
            throw new RuntimeException("[Bad Request] missing target user");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] missing auth token");
        } else if(request.getLimit() < 0) {
            throw new RuntimeException("[Bad Request] missing limit");
        } else if(request.getLastFolloweeAlias()== null) {
            throw new RuntimeException("[Bad Request] missing last followee");
        }
        return getFollowingDAO().getFollowers(request.getTargetUser(), request.getLimit(), request.getLastFolloweeAlias());
    }

    public GetFollowingCountResponse getFollowingCount(GetFollowingCountRequest request) {
        if(request.getSelectedUserAlias() == null) {
            throw new RuntimeException("[Bad Request] missing selected user alias");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] missing auth token");
        }
        int count = getFollowingDAO().getFolloweeCount(request.getSelectedUserAlias());
        return new GetFollowingCountResponse(count);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        if(request.getTargetUser() == null) {
            throw new RuntimeException("[Bad Request] missing target user");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] missing auth token");
        } else if(request.getUser() == null) {
            throw new RuntimeException("[Bad Request] missing user");
        }
        boolean isFollow = new Random().nextBoolean();
        return new IsFollowerResponse(isFollow);
    }

    public SuccessResponse unfollowUser(FollowRequest request) {
        if (request.getSelectedUserAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a followee");
        }
        return getFollowingDAO().followUser(request);
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowDAO getFollowingDAO() {
        return new FollowDAO();
    }

    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }

    FakeData getFakeData() {
        return FakeData.getInstance();
    }
}
