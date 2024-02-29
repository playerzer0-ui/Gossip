package daos;

import business.Blockedusers;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BlockedusersDaoIsolationTest {

   // private Blockedusers blockUser1 = new Blockedusers(2,4);

    /**
     * Test of addBlockUser_Success() method, of class BlockedusersDao.
     */
    @Test
    void addBlockUser_Success() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "INSERT INTO blockedusers (userId, blockedId) VALUES (?, ?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        BlockedusersDao blockedusersDao = new BlockedusersDao(dbConn);
        int result = blockedusersDao.addBlockUser(2,4);

        verify(ps).setInt(1, 2);
        verify(ps).setInt(2, 4);

        assertTrue((result > 0));
    }

    /**
     * Test of addBlockUser_SuccessWithDelete() method, of class BlockedusersDao.
     */
    @Test
    void addBlockUser_SuccessWithDelete() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "INSERT INTO blockedusers (userId, blockedId) VALUES (?, ?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        BlockedusersDao blockedusersDao = new BlockedusersDao(dbConn);
        int result = blockedusersDao.addBlockUser(2,3);

        verify(ps).setInt(1, 2);
        verify(ps).setInt(2, 3);

        assertTrue((result > 0));

        if (result != -1) {
            System.out.println("Method returned appropriately, confirming database changed by trying to remove what was added");
            int rowsDeleted = blockedusersDao.deleteBlockUser(3);
            assertEquals(rowsDeleted, 1);
        }
    }

    /**
     * Test of deleteBlockUser_byBlockedId() method, of class BlockedusersDao.
     */
    @Test
    void deleteBlockUser_byBlockedId() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "blockedusers";
        String IDname = "blockedId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        BlockedusersDao blockedusersDao = new BlockedusersDao(dbConn);
        int result = blockedusersDao.deleteBlockUser(4);

        verify(ps).setInt(1, 4);

        assertEquals(1, result);
    }
}