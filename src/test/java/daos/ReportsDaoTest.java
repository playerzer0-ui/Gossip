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
        assertTrue((result > 0));

        if (result != -1) {
            System.out.println("Method returned appropriately, confirming database changed by trying to remove what was added");
            int rowsDeleted = reportsDao.deleteReport(2);
            assertEquals(rowsDeleted, 1);
        }
    }

    @Test
    void deleteReport() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("deleteReport");
        reportsDao.addReport(2, 4, "I don't know him", LocalDateTime.now(), 1);

        Reports r = new Reports(3,2, 4, "I don't know him", LocalDateTime.now(), 1);
        int id = r.getReportId();
        int expResult = 1;

        int result = reportsDao.deleteReport(id);
        assertEquals(expResult, result);

        if (result == 1) {
            System.out.println("Method returned appropriately, confirming database "
                    + "changed by trying to select what was deleted");

            reportsDao.addReport(2, 4, "I don't know him", LocalDateTime.now(), 1);
        }
    }

    @Test
    void getReportById() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("getReportById");
        int reportId = 4;
        LocalDateTime reporteDate = LocalDateTime.of(2024,03,07,02,51,42);

        Reports expResult = new Reports(reportId,2, 4, "I don't know him", reporteDate, 1);
        Reports result = reportsDao.getReportById(reportId);
        assertEquals(expResult, result);
    }

    @Test
    void getAllReports() {
        ReportsDao reportsDao = new ReportsDao("gossiptest");
        System.out.println("getAllReports");

        ArrayList<Reports> result = (ArrayList<Reports>) reportsDao.getAllReports();
        assertEquals(2, result.size());
    }

    @Test
    void updateReport() {
    }
}