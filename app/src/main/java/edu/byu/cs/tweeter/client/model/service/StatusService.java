package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.handler.ScrollableHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ScrollableObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends BaseService {

    public void getFeed(AuthToken token, User user, int limit, Status lastStatus, ScrollableObserver<Status> observer) {
        GetFeedTask getFeedTask = new GetFeedTask(token, user, limit, lastStatus, new ScrollableHandler<Status>(observer));
        executeTask(getFeedTask);
    }

    public void getStory(AuthToken token, User user, int limit, Status lastStatus, ScrollableObserver<Status> observer) {
        GetStoryTask getStoryTask = new GetStoryTask(token,
                user, limit, lastStatus, new ScrollableHandler<Status>(observer));
        executeTask(getStoryTask);
    }
}
