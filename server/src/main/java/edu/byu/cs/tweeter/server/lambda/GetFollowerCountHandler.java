package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetFollowerCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowerCountResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowerCountHandler implements RequestHandler<GetFollowerCountRequest, GetFollowerCountResponse> {
    @Override
    public GetFollowerCountResponse handleRequest(GetFollowerCountRequest input, Context context) {
        FollowService followService = new FollowService();
        return followService.getFollowerCount(input);
    }
}
