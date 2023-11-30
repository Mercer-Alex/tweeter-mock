package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.net.request.SendStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStatusResponse;
import edu.byu.cs.tweeter.model.net.response.SuccessResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.util.FakeData;

public class StatusService {
    public GetFeedResponse getFeed(GetFeedRequest request) {
        if(request.getLastStatus() == null){
            throw new RuntimeException("[Bad Request] Missing a last status");
        } else if(request.getLimit() < 0) {
            throw new RuntimeException("[Bad Request] limit less than 0");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] Missing auth token");
        } else if(request.getUserAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a user");
        }
        return getFeedDAO().getFeed(request);
    }

    public GetStatusResponse getStatus(GetStatusRequest request) {
        if(request.getLastStatus() == null){
            throw new RuntimeException("[Bad Request] Missing a last status");
        } else if(request.getLimit() < 0) {
            throw new RuntimeException("[Bad Request] limit less than 0");
        } else if(request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] Missing auth token");
        } else if(request.getUserAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a user");
        }
        return getStoryDAO().getStory(request);
    }

    public SuccessResponse sendStatus(SendStatusRequest request) {
        if (request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] Missing auth token");
        } else if (request.getStatus() == null) {
            throw new RuntimeException("[Bad Request] Missing a status");
        }
        return new SuccessResponse(true, "Sending a status");
    }

    FakeData getFakeData() {
        return FakeData.getInstance();
    }

    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }
    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }

    StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
