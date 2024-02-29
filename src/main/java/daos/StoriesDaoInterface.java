package daos;

import business.Stories;
import business.Users;

import java.time.LocalDateTime;

public interface StoriesDaoInterface {

    /**
     * addStory(with storyId) method able to add a new story.
     * storyId will increase automatic.
     *
     * @param userId is user's id
     * @param story is the story's file name
     * @param storyType is story's type - 1 is picture and 2 is video
     * @param dateTime is when story's date time is post
     * @param storyDescription is description of story

     * @return int of story id if added else added fail will return -1
     */
    public int addStory(int userId, String story, int storyType, LocalDateTime dateTime, String storyDescription);

    /**
     * deleteStory method able to delete story by story's id .
     *
     * @param storyId is the story's id to delete
     *
     * @return an int after deleted else return 0 when no rows are affected by the deleted.
     */
    public int deleteStory (int storyId);

    /**
     * getStoryById method able to get story by storyId.
     *
     * @param id is the story's id that want to get.
     *
     * @return that story of id's detail
     */
    public Stories getStoryById (int id);
}
