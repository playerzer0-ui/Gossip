package daos;

import business.Message;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageDaoTest {

    private MessageDao messageDao = new MessageDao("gossiptest");

    /**
     * getMessage, normal scenario
     */
    @Test
    void getMessage_normal() {
        Message exp = new Message(1, 1, 1, "hello", 1, LocalDateTime.of(2024, 01, 31, 21, 57, 14), 0);
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
     *
     */
    @Test
    void sendMessage() {

    }

    @Test
    void getMessages() {
    }
}