package edu.byu.cs.tweeter.model.net.response;

public class GetFollowerCountResponse extends Response{
        private Integer count;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public GetFollowerCountResponse(int count) {
            super(true, null);
            this.count = count;
        }

        public GetFollowerCountResponse(String message) {
            super(false, message);
        }
}
