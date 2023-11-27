package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handler.ScrollableHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ScrollableObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends BaseService {

    public void getFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, ScrollableObserver followObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new ScrollableHandler<User>(followObserver));
        executeTask(getFollowersTask);
    }

    public void getFollowees(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, ScrollableObserver followObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken, user, pageSize, lastFollowee, new ScrollableHandler<User>(followObserver));
        executeTask(getFollowingTask);
    }

    public void updateSelectedUserFollowingAndFollowers(User user, AuthToken token, GetNumFollowersObserver followersObserver, GetNumFollowingObserver followingObserver) {
        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(token,
                user, new GetFollowersCountHandler(followersObserver));
        executeTask(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(token,
                user, new GetFollowingCountHandler(followingObserver));
        executeTask(followingCountTask);
    }

    public interface GetNumFollowersObserver {
        void handleGetNumFollowersSuccess(int num);
        void handleGetNumFollowersFailure(String message);
        void handleGetNumFollowersThrewException(Exception ex);
    }

    private class GetFollowersCountHandler extends Handler {
        private GetNumFollowersObserver observer;
        public GetFollowersCountHandler(GetNumFollowersObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
            if (success) {
                int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
                observer.handleGetNumFollowersSuccess(count);
            } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
                observer.handleGetNumFollowersFailure(message);
            } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
                observer.handleGetNumFollowersThrewException(ex);
            }
        }
    }

    public interface GetNumFollowingObserver {
        void handleGetNumFollowingSuccess(int num);
        void handleGetNumFollowingFailure(String message);
        void handleGetNumFollowingThrewException(Exception ex);
    }

    private class GetFollowingCountHandler extends Handler {
        private GetNumFollowingObserver observer;
        public GetFollowingCountHandler(GetNumFollowingObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFollowingCountTask.SUCCESS_KEY);
            if (success) {
                int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
                observer.handleGetNumFollowingSuccess(count);
            } else if (msg.getData().containsKey(GetFollowingCountTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);
                observer.handleGetNumFollowingFailure(message);
            } else if (msg.getData().containsKey(GetFollowingCountTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);
                observer.handleGetNumFollowingThrewException(ex);
            }
        }
    }

    public interface FollowUserObserver {
        void handleFollowUserSuccess();
        void handleFollowUserFailure(String message);
        void handleFollowUserThrewException(Exception ex);
    }
    public void followUser(AuthToken token, User user, FollowUserObserver observer) {
        FollowTask followTask = new FollowTask(token, user, new FollowHandler(observer));
        executeTask(followTask);
    }
    private class FollowHandler extends Handler {
        private FollowUserObserver observer;
        public FollowHandler(FollowUserObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(FollowTask.SUCCESS_KEY);
            if (success) {
                observer.handleFollowUserSuccess();
            } else if (msg.getData().containsKey(FollowTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
                observer.handleFollowUserFailure(message);
            } else if (msg.getData().containsKey(FollowTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
                observer.handleFollowUserThrewException(ex);
            }
        }
    }
    public void unfollowUser(AuthToken token, User user, UnfollowUserObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(token, user, new UnfollowHandler(observer));
        executeTask(unfollowTask);
    }

    public interface UnfollowUserObserver {
        void handleUnfollowUserSuccess();
        void handleUnfollowUserFailure(String message);
        void handleUnfollowUserThrewException(Exception ex);
    }

    private class UnfollowHandler extends Handler {
        UnfollowUserObserver observer;
        public UnfollowHandler(UnfollowUserObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);
            if (success) {
                observer.handleUnfollowUserSuccess();
            } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
                observer.handleUnfollowUserFailure(message);
            } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
                observer.handleUnfollowUserThrewException(ex);
            }
        }
    }


    public interface IsFollowerObserver {
        void handleIsFollowerSuccess(boolean isFollower);
        void handleIsFollowerFailure(String message);
        void handleIsFollowerThrewException(Exception ex);
    }
    public void isFollower(AuthToken token, User user, User targetUser, IsFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(token, user, targetUser, new IsFollowerHandler(observer));
        executeTask(isFollowerTask);
    }
    private class IsFollowerHandler extends Handler {
        private IsFollowerObserver observer;
        public IsFollowerHandler(IsFollowerObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(IsFollowerTask.SUCCESS_KEY);
            if (success) {
                boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
                observer.handleIsFollowerSuccess(isFollower);
            } else if (msg.getData().containsKey(IsFollowerTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
                observer.handleIsFollowerFailure(message);
            } else if (msg.getData().containsKey(IsFollowerTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
                observer.handleIsFollowerThrewException(ex);
            }
        }
    }
}
