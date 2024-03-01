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
        Message exp = new Message(1, 1, 1, "hello", 1, LocalDateTime.of(2024, 1, 31, 21, 57, 14), 0, 0, null);
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
        Message exp = new Message(1, 1, 1, "hello", 1, LocalDateTime.of(2024, 1, 31, 21, 57, 14), 0, 0, null);
        Message exp1 = new Message(2, 1, 2, "hi", 1, LocalDateTime.of(2024,1,31,21,58,28), 0, 0, null);

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

    /**
     * delete messages, normal scenario
     */
    @Test
    void deleteMessages_normal(){
        messageDao.sendMessage(1, 1, "asdasdad", 1);
        int act = messageDao.deleteMessages(6);
        messageDao.updateIncrement("messages", 5);

        assertEquals(1, act);
    }

    /**
     * delete messages, no ID found
     */
    @Test
    void deleteMessages_noID(){
        int act = messageDao.deleteMessages(60);

        assertEquals(0, act);
    }

    /**
     * getDailyMessageCount, there is one new message in the day
     */
    @Test
    void getDailyMessageCount_normal(){
        messageDao.sendMessage(1, 1, "asdasdad", 1);
        int act = messageDao.getDailyMessageCount();
        messageDao.deleteMessages(6);
        messageDao.updateIncrement("messages", 5);

        assertEquals(1, act);
    }

    /**
     * getDailyMessageCount, no messages in today
     */
    @Test
    void getDailyMessageCount_noMessage(){
        int act = messageDao.getDailyMessageCount();

        assertEquals(0, act);
    }

    /**
     * getTotalMessageCount, normal scenario, 5
     */
    @Test
    void getTotalMessageCount_normal(){
        int act = messageDao.getTotalMessageCount();

        assertEquals(5, act);
    }
}