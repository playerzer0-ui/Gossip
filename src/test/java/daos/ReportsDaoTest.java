package daos;

import business.Blockedusers;
import business.InboxParticipants;
import business.Reports;
import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportsDaoTest {

    /**
     * Test of addReport() method, of class ReportsDao.
     */
    @Test
    void addReport() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("addReport");

        int reporterId = 1;
        int userReportedId =4;
        String reportReason = "I don't know him";
        LocalDateTime reportDate = LocalDateTime.now();
        int reportStatus = 1;

        int result = reportsDao.addReport(reporterId, userReportedId, reportReason, reportDate, reportStatus);
        int rowsDeleted = reportsDao.deleteReport(2);
        reportsDao.updateIncrement("reports", 2);
        assertTrue((result > 0));

        assertEquals(rowsDeleted, 1);
    }

    /**
     * Test of deleteReport_byReportId() method, of class ReportsDao.
     */
    @Test
    void deleteReport_byReportId() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("deleteReport_byReportId");
        reportsDao.addReport(2, 4, "I don't know him", LocalDateTime.now(), 1);

        Reports r = new Reports(2,2, 4, "I don't know him", LocalDateTime.now(), 1);
        int id = r.getReportId();
        int expResult = 1;

        int result = reportsDao.deleteReport(id);
        reportsDao.updateIncrement("reports", 2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getReportById() method, of class ReportsDao.
     */
    @Test
    void getReportById() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("getReportById");
        int reportId = 1;
        LocalDateTime reporteDate = LocalDateTime.of(2024,2,2,0,0,0);

        Reports expResult = new Reports(reportId,4, 1, "I don't know him", reporteDate, 1);
        Reports result = reportsDao.getReportById(reportId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getReportById_notFoundId() method, of class ReportsDao.
     */
    @Test
    void getReportById_notFoundId() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("getReportById_notFoundId");
        int reportId = 100;

        Reports expResult = null;
        Reports result = reportsDao.getReportById(reportId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllReports() method, of class ReportsDao.
     */
    @Test
    void getAllReports() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("getAllReports");

        ArrayList<Reports> result = (ArrayList<Reports>) reportsDao.getAllReports();
        assertEquals(1, result.size());
    }

    /**
     * Test of updateReport() method, of class ReportsDao.
     */
    @Test
    void updateReport() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("updateReport");

        int reportId = 1;
        int reporterId = 4;
        int userReportedId = 1;
        String reportReason = "he keeps on spamming me";
        LocalDateTime reportDate = LocalDateTime.of(2024,02,02,00,00,00);
        int reportStatus = 1;

        Reports r = new Reports(reportId,reporterId,userReportedId,reportReason,reportDate,reportStatus);

        int expResult = 1;
        int result = reportsDao.updateReport(1,3);

        assertEquals(expResult, result);

        if (expResult == result) {

            Reports expectedReport = new Reports(r.getReportId(), r.getReporterId(),r.getUserReportedId() ,r.getReportReason()
                    ,r.getReportDate(),r.getReportStatus());
            Reports resultReport = reportsDao.getReportById(r.getReportId());
            assertEquals(resultReport, expectedReport);

            reportsDao.updateReport(1,3);
        }
    }

    /**
     * Test of updateReport_notFoundReportId() method, of class ReportsDao.
     */
    @Test
    void updateReport_notFoundReportId() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("updateReport_notFoundReportId");

        int reportId = 100;
        int reporterId = 4;
        int userReportedId = 1;
        String reportReason = "he keeps on spamming me";
        LocalDateTime reportDate = LocalDateTime.of(2024,02,02,00,00,00);
        int reportStatus = 1;

        Reports r = new Reports(reportId,reporterId,userReportedId,reportReason,reportDate,reportStatus);

        int expResult = 0;
        int result = reportsDao.updateReport(100,3);

        assertEquals(expResult, result);
    }
}