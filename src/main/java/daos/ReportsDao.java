package daos;

import business.Reports;
import business.Stories;
import business.Users;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * getAllReports method able to list out all reports.
     *
     * @return a list of reports
     */
    @Override
    public List<Reports> getAllReports() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reports> reports = new ArrayList<Reports>();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM reports";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int reportId = rs.getInt("reportId");
                int reporterId = rs.getInt("reporterId");
                int userReportedId = rs.getInt("userReportedId");
                String reportReason = rs.getString("reportReason");
                LocalDateTime reportDate = rs.getTimestamp("reportDate").toLocalDateTime();
                int reportStatus = rs.getInt("reportStatus");
                Reports r = new Reports(reportId, reporterId, userReportedId, reportReason, reportDate, reportStatus);
                reports.add(r);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getAllReports() method: " + e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection("");
                }
            }
            catch (SQLException e)
            {
                System.out.println("An error occurred when shutting down the getAllReports() method: " + e.getMessage());
            }
        }
        return reports;
    }

    /**
     * updateReport method let admin able to update report status.
     *
     * @param reportId is the report's id.
     * @param reportStatus is the report's status - 1 is unseen, 2 is solved, 3 is ignored,4 is inreview
     *
     * @return 1 when report is unseen, 2 when report is solved, 3 when report is ignored and 4 when report is inreview
     */
    @Override
    public int updateReport(int reportId, int reportStatus) {
        Connection con = null;
        PreparedStatement ps = null;
        int rpStatus = 1;
        try {
            con = this.getConnection();

            String query = "UPDATE reports SET reportStatus = ? WHERE reportId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, reportStatus);
            ps.setInt(2, reportId);

            rpStatus = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("An error occurred in the updateReport() method: " + e.getMessage());
        }
        finally{
            try{
                if (ps != null){
                    ps.close();
                }
                if (con != null){
                    freeConnection(con);
                }
            }
            catch (SQLException e){
                System.out.println("An error occurred when shutting down the updateReport() method: " + e.getMessage());
            }
        }
        return rpStatus;
    }
}
