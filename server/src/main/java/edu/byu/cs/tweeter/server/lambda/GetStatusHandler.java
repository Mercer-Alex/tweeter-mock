package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetStatusResponse;
import edu.byu.cs.tweeter.server.service.StatusService;

public class GetStatusHandler implements RequestHandler<GetStatusRequest, GetStatusResponse> {
    @Override
    public GetStatusResponse handleRequest(GetStatusRequest input, Context context) {
        StatusService statusService = new StatusService();
        return statusService.getStatus(input);
    }
}
