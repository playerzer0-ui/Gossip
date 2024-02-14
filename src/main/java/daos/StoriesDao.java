package daos;

import business.Stories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StoriesDao extends Dao implements StoriesDaoInterface{
    public StoriesDao(String databaseName) {
        super(databaseName);
    }

    public StoriesDao(Connection con) {
        super(con);
    }

    /**
     * addStory method able to add a new story.
     * storyId will increase automatic.
     *
     * @param storyId is story's id
     * @param userId is user's id
     * @param story is the story's file name
     * @param storyType is story's type - 1 is picture and 2 is video
     * @param dateTime is when story's date time is post
     * @param storyDescription is description of story

     * @return int of story id if added else added fail will return -1
     */
    @Override
    public int addStory(int storyId, int userId, String story, int storyType, LocalDateTime dateTime, String storyDescription) {
        int rowsAffected = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO stories (storyId, userId, story, storyType, dateTime, storyDescription) VALUES (?, ?, ?, ?, ?，?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            ps.setInt(2, userId);
            ps.setString(3,story);
            ps.setInt(4,storyType);
            ps.setTimestamp(5, Timestamp.valueOf(dateTime));
            ps.setString(6, storyDescription);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the addStory() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the addStory() final method:");
        }
        return rowsAffected;
    }

    /**
     * deleteStory method able to delete story by story's id .
     *
     * @param storyId is the story's id to delete
     *
     * @return an int after deleted else return 0 when no rows are affected by the deleted.
     */
    @Override
    public int deleteStory(int storyId) {
        return deleteItem(storyId, "stories", "storyId");
    }

    /**
     * getStoryById method able to get story by storyId.
     *
     * @param id is the story's id that want to get.
     *
     * @return that story of id's detail
     */
    @Override
    public Stories getStoryById(int id) {
        Stories s = null;
        try{
            con = getConnection();

            String query = "SELECT * FROM stories WHERE storyId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next())
            {
                int storyId = rs.getInt("storyId");
                int userId = rs.getInt("userID");
                String story = rs.getString("story");
                int storyType = rs.getInt("storyType");
                LocalDateTime dateTime = rs.getTimestamp("dateTime").toLocalDateTime();
                String storyDescription = rs.getString("storyDescription");

                s = new Stories(storyId, userId, story, storyType, dateTime, storyDescription);
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getViewersByStoryID() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getViewersByStoryID() final method:");
        }
        return s;
    }
}
