package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.presenter.view.ScrollableView;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends ScrollablePresenter<User> {

    private FollowService followService;

    public FollowersPresenter(ScrollableView<User> view) {
        super(view);
        followService = new FollowService();
    }

    @Override
    protected void getItems(User user) {
            followService.getFollowers(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, getLastItem(), new GetFollowerObserver());
    }

    public class GetFollowerObserver extends GetItemsObserver {
        @Override
        protected String getBaseMessage() {
            return "Failed to get followers";
        }
    }
}
