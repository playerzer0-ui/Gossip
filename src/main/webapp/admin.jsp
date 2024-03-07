<%@page import="daos.MessageDao" %>
<%@page import="daos.UsersDao" %>

<%
    MessageDao messageDao = new MessageDao("gossip");
    int daily = messageDao.getDailyMessageCount();
    int total = messageDao.getTotalMessageCount();

    UsersDao usersDao = new UsersDao("gossip");
    int dailyOnline = usersDao.getOnlineUsers();
%>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>admin dashboard</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/admin.css">
    <link rel="stylesheet" href="css/mobile-admin.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
</head>
<body>
<!-- navigation -->
<%@include file="adminNav.jsp"%>

<!-- main -->
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

    <!-- cards -->
    <div class="cardBox">
        <div class="card">
            <div>
                <div class="numbers"><%=dailyOnline%></div>
                <div class="cardName">Daily Online</div>
            </div>
            <div class="iconBx">
                <ion-icon name="eye-outline"></ion-icon>
            </div>
        </div>
<%--        <div class="card">--%>
<%--            <div>--%>
<%--                <div class="numbers">10002</div>--%>
<%--                <div class="cardName">Total Online</div>--%>
<%--            </div>--%>
<%--            <div class="iconBx">--%>
<%--                <ion-icon name="eye-outline"></ion-icon>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="card">
            <div>
                <div class="numbers"><%=daily%></div>
                <div class="cardName">Daily Messages</div>
            </div>
            <div class="iconBx">
                <ion-icon name="chatbubbles-outline"></ion-icon>
            </div>
        </div>
        <div class="card">
            <div>
                <div class="numbers"><%=total%></div>
                <div class="cardName">Total Messages</div>
            </div>
            <div class="iconBx">
                <ion-icon name="chatbubbles-outline"></ion-icon>
            </div>
        </div>
    </div>
</main>

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="js/admin.js" async defer></script>
</body>
</html>
