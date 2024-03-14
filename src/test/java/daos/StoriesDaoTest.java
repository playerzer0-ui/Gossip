package daos;

import business.Reports;
import business.Stories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoriesDaoTest {

    /**
     * Test of addStory_Success() method, of class StoriesDao.
     */
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

    /**
     * Test of deleteStory_byStoryId() method, of class StoriesDao.
     */
    @Test
    void deleteStory_byStoryId() {
        StoriesDao storiesDao = new StoriesDao("gossiptest");
        System.out.println("deleteStory_byStoryId");

        LocalDateTime dateTime = LocalDateTime.of( 2023, 11,20,15,32,45);
        storiesDao.addStory(2, "view.png", 1, dateTime, "Enjoy");

        Stories s = new Stories(2,2, "view.png", 1, dateTime, "Enjoy");
        int id = s.getStoryId();
        int expResult = 1;

        int result = storiesDao.deleteStory(id);
        storiesDao.updateIncrement("reports", 2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getStoryById() method, of class StoriesDao.
     */
    @Test
    void getStoryById() {
        StoriesDao storiesDao = new StoriesDao("gossiptest");
        System.out.println("getStoryById");
        int storyId = 1;
        LocalDateTime reporteDate = LocalDateTime.of(2024,2,2,0,0,0);

        Stories expResult = new Stories(storyId,2, "story.png", 1, reporteDate, "Good morining viewers");
        Stories result = storiesDao.getStoryById(storyId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getStoryById_notFoundId() method, of class StoriesDao.
     */
    @Test
    void getStoryById_notFoundId() {
        StoriesDao storiesDao = new StoriesDao("gossiptest");
        System.out.println("getStoryById_notFoundId");
        int storyId = 100;

        Stories expResult = null;
        Stories result = storiesDao.getStoryById(storyId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllStories() method, of class StoriesDao.
     */
    @Test
    void getAllStories() {
        StoriesDao storiesDao = new StoriesDao("gossiptest");
        System.out.println("getAllStories");

        ArrayList<Stories> result = (ArrayList<Stories>) storiesDao.getAllStories();
        assertEquals(1, result.size());
    }
}