package daos;

import business.Stories;

import java.time.LocalDateTime;

public interface StoriesDaoInterface {

    public int addStory(int storyId, int userId, String story, int storyType, LocalDateTime dateTime, String storyDescription);

    public int deleteStory (int storyId);

    public Stories getStoryById (int id);
}
