package daos;

import business.Reports;
import business.Stories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StoriesDao extends Dao implements StoriesDaoInterface{
    public StoriesDao(String databaseName) {
        super(databaseName);
    }

    public StoriesDao(Connection con) {
        super(con);
    }

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
    @Override
    public int addStory(int userId, String story, int storyType, LocalDateTime dateTime, String storyDescription) {
        int rowsAffected = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO stories (userId, story, storyType, dateTime, storyDescription) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2,story);
            ps.setInt(3,storyType);
            ps.setTimestamp(4, Timestamp.valueOf(dateTime));
            ps.setString(5, storyDescription);

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

    /**
     * getAllStories method able to list out all stories.
     *
     * @return a list of stories
     */
    @Override
    public List<Stories> getAllStories() {
        List<Stories> stories = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM stories";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int storyId = rs.getInt("storyId");
                int userId = rs.getInt("userID");
                String story = rs.getString("story");
                int storyType = rs.getInt("storyType");
                LocalDateTime dateTime = rs.getTimestamp("dateTime").toLocalDateTime();
                String storyDescription = rs.getString("storyDescription");

                Stories s = new Stories(storyId, userId, story, storyType, dateTime, storyDescription);
                stories.add(s);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getAllStories() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getAllStories() method:");
        }
        return stories;
    }


    public List<Stories> getAllStoriesByUserId(int id) {
        List<Stories> stories = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM stories where userId=? and  TIMESTAMPDIFF(HOUR, dateTime,now())< 24 order by dateTime desc";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next())
            {
                int storyId = rs.getInt("storyId");
                int userId = rs.getInt("userID");
                String story = rs.getString("story");
                int storyType = rs.getInt("storyType");
                LocalDateTime dateTime = rs.getTimestamp("dateTime").toLocalDateTime();
                String storyDescription = rs.getString("storyDescription");

                Stories s = new Stories(storyId, userId, story, storyType, dateTime, storyDescription);
                stories.add(s);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getAllStories() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getAllStories() method:");
        }
        return stories;
    }

    /**
     * addStory(with storyId) method able to add a new story.
     * storyId will increase automatic.
     *
     * @param userId is user's id
     * @param story is the story's file name
     * @param storyType is story's type - 1 is picture and 2 is video
     * @param storyDescription is description of story

     * @return int of story id if added else added fail will return -1
     */

    public int addStory(int userId, String story, int storyType, String storyDescription) {
        int rowsAffected = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO stories (userId, story, storyType, storyDescription) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2,story);
            ps.setInt(3,storyType);
            ps.setString(4, storyDescription);

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


}

