package daos;

import business.Reports;
import business.Stories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReportsDao extends Dao implements ReportsDaoInterface {

    public ReportsDao(String databaseName) {
        super(databaseName);
    }

    public ReportsDao(Connection con) {
        super(con);
    }

    /**
     * addReport method able to add a new report.
     * reportId will increase automatic.
     *
     * @param reportId is report's id
     * @param reporterId is user's id who do the report
     * @param userReportedId is user's id who be reported
     * @param reportReason is the reason of why report
     * @param reportDate is when report date time is made
     * @param reportStatus is status of report - 1 is unsolved(unseen) 2 is solved, 3 is ignored,4 in inreview

     * @return int of report id if report added else added fail will return -1
     */
    @Override
    public int addReport(int reportId, int reporterId, int userReportedId, String reportReason, LocalDateTime reportDate, int reportStatus) {
        int rowsAffected = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO reports (reportId, reporterId, userReportedId, reportReason, reportDate, reportStatus) VALUES (?, ?, ?, ?, ?，?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, reportId);
            ps.setInt(2, reporterId);
            ps.setInt(3,userReportedId);
            ps.setString(4,reportReason);
            ps.setTimestamp(5, Timestamp.valueOf(reportDate));
            ps.setInt(6, reportStatus);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the addReport() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the addReport() final method:");
        }
        return rowsAffected;
    }

    /**
     * deleteReport method able to delete report by report's id .
     *
     * @param reportId is the report's id to delete
     *
     * @return an int after report deleted else return 0 when no rows are affected by the deleted.
     */
    @Override
    public int deleteReport(int reportId) {
        return deleteItem(reportId, "reports", "reportId");
    }

    /**
     * getReportById method able to get report by reportId.
     *
     * @param id is the report's id that want to get.
     *
     * @return that report of id's detail
     */
    @Override
    public Reports getReportById(int id) {
        Reports r = null;
        try{
            con = getConnection();

            String query = "SELECT * FROM reports WHERE reportId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next())
            {
                int reportId = rs.getInt("reportId");
                int reporterId = rs.getInt("reporterId");
                int userReportedId = rs.getInt("userReportedId");
                String reportReason = rs.getString("reportReason");
                LocalDateTime reportDate = rs.getTimestamp("reportDate").toLocalDateTime();
                int reportStatus = rs.getInt("reportStatus");

                r = new Reports(reportId, reporterId, userReportedId, reportReason, reportDate, reportStatus);
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getReportById() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getReportById() final method:");
        }
        return r;
    }
}
