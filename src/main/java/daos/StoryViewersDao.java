package daos;

import business.StoryViewers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StoryViewersDao extends Dao{

    public StoryViewersDao(String databaseName) {
        super(databaseName);
    }

    public StoryViewersDao(Connection con) {
        super(con);
    }

    public StoryViewers getViewersByStoryID(int storyId){
        StoryViewers sv = null;
        try{
            con = getConnection();

            String query = "select * from storyviewers where storyId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            rs = ps.executeQuery();

            if(rs.next()){
                sv = new StoryViewers(
                        rs.getInt("storyId"),
                        rs.getInt("viewerId"),
                        rs.getTimestamp("viewTime").toLocalDateTime()
                );
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getViewersByStoryID() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getViewersByStoryID() final method:");
        }
        return sv;
    }

    public int insertStoryViewer(int viewerId, LocalDateTime viewTime){
        int newId = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO storyviewers (viewerId, viewTime) VALUES (?, ?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, viewerId);
            ps.setTimestamp(2, Timestamp.valueOf(viewTime));

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if(rs.next())
            {
                newId = rs.getInt(1);
            }

        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the insertStoryViewer() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the insertStoryViewer() final method:");
        }
        return newId;
    }

    public int deleteStoryFromStoryViewers(int storyId){
        return deleteItem(storyId, "storyviewers", "storyId");
    }

    public int deleteViewerFromStoryViewers(int viewerId){
        return deleteItem(viewerId, "storyviewers", "viewerId");
    }
}
