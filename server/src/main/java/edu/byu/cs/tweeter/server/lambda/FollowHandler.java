package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.SuccessResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowHandler implements RequestHandler<FollowRequest, SuccessResponse> {
    @Override
    public SuccessResponse handleRequest(FollowRequest input, Context context) {
        FollowService followService = new FollowService();
        return followService.followUser(input);
    }
}
