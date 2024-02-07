package daos;

import static org.junit.jupiter.api.Assertions.*;

import business.Message;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class MessageDaoIsolationTest {

    private Message msg1 = new Message(1, 1, 1, "hello", 1,LocalDateTime.of(2024, 1, 31, 21, 57, 14), 0);

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
        when(rs.getString("timeSent")).thenReturn("2024-01-31 21:57:14");
        when(rs.getInt("deletedState")).thenReturn(msg1.getDeletedState());

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

        when(dbConn.prepareStatement("insert into messages (inboxId,senderId,message,messageType) values (?,?,?,?)")).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        MessageDao messageDao = new MessageDao(dbConn);
        boolean result = messageDao.sendMessage(1, 1, "asdasdad", 1);

        verify(ps).executeUpdate();
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
    void sendMessage_noInboxId() {

    }
}