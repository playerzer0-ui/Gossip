package daos;

import business.Message;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MessageDaoTest {

    private final MessageDao messageDao = new MessageDao("gossiptest");

    /**
     * getMessage, normal scenario
     */
    @Test
    void getMessage_normal() {
        Message exp = new Message(1, 1, 1, "hello", 1, LocalDateTime.of(2024, 1, 31, 21, 57, 14), 0);
        Message act = messageDao.getMessage(1);

        assertEquals(exp, act);
        assertEquals(exp.getMessage(), act.getMessage());
    }

    /**
     * getMessage, but no ID
     */
    @Test
    void getMessage_noID() {
        Message act = messageDao.getMessage(10);

        assertNull(act);
    }

    /**
     *sendMessage, normal scenario
     */
    @Test
    void sendMessage_normal() {
        boolean act = messageDao.sendMessage(1, 1, "asdasdad", 1);

        messageDao.deleteItem(6, "messages", "messageId");
        messageDao.updateIncrement("messages", 5);

        assertTrue(act);
    }

    /**
     *sendMessage, no inboxId found
     */
    @Test
    void sendMessage_noInboxId() {
        boolean act = messageDao.sendMessage(100, 1, "asdasdad", 1);
        messageDao.updateIncrement("messages", 5);
        assertFalse(act);
    }

    /**
     *sendMessage, no sender found
     */
    @Test
    void sendMessage_noSenderId() {
        boolean act = messageDao.sendMessage(1, 100, "asdasdad", 1);
        messageDao.updateIncrement("messages", 5);
        assertFalse(act);
    }

    /**
     * getMessages, normal scenario
     */
    @Test
    void getMessages_normal() {
        ArrayList<Message> messages = messageDao.getMessages(1);
        Message exp = new Message(1, 1, 1, "hello", 1, LocalDateTime.of(2024, 1, 31, 21, 57, 14), 0);
        Message exp1 = new Message(2, 1, 2, "hi", 1, LocalDateTime.of(2024,1,31,21,58,28), 0);

        ArrayList<Message> expected = new ArrayList<>();
        expected.add(exp);
        expected.add(exp1);

        assertEquals(messages, expected);
    }

    /**
     * getMessages, no inboxId, means so such conversation happened
     */
    @Test
    void getMessages_noInboxId(){
        ArrayList<Message> messages = messageDao.getMessages(100);
        ArrayList<Message> exp = new ArrayList<>();
        assertEquals(exp, messages);
    }
}