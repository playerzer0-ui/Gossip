package daos;

import business.StoryViewers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StoryViewersDao extends Dao{

    public StoryViewersDao(String databaseName) {
        super(databaseName);
    }

    public StoryViewersDao(Connection con) {
        super(con);
    }

    /**
     * get a storyviewer by storyId
     * @param storyId the storyId
     * @return storyviewer based on the id
     */
    public List<StoryViewers> getViewersByStoryID(int storyId){
        List<StoryViewers> storyViewers = new ArrayList<>();
        try{
            con = getConnection();

            String query = "select * from storyviewers where storyId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            rs = ps.executeQuery();

            while(rs.next()){
                storyViewers.add(new StoryViewers(
                        rs.getInt("storyId"),
                        rs.getInt("viewerId"),
                        rs.getTimestamp("viewTime").toLocalDateTime()
                ));
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getViewersByStoryID() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getViewersByStoryID() final method:");
        }
        return storyViewers;
    }

    /**
     * get a storyViewer by viewerId
     * @param viewerId the viewerId
     * @return storyviewer based on the id
     */
    public List<StoryViewers> getViewersByViewerID(int viewerId){
        List<StoryViewers> storyViewers = new ArrayList<>();
        try{
            con = getConnection();

            String query = "select * from storyviewers where viewerId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, viewerId);
            rs = ps.executeQuery();

            while(rs.next()){
                storyViewers.add(new StoryViewers(
                        rs.getInt("storyId"),
                        rs.getInt("viewerId"),
                        rs.getTimestamp("viewTime").toLocalDateTime()
                ));
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getViewersByStoryID() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getViewersByStoryID() final method:");
        }
        return storyViewers;
    }

    /**
     * get a storyViewer by viewerId
     * @param viewerId the viewerId
     *  @param storyId the storyId
     * @return storyviewer based on the id
     */
    public StoryViewers getViewersByStatusViewer(int storyId,int viewerId){
        StoryViewers storyViewers = null;
        try{
            con = getConnection();

            String query = "select * from storyviewers where storyId=? and viewerId=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            ps.setInt(2, viewerId);
            rs = ps.executeQuery();

            if(rs.next()){
                storyViewers = new StoryViewers(
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
        return storyViewers;
    }

    /**
     * insert a storyviewer to the database
     * @param storyId the storyId
     * @param viewerId the viewerId
     * @param viewTime the view time
     * @return number of storyviewers added, 1 is the correct value
     */
    public int insertStoryViewer(int storyId, int viewerId, LocalDateTime viewTime){
        int rowsAffected = 0;

        try{
            con = getConnection();

            String query = "INSERT INTO storyviewers (storyId, viewerId, viewTime) VALUES (?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            ps.setInt(2, viewerId);
            ps.setTimestamp(3, Timestamp.valueOf(viewTime));

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the insertStoryViewer() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the insertStoryViewer() final method:");
        }
        return rowsAffected;
    }

    /**
     * insert a storyViewer to the database
     * @param storyId the storyId
     * @param viewerId the viewerId
     * @return number of storyviewers added, 1 is the correct value
     */
    public int insertStoryViewer(int storyId, int viewerId){
        int rowsAffected = 0;

        try{
            con = getConnection();

            String query = "INSERT INTO storyviewers (storyId, viewerId) VALUES (?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            ps.setInt(2, viewerId);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the insertStoryViewer() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the insertStoryViewer() final method:");
        }
        return rowsAffected;
    }

    //select count(*) from storyviewers where storyId=4 and TIMESTAMPDIFF(HOUR, viewTime,now())< 24;
    /**
     * get a storyviewer by storyId
     * @param storyId the storyId
     * @return storyviewer based on the id
     */
    public List<StoryViewers> getViewersByStoryId(int storyId){
        List<StoryViewers> storyViewers = new ArrayList<>();
        try{
            con = getConnection();

            String query = "select * from storyviewers where storyId=? and TIMESTAMPDIFF(HOUR, viewTime,now())< 24 order by viewTime desc";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            rs = ps.executeQuery();

            while(rs.next()){
                storyViewers.add(new StoryViewers(
                        rs.getInt("storyId"),
                        rs.getInt("viewerId"),
                        rs.getTimestamp("viewTime").toLocalDateTime()
                ));
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getViewersByStoryID() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getViewersByStoryID() final method:");
        }
        return storyViewers;
    }

    public int countViewers(int storyId){
       int count=-1;
        try{
            con = getConnection();

            String query = "select count(*) from storyviewers where storyId=? and TIMESTAMPDIFF(HOUR, viewTime,now())< 24";
            ps = con.prepareStatement(query);
            ps.setInt(1, storyId);
            rs = ps.executeQuery();

            if(rs.next()){
                   count=     rs.getInt("count(*)");
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the countViewers() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the countViewers() final method:");
        }
        return count;
    }

    /**
     * delete storyviewer from database
     * @param storyId the storyId
     * @return the number of storyviewers deleted
     */
    public int deleteStoryFromStoryViewers(int storyId){
        return deleteItem(storyId, "storyviewers", "storyId");
    }

    /**
     * delete storyviewer by viewerId from database
     * @param viewerId the viewerId
     * @return the number of storyviewers deleted by viewerId
     */
    public int deleteViewerFromStoryViewers(int viewerId){
        return deleteItem(viewerId, "storyviewers", "viewerId");
    }
}
