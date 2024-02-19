package daos;

import business.InboxParticipants;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InboxParticipantsDaoTest {
    private InboxParticipantsDao ibpDao = new InboxParticipantsDao("gossiptest");

    /**
     * when there are InboxParticipants available
     **/
    @Test
    void getAllInbox() {
        ArrayList<InboxParticipants> expected = new ArrayList<>();
        LocalDateTime timeSent = null;
        InboxParticipants ibp = new InboxParticipants(3, 2, 0, 0, 0, timeSent);
        expected.add(ibp);
        ArrayList<InboxParticipants> actual = ibpDao.getAllInbox(3);
        assertEquals(actual, expected);
    }

    /**
     * when there are InboxParticipants available
     **/
    @Test
    void getAllInbox_whenNoInboxParticipant() {
        ArrayList<InboxParticipants> expected = new ArrayList<>();
        ArrayList<InboxParticipants> actual = ibpDao.getAllInbox(5);
        assertEquals(actual, expected);
    }

    @Test
    void getAllInboxParticipants() {
        ArrayList<InboxParticipants> actual = ibpDao.getAllInboxParticipants(1);
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
        InboxParticipants actual = ibpDao.getOtherInboxParticipant(1, 1);
        LocalDateTime timeSent = null;
        InboxParticipants expected = new InboxParticipants(2, 1, 0, 0, 0, timeSent);
        assertEquals(actual, expected);
    }

    /**
     * when no InboxParticipant is present
     **/
    @Test
    void getOtherInboxParticipant_whenNotPresent() {
        InboxParticipants actual = ibpDao.getOtherInboxParticipant(5, 5);
        InboxParticipants expected = null;
        assertEquals(actual, expected);
    }

    /**
     * when it succeeds
     **/
    @Test
    void openInbox() {
        InboxParticipants ibp1 = ibpDao.getInboxParticipant(1, 1);
        Boolean actual = ibpDao.openInbox(1, 1, 1);
        Boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp2 = ibpDao.getInboxParticipant(1, 1);
            assertEquals(ibp1.getInboxId(), ibp2.getInboxId());
            assertEquals(ibp1.getDeletedState(), ibp2.getDeletedState());
            assertEquals(ibp1.getUserId(), ibp2.getUserId());
            //assertEquals(ibp1.getTimeSent(), ibp2.getTimeSent());
            System.out.println(ibp1.getTimeSent().toString() + " " + ibp2.getTimeSent().toString());
            assertEquals(ibp1.getUnseenMessages(), ibp2.getUnseenMessages());
            assertEquals(ibp2.getIsOpen(), 1);
            if (ibp2.getIsOpen() == 1) {
                ibpDao.openInbox(1, 1, 0);
            }
        }
    }

    /**
     * when it fails
     **/
    @Test
    void openInbox_whenItFails() {
        Boolean actual = ibpDao.openInbox(1, 5, 1);
        Boolean expected = false;
        assertEquals(actual, expected);
    }

    /**
     * when unSeen messages gets updated
     **/
    @Test
    void updateUnSeenMessages() {
        Boolean actual = ibpDao.updateUnSeenMessages(1, 1);
        InboxParticipants ibp1 = ibpDao.getInboxParticipant(1, 1);
        Boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp2 = ibpDao.getInboxParticipant(1, 1);
            assertEquals(ibp2.getUnseenMessages(), 1);
            assertEquals(ibp1.getInboxId(), ibp2.getInboxId());
            assertEquals(ibp1.getDeletedState(), ibp2.getDeletedState());
            assertEquals(ibp1.getUserId(), ibp2.getUserId());
            assertEquals(ibp1.getIsOpen(), ibp2.getIsOpen());
            // assertEquals(ibp1.getTimeSent(), ibp2.getTimeSent());
            System.out.println(ibp1.getTimeSent().toString());
            if (ibp2.getUnseenMessages() == 1) {
                ibpDao.resetUnSeenMessages(1, 1);
            }
        }

    }

    /**
     * when update fails
     **/
    @Test
    void updateUnSeenMessages_whenUpdateFails() {
        Boolean actual = ibpDao.updateUnSeenMessages(1, 5);
        Boolean expected = false;
        assertEquals(actual, expected);
    }

    /**
     * when it gets to reset
     **/
    @Test
    void resetUnSeenMessages() {
        ibpDao.updateUnSeenMessages(1, 1);
        Boolean actual = ibpDao.resetUnSeenMessages(1, 1);
        InboxParticipants ibp1 = ibpDao.getInboxParticipant(1, 1);
        Boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp2 = ibpDao.getInboxParticipant(1, 1);
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
        Boolean actual = ibpDao.resetUnSeenMessages(1, 5);
        Boolean expected = false;
        assertEquals(actual, expected);
    }

    /**
     * When insert is successful
     **/
    @Test
    void insertInboxParticipant() {
        boolean actual = ibpDao.insertInboxParticipant(2, 4);
        boolean expected = true;
        assertEquals(actual, expected);
        if (actual == expected) {
            InboxParticipants ibp1 = ibpDao.getInboxParticipant(2, 4);
            InboxParticipants ibp2 = new InboxParticipants(4, 2, 0, 0, 0, null);
            assertEquals(ibp1.getUserId(), ibp2.getUserId());
            assertEquals(ibp1.getInboxId(), ibp2.getInboxId());
            assertEquals(ibp1.getDeletedState(), ibp2.getDeletedState());
            assertEquals(ibp1.getUnseenMessages(), ibp2.getUnseenMessages());
            assertEquals(ibp1.getIsOpen(), ibp2.getIsOpen());
            //assertEquals(ibp1.getTimeSent(),ibp2.getTimeSent());
            ibpDao.deleteInboxParticipant(2, 4);
        }
    }

    /**
     * when InboxParticipant is available
     **/
    @Test
    void getInboxParticipant() {
        InboxParticipants actual = ibpDao.getInboxParticipant(1, 1);
        LocalDateTime localDateTime = LocalDateTime.of(2024, 02, 15, 21, 56, 20);
        InboxParticipants expected = new InboxParticipants(1, 1, 0, 0, 0, localDateTime);
        assertEquals(actual, expected);
    }

    /**
     * when InboxParticipant is not available
     **/
    @Test
    void getInboxParticipant_whenNotPresent() {
        InboxParticipants actual = ibpDao.getInboxParticipant(1, 10);
        InboxParticipants expected = null;
        assertEquals(actual, expected);
    }

    /**
     * when delete is successful
     **/
    @Test
    void deleteInboxParticipant() {
        ibpDao.insertInboxParticipant(2, 4);
        boolean actual = ibpDao.deleteInboxParticipant(2, 4);
        Boolean expected = true;
        assertEquals(actual, expected);
    }

    /**
     * when delete is fails
     **/
    @Test
    void deleteInboxParticipant_whenItFails() {
        boolean actual = ibpDao.deleteInboxParticipant(2, 10);
        Boolean expected = false;
        assertEquals(actual, expected);
    }
}