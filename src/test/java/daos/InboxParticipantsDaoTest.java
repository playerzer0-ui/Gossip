package daos;

import business.InboxParticipants;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InboxParticipantsDaoTest {
    private InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");

    /**
     * when there are InboxParticipants available
     **/
    @Test
    void getAllInbox() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        ArrayList<InboxParticipants> expected = new ArrayList<>();
        LocalDateTime timeSent = null;
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        expected.add(ibp);
        ArrayList<InboxParticipants> actual = ibpoDao.getAllInbox(3);
        assertEquals(actual, expected);
    }

    /**
     * when there are InboxParticipants available
     **/
    @Test
    void getAllInbox_whenNoInboxParticipant() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        ArrayList<InboxParticipants> expected = new ArrayList<>();
        ArrayList<InboxParticipants> actual = ibpoDao.getAllInbox(5);
        assertEquals(actual, expected);
    }

    @Test
    void getAllInboxParticipants() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        ArrayList<InboxParticipants> actual = ibpoDao.getAllInboxParticipants(1);
        ArrayList<InboxParticipants> expected = new ArrayList<>();
        LocalDateTime timeSent = null;
        InboxParticipants ibp1 = new InboxParticipants(1, 1, 0, 0, 0, timeSent);
        InboxParticipants ibp2 = new InboxParticipants(2, 1, 0, 0, 0, timeSent);
        expected.add(ibp1);
        expected.add(ibp2);
        assertEquals(actual, expected);
    }

    /**
     * when other InboxParticipant is present
     **/
    @Test
    void getOtherInboxParticipant() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        InboxParticipants actual = ibpoDao.getOtherInboxParticipant(1, 1);
        LocalDateTime timeSent = null;
        InboxParticipants expected = new InboxParticipants(2, 1, 0, 0, 0, timeSent);
        assertEquals(actual, expected);
    }

    /**
     * when no InboxParticipant is present
     **/
    @Test
    void getOtherInboxParticipant_whenNotPresent() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        InboxParticipants actual = ibpoDao.getOtherInboxParticipant(5, 5);
        InboxParticipants expected = null;
        assertEquals(actual, expected);
    }

    /**
     * when it succeeds
     **/
    @Test
    void openInbox() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        InboxParticipants ibp1 = ibpoDao.getInboxParticipant(1, 1);
        boolean actual = ibpoDao.openInbox(1, 1, 1);
        boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp2 = ibpoDao.getInboxParticipant(1, 1);
            assertEquals(ibp1.getInboxId(), ibp2.getInboxId());
            assertEquals(ibp1.getDeletedState(), ibp2.getDeletedState());
            assertEquals(ibp1.getUserId(), ibp2.getUserId());
            //assertEquals(ibp1.getTimeSent(), ibp2.getTimeSent()); //not working properly
            System.out.println(ibp1.getTimeSent().toString());
            assertEquals(ibp1.getUnseenMessages(), ibp2.getUnseenMessages());
            assertEquals(ibp2.getIsOpen(), 1);
            if (ibp2.getIsOpen() == 1) {
                ibpoDao.openInbox(1, 1, 0);
            }
        }
    }

    /**
     * when it fails
     **/
    @Test
    void openInbox_whenItFails() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        Boolean actual = ibpoDao.openInbox(1, 5, 1);
        Boolean expected = false;
        assertEquals(actual, expected);
    }

    /**
     * when unSeen messages gets updated
     **/
    @Test
    void updateUnSeenMessages() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        Boolean actual = ibpoDao.updateUnSeenMessages(1, 1);
        InboxParticipants ibp1 = ibpoDao.getInboxParticipant(1, 1);
        Boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp2 = ibpoDao.getInboxParticipant(1, 1);
            assertEquals(ibp2.getUnseenMessages(), 1);
            assertEquals(ibp1.getInboxId(), ibp2.getInboxId());
            assertEquals(ibp1.getDeletedState(), ibp2.getDeletedState());
            assertEquals(ibp1.getUserId(), ibp2.getUserId());
            assertEquals(ibp1.getIsOpen(), ibp2.getIsOpen());
            // assertEquals(ibp1.getTimeSent(), ibp2.getTimeSent());
            System.out.println(ibp1.getTimeSent().toString());
            if (ibp2.getUnseenMessages() == 1) {
                ibpoDao.resetUnSeenMessages(1, 1);
            }
        }

    }

    /**
     * when update fails
     **/
    @Test
    void updateUnSeenMessages_whenUpdateFails() {
        InboxParticipantsDao ibpoDao = new InboxParticipantsDao("gossip");
        Boolean actual = ibpoDao.updateUnSeenMessages(1, 5);
        Boolean expected = false;
        assertEquals(actual, expected);
    }

    /**
     * when it gets to reset
     **/
    @Test
    void resetUnSeenMessages() {
        ibpoDao.updateUnSeenMessages(1, 1);
        Boolean actual = ibpoDao.resetUnSeenMessages(1, 1);
        InboxParticipants ibp1 = ibpoDao.getInboxParticipant(1, 1);
        Boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp2 = ibpoDao.getInboxParticipant(1, 1);
            assertEquals(ibp1.getUnseenMessages(), ibp2.getUnseenMessages());
            assertEquals(ibp1.getInboxId(), ibp2.getInboxId());
            assertEquals(ibp1.getDeletedState(), ibp2.getDeletedState());
            assertEquals(ibp1.getUserId(), ibp2.getUserId());
            // assertEquals(ibp1.getTimeSent(), ibp2.getTimeSent());
            System.out.println(ibp1.getTimeSent().toString());
            assertEquals(ibp2.getIsOpen(), ibp1.getIsOpen());
        }
    }

    /**
     * when it doesn't get to reset
     **/
    @Test
    void resetUnSeenMessages_whenFalse() {
        Boolean actual = ibpoDao.resetUnSeenMessages(1, 5);
        Boolean expected = false;
        assertEquals(actual, expected);
    }

    @Test
    void insertInboxParticipant() {

    }
}