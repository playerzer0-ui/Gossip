package daos;

import business.Inbox;
import business.InboxParticipants;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InboxParticipantsDaoIsolationTest {
    /**
     * when there are InboxParticipants available
     **/
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

    /**
     * when there are no Inbox available
     **/
    @Test
    void getAllInbox_whenNoInboxParticipant() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        ArrayList<InboxParticipants> actual = ibpDao.getAllInbox(ibp.getUserId());
        ArrayList<InboxParticipants> expected = new ArrayList();
        assertEquals(expected, actual);
    }

    /**
     * when there are InboxParticipants available
     **/
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

    /**
     * when there are no InboxParticipants available
     **/
    @Test
    void getAllInboxParticipants_whenNoInboxParticipant() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where inboxId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        ArrayList<InboxParticipants> actual = ibpDao.getAllInboxParticipants(ibp.getInboxId());
        ArrayList<InboxParticipants> expected = new ArrayList();
        assertEquals(expected, actual);
    }

    /**
     * when other InboxParticipant is present
     **/
    @Test
    void getOtherInboxParticipant() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where inboxId=? and userId!=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("userId")).thenReturn(4);
        when(rs.getInt("inboxId")).thenReturn(ibp.getInboxId());
        when(rs.getInt("deletedState")).thenReturn(ibp.getDeletedState());
        when(rs.getInt("unseenMessages")).thenReturn(ibp.getUnseenMessages());
        when(rs.getInt("isOpen")).thenReturn(ibp.getIsOpen());
        when(rs.getTimestamp("timeSent")).thenReturn(Timestamp.valueOf(ibp.getTimeSent()));
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        InboxParticipants actual = ibpDao.getOtherInboxParticipant(ibp.getInboxId(), ibp.getUserId());
        InboxParticipants expected = new InboxParticipants(4, 2, 0, 0, 0, timeSent);
        ;
        assertEquals(expected, actual);
    }

    /**
     * when no InboxParticipant is present
     **/
    @Test
    void getOtherInboxParticipant_whenNotPresent() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where inboxId=? and userId!=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        InboxParticipants actual = ibpDao.getOtherInboxParticipant(ibp.getInboxId(), ibp.getUserId());
        InboxParticipants expected = null;
        assertEquals(expected, actual);
    }

    @Test
    void openInbox() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "update inboxparticipants set isOpen=? where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.openInbox(1, 1, 1);
        boolean expected = true;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        verify(ps).setInt(3, 1);
        assertEquals(expected, actual);
    }

    /**
     * when it fails
     **/
    @Test
    void openInbox_whenItFails() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "update inboxparticipants set isOpen=? where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.openInbox(1, 1, 1);
        boolean expected = false;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        verify(ps).setInt(3, 1);
        assertEquals(expected, actual);
    }

    /**
     * when unSeen messages gets updated
     **/
    @Test
    void updateUnSeenMessages() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "update inboxparticipants set unseenMessages=unseenMessages+1 where inboxId=? and userId=? and isOpen=0";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.updateUnSeenMessages(3, 2);
        boolean expected = true;
        verify(ps).setInt(1, 3);
        verify(ps).setInt(2, 2);
        assertEquals(expected, actual);
    }

    /**
     * when update fails
     **/
    @Test
    void updateUnSeenMessages_whenUpdateFails() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "update inboxparticipants set unseenMessages=unseenMessages+1 where inboxId=? and userId=? and isOpen=0";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.updateUnSeenMessages(3, 2);
        boolean expected = false;
        verify(ps).setInt(1, 3);
        verify(ps).setInt(2, 2);
        assertEquals(expected, actual);
    }

    /**
     * when it gets to reset
     **/
    @Test
    void resetUnSeenMessages() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "update inboxparticipants set unseenMessages=0 where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.resetUnSeenMessages(1, 1);
        boolean expected = true;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        assertEquals(expected, actual);
    }

    /**
     * when it doesn't get to reset
     **/
    @Test
    void resetUnSeenMessages_whenFalse() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "update inboxparticipants set unseenMessages=0 where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.resetUnSeenMessages(1, 1);
        boolean expected = false;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        assertEquals(expected, actual);
    }

    /**
     * insert under normal scenario
     **/
    @Test
    void insertInboxParticipant() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "insert into inboxparticipants (inboxId,userId,deletedState,unseenMessages,isOpen) values (?,?,?,?,?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.insertInboxParticipant(7, 2);
        boolean expected = true;
        verify(ps).setInt(1, 7);
        verify(ps).setInt(2, 2);
        assertEquals(expected, actual);
    }

    /**
     * when InboxParticipant is available
     **/
    @Test
    void getInboxParticipant() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where inboxId=? and userId=?";
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
        InboxParticipants actual = ibpDao.getInboxParticipant(ibp.getInboxId(), ibp.getUserId());
        InboxParticipants expected = ibp;
        assertEquals(expected, actual);
    }

    /**
     * when InboxParticipant is not available
     **/
    @Test
    void getInboxParticipant_whenNotPresent() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        LocalDateTime timeSent = LocalDateTime.now();
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        String query = "Select * from inboxparticipants where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        InboxParticipants actual = ibpDao.getInboxParticipant(ibp.getInboxId(), ibp.getUserId());
        InboxParticipants expected = null;
        assertEquals(expected, actual);
    }

    /**
     * when delete is successful
     **/
    @Test
    void deleteInboxParticipant() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "delete from inboxparticipants where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.deleteInboxParticipant(1, 1);
        boolean expected = true;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        assertEquals(expected, actual);
    }

    /**
     * when delete is fails
     **/
    @Test
    void deleteInboxParticipant_whenItFails() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        String query = "delete from inboxparticipants where inboxId=? and userId=?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);
        InboxParticipantsDao ibpDao = new InboxParticipantsDao(dbConn);
        boolean actual = ibpDao.deleteInboxParticipant(1, 1);
        boolean expected = false;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 1);
        assertEquals(expected, actual);
    }

}