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

    @Test
    void deleteReport() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("deleteReport");
        reportsDao.addReport(2, 4, "I don't know him", LocalDateTime.now(), 1);

        Reports r = new Reports(2,2, 4, "I don't know him", LocalDateTime.now(), 1);
        int id = r.getReportId();
        int expResult = 1;

        int result = reportsDao.deleteReport(id);
        reportsDao.updateIncrement("reports", 2);
        assertEquals(expResult, result);
    }

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

    @Test
    void getAllReports() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("getAllReports");

        ArrayList<Reports> result = (ArrayList<Reports>) reportsDao.getAllReports();
        assertEquals(1, result.size());
    }

    @Test
    void updateReport() {
    }
}