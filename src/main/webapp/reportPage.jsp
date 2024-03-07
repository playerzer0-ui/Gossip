<%--commend out because not fully working yet--%>
<%--<%@page import="daos.ReportsDao" %>--%>
<%--<%@ page import="business.Reports" %>--%>
<%--<%@ page import="java.util.List" %>--%>

<%--<%Reports reports = (Reports) session.getAttribute("report");%>--%>

<%--<%--%>
<%--    ReportsDao reportsDao = new ReportsDao("gossip");--%>
<%--    List<Reports> r = reportsDao.getAllReports();--%>
<%--%>--%>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>report</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/admin.css">
    <link rel="stylesheet" href="css/mobile-admin.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
</head>
<body>
<!--nav-->
<%@include file="adminNav.jsp"%>

<!--main-->
<main>
    <div class="topbar">
        <div class="toggle">
            <ion-icon name="menu"></ion-icon>
        </div>
        <!-- search -->
        <div class="search">
            <label>
                <input type="text" placeholder="search here">
                <ion-icon name="search-outline"></ion-icon>
            </label>
        </div>
        <!-- userimg -->
        <div class="user">
            <img src="img/profile.jpg" alt="">
        </div>
    </div>

    <div class="report-list">
<%--        <div class="report">--%>
<%--            <div class="report-header">--%>
<%--                <div>--%>
<%--                    <div class="reportID"><ion-icon name="document"></ion-icon>reportID: <%=reports.getReportId()%></div>--%>
<%--                    <div class="name"><ion-icon name="person"></ion-icon>reporterID: <%=reports.getReporterId()%></div>--%>
<%--                    <div class="report-date"><ion-icon name="time"></ion-icon>report date: <%=reports.getReportDate()%></div>--%>
<%--                </div>--%>
<%--                <div class="choices">--%>
<%--                    <ion-icon name="close-outline"></ion-icon>--%>
<%--                    <ion-icon name="person-remove"></ion-icon>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="description">--%>
<%--                Report reason: <%=reports.getReportReason()%>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="report">
            <div class="report-header">
                <div>
                    <div class="reportID"><ion-icon name="document"></ion-icon>reportID: 212121</div>
                    <div class="name"><ion-icon name="person"></ion-icon>saaamm</div>
                    <div class="report-date"><ion-icon name="time"></ion-icon>12-12-2000 07:00:00</div>
                </div>
                <div class="choices">
                    <ion-icon name="close-outline"></ion-icon>
                    <ion-icon name="person-remove"></ion-icon>
                </div>
            </div>
            <div class="description">
                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Tempore ex maiores minus rerum alias perferendis recusandae id beatae error fugit? Minus cupiditate dolor sint quo totam. Necessitatibus accusamus obcaecati possimus.
            </div>
        </div>
        <div class="report">
            <div class="report-header">
                <div>
                    <div class="reportID"><ion-icon name="document"></ion-icon>reportID: 212121</div>
                    <div class="name"><ion-icon name="person"></ion-icon>saaamm</div>
                    <div class="report-date"><ion-icon name="time"></ion-icon>12-12-2000 07:00:00</div>
                </div>
                <div class="choices">
                    <ion-icon name="close-outline"></ion-icon>
                    <ion-icon name="person-remove"></ion-icon>
                </div>
            </div>
            <div class="description">
                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Tempore ex maiores minus rerum alias perferendis recusandae id beatae error fugit? Minus cupiditate dolor sint quo totam. Necessitatibus accusamus obcaecati possimus.
            </div>
        </div>
        <div class="report">
            <div class="report-header">
                <div>
                    <div class="reportID"><ion-icon name="document"></ion-icon>reportID: 212121</div>
                    <div class="name"><ion-icon name="person"></ion-icon>saaamm</div>
                    <div class="report-date"><ion-icon name="time"></ion-icon>12-12-2000 07:00:00</div>
                </div>
                <div class="choices">
                    <ion-icon name="close-outline"></ion-icon>
                    <ion-icon name="person-remove"></ion-icon>
                </div>
            </div>
            <div class="description">
                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Tempore ex maiores minus rerum alias perferendis recusandae id beatae error fugit? Minus cupiditate dolor sint quo totam. Necessitatibus accusamus obcaecati possimus.
            </div>
        </div>
        <div class="report">
            <div class="report-header">
                <div>
                    <div class="reportID"><ion-icon name="document"></ion-icon>reportID: 212121</div>
                    <div class="name"><ion-icon name="person"></ion-icon>saaamm</div>
                    <div class="report-date"><ion-icon name="time"></ion-icon>12-12-2000 07:00:00</div>
                </div>
                <div class="choices">
                    <ion-icon name="close-outline"></ion-icon>
                    <ion-icon name="person-remove"></ion-icon>
                </div>
            </div>
            <div class="description">
                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Tempore ex maiores minus rerum alias perferendis recusandae id beatae error fugit? Minus cupiditate dolor sint quo totam. Necessitatibus accusamus obcaecati possimus.
            </div>
        </div>
    </div>
</main>

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="js/admin.js" async defer></script>
</body>
</html>
