package daos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class StoriesDaoTest {

    @Test
    void addStory_Success() {
        StoriesDao storiesDao = new StoriesDao("gossiptest");
        System.out.println("addStory_Success");
        int userId = 1;
        String story = "food.png";
        int storyType =2;
        LocalDateTime dateTime = LocalDateTime.of( 2023, 11,20,15,32,45);
        String storyDes ="Happy";

        int result = storiesDao.addStory(userId,story,storyType,dateTime,storyDes);
        storiesDao.deleteStory(2);
        storiesDao.updateIncrement("stories", 2);
        assertTrue((result > 0));
    }

    @Test
    void deleteStory() {
    }

    @Test
    void getStoryById() {
    }
}