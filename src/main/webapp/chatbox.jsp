<%@ page import="business.Users" %>
<%@ page import="daos.InboxParticipantsDao" %>
<%@ page import="business.InboxParticipants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="daos.InboxDao" %>
<%@ page import="daos.MessageDao" %>
<%@ page import="business.Inbox" %>
<%@ page import="business.Message" %>

<% Users u = (Users) session.getAttribute("user"); %>

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Gossip | chatbox</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/chatbox.css">
    <link rel="stylesheet" href="css/mobile-chatbox.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="left">
        <!-- header -->
        <div class="header">
            <div class="userimg" onclick="seeProfile()">
                <img src="img/<%=u.getProfilePicture()%>" alt="profile" class="cover">
            </div>
            <ul class="nav-icons">
                <li><ion-icon name="scan-circle-outline"></ion-icon></li>
                <li><ion-icon name="chatbox-ellipses"></ion-icon></li>
                <li><ion-icon name="ellipsis-vertical" class="header-menu" onclick="seeHeaderMenu()"></ion-icon></li>
            </ul>
            <div class="drop-menu">
                <ul>
                    <a href="controller?action=show_update"><li>update profile</li></a>
                    <a href="controller?action=logout"><li>logout</li></a>
                </ul>
            </div>
        </div>
        <!-- search -->
        <div class="search-chat">
            <div>
                <input type="text" placeholder="search or start new chat">
                <ion-icon name="search-outline"></ion-icon>
            </div>
        </div>
        <!-- chat-list -->
        <div class="chatlist">
            <div class="block active">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4>Player zero</h4>
                        <p class="time">12:00 AM</p>
                    </div>
                    <div class="message-p">
                        <p>wffewefffadsadsadsadasdasdffewefffadsadsadsadasdasdffewefffadsadsadsadasdasdffewefffadsadsadsadasdasd
                        </p>
                    </div>
                </div>
            </div>
            <div class="block unread">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4>Player zero</h4>
                        <p class="time">12:00 AM</p>
                    </div>
                    <div class="message-p">
                        <p>wfewefffwefwe</p>
                        <b>1</b>
                    </div>
                </div>
            </div>
            <div class="block">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4>Player zero</h4>
                        <p class="time">12:00 AM</p>
                    </div>
                    <div class="message-p">
                        <p>wfewefffwefwe</p>
                    </div>
                </div>
            </div>


        </div>

    </div>
    <div class="leftSide">
        <div class="profile-header">
            <ion-icon name="arrow-back-outline" onclick="seeChatList()"></ion-icon>
            <h4>profile</h4>
        </div>
        <div class="profile-details">
            <div class="profile-img">
                <img src="img/<%=u.getProfilePicture()%>" class="cover" alt="">
            </div>
            <h2><%=u.getUserName()%></h2>
            <p><%=u.getEmail()%></p>
            <p><%=u.getBio()%></p>
        </div>
    </div>


    <div class="right">
        <div class="header">
            <div class="imgtext">
                <ion-icon class="return" name="arrow-back-outline"></ion-icon>
                <div class="userimg">
                    <img src="img/profile.jpg" alt="profile" class="cover">
                </div>
                <h4>player-zero<br><span>online</span></h4>
            </div>
            <ul class="nav-icons">
                <li><ion-icon name="search-outline"></ion-icon></li>
                <li><ion-icon name="ellipsis-vertical"></ion-icon></li>
            </ul>
        </div>

        <!-- chatbox -->
        <div class="chatbox">
            <div class="message my-message">
                <p>hi<br><span>12:45 AM</span></p>
            </div>
            <div class="message frnd-message">
                <p>hi<br><span>12:45 AM</span></p>
            </div>

        </div>
        <!-- chat input -->
        <div class="chatbox-input">
            <ion-icon name="happy-outline"></ion-icon>
            <ion-icon name="attach-outline"></ion-icon>
            <input type="text" placeholder="type a message">
            <ion-icon name="send"></ion-icon>
        </div>
    </div>

</div>

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="js/chatbox.js"></script>
</body>

</html>
