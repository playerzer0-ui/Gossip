<%@ page import="business.Users" %>
<%@ page import="daos.InboxParticipantsDao" %>
<%@ page import="business.InboxParticipants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="daos.InboxDao" %>
<%@ page import="daos.MessageDao" %>
<%@ page import="business.Inbox" %>
<%@ page import="business.Message" %>
<%@ page import="daos.UsersDao" %>

<%Users user = (Users) session.getAttribute("user");%>

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
                <img src="img/<%=user.getProfilePicture()%>" alt="profile" class="cover">
            </div>
            <ul class="nav-icons">
                <li>
                    <ion-icon name="scan-circle-outline"></ion-icon>
                </li>
                <li>
                    <ion-icon name="chatbox-ellipses"></ion-icon>
                </li>
                <li>
                    <ion-icon name="ellipsis-vertical" class="header-menu" onclick="seeHeaderMenu()"></ion-icon>
                </li>
            </ul>
            <div class="drop-menu">
                <ul>
                    <a href="controller?action=show_update">
                        <li>update profile</li>
                    </a>
                    <a href="controller?action=logout">
                        <li>logout</li>
                    </a>
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
        <div class="chatlist" id="chatlist">
            <%
                session.setAttribute("openedInbox", -1);
                InboxParticipantsDao ibpDao = new InboxParticipantsDao("gossip");
                InboxDao inboxDao = new InboxDao("gossip");
                MessageDao messageDao = new MessageDao("gossip");
                UsersDao usersDao = new UsersDao("gossip");
                //gets all the inboxParticipants for that particular user
                ArrayList<InboxParticipants> Ibps = ibpDao.getAllInbox(user.getUserId());
                //loop through inboxparticipants
                for (InboxParticipants ibps : Ibps) {
                    Inbox myInbox = inboxDao.getInbox(ibps.getInboxId());
                    //if it's a normal chat
                    if (myInbox.getInboxType() == 1) {
                        //get the other person's inboxPartcipant
                        InboxParticipants otherIbp = ibpDao.getOtherInboxParticipant(myInbox.getInboxId(), user.getUserId());
                        //get the other User
                        Users otherUser = usersDao.getUserById(otherIbp.getUserId());
                        //get all the messages
                        ArrayList<Message> messages = messageDao.getMessages(myInbox.getInboxId());
                        Message m = null;
                        //if there are messages
                        if (messages.size() > 0) {
                            //get the last message
                            m = messages.get(messages.size() - 1);
                            //if there are unseenMessages
                            if (ibps.getUnseenMessages() > 0) {
            %>
            <div class="block unread" onclick="getMessages(<%=ibps.getInboxId()%>)">
                <div class="imgbox">
                    <img src="img/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=otherUser.getUserName()%>
                        </h4>
                        <p class="time"><%=m.getTimeSent().getHour()%>:<%=m.getTimeSent().getMinute()%>
                        </p>
                    </div>
                    <div class="message-p">
                        <p><%=m.getMessage()%>
                        </p>
                        <b><%=ibps.getUnseenMessages()%>
                        </b>
                    </div>
                </div>
            </div>
            <%
            } //if the inboxParticipant is active or open
            else if ((Integer) session.getAttribute("openedInbox") == ibps.getInboxId()) {
            %>
            <div class="block active" onclick="getMessages(<%=ibps.getInboxId()%>)">
                <div class="imgbox">
                    <img src="img/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=otherUser.getUserName()%>
                        </h4>
                        <p class="time"><%=m.getTimeSent().getHour()%>:<%=m.getTimeSent().getMinute()%>
                        </p>
                    </div>
                    <div class="message-p">
                        <p><%=m.getMessage() %>
                        </p>
                        <b></b>
                    </div>
                </div>
            </div>
            <%
            } //else just display the inboxParticipant
            else {

            %>
            <div class="block" onclick="getMessages(<%=ibps.getInboxId()%>)">
                <div class="imgbox">
                    <img src="img/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=otherUser.getUserName()%>
                        </h4>
                        <p class="time"><%=m.getTimeSent().getHour()%>:<%=m.getTimeSent().getMinute()%>
                        </p>
                    </div>
                    <div class="message-p">
                        <p><%=m.getMessage()%>
                        </p>

                    </div>
                </div>
            </div>
            <%
                    }
                }
            } //if it's a groupChat
            else if (myInbox.getInboxType() == 2) {
                //get the group inbox
                Inbox groupInbox = inboxDao.getInbox(ibps.getInboxId());
                ArrayList<Message> messages = messageDao.getMessages(myInbox.getInboxId());
                Message m = null;
                //if there are messages
                if (messages.size() > 0) {
                    //get the last message
                    m = messages.get(messages.size() - 1);
                    //if there are unseen messages
                    if (ibps.getUnseenMessages() > 0) {
            %>
            <div class="block unread" onclick="getMessages(<%=ibps.getInboxId()%>)">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=groupInbox.getGroupName()%>
                        </h4>
                        <p class="time"><%=m.getTimeSent().getHour()%>:<%=m.getTimeSent().getMinute()%>
                        </p>
                    </div>
                    <div class="message-p">
                        <p><%=m.getMessage()%>
                        </p>
                        <b><%=ibps.getUnseenMessages()%>
                        </b>
                    </div>
                </div>
            </div>
            <%
            }//if the inboxParticipant is active or open
            else if ((Integer) session.getAttribute("openedInbox") == ibps.getInboxId()) {
            %>
            <div class="block active" onclick="getMessages(<%=ibps.getInboxId()%>)">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=groupInbox.getGroupName()%>
                        </h4>
                        <p class="time"><%=m.getTimeSent().getHour()%>:<%=m.getTimeSent().getMinute()%>
                        </p>
                    </div>
                    <div class="message-p">
                        <p><%=m.getMessage() %>
                        </p>
                    </div>
                </div>
            </div>
            <%
            } //else just display the inboxParticipant
            else {

            %>
            <div class="block" onclick="getMessages(<%=ibps.getInboxId()%>)">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=groupInbox.getGroupName()%>
                        </h4>
                        <p class="time"><%=m.getTimeSent().getHour()%>:<%=m.getTimeSent().getMinute()%>
                        </p>
                    </div>
                    <div class="message-p">
                        <p><%=m.getMessage()%>
                        </p>
                    </div>
                </div>
            </div>
            <%
                            }

                        }
                    }
                }

            %>


        </div>

    </div>

    <div class="leftSide">
        <div class="profile-header">
            <ion-icon name="arrow-back-outline" onclick="seeChatList()"></ion-icon>
            <h4>profile</h4>
        </div>
        <div class="profile-details">
            <div class="profile-img">
                <img src="img/<%=user.getProfilePicture()%>" class="cover" alt="">
            </div>
            <h2><%=user.getUserName()%>
            </h2>
            <p><%=user.getEmail()%>
            </p>
            <p><%=user.getBio()%>
            </p>
        </div>
    </div>


    <div class="right">
        <div class="header">
            <div class="imgtext" id="imgtext">
            </div>
            <ul class="nav-icons">
                <li>
                    <ion-icon name="search-outline"></ion-icon>
                </li>
                <li>
                    <ion-icon name="ellipsis-vertical"></ion-icon>
                </li>
            </ul>
        </div>

        <!-- chatbox -->
        <div class="chatbox" id="chatbox">


        </div>
        <!-- chat input -->
        <div class="chatbox-input">
            <ion-icon name="happy-outline"></ion-icon>
            <ion-icon name="attach-outline"></ion-icon>
            <input type="text" placeholder="type a message" id="messageEntered">
            <ion-icon name="send" onclick="sendMessage()"></ion-icon>
        </div>
    </div>

</div>
<script>
    var mainInboxId = 0;
    var otherUserId = 0;
    setInterval(refreshMessages, 2000);
    setInterval(getChatList, 2000);

    function refreshMessages() {
        if (mainInboxId !== 0) {
            // alert("in");
            getMessages(mainInboxId);
        }
    }

    function getMessagesHeader(inboxId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getMessagesHeader", "inboxId": inboxId},
                success: function (data) {
                    var header = document.getElementById("imgtext");
                    header.innerHTML = data;

                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function getMessages(inboxId) {
        mainInboxId = inboxId;
        otherUserId = 0;
        getMessagesHeader(inboxId);
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getMessages", "inboxId": inboxId},
                success: function (data) {
                    var allMessages=JSON.parse(data);
                    var chatBox = document.getElementById("chatbox");
                   chatBox.innerHTML = "";
                   for(var i=0; i<allMessages.length; i++){
                       if(parseInt(allMessages[i][2])===<%=user.getUserId()%>){
                         if(parseInt(allMessages[i][4])===1){
                             chatBox.innerHTML+="<div class='message my-message'><p>" + allMessages[i][3]+ "<br><span>" + allMessages[i][5] + "</span></p></div>";
                         }
                       }
                       else{
                           if(parseInt(allMessages[i][4])===1) {
                               chatBox.innerHTML += "<div class='message frnd-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                           }
                       }
                   }
                    ///chatBox.innerHTML = data;
                    /* var messages = "";
                     var allMessages =
                     if (allMessages!=null){
                         for (var i = 0; i < allMessages.length; i++) {
                             messages = messages + " " + allMessages[i];
                             alert("in");
                         }
                 }
                     chatBox.innerHTML =messages;*/

                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function getChatList() {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getChatlist"},
                success: function (data) {
                    var chatList = document.getElementById("chatlist");
                    chatList.innerHTML = data;
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    //I need to add another function to set otherUserId


    function sendMessage() {
        var msg = document.getElementById("messageEntered").value;
        if (msg !== null) {

            //if it's the first time sending the user a message
            if (mainInboxId === 0 && otherUserId !== 0) {
                $(document).ready(function () {
                    $.ajax({
                        url: "controller",
                        type: 'post',
                        data: {action: "firstMessage", "userId": otherUserId, "message": msg},
                        success: function (data) {

                        },
                        error: function () {
                            alert("Error with ajax");
                        }
                    });
                });
            } else if (mainInboxId !== 0) {
                $(document).ready(function () {
                    $.ajax({
                        url: "controller",
                        type: 'post',
                        data: {action: "sendMessage", "inboxId": mainInboxId, "message": msg},
                        success: function (data) {

                        },
                        error: function () {
                            alert("Error with ajax");
                        }
                    });
                });
            }
        } else {
            alert("please enter a message");
        }
        document.getElementById("messageEntered").value = "";
    }

    document.addEventListener('keydown', function(event){
        if(event.key === "Enter"){
            sendMessage();
        }
    });

</script>

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="js/chatbox.js"></script>
<script src="js/index.js"></script>
</body>

</html>
