package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.SendStatusRequest;
import edu.byu.cs.tweeter.model.net.response.SuccessResponse;
import edu.byu.cs.tweeter.server.service.StatusService;

public class SendStatusHandler implements RequestHandler<SendStatusRequest, SuccessResponse> {
    @Override
    public SuccessResponse handleRequest(SendStatusRequest input, Context context) {
        StatusService statusService = new StatusService();
        return statusService.sendStatus(input);
    }
}
