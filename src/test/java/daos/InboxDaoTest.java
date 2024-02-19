package daos;

import business.Inbox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InboxDaoTest {
  InboxDao inboxDao =new InboxDao("gossiptest");
    @Test
    void createGroupChat() {
        int id =inboxDao.createGroupChat(1,"Barcelona");
        boolean actual=false;
        if(id>0){
            actual=true;
         Inbox  inbox1 = inboxDao.getInbox(id);
         Inbox  inbox2 =new Inbox(id,2,1,"Barcelona","groupPicture.png");
         assertEquals(inbox1,inbox2);
            assertEquals(inbox1.getInboxType(),inbox2.getInboxType());
            assertEquals(inbox1.getGroupName(),inbox2.getGroupName());
            assertEquals(inbox1.getAdminId(),inbox2.getAdminId());
            assertEquals(inbox1.getGroupProfilePicture(),inbox2.getGroupProfilePicture());
         inboxDao.deleteItem(id,"inbox","inboxId");
         inboxDao.updateIncrement("inbox",2);
        }
        assertEquals(actual,true);
    }

    @Test
    void updateInbox() {
    }

    @Test
    void createNormalInbox() {
    }

    @Test
    void getInbox() {
    }
}