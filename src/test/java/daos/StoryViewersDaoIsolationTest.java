package daos;

import business.StoryViewers;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoryViewersDaoIsolationTest {

    private final StoryViewers sv1 = new StoryViewers(1, 1, LocalDateTime.of(2024,2,2, 8,30,0));
    private final StoryViewers sv2 = new StoryViewers(1, 3, LocalDateTime.of(2024,2,2, 10,30,0));

    /**
     * getViewersByStoryID, normal
     */
    @Test
    void getViewersByStoryID_normal() throws SQLException {
        List<StoryViewers> exp = new ArrayList<>();
        exp.add(sv1);
        exp.add(sv2);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("select * from storyviewers where storyId=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("storyId")).thenReturn(sv1.getStoryId(), sv2.getStoryId());
        when(rs.getInt("viewerId")).thenReturn(sv1.getViewId(), sv2.getViewId());
        when(rs.getTimestamp("viewTime")).thenReturn(Timestamp.valueOf(sv1.getViewTime()), Timestamp.valueOf(sv2.getViewTime()));

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        List<StoryViewers> result = sv.getViewersByStoryID(1);

        verify(ps).setInt(1, 1);

        assertEquals(exp, result);
    }

    /**
     * getViewersByStoryID, no story ID
     */
    @Test
    void getViewersByStoryID_noID() throws SQLException {
        List<StoryViewers> exp = new ArrayList<>();
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("select * from storyviewers where storyId=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn( false);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        List<StoryViewers> result = sv.getViewersByStoryID(10);

        verify(ps).setInt(1, 10);

        assertEquals(exp, result);
    }

    /**
     * getViewersByViewerID, normal scenario
     */
    @Test
    void getViewersByViewerID_normal() throws SQLException {
        List<StoryViewers> exp = new ArrayList<>();
        exp.add(sv1);
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("select * from storyviewers where viewerId=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true,  false);
        when(rs.getInt("storyId")).thenReturn(sv1.getStoryId());
        when(rs.getInt("viewerId")).thenReturn(sv1.getViewId());
        when(rs.getTimestamp("viewTime")).thenReturn(Timestamp.valueOf(sv1.getViewTime()));

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        List<StoryViewers> result = sv.getViewersByViewerID(1);

        verify(ps).setInt(1, 1);

        assertEquals(exp, result);
    }

    /**
     * getViewersByViewerID, no viewer ID
     */
    @Test
    void getViewersByViewerID_noID() throws SQLException {
        List<StoryViewers> exp = new ArrayList<>();
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("select * from storyviewers where viewerId=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(  false);
        StoryViewersDao sv = new StoryViewersDao(dbConn);
        List<StoryViewers> result = sv.getViewersByViewerID(100);

        verify(ps).setInt(1, 100);

        assertEquals(exp, result);

    }

    /**
     * insertStoryViewer, normal scenario
     */
    @Test
    void insertStoryViewer_normal() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(dbConn.prepareStatement("INSERT INTO storyviewers (storyId, viewerId, viewTime) VALUES (?, ?, ?)")).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.insertStoryViewer(1, 2, sv1.getViewTime());

        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 2);
        verify(ps).setTimestamp(3, Timestamp.valueOf(sv1.getViewTime()));

        assertEquals(1, result);
    }

    /**
     * insertStoryViewer, incorrect storyID
     */
    @Test
    void insertStoryViewer_noStoryID_found() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(dbConn.prepareStatement("INSERT INTO storyviewers (storyId, viewerId, viewTime) VALUES (?, ?, ?)")).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.insertStoryViewer(10, 2, sv1.getViewTime());

        verify(ps).setInt(1, 10);
        verify(ps).setInt(2, 2);
        verify(ps).setTimestamp(3, Timestamp.valueOf(sv1.getViewTime()));

        assertEquals(0, result);
    }

    /**
     * insertStoryViewer, incorrect viewerID
     */
    @Test
    void insertStoryViewer_noViewerID_found() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(dbConn.prepareStatement("INSERT INTO storyviewers (storyId, viewerId, viewTime) VALUES (?, ?, ?)")).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.insertStoryViewer(1, 20, sv1.getViewTime());

        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 20);
        verify(ps).setTimestamp(3, Timestamp.valueOf(sv1.getViewTime()));

        assertEquals(1, result);
    }

    /**
     * deleteStoryFromStoryViewers, normal scenario
     */
    @Test
    void deleteStoryFromStoryViewers_normal() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "storyviewers";
        String IDname = "storyId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(2);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.deleteStoryFromStoryViewers(1);

        verify(ps).setInt(1, 1);

        assertEquals(2, result);
    }

    /**
     * deleteStoryFromStoryViewers, no story found
     */
    @Test
    void deleteStoryFromStoryViewers_noID() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "storyviewers";
        String IDname = "storyId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.deleteStoryFromStoryViewers(10);

        verify(ps).setInt(1, 10);

        assertEquals(0, result);
    }

    /**
     * deleteViewerFromStoryViewers, normal scenario
     */
    @Test
    void deleteViewerFromStoryViewers_normal() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "storyviewers";
        String IDname = "viewerId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.deleteViewerFromStoryViewers(1);

        verify(ps).setInt(1, 1);

        assertEquals(1, result);
    }

    /**
     * deleteViewerFromStoryViewers, normal scenario
     */
    @Test
    void deleteViewerFromStoryViewers_noID() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "storyviewers";
        String IDname = "viewerId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        StoryViewersDao sv = new StoryViewersDao(dbConn);
        int result = sv.deleteViewerFromStoryViewers(100);

        verify(ps).setInt(1, 100);

        assertEquals(0, result);
    }
}