package daos;

import business.Inbox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InboxDaoTest {
    InboxDao inboxDao = new InboxDao("gossiptest");
/**when creating a groupChat*/
    @Test
    void createGroupChat() {
        int id = inboxDao.createGroupChat(1, "Barcelona");
        boolean actual = false;
        if (id > 0) {
            actual = true;
            Inbox inbox1 = inboxDao.getInbox(id);
            Inbox inbox2 = new Inbox(id, 2, 1, "Barcelona", null);
            assertEquals(inbox1, inbox2);
            assertEquals(inbox1.getInboxType(), inbox2.getInboxType());
            assertEquals(inbox1.getGroupName(), inbox2.getGroupName());
            assertEquals(inbox1.getAdminId(), inbox2.getAdminId());
            assertEquals(inbox1.getGroupProfilePicture(), inbox2.getGroupProfilePicture());
            inboxDao.deleteItem(id, "inbox", "inboxId");
            inboxDao.updateIncrement("inbox", 2);
        }
        assertEquals(actual, true);
    }
/****/
    @Test
    void updateInbox() {
       int id = inboxDao.createGroupChat(2,"fun group");
        Inbox inbox1 = inboxDao.getInbox(id);
       Inbox inbox2=new Inbox(id,2,2,"justFootball",null);
        inbox1.updateInbox(inbox2);
        boolean actual=inboxDao.updateInbox(inbox1);
       boolean expected= true;
        assertEquals(actual,expected);
        if(actual==expected){
            Inbox inbox3 = inboxDao.getInbox(id);
            assertEquals(inbox3.getInboxId(),inbox2.getInboxId());
            assertEquals(inbox3.getInboxType(),inbox2.getInboxType());
            assertEquals(inbox3.getGroupProfilePicture(),inbox2.getGroupProfilePicture());
            assertEquals(inbox3.getGroupName(),inbox2.getGroupName());
            assertEquals(inbox3.getAdminId(),inbox2.getAdminId());
            inboxDao.deleteItem(id, "inbox", "inboxId");
            inboxDao.updateIncrement("inbox", 2);
        }
    }

    @Test
    void createNormalInbox() {
        int id = inboxDao.createNormalInbox();
        Inbox actual = inboxDao.getInbox(id);
        Inbox expected = new Inbox(id, 1);
        assertEquals(actual, expected);
       // if(id>0 && actual==expected) {
            assertEquals(actual.getInboxType(),expected.getInboxType());
            assertEquals(actual.getAdminId(),expected.getAdminId());
            assertEquals(actual.getGroupName(),expected.getGroupName());
            assertEquals(actual.getGroupProfilePicture(),actual.getGroupProfilePicture());
            inboxDao.deleteItem(id,"inbox", "inboxId");
            inboxDao.updateIncrement("inbox", 2);
      //  }
    }
/**when inbox is available**/
    @Test
    void getInbox() {
        Inbox actual = inboxDao.getInbox(1);
        Inbox expected = new Inbox(1,1);
        assertEquals(actual,expected);
       // assertEquals(actual.getInboxType(),expected.getInboxType());

    }

    /**when inbox is not available**/
    @Test
    void getInbox_whenNotFound() {
        Inbox actual = inboxDao.getInbox(1000);
        Inbox expected = null;
        assertEquals(actual,expected);
        // assertEquals(actual.getInboxType(),expected.getInboxType());

    }
}