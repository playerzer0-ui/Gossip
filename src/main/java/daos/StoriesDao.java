package daos;

import business.Stories;
import business.StoryViewers;
import business.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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

    @Override
    public int addStory(int storyId, int userId, String story, int storyType, LocalDateTime dateTime, String storyDescription) {
        int rowsAffected = 0;

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

    @Override
    public int deleteStory(int storyId) {
        return deleteItem(storyId, "stories", "storyId");
    }

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
