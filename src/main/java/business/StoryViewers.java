package business;

import java.time.LocalDateTime;

public class StoryViewers {
    private int storyId;
    private int viewId;
    private LocalDateTime viewTime;

    public StoryViewers(int storyId, int viewId, LocalDateTime viewTime) {
        this.storyId = storyId;
        this.viewId = viewId;
        this.viewTime = viewTime;
    }

    public StoryViewers() {
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public LocalDateTime getViewTime() {
        return viewTime;
    }

    public void setViewTime(LocalDateTime viewTime) {
        this.viewTime = viewTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoryViewers that = (StoryViewers) o;

        return storyId == that.storyId;
    }

    @Override
    public int hashCode() {
        return storyId;
    }

    @Override
    public String toString() {
        return "StoryViewers{" +
                "storyId=" + storyId +
                ", viewId=" + viewId +
                ", viewTime=" + viewTime +
                '}';
    }
}
