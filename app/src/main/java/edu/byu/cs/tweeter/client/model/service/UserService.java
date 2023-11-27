package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handler.AuthenticateHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.observer.AuthenticateObserver;
import edu.byu.cs.tweeter.client.model.service.observer.GetUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends BaseService {

    public void getUser(AuthToken token, String alias, GetUserObserver observer) {
        GetUserTask getUserTask = new GetUserTask(token, alias, new GetUserHandler(observer));
        executeTask(getUserTask);
    }

    public interface LoginObserver {
        void loginSucceeded(AuthToken authToken, User user);
        void loginFailed(String message);

    }
    public void login(String alias, String password, AuthenticateObserver observer) {
        // Send the login request.
        LoginTask loginTask = new LoginTask(alias,
                password,
                new AuthenticateHandler(observer));
        executeTask(loginTask);
    }


    public interface LogoutObserver {
        void logoutSucceeded();
        void logoutFailed(String message);
    }

    public void logout(AuthToken token, LogoutObserver observer) {
        LogoutTask logoutTask = new LogoutTask(token, new LogoutHandler(observer));
        executeTask(logoutTask);
    }

    private class LogoutHandler extends Handler {
        LogoutObserver observer;
        public LogoutHandler(LogoutObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);
            if (success) {
                observer.logoutSucceeded();
            } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
                observer.logoutFailed(message);
            } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
                observer.logoutFailed("Failed logout due to exception: " + ex.getMessage());
            }
        }
    }
    public interface RegisterObserver {
        void registerSucceeded(AuthToken authToken, User user);
        void registerFailed(String message);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, AuthenticateObserver observer) {
        // Send register request.
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new AuthenticateHandler(observer));

        executeTask(registerTask);
    }
}
