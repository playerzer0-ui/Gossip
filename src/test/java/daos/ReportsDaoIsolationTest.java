package daos;

import business.Inbox;
import business.InboxParticipants;
import business.Reports;
import business.Users;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.sql.Date.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ReportsDaoIsolationTest {

    /**
     * Test of addReport() method, of class ReportsDao.
     */
    @Test
    void addReport() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "INSERT INTO reports (reporterId, userReportedId, reportReason, reportDate, reportStatus) VALUES ( ?, ?, ?, ?,?)";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        LocalDateTime reportDate = LocalDateTime.of(2024,1,10,19,8,36);

        ReportsDao reportsDao = new ReportsDao(dbConn);
        int result = reportsDao.addReport(2,4,"I don't know him",reportDate,1);

        verify(ps).setInt(1, 2);
        verify(ps).setInt(2, 4);
        verify(ps).setString(3, "I don't know him");
        verify(ps).setTimestamp(4, Timestamp.valueOf(reportDate));
        verify(ps).setInt(5, 1);

        assertTrue((result > 0));
    }

    /**
     * Test of deleteReport_byReportId() method, of class ReportsDao.
     */
    @Test
    void deleteReport_byReportId() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String tableName = "reports";
        String IDname = "reportId";

        String q = String.format("DELETE FROM %s WHERE %s = ?", tableName, IDname);
        when(dbConn.prepareStatement(q)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        ReportsDao reportsDao = new ReportsDao(dbConn);
        int result = reportsDao.deleteReport(5);

        verify(ps).setInt(1, 5);

        assertEquals(1, result);
    }

    /**
     * Test of getReportById() method, of class ReportsDao.
     */
    @Test
    void getReportById() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        int reportId = 1;
        LocalDateTime reportDate = LocalDateTime.of(2024,1, 2,20,15,33 );
        Reports r = new Reports(reportId,4, 1, "I don't know him", reportDate, 1);

        when(dbConn.prepareStatement("SELECT * FROM reports WHERE reportId = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("reportId")).thenReturn(r.getReportId());
        when(rs.getInt("reporterId")).thenReturn(r.getReporterId());
        when(rs.getString("reportReason")).thenReturn(r.getReportReason());
        when(rs.getTimestamp("reportDate")).thenReturn(Timestamp.valueOf(r.getReportDate()));
        when(rs.getInt("reportStatus")).thenReturn(r.getReportStatus());

        ReportsDao reportsDao = new ReportsDao(dbConn);
        Reports actual = reportsDao.getReportById(1);
        assertEquals(actual, r);
    }

    /**
     * Test of getReportById_notFoundId() method, of class ReportsDao.
     */
    @Test
    void getReportById_notFoundId() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        LocalDateTime reportDate = LocalDateTime.of(2024,10, 28,12,55,13 );
        Reports r = new Reports(100,4, 1, "I don't know him", reportDate, 1);

        when(dbConn.prepareStatement("SELECT * FROM reports WHERE reportId = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        ReportsDao reportsDao = new ReportsDao(dbConn);
        Reports actual = reportsDao.getReportById(r.getReportId());

        assertEquals(null,actual);
    }

    /**
     * Test of getAllReports() method, of class ReportsDao.
     */
    @Test
    void getAllReports() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        LocalDateTime reportDate = LocalDateTime.of(2024,1, 2,20,15,33 );
        Reports r = new Reports(1,4, 1, "I don't know him", reportDate, 1);

        when(dbConn.prepareStatement("SELECT * FROM reports")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("reportId")).thenReturn(r.getReportId());
        when(rs.getInt("reporterId")).thenReturn(r.getReporterId());
        when(rs.getString("reportReason")).thenReturn(r.getReportReason());
        when(rs.getTimestamp("reportDate")).thenReturn(Timestamp.valueOf(r.getReportDate()));
        when(rs.getInt("reportStatus")).thenReturn(r.getReportStatus());

        ReportsDao reportsDao = new ReportsDao(dbConn);
        ArrayList<Reports> actual = (ArrayList<Reports>) reportsDao.getAllReports();
        ArrayList<Reports> expected = new ArrayList();
        expected.add(r);
        assertEquals(expected, actual);

    }

    /**
     * Test of updateReport() method, of class ReportsDao.
     */
    @Test
    void updateReport() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "UPDATE reports SET reportStatus = ? WHERE reportId = ?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        ReportsDao reportsDao = new ReportsDao(dbConn);
        LocalDateTime reportDate = LocalDateTime.of(2024,1,10,19,8,36);
        Reports r = new Reports(1,4, 1, "I don't know him", reportDate, 1);
        int expected= reportsDao.updateReport(1,3);

        verify(ps).setInt(1,r.getReportId());
        verify(ps).setInt(2,r.getReporterId());
        verify(ps).setInt(3,r.getUserReportedId());
        verify(ps).setString(4,r.getReportReason());
        verify(ps).setTimestamp(5, Timestamp.valueOf(reportDate));
        verify(ps).setInt(6,r.getReportStatus());
        assertEquals(expected,1);

    }

    /**
     * Test of updateReport_notFoundReportId() method, of class ReportsDao.
     */
    @Test
    void updateReport_notFoundReportId() throws SQLException {
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        String query = "UPDATE reports SET reportStatus = ? WHERE reportId = ?";
        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        ReportsDao reportsDao = new ReportsDao(dbConn);
        int actual = reportsDao.updateReport(1, 3);
        int expected = 0;
        verify(ps).setInt(1, 1);
        verify(ps).setInt(2, 3);
        assertEquals(expected, actual);

//        LocalDateTime reportDate = LocalDateTime.of(2024,1,10,19,8,36);
//        Reports r = new Reports(1,4, 1, "I don't know him", reportDate, 1);
//        int expected= reportsDao.updateReport(1,3);
//
//        verify(ps).setInt(1,r.getReportId());
//        verify(ps).setInt(2,r.getReporterId());
//        verify(ps).setInt(3,r.getUserReportedId());
//        verify(ps).setString(4,r.getReportReason());
//        verify(ps).setTimestamp(5, Timestamp.valueOf(reportDate));
//        verify(ps).setInt(6,r.getReportStatus());
//        assertFalse((expected>0));
    }
}