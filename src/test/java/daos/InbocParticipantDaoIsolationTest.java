package daos;

import business.Inbox;
import business.InboxParticipants;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InboxParticipantsDaoIsolationTest {
    @Test
    void getAllInbox() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("userId")).thenReturn(ibp.getUserId());
        when(rs.getInt("inboxId")).thenReturn(ibp.getInboxId());
        when(rs.getInt("deletedState")).thenReturn(ibp.getDeletedState());
        when(rs.getInt("unseenMessages")).thenReturn(ibp.getUnseenMessages());
        when(rs.getInt("isOpen")).thenReturn(ibp.getIsOpen());
        when(rs.getTimestamp("timeSent")).thenReturn(Timestamp.valueOf(ibp.getTimeSent()));

        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        ArrayList<InboxParticipants> actual = ibpDao.getAllInbox(ibp.getUserId());
        ArrayList<InboxParticipants> expected = new ArrayList();
        expected.add(ibp);
        assertEquals(expected, actual);
    }

    @Test
    void getAllInboxParticipants() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where inboxId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("userId")).thenReturn(ibp.getUserId());
        when(rs.getInt("inboxId")).thenReturn(ibp.getInboxId());
        when(rs.getInt("deletedState")).thenReturn(ibp.getDeletedState());
        when(rs.getInt("unseenMessages")).thenReturn(ibp.getUnseenMessages());
        when(rs.getInt("isOpen")).thenReturn(ibp.getIsOpen());
        when(rs.getTimestamp("timeSent")).thenReturn(Timestamp.valueOf(ibp.getTimeSent()));

        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        ArrayList<InboxParticipants> actual = ibpDao.getAllInboxParticipants(ibp.getInboxId());
        ArrayList<InboxParticipants> expected = new ArrayList();
        expected.add(ibp);
        assertEquals(expected, actual);
    }
}