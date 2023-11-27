package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowerCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.SendStatusRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowerCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetStatusResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.SuccessResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://kmxezagce5.execute-api.us-east-1.amazonaws.com/dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
    }
    public SuccessResponse followUser(FollowRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/follow", request, null, SuccessResponse.class);
    }

    public GetStatusResponse getFeed(GetStatusRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfeed", request, null, GetStatusResponse.class);
    }

    public GetFollowingCountResponse getFollowerCount(GetFollowingCountRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowerscount", request, null, GetFollowingCountResponse.class);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowers", request, null, GetFollowersResponse.class);
    }

    public GetFollowerCountResponse getFollowerCount(GetFollowerCountRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getfollowingcount", request, null, GetFollowerCountResponse.class);
    }

    public GetStatusResponse getStatus(GetStatusRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/getstatus", request, null, GetStatusResponse.class);
    }

    public SuccessResponse isFollower(IsFollowerRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/isfollower", request, null, SuccessResponse.class);
    }

    public SuccessResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/logout", request, null, SuccessResponse.class);
    }

    public SuccessResponse sendStatus(SendStatusRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/sendstatus", request, null, SuccessResponse.class);
    }

    public LoginResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/register", request, null, LoginResponse.class);
    }

    public SuccessResponse unfollow(FollowRequest request) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost("/unfollow", request, null, SuccessResponse.class);
    }
}
