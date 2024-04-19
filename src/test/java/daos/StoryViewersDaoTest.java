package daos;

import business.StoryViewers;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryViewersDaoTest {

    private final StoryViewersDao storyViewersDao = new StoryViewersDao("gossiptest");

    private final StoryViewers sv1 = new StoryViewers(1, 1, LocalDateTime.of(2024,2,2, 8,30,0));
    private final StoryViewers sv2 = new StoryViewers(1, 3, LocalDateTime.of(2024,2,2, 10,30,0));
    /**
     * getViewersByStoryID, normal
     */
    @Test
    void getViewersByStoryID_normal() {
        List<StoryViewers> exp = new ArrayList<>();
        List<StoryViewers> sv = storyViewersDao.getViewersByStoryID(1);

        exp.add(sv1);
        exp.add(sv2);

        assertEquals(exp, sv);
    }

    /**
     * getViewersByStoryID, no story ID
     */
    @Test
    void getViewersByStoryID_noID(){
        List<StoryViewers> exp = new ArrayList<>();
        List<StoryViewers> sv = storyViewersDao.getViewersByStoryID(100);

        assertEquals(exp, sv);
    }

    /**
     * getViewersByViewerID, normal scenario
     */
    @Test
    void getViewersByViewerID_normal() {
        List<StoryViewers> exp = new ArrayList<>();
        List<StoryViewers> sv = storyViewersDao.getViewersByViewerID(1);

        exp.add(sv1);

        assertEquals(exp, sv);
    }

    /**
     * getViewersByViewerID, no viewer ID
     */
    @Test
    void getViewersByViewerID_noID() {
        List<StoryViewers> exp = new ArrayList<>();
        List<StoryViewers> sv = storyViewersDao.getViewersByViewerID(100);

        assertEquals(exp, sv);
    }

    /**
     * insertStoryViewer, normal scenario
     */
    @Test
    void insertStoryViewer_normal() {
        int act = storyViewersDao.insertStoryViewer(1, 2, LocalDateTime.now());
        storyViewersDao.deleteViewerFromStoryViewers(2);

        assertEquals(1, act);
    }

    /**
     * insertStoryViewer, incorrect storyID
     */
    @Test
    void insertStoryViewer_noStoryID_found() {
        int act = storyViewersDao.insertStoryViewer(10, 2, LocalDateTime.now());

        assertEquals(0, act);
    }

    /**
     * insertStoryViewer, incorrect viewerID
     */
    @Test
    void insertStoryViewer_noViewerID_found() {
        int act = storyViewersDao.insertStoryViewer(1, 20, LocalDateTime.now());

        assertEquals(0, act);
    }

    /**
     * deleteStoryFromStoryViewers, normal scenario
     */
    @Test
    void deleteStoryFromStoryViewers_normal() {
        int act = storyViewersDao.deleteStoryFromStoryViewers(1);
        storyViewersDao.insertStoryViewer(sv1.getStoryId(), sv1.getViewId(), sv1.getViewTime());
        storyViewersDao.insertStoryViewer(sv2.getStoryId(), sv2.getViewId(), sv2.getViewTime());

        assertEquals(2, act);
    }

    /**
     * deleteStoryFromStoryViewers, no story found
     */
    @Test
    void deleteStoryFromStoryViewers_noID() {
        int act = storyViewersDao.deleteStoryFromStoryViewers(10);

        assertEquals(0, act);
    }

    /**
     * deleteViewerFromStoryViewers, normal scenario
     */
    @Test
    void deleteViewerFromStoryViewers_normal() {
        int act = storyViewersDao.deleteViewerFromStoryViewers(1);
        storyViewersDao.insertStoryViewer(sv1.getStoryId(), sv1.getViewId(), sv1.getViewTime());

        assertEquals(1, act);
    }

    /**
     * deleteViewerFromStoryViewers, normal scenario
     */
    @Test
    void deleteViewerFromStoryViewers_noID(){
        int act = storyViewersDao.deleteViewerFromStoryViewers(10);

        assertEquals(0, act);
    }
}