package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.AuthenticatePresenter;
import edu.byu.cs.tweeter.client.presenter.view.AuthenticateView;

public class LoginPresenter extends AuthenticatePresenter {



    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String alias, String password) {
        String errorMessage = validateLogin(alias, password);
        AuthenticateView view = getView();
        if (errorMessage == null) {
            view.clearErrorMessage();
            view.clearInfoMessage();
            view.displayMessage("Logging in...");
            userService.login(alias, password, new LoginObserver());
        }
        else {
            view.displayErrorMessage(errorMessage);
        }
    }

    public class LoginObserver extends AuthObserver {
        @Override
        protected String getBaseMessage() {
            return "Failed to login";
        }
    }
}
