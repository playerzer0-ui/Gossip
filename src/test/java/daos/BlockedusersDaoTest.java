package daos;

import business.Blockedusers;
import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BlockedusersDaoTest {

    /**
     * Test of addBlockUser_Success() method, of class BlockedusersDao.
     */
    @Test
    void addBlockUser_Success() {
        BlockedusersDao blockedusersDao = new BlockedusersDao("gossiptest");
        System.out.println("addBlockUser_Success");
        int userId = 4;
        int blockedId = 1;

        int result = blockedusersDao.addBlockUser(userId,blockedId);
        blockedusersDao.deleteBlockUser(1);
        assertTrue((result > 0));
    }

    /**
     * Test of addBlockUser_SuccessWithDelete() method, of class BlockedusersDao.
     */
    @Test
    void addBlockUser_SuccessWithDelete() {
        BlockedusersDao blockedusersDao = new BlockedusersDao("gossiptest");
        System.out.println("addBlockUser_SuccessWithDelete");
        int userId = 4;
        int blockedId = 3;

        int result = blockedusersDao.addBlockUser(userId,blockedId);
        int rowsDeleted = blockedusersDao.deleteBlockUser(3);

        assertTrue((result > 0));
        assertEquals(rowsDeleted, 1);
    }

    /**
     * Test of deleteBlockUser_byBlockedId() method, of class BlockedusersDao.
     */
    @Test
    void deleteBlockUser_byBlockedId() {
        BlockedusersDao blockedusersDao = new BlockedusersDao("gossiptest");
        System.out.println("deleteBlockUser_byBlockedId");
        blockedusersDao.addBlockUser(4,2);

        Blockedusers b = new Blockedusers(4,2);
        int id = b.getBlockedId();
        int expResult = 1;

        int result = blockedusersDao.deleteBlockUser(id);
        assertEquals(expResult, result);
    }
}