package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.presenter.view.ScrollableView;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends ScrollablePresenter<User> {
    private FollowService followService;

    public FollowingPresenter(ScrollableView<User> view) {
        super(view);
        followService = new FollowService();
    }

    @Override
    protected void getItems(User user) {
        followService.getFollowees(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, getLastItem(), new GetFolloweesObserver());
    }

    public class GetFolloweesObserver extends GetItemsObserver {
        @Override
        protected String getBaseMessage() {
            return "Failed to get following";
        }
    }
}
