package edu.byu.cs.tweeter.client.model.service;


import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observer.AuthenticateObserver;
import edu.byu.cs.tweeter.client.presenter.BasePresenter;
import edu.byu.cs.tweeter.client.presenter.view.AuthenticateView;
import edu.byu.cs.tweeter.client.presenter.view.BaseView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticatePresenter extends BasePresenter {
    public AuthenticatePresenter(BaseView view) {
        super(view);
    }

    public AuthenticateView getView() {
        return (AuthenticateView) view;
    }
    protected String validateLogin(String alias, String password) {
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }
        return null;
    }

    public abstract class AuthObserver extends Observer implements AuthenticateObserver {
        @Override
        public void handleSuccess(User user, AuthToken token) {
            Cache.getInstance().setCurrUser(user);
            Cache.getInstance().setCurrUserAuthToken(token);
            getView().authSuccessful(user);
        }
    }
}
