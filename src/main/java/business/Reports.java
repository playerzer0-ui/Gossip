package business;

import java.time.LocalDate;
import java.util.Objects;

public class Reports {
//  `reportId` int(11) NOT NULL AUTO_INCREMENT,
//  `reporterId` int(11) NOT NULL,
//  `userReportedId` int(11) NOT NULL,
//  `reportReason` varchar(80) NOT NULL,
//  `reportDate` datetime NOT NULL,
//  `reportStatus` int(11) NOT NULL DEFAULT 1 COMMENT '1 for unsolved(unseen) 2 for solved, 3 for ignored,4 for inreview',
//    PRIMARY KEY (`reportId`),
//    KEY `reporterId` (`reporterId`),
//    KEY `userReportedId` (`userReportedId`)

    private int reportId;
    private int reporterId;
    private int userReportedId;
    private String reportReason;
    private LocalDate reportDate;
    private int reportStatus;

    public Reports() {
    }

    public Reports(int reportId, int reporterId, int userReportedId, String reportReason, LocalDate reportDate, int reportStatus) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.userReportedId = userReportedId;
        this.reportReason = reportReason;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getUserReportedId() {
        return userReportedId;
    }

    public void setUserReportedId(int userReportedId) {
        this.userReportedId = userReportedId;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public int getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(int reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reports reports = (Reports) o;
        return reportId == reports.reportId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }

    @Override
    public String toString() {
        return "Reports{" +
                "reportId=" + reportId +
                ", reporterId=" + reporterId +
                ", userReportedId=" + userReportedId +
                ", reportReason='" + reportReason + '\'' +
                ", reportDate=" + reportDate +
                ", reportStatus=" + reportStatus +
                '}';
    }
}
