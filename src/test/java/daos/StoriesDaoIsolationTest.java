package daos;

import business.Reports;
import business.Stories;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class StoriesDaoIsolationTest {

    /**
     * Test of addStory_Success() method, of class StoriesDao.
     */
    @Test
    void addStory_Success() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "INSERT INTO stories (userId, story, storyType, dateTime, storyDescription) VALUES (?, ?, ?, ?, ?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        LocalDateTime reportDate = LocalDateTime.of(2024,1,10,19,8,36);

        StoriesDao storiesDao = new StoriesDao(dbConn);
        int result = storiesDao.addStory(1,"food.png",1,reportDate,"Happy");

        verify(ps).setInt(1, 1);
        verify(ps).setString(2,"food.png");
        verify(ps).setInt(3, 1);
        verify(ps).setTimestamp(4, Timestamp.valueOf(reportDate));
        verify(ps).setString(5, "Happy");

        assertTrue((result > 0));
    }

    /**
     * Test of deleteStory_byStoryId() method, of class StoriesDao.
     */
    @Test
    void deleteStory_byStoryId() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "stories";
        String IDname = "storyId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        StoriesDao storiesDao = new StoriesDao(dbConn);
        int result = storiesDao.deleteStory(2);
        verify(ps).setInt(1, 2);

        assertEquals(1, result);
    }

    /**
     * Test of getStoryById() method, of class StoriesDao.
     */
    @Test
    void getStoryById() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        int storyId = 1;
        LocalDateTime reporteDate = LocalDateTime.of(2024,2,2,0,0,0);
        Stories s = new Stories(storyId,2, "story.png", 1, reporteDate, "Good morining viewers");

        when(dbConn.prepareStatement("SELECT * FROM stories WHERE storyId = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("storyId")).thenReturn(s.getStoryId());
        when(rs.getInt("userID")).thenReturn(s.getUserId());
        when(rs.getString("story")).thenReturn(s.getStory());
        when(rs.getInt("storyType")).thenReturn(s.getStoryType());
        when(rs.getTimestamp("dateTime")).thenReturn(Timestamp.valueOf(s.getDateTime()));
        when(rs.getString("storyDescription")).thenReturn(s.getStoryDescription());

        StoriesDao storiesDao = new StoriesDao(dbConn);
        Stories actual = storiesDao.getStoryById(storyId);
        assertEquals(actual, s);

    }

    /**
     * Test of getStoryById_notFoundId() method, of class StoriesDao.
     */
    @Test
    void getStoryById_notFoundId() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        LocalDateTime reporteDate = LocalDateTime.of(2024,2,2,0,0,0);
        Stories s = new Stories(122,2, "story.png", 1, reporteDate, "Good morining viewers");

        when(dbConn.prepareStatement("SELECT * FROM stories WHERE storyId = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        StoriesDao storiesDao = new StoriesDao(dbConn);
        Stories actual = storiesDao.getStoryById(s.getStoryId());
        assertEquals(null,actual);
    }

    /**
     * Test of getAllStories() method, of class StoriesDao.
     */
    @Test
    void getAllStories() throws SQLException {

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        LocalDateTime reporteDate = LocalDateTime.of(2024,2,2,0,0,0);
        Stories s = new Stories(1,2, "story.png", 1, reporteDate, "Good morining viewers");


        when(dbConn.prepareStatement("SELECT * FROM stories")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("storyId")).thenReturn(s.getStoryId());
        when(rs.getInt("userID")).thenReturn(s.getUserId());
        when(rs.getString("story")).thenReturn(s.getStory());
        when(rs.getInt("storyType")).thenReturn(s.getStoryType());
        when(rs.getTimestamp("dateTime")).thenReturn(Timestamp.valueOf(s.getDateTime()));
        when(rs.getString("storyDescription")).thenReturn(s.getStoryDescription());

        StoriesDao storiesDao = new StoriesDao(dbConn);
        ArrayList<Stories> actual = (ArrayList<Stories>) storiesDao.getAllStories();
        ArrayList<Stories> expected = new ArrayList();
        expected.add(s);
        assertEquals(expected, actual);

    }
}