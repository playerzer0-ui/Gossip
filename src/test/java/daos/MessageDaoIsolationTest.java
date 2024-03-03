package daos;

import static org.junit.jupiter.api.Assertions.*;

import business.Message;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class
MessageDaoIsolationTest {

    private Message msg1 = new Message(1, 1, 1, "hello", 1,LocalDateTime.of(2024, 1, 31, 21, 57, 14), 0, 0, null);

    /**
     * getMessage, normal scenario
     */
    @Test
    void getMessage_normal() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("Select * from messages where messageId=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false); // First call returns true, second call returns false

        // Set up the values to be returned when rs.getInt, rs.getString, etc. are called
        when(rs.getInt("messageId")).thenReturn(msg1.getMessageId());
        when(rs.getInt("inboxId")).thenReturn(msg1.getInboxId());
        when(rs.getInt("senderId")).thenReturn(msg1.getSenderId());
        when(rs.getString("message")).thenReturn(msg1.getMessage());
        when(rs.getInt("messageType")).thenReturn(msg1.getMessageType());
        when(rs.getTimestamp("timeSent")).thenReturn(Timestamp.valueOf(msg1.getTimeSent()));
        when(rs.getInt("deletedState")).thenReturn(msg1.getDeletedState());
        when(rs.getInt("messageKey")).thenReturn(msg1.getMessageKey());
        when(rs.getString("originalFileName")).thenReturn(msg1.getOriginalFileName());

        MessageDao messageDao = new MessageDao(dbConn);
        Message result = messageDao.getMessage(1);

        // Verify that setInt was called with the correct parameter
        verify(ps).setInt(1, 1);

        assertEquals(msg1, result);
    }

    /**
     * getMessage, but no ID
     */
    @Test
    void getMessage_noID() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("Select * from messages where messageId=?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        MessageDao messageDao = new MessageDao(dbConn);
        Message result = messageDao.getMessage(100);

        // Verify that setInt was called with the correct parameter
        verify(ps).setInt(1, 100);

        assertNull(result);
    }

    /**
     *sendMessage, normal scenario
     */
    @Test
    void sendMessage_normal() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "insert into messages (inboxId,senderId,message,messageType) values (?,?,?,?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        MessageDao messageDao = new MessageDao(dbConn);
        boolean result = messageDao.sendMessage(1, 1, "asdasdad", 1);

        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        verify(ps).setString(3, "asdasdad");
        verify(ps).setInt(4, 1);

        assertTrue(result);
    }

    /**
     *sendMessage, no inboxId found
     */
    @Test
    void sendMessage_noInboxId() throws SQLException {
// Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(dbConn.prepareStatement
                ("insert into messages (inboxId,senderId,message,messageType) values (?,?,?,?)")).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        MessageDao messageDao = new MessageDao(dbConn);
        boolean result = messageDao.sendMessage(100, 1, "asdasdad", 1);

        verify(ps).setInt(1, 100);
        verify(ps).setInt(2, 1);
        verify(ps).setString(3, "asdasdad");
        verify(ps).setInt(4, 1);

        assertFalse(result);
    }

    /**
     * getMessages, normal scenario
     */
    @Test
    void getMessages_normal() throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();

        Message exp1 = new Message(2, 1, 2, "hi", 1, LocalDateTime.of(2024,1,31,21,58,28), 0);

        messages.add(msg1);
        messages.add(exp1);
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("Select * from messages where inboxId=? and deletedState=0")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false); // First call returns true, second call returns false

        // Set up the values to be returned when rs.getInt, rs.getString, etc. are called
        when(rs.getInt("messageId")).thenReturn(msg1.getMessageId(), exp1.getMessageId());
        when(rs.getInt("inboxId")).thenReturn(msg1.getInboxId(), exp1.getInboxId());
        when(rs.getInt("senderId")).thenReturn(msg1.getSenderId(), exp1.getSenderId());
        when(rs.getString("message")).thenReturn(msg1.getMessage(), exp1.getMessage());
        when(rs.getInt("messageType")).thenReturn(msg1.getMessageType(), exp1.getMessageType());
        when(rs.getTimestamp("timeSent")).thenReturn(Timestamp.valueOf(msg1.getTimeSent()), Timestamp.valueOf(exp1.getTimeSent()));
        when(rs.getInt("deletedState")).thenReturn(msg1.getDeletedState(), exp1.getDeletedState());
        when(rs.getInt("messageKey")).thenReturn(msg1.getMessageKey());
        when(rs.getString("originalFileName")).thenReturn(msg1.getOriginalFileName());

        MessageDao messageDao = new MessageDao(dbConn);
        ArrayList<Message> result = messageDao.getMessages(1);

        assertEquals(messages, result);
    }

    /**
     * getMessages, no inboxId, means so such conversation happened
     */
    @Test
    void getMessages_noInboxId() throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("Select * from messages where inboxId=? and deletedState=0")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        MessageDao messageDao = new MessageDao(dbConn);
        ArrayList<Message> result = messageDao.getMessages(100);

        assertEquals(messages, result);
    }

    /**
     * delete messages, normal scenario
     */
    @Test
    void deleteMessages_normal() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String tableName = "messages";
        String IDname = "messageId";

        String query = "DELETE FROM " + tableName + " WHERE " + IDname + " = ?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        MessageDao messageDao = new MessageDao(dbConn);
        int result = messageDao.deleteMessages(6);

        verify(ps).setInt(1, 6);

        assertEquals(1, result);
    }

    /**
     * delete messages, no ID found
     */
    @Test
    void deleteMessages_noID() throws SQLException {
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String tableName = "messages";
        String IDname = "messageId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        MessageDao messageDao = new MessageDao(dbConn);
        int result = messageDao.deleteMessages(60);

        verify(ps).setInt(1, 60);

        assertEquals(0, result);
    }

    /**
     * getDailyMessageCount, there is one new message in the day
     */
    @Test
    void getDailyMessageCount_normal() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("SELECT count(*) FROM messages where DATE(timeSent) = CURDATE()")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("count(*)")).thenReturn(1);

        MessageDao messageDao = new MessageDao(dbConn);
        int act = messageDao.getDailyMessageCount();

        assertEquals(1, act);
    }

    /**
     * getTotalMessageCount, normal scenario
     */
    @Test
    void getTotalMessageCount_normal() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("SELECT count(*) FROM messages")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("count(*)")).thenReturn(1);

        MessageDao messageDao = new MessageDao(dbConn);
        int act = messageDao.getTotalMessageCount();

        assertEquals(1, act);
    }
}