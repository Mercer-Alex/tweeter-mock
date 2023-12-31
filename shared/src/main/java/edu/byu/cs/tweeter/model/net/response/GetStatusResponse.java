package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.Status;

public class GetStatusResponse extends PagedResponse{
    List<Status> stories;

    public GetStatusResponse(boolean hasMorePages, List<Status> stories) {
        super(true, hasMorePages);
        this.stories = stories;
    }

    public GetStatusResponse(String message) {
        super(false, message, false);
    }

    public List<Status> getStories() {
        return stories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetStatusResponse that = (GetStatusResponse) o;
        return (Objects.equals(stories, that.stories) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(stories);
    }

}
