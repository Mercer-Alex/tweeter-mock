package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class SendStatusRequest extends AuthenticatedRequest {
        private Status status;

        private SendStatusRequest() {}

        public SendStatusRequest(AuthToken authToken, Status status) {
            this.authToken = authToken;
            this.status = status;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
}
