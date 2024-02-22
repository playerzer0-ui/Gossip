package daos;

import business.Reports;
import business.Users;

import java.time.LocalDateTime;
import java.util.List;

public interface  ReportsDaoInterface {

    /**
     * addReport method able to add a new report.
     * reportId will increase automatic.
     *
     * @param reporterId is user's id who do the report
     * @param userReportedId is user's id who be reported
     * @param reportReason is the reason of why report
     * @param reportDate is when report date time is made
     * @param reportStatus is status of report - 1 is unsolved(unseen) 2 is solved, 3 is ignored,4 in inreview

     * @return int of report id if report added else added fail will return -1
     */
    public int addReport( int reporterId, int userReportedId, String reportReason, LocalDateTime reportDate, int reportStatus);


    /**
     * deleteReport method able to delete report by report's id .
     *
     * @param reportId is the report's id to delete
     *
     * @return an int after report deleted else return 0 when no rows are affected by the deleted.
     */
    public int deleteReport (int reportId);

    /**
     * getReportById method able to get report by reportId.
     *
     * @param id is the report's id that want to get.
     *
     * @return that report of id's detail
     */
    public Reports getReportById (int id);

    /**
     * getAllReports method able to list out all reports.
     * @return a list of reports
     */
    public List<Reports> getAllReports();
}
