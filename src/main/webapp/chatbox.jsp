<%@ page import="java.util.ArrayList" %>
<%@ page import="miscellaneous.Miscellaneous" %>
<%@ page import="miscellaneous.Aes" %>
<%@ page import="business.*" %>
<%@ page import="daos.*" %>
<%Users user = (Users) session.getAttribute("user");%>
<%
    String msg = (String) session.getAttribute("msg");
    session.removeAttribute("msg");
    if (msg == null) {
        msg = "";
    } else {
        msg = "<p>" + msg + "</p>";
    }
%>

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
    <link rel="icon" href="img/favicon.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
</head>

<body>
<div class="warning-msg">
    <%=msg%>
</div>
<div class="zoom">
    <ion-icon name="close-outline" onclick="closeZoom()"></ion-icon>
    <img src="" alt="">
</div>

<div class="storyview">
    <ion-icon name="close-outline" onclick="closeStoryView()"></ion-icon>
    <div class="bars">
    </div>
    <div class="bars">
    </div>
    <div class="view-container">
        <ion-icon id="left-arrow" name="chevron-back-outline" onclick="switchImageLeft()"></ion-icon>
        <div class="img-or-vid">
            <img src="" class="story-image" id="storyImage" />
            <video class="story-video" controls>
                <source id="storyVideo" src="video/2023-09-30 16-22-05.mp4" type="video/mp4">
                Your browser does not support the video tag.
            </video>
        </div>
        <ion-icon id="right-arrow" name="chevron-forward-outline" onclick="switchImageRight()"></ion-icon>
    </div>

</div>

<div class="report-page">
    <ion-icon name="close-outline" onclick="closeReport()"></ion-icon>
    <div class="form-page">
        <h1>Report User</h1>
        <form action="controller" method="post">
            <div class="mb-3">
                <label class="form-label">enter reason: </label> <br>
                <input type="text" name="reportReason" class="form-control"/>
            </div>
            <input type="hidden" name="action" value="send_report"/>
            <button type="submit" class="report-button">Submit</button>
        </form>
    </div>
</div>
<div class="create-group-page">
    <ion-icon name="close-outline" onclick="closeCreateGroupPage()"></ion-icon>
    <div class="form-page">
        <h1>create group</h1>
        <img id="groupProfilePic" src="profilePictures/profile.jpg" alt="group picture">
        <input accept="image/*" type="file" id="imgInp" required>
        <input type="text" id="groupName" placeholder="enter group name" required>
        <button class="report-button" onclick="createGroupChat()">create group</button>
    </div>
</div>
<div class="group-page">
    <ion-icon name="close-outline" onclick="closeGroupPage()"></ion-icon>
    <div class="form-page">
        <h1>Add member</h1>
        <div class="search-chat">
            <div>
                <input type="text" id="searchGroup" placeholder="search a member by username">
                <ion-icon name="search-outline"></ion-icon>
            </div>
        </div>
        <hr>
        <p><b>invite people</b></p>
        <div class="list-invite" id="list-invite">

        </div>
        <hr>
        <p><b>group members</b></p>
        <div class="list-invite" id="groupMembers">

        </div>
    </div>
</div>
<div class="container">
    <div class="left">
        <!-- header -->
        <div class="header">
            <div class="userimg" onclick="seeProfile();closeInbox()">
                <img src="profilePictures/<%=user.getProfilePicture()%>" alt="profile" class="cover">
            </div>
            <ul class="nav-icons">
                <li>
                    <ion-icon name="scan-circle-outline" onclick="seeStories();getStoriesList()"></ion-icon>
                </li>
                <li>
                    <ion-icon name="chatbox-ellipses" onclick="seeChatList()"></ion-icon>
                </li>
                <li>
                    <ion-icon name="ellipsis-vertical" class="header-menu" onclick="seeHeaderMenu()"></ion-icon>
                </li>
            </ul>
            <div class="drop-menu">
                <ul>
                    <li onclick="openCreateGroupPage()">create group</li>
                    <li onclick="seeProfileUpdate()">update profile</li>
                    <a href="controller?action=logout">
                        <li>logout</li>
                    </a>
                </ul>
            </div>
        </div>
        <!-- search -->
        <div class="search-chat">
            <div>
                <input type="text" placeholder="search or start new chat" id="search">
                <ion-icon name="search-outline"></ion-icon>
            </div>
            <div id="suggestions">
                <%--                <div id="2" class="suggestion">--%>
                <%--                    <img src="img/default.png" alt="user-image">--%>
                <%--                    <p>default user</p>--%>
                <%--                </div>--%>
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
                Aes aes = new Aes();
                Inbox myInbox = null;
                //gets all the inboxParticipants for that particular user
                ArrayList<InboxParticipants> Ibps = ibpDao.getAllInbox(user.getUserId());
                BlockedusersDao blockedusersDao= new BlockedusersDao("gossip");
                //filtering blockedUsers
                ArrayList <Blockedusers> blockedusers=blockedusersDao.getBlockedUsers(user.getUserId());
                ArrayList<InboxParticipants> filteredUsers = new ArrayList();
                for(InboxParticipants ibp: Ibps){
                    InboxParticipants otherIbp=ibpDao.getOtherInboxParticipant(ibp.getInboxId(),user.getUserId());
                    boolean add=true;
                    for(Blockedusers b: blockedusers){
                        if(otherIbp.getUserId()==b.getUserId()){
                            add=false;
                            break;
                        }
                    }
                    if(add==true){
                        filteredUsers.add(ibp);
                    }
                }
                Ibps=filteredUsers;
                //loop through inboxparticipants
                for (InboxParticipants ibps : Ibps) {
                    myInbox = inboxDao.getInbox(ibps.getInboxId());
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
                            try {
                                m.setMessage(aes.decrypt(m.getMessage(), m.getMessageKey()));
                            } catch (Exception ex) {
                                System.out.println("error occurred when getting last message" + ex.getMessage());
                            }
                            //if there are unseenMessages
                            if (ibps.getUnseenMessages() > 0) {
            %>
            <div class="block unread"
                 onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
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
            <div class="block active"
                 onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
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
            <div class="block" onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
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
            //if there are no messages
            else {

                //if there are unseenMessages
                //if the inboxParticipant is active or open
                if ((Integer) session.getAttribute("openedInbox") == ibps.getInboxId()) {
            %>
            <div class="block active"
                 onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=otherUser.getUserName()%>
                        </h4>
                        <p class="time">
                        </p>
                    </div>
                    <div class="message-p">
                        <p>
                        </p>
                        <b></b>
                    </div>
                </div>
            </div>
            <%
            } //else just display the inboxParticipant
            else {

            %>
            <div class="block" onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/<%=otherUser.getProfilePicture()%>" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=otherUser.getUserName()%>
                        </h4>
                        <p class="time">
                        </p>
                    </div>
                    <div class="message-p">
                        <p>
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
                    try {
                        m.setMessage(aes.decrypt(m.getMessage(), m.getMessageKey()));
                    } catch (Exception ex) {
                        System.out.println("error occurred when getting last message" + ex.getMessage());
                    }
                    //if there are unseen messages
                    if (ibps.getUnseenMessages() > 0) {
            %>
            <div class="block unread"
                 onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/profile.jpg" alt="" class="cover">
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
            <div class="block active"
                 onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/profile.jpg" alt="" class="cover">
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
            <div class="block" onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/profile.jpg" alt="" class="cover">
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

            } else {

               //if the inboxParticipant is active or open
             if ((Integer) session.getAttribute("openedInbox") == ibps.getInboxId()) {
            %>
            <div class="block active"
                 onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=groupInbox.getGroupName()%>
                        </h4>
                        <p class="time">
                        </p>
                    </div>
                    <div class="message-p">
                        <p>
                        </p>
                    </div>
                </div>
            </div>
            <%
            } //else just display the inboxParticipant
            else {

            %>
            <div class="block" onclick="getMessages(<%=ibps.getInboxId()%>);seeMessage(<%=myInbox.getInboxType()%>);">
                <div class="imgbox">
                    <img src="profilePictures/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><%=groupInbox.getGroupName()%>
                        </h4>
                        <p class="time">
                        </p>
                    </div>
                    <div class="message-p">
                        <p>
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

        <!-- stories-list -->
        <div class="storiesList">

            <input type="file" id="statusFile" onchange=""  accept="image/png, image/jpeg, image/jpg, video/mp4, video/x-m4v">
            <input type="text" id="statusDescription">
            <button onclick="uploadStatus()">Upload</button>

            <div class="block" onclick="seeYourStories();myStories()">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4><u>My stories</u></h4>
                    </div>
                </div>
            </div>


            <div id="storiesList">
            <div class="block" onclick="openStoryView(['img/pattern.jpg', 'img/phones.png', 'img/picture1.jpg', 'img/picture2.jpg'])">
                <div class="imgbox">
                    <img src="img/profile.jpg" alt="" class="cover">
                </div>
                <div class="details">
                    <div class="listhead">
                        <h4>Player zero</h4>
                        <p class="time">12:00 AM</p>
                    </div>
                    <div class="message-p">
                        <p>insert story description here</p>
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
                        <p>insert story description here</p>
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
                        <p>insert story description here</p>
                    </div>
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
            <div class="profile-img" onclick="openProfileInput()">
                <div class="camera-logo">
                    <ion-icon name="camera"></ion-icon>
                    <p>CHANGE PROFILE PHOTO</p>
                </div>
                <img src="profilePictures/<%=user.getProfilePicture()%>" alt="" class="cover">
            </div>
            <h2><%=user.getUserName()%>
            </h2>
            <p><%=user.getEmail()%>
            </p>
            <p><%=user.getBio()%>
            </p>
            <input style="visibility: hidden" id="newProfilePic" type="file" onchange="changeProfilePic()"
                   accept="image/png, image/jpeg, image/jpg">
            <%--            <button onclick="changeProfilePic()">upload</button>--%>
        </div>
    </div>

    <div class="leftUpdate">
        <div class="profile-header">
            <ion-icon name="arrow-back-outline" onclick="seeChatList()"></ion-icon>
            <h4>profile</h4>
        </div>
        <div class="profile-details">
            <div class="edit-profile">
                <form action="controller" method="post">
                    <h3>Update my profile</h3>
                    <label class="form-label">Username</label> <br/>
                    <input class="form-control" name="username" value="<%=user.getUserName()%>" required/> <br/>

                    <label class="form-label">Bio</label> <br/>
                    <input class="form-control" name="bio" value="<%=user.getBio()%>"/> <br/><br/>

                    <input type="submit" value="Update" class="btn btn-success"/>
                    <!-- Include a hidden field to identify what the user wants to do -->
                    <input type="hidden" name="action" value="do_editProfile"/>
                </form>
            </div>

            <div class="change-Password">
                <form action="controller" method="post">
                    <h3>Change password</h3>
                    <label class="form-label">Username</label> <br/>
                    <input class="form-control" name="username" value="<%=user.getUserName()%>" required/> <br/>

                    <label class="form-label">Old Password</label> <br/>
                    <input class="form-control" name="oldPassword" type="password" required/> <br/>

                    <label class="form-label">New Password</label> <br/>
                    <input class="form-control" name="newPassword" type="password" required/> <br/>

                    <input type="submit" value="Change password" class="btn btn-success"/>
                    <!-- Include a hidden field to identify what the user wants to do -->
                    <input type="hidden" name="action" value="changePassword"/>
                </form>
            </div>
        </div>
    </div>

    <div class="leftYourStory" id="leftYourStory">
        <div class="profile-header">
            <ion-icon name="arrow-back-outline" onclick="seeChatList()"></ion-icon>
            <h4>my stories</h4>
        </div>
        <div class="yourList" id="yourList">
            <div class="yourStoryList" id="yourStoryList">
                <div class="block">
                    <div class="imgbox">
                        <img src="img/profile.jpg" alt="" class="cover">
                    </div>
                    <div class="details">
                        <div class="listhead">
                            <h4>2 views</h4>
                            <p class="time">12:00 AM</p>
                        </div>
                        <ion-icon name="close-outline"></ion-icon>
                    </div>
                </div>
                <div class="block">
                    <div class="imgbox">
                        <img src="img/profile.jpg" alt="" class="cover">
                    </div>
                    <div class="details">
                        <div class="listhead">
                            <h4>2 views</h4>
                            <p class="time">12:00 AM</p>
                        </div>
                        <ion-icon name="close-outline"></ion-icon>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="right">
        <div class="header" id="chat-hide">
            <div class="imgtext" id="imgtext">
            </div>
            <ul class="nav-icons">
                <li>
                    <ion-icon name="search-outline"></ion-icon>
                </li>
                <li>
                    <ion-icon name="ellipsis-vertical" class="chat-menu" onclick="seeChatMenu()"></ion-icon>
                </li>
            </ul>
        </div>
        <div class='drop-menu-chat' id='drop-menu-chat'>

        </div>

        <!-- chatbox -->
        <div class="chatbox" id="chatbox">

        </div>
        <!-- chat input -->
        <div class="chatbox-input">
            <ion-icon name="happy-outline"></ion-icon>
            <ion-icon name="attach-outline" class="custom-file-icon" onclick="openFileInput()"></ion-icon>
            <input type="file" id="msgFile" onchange="sendFile(this)">
            <input type="text" placeholder="type a message" id="messageEntered">
            <ion-icon name="send" onclick="sendMessage()"></ion-icon>
        </div>
    </div>

</div>
<script>
    var mainInboxId = 0;
    var otherUserId = 0;
    var previousInboxId = 0;
    var counter = 0;
    var chatBoxScroll = document.getElementById("chatbox");
    var chatBoxScrollHeight = 0;
    var groupSuggestions = [];
    setInterval(refreshMessages, 2000);
    setInterval(getChatList, 2000);

    function refreshMessages() {
        if (mainInboxId !== 0) {
            // alert("in");
            updateMessages(mainInboxId);
        } else if (otherUserId !== 0) {
            getLinkingInboxId(otherUserId);
        }
    }

    function sendFile(input) {
        // Check if a file is selected
        if (input.files && input.files[0]) {
            // You can perform additional checks or validations here if needed

            // Call your sendMessage function
            sendMessage(input.files[0]);

            // Reset the file input
            input.value = null;
        }
    }

    function getMessagesHeader(inboxId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getMessagesHeader", "inboxId": inboxId},
                success: function (data) {
                    var header = document.getElementById("chat-hide");
                    header.innerHTML = '<div class="imgtext" id="imgtext"> ' +
                        '</div> ' +
                        '<ul class="nav-icons"> ' +
                        '<li> <ion-icon name="search-outline"></ion-icon></li> ' +
                        '<li> <ion-icon name="ellipsis-vertical" class="chat-menu" onclick="seeChatMenu()"></ion-icon> </li>' +
                        ' </ul>';

                    var imgtext = document.getElementById("imgtext");

                    imgtext.innerHTML = data;
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function getMessages(inboxId) {
        if (counter > 0 && mainInboxId !== 0) {
            previousInboxId = mainInboxId;
            closePreviousInbox();
        }
        mainInboxId = inboxId;
        otherUserId = 0;
        getMessagesHeader(inboxId);
        // alert("inside");
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getMessages", "inboxId": inboxId},
                success: function (data) {
                    counter++;
                    var allMessages = JSON.parse(data);
                    var chatBox = document.getElementById("chatbox");
                    chatBoxScroll = document.getElementById("chatbox");
                    chatBox.innerHTML = "";
                    for (var i = 0; i < allMessages.length; i++) {
                        console.log("Message Type:", allMessages[i][4]); // Log message type
                        console.log("File Name:", allMessages[i][3]);
                        if (parseInt(allMessages[i][2]) ===<%=user.getUserId()%>) {
                            if (parseInt(allMessages[i][4]) === 1) {
                                chatBox.innerHTML += "<div class='message my-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                            } else if (parseInt(allMessages[i][4]) === 2) {
                                chatBox.innerHTML += "<div class='chat-bubble my-message-file' onclick='checkImage(this)'> <img src='imageMessages/" + allMessages[i][3] + "' alt='User Image'> <span>" + allMessages[i][5] + "</span> </div>";
                            } else if (parseInt(allMessages[i][4]) === 3) {
                                chatBox.innerHTML += "<div class='chat-bubble my-message-file'> <video controls> <source src='videoMessages/" + allMessages[i][3] + "' type='video/mp4'>Your browser does not support the video tag. </video> <span>" + allMessages[i][5] + "</span> </div>"
                            } else if (parseInt(allMessages[i][4]) === 4) {
                                chatBox.innerHTML += "<div class='chat-bubble my-message-file'> <div class='file-details'><div><p>" + allMessages[i][7] + "</p> <span>12 MB</span> </div> <div class='iconbx'> <ion-icon name='arrow-down-circle-outline'></ion-icon> </div> </div> <span>" + allMessages[i][5] + "</span> </div>";
                                //"<div><object data='fileMessages/" + allMessages[i][3] + "' type='application/pdf' width='300' height='200'> </object> <a href='fileMessages/" + allMessages[i][3] + "' download>(PDF)</a>" + allMessages[i][7] + "</div>"
                                //"<div><embed src='fileMessages/"+allMessages[i][3]+"' type='application/pdf' >"+allMessages[i][7]+"</div>";
                            }
                        } else {
                            if (parseInt(allMessages[i][4]) === 1) {
                                chatBox.innerHTML += "<div class='message frnd-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                            } else if (parseInt(allMessages[i][4]) === 2) {
                                chatBox.innerHTML += "<div class='chat-bubble frnd-message-file' onclick='checkImage(this)'> <img src='imageMessages/" + allMessages[i][3] + "' alt='User Image'> <span>" + allMessages[i][5] + "</span> </div>";
                            } else if (parseInt(allMessages[i][4]) === 3) {
                                chatBox.innerHTML += "<div class='chat-bubble frnd-message-file'> <video controls> <source src='videoMessages/" + allMessages[i][3] + "' type='video/mp4'>Your browser does not support the video tag. </video> <span>" + allMessages[i][5] + "</span> </div>"
                            } else if (parseInt(allMessages[i][4]) === 4) {
                                chatBox.innerHTML += "<div class='chat-bubble frnd-message-file'> <div class='file-details'><div><p>" + allMessages[i][7] + "</p> <span>12 MB</span> </div> <div class='iconbx'> <ion-icon name='arrow-down-circle-outline'></ion-icon> </div> </div> <span>" + allMessages[i][5] + "</span> </div>";
                                //"<div><object data='fileMessages/" + allMessages[i][3] + "' type='application/pdf' width='300' height='200'> </object> <a href='fileMessages/" + allMessages[i][3] + "' download>(PDF)</a>" + allMessages[i][7] + "</div>";

                            }
                        }
                    }
                    //set to the height of the page
                    chatBox.scrollTop = chatBox.scrollHeight;
                    chatBoxScrollHeight = chatBox.scrollHeight;
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


    function updateMessages(inboxId) {
        mainInboxId = inboxId;
        otherUserId = 0;
        getMessagesHeader(inboxId);
        // alert("inside");
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getMessages", "inboxId": inboxId},
                success: function (data) {
                    var allMessages = JSON.parse(data);
                    var chatBox = document.getElementById("chatbox");
                    chatBoxScroll = document.getElementById("chatbox");
                    chatBox.innerHTML = "";
                    for (var i = 0; i < allMessages.length; i++) {
                        console.log("Message Type:", allMessages[i][4]); // Log message type
                        console.log("File Name:", allMessages[i][3]);
                        if (parseInt(allMessages[i][2]) ===<%=user.getUserId()%>) {
                            if (parseInt(allMessages[i][4]) === 1) {
                                chatBox.innerHTML += "<div class='message my-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                            } else if (parseInt(allMessages[i][4]) === 2) {
                                chatBox.innerHTML += "<div class='chat-bubble my-message-file' onclick='checkImage(this)'> <img src='imageMessages/" + allMessages[i][3] + "' alt='User Image'> <span>" + allMessages[i][5] + "</span> </div>";
                            } else if (parseInt(allMessages[i][4]) === 3) {
                                chatBox.innerHTML += "<div class='chat-bubble my-message-file'> <video controls> <source src='videoMessages/" + allMessages[i][3] + "' type='video/mp4'>Your browser does not support the video tag. </video> <span>" + allMessages[i][5] + "</span> </div>"
                            } else if (parseInt(allMessages[i][4]) === 4) {
                                chatBox.innerHTML += "<div class='chat-bubble my-message-file'> <div class='file-details'><div><p>" + allMessages[i][7] + "</p> <span>12 MB</span> </div> <div class='iconbx'> <ion-icon name='arrow-down-circle-outline'></ion-icon> </div> </div> <span>" + allMessages[i][5] + "</span> </div>";
                                //"<div><object data='fileMessages/" + allMessages[i][3] + "' type='application/pdf' width='300' height='200'> </object> <a href='fileMessages/" + allMessages[i][3] + "' download>(PDF)</a>" + allMessages[i][7] + "</div>"
                                //"<div><embed src='fileMessages/"+allMessages[i][3]+"' type='application/pdf' >"+allMessages[i][7]+"</div>";
                            }
                        } else {
                            if (parseInt(allMessages[i][4]) === 1) {
                                chatBox.innerHTML += "<div class='message frnd-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                            } else if (parseInt(allMessages[i][4]) === 2) {
                                chatBox.innerHTML += "<div class='chat-bubble frnd-message-file' onclick='checkImage(this)'> <img src='imageMessages/" + allMessages[i][3] + "' alt='User Image'> <span>" + allMessages[i][5] + "</span> </div>";
                            } else if (parseInt(allMessages[i][4]) === 3) {
                                chatBox.innerHTML += "<div class='chat-bubble frnd-message-file'> <video controls> <source src='videoMessages/" + allMessages[i][3] + "' type='video/mp4'>Your browser does not support the video tag. </video> <span>" + allMessages[i][5] + "</span> </div>"
                            } else if (parseInt(allMessages[i][4]) === 4) {
                                chatBox.innerHTML += "<div class='chat-bubble frnd-message-file'> <div class='file-details'><div><p>" + allMessages[i][7] + "</p> <span>12 MB</span> </div> <div class='iconbx'> <ion-icon name='arrow-down-circle-outline'></ion-icon> </div> </div> <span>" + allMessages[i][5] + "</span> </div>";
                                //"<div><object data='fileMessages/" + allMessages[i][3] + "' type='application/pdf' width='300' height='200'> </object> <a href='fileMessages/" + allMessages[i][3] + "' download>(PDF)</a>" + allMessages[i][7] + "</div>";

                            }
                        }
                    }
                    //set to the height of the page
                    chatBox.scrollTop = chatBoxScrollHeight;
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

    /* function seeReport() {
         $(document).ready(function () {
             $.ajax({
                 url: "controller",
                 type: 'post',
                 data: {action: "showReport","inboxId": mainInboxId},
                 success: function (data) {
                    /* var chatList = document.getElementById("chatlist");
                     chatList.innerHTML = data;
                 },
                 error: function () {
                     alert("Error with ajax");
                 }
             });
         });
     }*/

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

    //I need to add another function to set otherUserId when searching for a user


    /*async*/
    async function sendMessage() {
        var file = document.getElementById("msgFile");
        var chatBox = document.getElementById("chatbox");
        if (file.value != "") {
            var formData = new FormData();
            var extension = file.value.split(".").pop();
            formData.append("action", "sendFile");
            formData.append("file", file.files[0]);
            formData.append("extension", extension);
            formData.append("inboxId", mainInboxId);

            await fetch('controller', {
                method: "POST",
                body: formData
            });
            file.value = "";
        } else {
            var msg = document.getElementById("messageEntered").value.trim();
            if (msg !== "" && msg !== null) {

                //if it's the first time sending the user a message
                if (mainInboxId === 0 && otherUserId !== 0) {
                    $(document).ready(function () {
                        $.ajax({
                            url: "controller",
                            type: 'post',
                            data: {action: "firstMessage", "userId": otherUserId, "message": msg},
                            success: function (data) {
                                getLinkingInboxId(otherUserId);
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
                                chatBox.scrollTop = chatBox.scrollHeight;
                                chatBoxScrollHeight = chatBox.scrollHeight;
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
    }

    async function changeProfilePic() {
        var file = document.getElementById("newProfilePic");
        if (file.value !== "") {
            var formData = new FormData();
            var extension = file.value.split(".").pop();
            formData.append("action", "changeProfilePic");
            formData.append("file", file.files[0]);
            formData.append("extension", extension);
            await fetch('controller', {
                method: "POST",
                body: formData
            });
            file.value = "";
            location.reload();
        }
    }

    const entry = document.getElementById("search");
    var timeOutId = null;
    entry.addEventListener('input', function search() {
            const searchInput = entry.value.trim();
            if (searchInput != null && searchInput !== "") {
                clearTimeout(timeOutId);
                timeOutId = setTimeout(function () {
                    //alert("hey");
                    $.ajax({
                        url: "controller",
                        type: 'post',
                        data: {action: "search", "search": searchInput},
                        success: function (data) {
                            var allSuggestions = JSON.parse(data);
                            var suggestions = document.getElementById("suggestions");
                            suggestions.innerHTML = "";
                            for (var i = 0; i < allSuggestions.length; i++) {
                                let idx = allSuggestions[i][2] + allSuggestions[i][3];
                                let imgName = allSuggestions[i][0];
                                if (imgName === null || imgName === "null" || imgName === "") {
                                    imgName = "profile.jpg";
                                }
                                // profile picture is == allSuggestions[i][0] generate based on category u or c ;
                                suggestions.innerHTML += "<div id='" + idx + "' class='suggestion' onclick=" + "handleSearch('" + idx + "')" + ">" +
                                    "<img src='profilePictures/" + imgName + "' alt='user-image'><p>" + allSuggestions[i][1] + "</p></div>";

                            }
                            // var allSuggestion = Array.from(document.querySelectorAll(".suggestion"));
                            // // elements.forEach(function(element) {
                            // allSuggestion.forEach(function (element) {
                            //     element.addEventListener('click', handleSearch
                            //     );
                            //     // return " ";
                            // });

                        },
                        error: function () {
                            alert("Error with ajax");
                        }
                    });
                }, 500);
            }
        }
    );

    function handleSearch(cat) {
        var suggestions = document.getElementById("suggestions");
        suggestions.innerHTML = "";
        console.log(cat);
        seeMessage();
        //alert(cat);
        var identifier = cat;
        var searchId = parseInt(identifier.substring(0, identifier.length - 1));
        var searchCategory = identifier.substring(identifier.length - 1);
        var chatBox = document.getElementById("chatbox");
        if (searchCategory === "g") {
            getMessages(searchId);
            //return "";
            // allSuggestions="";
            //break;
        } else {
            getLinkingInboxId(searchId);
            chatBox.innerHTML = " ";
            /*if(mainInboxId>0){
                getMessages(mainInboxId)
                alert("wronf one");
            }
            else if((mainInboxId===0)) {
                alert("in kckkc");
                getMessagesHeaderByUserId(searchId);
                var chatBox = document.getElementById("chatbox");
                chatBox.innerHTML = " ";
            }*/
            /*otherUserId=searchId;
            mainInboxId=0;*/
            //getMessagesHeaderByUserId(searchId);

            /*$(document).ready(function () {
                $.ajax({
                    url: "controller",
                    type: 'post',
                    data: {action: "getMessagesByUserId", "userId": searchId},
                    success: function (data) {
                        var chatBox = document.getElementById("chatbox");
                        if (data!=="noMessages"){
                            var allMessages = JSON.parse(data);
                            alert(allMessages.length);
                        if (allMessages.length > 0) {
                            for (var i = 0; i < allMessages.length; i++) {
                                console.log("Message Type:", allMessages[i][4]); // Log message type
                                console.log("File Name:", allMessages[i][3]);
                                if (parseInt(allMessages[i][2]) ===






            <%=user.getUserId()%>) {
                                    if (parseInt(allMessages[i][4]) === 1) {
                                        chatBox.innerHTML += "<div class='message my-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                                    } else if (parseInt(allMessages[i][4]) === 2) {
                                        chatBox.innerHTML += "<div class='chat-bubble my-message-file' onclick='checkImage(this)'> <img src='imageMessages/" + allMessages[i][3] + "' alt='User Image'> <span>" + allMessages[i][5] + "</span> </div>";
                                    } else if (parseInt(allMessages[i][4]) === 3) {
                                        chatBox.innerHTML += "<div class='chat-bubble my-message-file'> <video controls> <source src='videoMessages/" + allMessages[i][3] + "' type='video/mp4'>Your browser does not support the video tag. </video> <span>" + allMessages[i][5] + "</span> </div>"
                                    } else if (parseInt(allMessages[i][4]) === 4) {
                                        chatBox.innerHTML += "<div class='chat-bubble my-message-file'> <div class='file-details'><div><p>" + allMessages[i][7] + "</p> <span>12 MB</span> </div> <div class='iconbx'> <ion-icon name='arrow-down-circle-outline'></ion-icon> </div> </div> <span>" + allMessages[i][5] + "</span> </div>";
                                        //"<div><object data='fileMessages/" + allMessages[i][3] + "' type='application/pdf' width='300' height='200'> </object> <a href='fileMessages/" + allMessages[i][3] + "' download>(PDF)</a>" + allMessages[i][7] + "</div>"
                                        //"<div><embed src='fileMessages/"+allMessages[i][3]+"' type='application/pdf' >"+allMessages[i][7]+"</div>";
                                    }
                                } else {
                                    if (parseInt(allMessages[i][4]) === 1) {
                                        chatBox.innerHTML += "<div class='message frnd-message'><p>" + allMessages[i][3] + "<br><span>" + allMessages[i][5] + "</span></p></div>";
                                    } else if (parseInt(allMessages[i][4]) === 2) {
                                        chatBox.innerHTML += "<div class='chat-bubble frnd-message-file' onclick='checkImage(this)'> <img src='imageMessages/" + allMessages[i][3] + "' alt='User Image'> <span>" + allMessages[i][5] + "</span> </div>";
                                    } else if (parseInt(allMessages[i][4]) === 3) {
                                        chatBox.innerHTML += "<div class='chat-bubble frnd-message-file'> <video controls> <source src='videoMessages/" + allMessages[i][3] + "' type='video/mp4'>Your browser does not support the video tag. </video> <span>" + allMessages[i][5] + "</span> </div>"
                                    } else if (parseInt(allMessages[i][4]) === 4) {
                                        chatBox.innerHTML += "<div class='chat-bubble frnd-message-file'> <div class='file-details'><div><p>" + allMessages[i][7] + "</p> <span>12 MB</span> </div> <div class='iconbx'> <ion-icon name='arrow-down-circle-outline'></ion-icon> </div> </div> <span>" + allMessages[i][5] + "</span> </div>";
                                        //"<div><object data='fileMessages/" + allMessages[i][3] + "' type='application/pdf' width='300' height='200'> </object> <a href='fileMessages/" + allMessages[i][3] + "' download>(PDF)</a>" + allMessages[i][7] + "</div>";

                                    }
                                }
                            }
                        }
                    }
                        else{
                            chatBox.innerHTML = " ";
                        }
                    },
                    error: function () {
                        alert("Error with ajax");
                    }
                });
            });*/

        }

    }

    function getLinkingInboxId(userId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getLinkingId", "userId": userId},
                success: function (data) {
                    //alert(data);
                    var id = parseInt(data);
                    //alert(id);
                    if (id > 0) {
                        otherUserId = 0;
                        getMessages(id);
                    } else {
                        otherUserId = userId;
                        previousInboxId = mainInboxId;
                        mainInboxId = 0;
                        getMessagesHeaderByUserId(userId);
                        var chatBox = document.getElementById("chatbox");
                        chatBox.innerHTML = " ";
                        closePreviousInbox();
                        /*counter=0;
                        previousInboxId=0;*/
                    }
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function closePreviousInbox() {
        if (counter > 0 && previousInboxId !== 0) {
            $(document).ready(function () {
                $.ajax({
                    url: "controller",
                    type: 'post',
                    data: {action: "closePreviousInbox", "inboxId": previousInboxId},
                    success: function (data) {
                        //alert(previousInboxId + " prev id");
                    },
                    error: function () {
                        alert("Error with ajax");
                    }
                });
            });
        }
    }

    /**for smaller devices **/
    function closeInbox() {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "closePreviousInbox", "inboxId": mainInboxId},
                success: function (data) {
                    previousInboxId = mainInboxId;
                    counter++;
                    mainInboxId = 0;
                    hideMessage();
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function hideMessage() {
        $("#chat-hide").css("visibility", "hidden");
        $(".chatbox").css("visibility", "hidden");
        $(".chatbox-input").css("visibility", "hidden");
    }

    function getMessagesHeaderByUserId(userId) {
        //alert("innn");
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getMessagesHeaderByUserId", "userId": userId},
                success: function (data) {
                    var header = document.getElementById("chat-hide");
                    var str = data.split("%%%");
                    if (str.length > 1) {
                        header.innerHTML = '<div class="imgtext" id="imgtext"> ' +
                            '</div> ' +
                            '<ul class="nav-icons"> ' +
                            '<li> <ion-icon name="search-outline"></ion-icon></li> ' +
                            '<li> <ion-icon name="ellipsis-vertical" class="chat-menu" onclick="seeChatMenu()"></ion-icon> </li>' +
                            ' </ul>' + str[1];
                    } else {
                        header.innerHTML = '<div class="imgtext" id="imgtext"> ' +
                            '</div> ' +
                            '<ul class="nav-icons"> ' +
                            '<li> <ion-icon name="search-outline"></ion-icon></li> ' +
                            '<li> <ion-icon name="ellipsis-vertical" class="chat-menu" onclick="seeChatMenu()"></ion-icon> </li>' +
                            ' </ul>';
                    }

                    var imgtext = document.getElementById("imgtext");

                    imgtext.innerHTML = str[0];
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    //when the user scrolls
    chatBoxScroll.addEventListener('scroll', (event) => {
        //set current scroll height to current scroll height
        chatBoxScrollHeight = chatBoxScroll.scrollTop;
    });

    const groupEntry = document.getElementById("searchGroup");
    var timeOutId2 = null;
    groupEntry.addEventListener('input', function search() {
            const searchInput = groupEntry.value.trim();
            if (searchInput != null && searchInput !== "") {
                clearTimeout(timeOutId2);
                timeOutId2 = setTimeout(function () {
                    //alert("hey");
                    $.ajax({
                        url: "controller",
                        type: 'post',
                        data: {action: "inviteGroupMember", "search": searchInput, "inboxId": mainInboxId},
                        success: function (data) {
                            groupSuggestions = JSON.parse(data);
                            getGroupSuggestions();
                        },
                        error: function () {
                            alert("Error with ajax");
                        }
                    });
                }, 500);
            }
        }
    );

    function getGroupSuggestions() {
        var list = document.getElementById("list-invite");
        list.innerHTML = "";
        for (var i = 0; i < groupSuggestions.length; i++) {
            let imgName = groupSuggestions[i][2];
            if (imgName === null || imgName === "null") {
                imgName = "profile.jpg";
            }
            list.innerHTML += "<div class='member'> <img src='profilePictures/" + groupSuggestions[i][2] + "'alt='user-image'><p>" + groupSuggestions[i][1] + "</p><ion-icon name='person-add-outline' onclick='addUser(" + groupSuggestions[i][0] + " )' ></ion-icon> </div>";
        }
    }

    function addUser(userId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "addGroupMember", "userId": userId, "inboxId": mainInboxId},
                success: function (data) {
                    var tempSuggestion = [];
                    var tracker = 0;
                    for (var i = 0; i < groupSuggestions.length; i++) {
                        if (groupSuggestions[i][0] != userId) {
                            tempSuggestion [tracker] = groupSuggestions[i];
                            tracker++;
                        }
                    }
                    groupSuggestions = tempSuggestion;
                    getGroupSuggestions();
                    getGroupMembers();
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function openGroupPage() {
        document.querySelector('.group-page').style.display = 'flex';
        getGroupMembers();
        console.log("has opened members")
    }

    function getGroupMembers() {
        var list = document.getElementById("groupMembers");
        list.innerHTML = "";
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "getGroupMembers", "inboxId": mainInboxId},
                success: function (data) {
                    var groupMembers = JSON.parse(data);
                    for (var i = 0; i < groupMembers.length; i++) {
                        let imgName = groupMembers[i][2];
                        if (imgName === null || imgName === "null") {
                            imgName = "profile.jpg";
                        }
                        list.innerHTML += "<div class='member'> <img src='profilePictures/" + groupMembers[i][2] + "' alt='user-image'><p>" + groupMembers[i][1] + "</p><ion-icon name='person-remove-outline' onclick='removeGroupMember(" + groupMembers[i][0] + ")' ></ion-icon> </div>";
                    }

                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function removeGroupMember(userId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "removeGroupMember", "userId": userId, "inboxId": mainInboxId},
                success: function (data) {
                    getGroupMembers();
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function leaveGroup(userId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "leaveGroup", "inboxId": mainInboxId},
                success: function (data) {
                    getGroupMembers();
                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function blockUser(userId) {
        $(document).ready(function () {
            $.ajax({
                url: "controller",
                type: 'post',
                data: {action: "blockUser", "inboxId": mainInboxId},
                success: function (data) {

                },
                error: function () {
                    alert("Error with ajax");
                }
            });
        });
    }

    function closeCreateGroupPage() {
        document.querySelector('.create-group-page').style.display = 'none';
    }

    async function createGroupChat() {
        var file = document.getElementById("imgInp");
        var groupProfilepic = document.getElementById("groupProfilePic");
        var groupName = document.getElementById("groupName").value.trim();
        var formData = new FormData();
        formData.append("action", "createGroupChat");
        if (file.value !== "" && groupName != "") {
            var extension = file.value.split(".").pop();
            formData.append("file", file.files[0]);
            formData.append("extension", extension);
            formData.append("groupName", groupName);
            await fetch('controller', {
                method: "POST",
                body: formData
            });
            file.value = "";
            groupProfilepic.src = "profilePictures/profile.jpg";
            document.getElementById("groupName").value = "";
            closeCreateGroupPage();
        } else if (groupName != "") {
            /* formData.append("file", "none");
             formData.append("extension", "none");*/
            formData.append("groupName", groupName);
            await fetch('controller', {
                method: "POST",
                body: formData
            });
            file.value = "";
            groupProfilepic.src = "profilePictures/profile.jpg";
            document.getElementById("groupName").value = "";
            closeCreateGroupPage();
        }
    }

     function getStoriesList(){
         var list = document.getElementById("storiesList");
         $(document).ready(function () {
             $.ajax({
                 url: "controller",
                 type: 'post',
                 data: {action: "getStoriesList"},
                 success: function (data) {
                     var storiesList = JSON.parse(data);
                     list.innerHTML="";
                     for (var i = 0; i < storiesList.length; i++) {
                      list.innerHTML += "<div class='block' onclick='getStories("+storiesList[i][0] +")'><div class='imgbox'> <img src='profilePictures/"+storiesList[i][2]+"' alt='' class='cover'> </div> <div class='details'> <div class='listhead'> <h4>"+storiesList[i][1]+"</h4> <p class='time'>"+storiesList[i][4]+"</p> </div> <div class='message-p'> <p>"+storiesList[i][5]+"</p> </div> </div> </div>"
                     }

                 },
                 error: function () {
                     alert("Error with ajax");
                 }
             });
         });
     }

     /*function getStories(id){
         $(document).ready(function () {
             $.ajax({
                 url: "controller",
                 type: 'post',
                 data: {action: "getStories", id:id},
                 success: function (data) {
                     var stories = JSON.parse(data);
                     for (var i = 0; i < stories.length; i++) {
                         storyMedia[i]="stories/"+stories[i][2];
                     }

                 },
                 error: function () {
                     alert("Error with ajax");
                 }
             });
         });
     }*/
    async function uploadStatus(){
         var file = document.getElementById("statusFile");
        var statusDescription = document.getElementById("statusDescription").value.trim();
         if (file.value != "") {
             var formData = new FormData();
             var extension = file.value.split(".").pop();
             formData.append("action", "uploadStatus");
             formData.append("file", file.files[0]);
             formData.append("extension", extension);
             formData.append("statusDescription", statusDescription);

             await fetch('controller', {
                 method: "POST",
                 body: formData
             });
             file.value = "";
             document.getElementById("statusDescription").value="";
         }
     }

    document.addEventListener('keydown', function (event) {
        if (event.key === "Enter") {
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

