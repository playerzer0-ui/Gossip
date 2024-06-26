<%--commend out because not fully working yet--%>
<%@page import="daos.ReportsDao" %>
<%@ page import="business.Reports" %>
<%@ page import="java.util.List" %>
<%@ page import="daos.UsersDao" %>
<%@ page import="business.Users" %>

<%
    ReportsDao reportsDao = new ReportsDao("gossip");
    UsersDao usersDao= new UsersDao("gossip");
    List<Reports> r = reportsDao.getAllReports();
%>

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
    <link rel="icon" href="img/favicon.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <link rel="manifest" href="manifest.json">
    <link rel="apple-touch-icon" href="img/icons/apple-touch-icon.png">
    <meta name="apple-mobile-web-app-status-bar" content="#FFE1C4">
    <meta name="theme-color" content="#FFE1C4">
</head>
<body>
<!--nav-->
<%@include file="adminNav.jsp" %>

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
        <%for (Reports reports : r) {
           Users user = usersDao.getUserById(reports.getUserReportedId());
        %>
        <%if(reports.getReportStatus() == 3) {%>
            <div class="report bg-grey">
                <div class="report-header">
                    <div>
                        <div class="reportID"><ion-icon name="document"></ion-icon>reportID: <%=reports.getReportId()%></div>
                        <div class="name"><ion-icon name="person"></ion-icon>reporterID: <%=reports.getReporterId()%></div>
                        <div class="report-date"><ion-icon name="time"></ion-icon>report date: <%=reports.getReportDate()%></div>
                    </div>
                    <div class="choices">
                        <a href="controller?action=ignoreReport&status=3&reportId=<%=reports.getReportId()%>"><ion-icon name="close-outline"></ion-icon></a>

                        <%if(user.getSuspended()==0){%>
                        <a href="controller?action=suspendUser&userId=<%=reports.getUserReportedId()%>&suspendState=1"><ion-icon name="person-remove"></ion-icon></a>
                        <%} else {%>
                        <a href="controller?action=suspendUser&userId=<%=reports.getUserReportedId()%>&suspendState=0"><ion-icon name="person-add"></ion-icon></a>
                        <% }%>
                    </div>
                </div>
                <div class="description">
                    Report reason: <%=reports.getReportReason()%>
                </div>
            </div>
        <% }
        //when user has been suspended but when the report hasn't been solved
        else if(reports.getReportStatus()==1) {%>
        <div class="report">
            <div class="report-header">
                <div>
                    <div class="reportID"><ion-icon name="document"></ion-icon>reportID: <%=reports.getReportId()%></div>
                    <div class="name"><ion-icon name="person"></ion-icon>reporterID: <%=reports.getReporterId()%></div>
                    <div class="report-date"><ion-icon name="time"></ion-icon>report date: <%=reports.getReportDate()%></div>
                </div>
                <div class="choices">
                    <ion-icon name="close-outline"><a href="controller?action=ignoreReport&status=3&reportId=<%=reports.getReportId()%>"></a></ion-icon>

                    <%if(user.getSuspended()==0){%>
                    <a href="controller?action=suspendUser&userId=<%=reports.getUserReportedId()%>&suspendState=1"><ion-icon name="person-remove"></ion-icon></a>
                    <%} else {%>
                    <a href="controller?action=suspendUser&userId=<%=reports.getUserReportedId()%>&suspendState=0"><ion-icon name="person-add"></ion-icon></a>
                    <% }%>
                </div>
            </div>
            <div class="description">
                Report reason: <%=reports.getReportReason()%>
            </div>
        </div>
        <% }%>
        <% }%>
    </div>
</main>

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="js/admin.js" async defer></script>
<script src="js/app.js"></script>
</body>
</html>
