package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStatusResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class FeedDAO {
    public GetFeedResponse getFeed(GetFeedRequest request) {
        assert request.getLimit() > 0;
        assert  request.getUserAlias() != null;

        List<Status> feed = FakeData.getInstance().getFakeStatuses();
        List<Status> responseFeed = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            if (feed != null) {
                int statusIndex = getFeedStartingIndex(request.getLastStatus(), feed);

                for (int limitCounter = 0; statusIndex < feed.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                    responseFeed.add(feed.get(statusIndex));
                }
                hasMorePages = statusIndex < feed.size();
            }
        }
        return new GetFeedResponse(hasMorePages, responseFeed);
    }

    private int getFeedStartingIndex(Status lastStatus, List<Status> allStatus) {
        int statusIndex = 0;

        if (lastStatus != null) {
            for (int i = 0; i < allStatus.size(); i++) {
                if (lastStatus.equals(allStatus.get(i))) {
                    statusIndex = i + 1;
                    break;
                }
            }
        }
        return statusIndex;
    }
}
