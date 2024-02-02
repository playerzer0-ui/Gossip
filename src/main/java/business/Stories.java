package business;

import java.time.LocalDate;
import java.util.Objects;

public class Stories {

//  `storyId` int(11) NOT NULL AUTO_INCREMENT,
//  `userId` int(11) NOT NULL,
//  `story` varchar(255) NOT NULL,
//  `storyType` int(1) NOT NULL COMMENT '1 for picture, 2 for video',
//  `dateTime` datetime NOT NULL DEFAULT current_timestamp(),
//  `storyDescription` varchar(80) NOT NULL,
//    PRIMARY KEY (`storyId`),
//    KEY `userId` (`userId`)

    private int storyId;
    private int userId;
    private String story;
    private int storyType;
    private LocalDate dateTime;
    private String storyDescription;

    public Stories() {
    }

    public Stories(int storyId, int userId, String story, int storyType, LocalDate dateTime, String storyDescription) {
        this.storyId = storyId;
        this.userId = userId;
        this.story = story;
        this.storyType = storyType;
        this.dateTime = dateTime;
        this.storyDescription = storyDescription;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getStoryType() {
        return storyType;
    }

    public void setStoryType(int storyType) {
        this.storyType = storyType;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stories stories = (Stories) o;
        return storyId == stories.storyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId);
    }

    @Override
    public String toString() {
        return "Stories{" +
                "storyId=" + storyId +
                ", userId=" + userId +
                ", story='" + story + '\'' +
                ", storyType=" + storyType +
                ", dateTime=" + dateTime +
                ", storyDescription='" + storyDescription + '\'' +
                '}';
    }
}
