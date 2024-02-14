package controller;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import business.Inbox;
import business.InboxParticipants;
import business.Message;
import business.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import daos.InboxDao;
import daos.InboxParticipantsDao;
import daos.MessageDao;
import daos.UsersDao;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        String dest = "index.jsp";

        if (action != null) {
            switch (action) {
                case "index":
                    break;

                case "logout":
                    response.sendRedirect(dest);
                    break;

                case "show_login":
                    dest = "login.jsp";
                    response.sendRedirect(dest);
                    break;
                case "do_login":
                    dest = Login(request, response);
                    response.sendRedirect(dest);
                    break;

                case "show_register":
                    dest = "register.jsp";
                    response.sendRedirect(dest);
                    break;
                case "do_register":
                    dest = Register(request, response);
                    response.sendRedirect(dest);
                    break;

                case "chatbox":
                    dest = "chatbox.jsp";
                    response.sendRedirect(dest);
                    break;
                case "getMessages":
                    getMessages(request, response);
                    break;
                case "firstMessage":
                    firstMessage(request, response);
                    break;
                case "sendMessage":
                    sendMessage(request, response);
                    break;
                case "getMessagesHeader":
                    getMessagesHeader(request, response);
                    break;
                case "getChatlist":
                    getChatList(request, response);
                    break;
            }
        }


    }

    public void destroy() {
    }

    public String Login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsersDao usersDao = new UsersDao("gossip");
        Users user = usersDao.Login(email, password);

        if (user != null) {
            user.setPassword(password);
            session.setAttribute("user", user);
            return "chatbox.jsp";
        } else {
            String msg = "Wrong password or email";
            session.setAttribute("msg", msg);
            return "login.jsp";
        }
    }

    public String Register(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));

        if (username != null && email != null && password != null && !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && dateOfBirth != null) {
            UsersDao userDao = new UsersDao("gossip");
            int id = userDao.Register(email, username, "default.png", password, dateOfBirth, 0, 0, "", 0);

            if (id != -1) {
                String msg = "You have been registered successfully!";
                Users user = new Users(id, email, username, "default.png", password, dateOfBirth, 0, 0, "", 0);
                session.setAttribute("user", user);
                session.setAttribute("msg", msg);
                return "chatbox.jsp";
            } else {
                String msg = "Registration was not successful, please try again!";
                session.setAttribute("msg", msg);
                return "register.jsp";
            }
        }
        return "register.jsp";
    }

    public void getMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int inboxId = Integer.parseInt(request.getParameter("inboxId"));
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        session.setAttribute("activeInboxId", inboxId);
        MessageDao messageDao = new MessageDao("gossip");
        ArrayList<Message> allMessages = messageDao.getMessages(inboxId);
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        //set unseenMessages to 0
        ibpsDao.resetUnSeenMessages(inboxId, user.getUserId());
        //set open state to true
        ibpsDao.openInbox(inboxId, user.getUserId(), 1);
        String messages = "";
        ArrayList<String[]> messagesList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Message m : allMessages) {
            String[] messageArray = new String[7];
            messageArray[0] = m.getMessageId() + "";
            messageArray[1] = m.getInboxId() + "";
            messageArray[2] = m.getSenderId() + "";
            messageArray[3] = m.getMessage();
            messageArray[4] = m.getMessageType() + "";
            messageArray[5] = m.getTimeSent().toString();
            messageArray[6] = m.getDeletedState() + "";
            messagesList.add(messageArray);

            //if it's the user that send the message
           /* if (user.getUserId() == m.getSenderId()) {
                if (m.getMessageType() == 1) {
                    messages += "<div class='message my-message'><p>" + m.getMessage() + "<br><span>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</span></p></div>";
                }
            } else if (user.getUserId() != m.getSenderId()) {
                if (m.getMessageType() == 1) {
                    messages += "<div class='message frnd-message'><p>" + m.getMessage() + "<br><span>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</span></p></div>";
                }
            }*/
        }
        String jsonString = objectMapper.writeValueAsString(messagesList);
        response.getWriter().write(jsonString);
        //response.getWriter().write(messages);
        //session.setAttribute("allMessages",messagesList);
    }

    public void firstMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        int otherUserId = Integer.parseInt(request.getParameter("userId"));
        String message = request.getParameter("message");
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        InboxDao inboxDao = new InboxDao("gossip");
        MessageDao messageDao = new MessageDao("gossip");
        ArrayList<InboxParticipants> myIbps = ibpsDao.getAllInbox(user.getUserId());
        ArrayList<InboxParticipants> otherIbps = ibpsDao.getAllInbox(otherUserId);
        Inbox matchingInbox = null;
        //checking if there is any Inbox that links the 2 users
        label:
        for (InboxParticipants Myibps : myIbps) {
            for (InboxParticipants Otheribps : otherIbps) {
                if (Myibps.getInboxId() == Otheribps.getInboxId()) {
                    Inbox inbox = inboxDao.getInbox(Myibps.getInboxId());
                    if (inbox.getInboxType() == 1) {
                        matchingInbox = inbox;
                        break label;
                    }
                }
            }
        }
        //if a matching inbox was found
        if (matchingInbox != null) {
            //send message
            messageDao.sendMessage(matchingInbox.getInboxId(), user.getUserId(), message, 1);
            //update unseen messages for the other user
            ibpsDao.updateUnSeenMessages(matchingInbox.getInboxId(), otherUserId);
            //set openState to true
            ibpsDao.openInbox(matchingInbox.getInboxId(), user.getUserId(), 1);
        } else {
            // create a new inbox for them
            int inboxId = inboxDao.createNormalInbox();
            //insert the current user
            ibpsDao.insertInboxParticipant(inboxId, user.getUserId());
            //insert the other user
            ibpsDao.insertInboxParticipant(inboxId, otherUserId);
            messageDao.sendMessage(inboxId, user.getUserId(), message, 1);
            ibpsDao.updateUnSeenMessages(inboxId, otherUserId);
        }

    }

    public void sendMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        int inboxId = Integer.parseInt(request.getParameter("inboxId"));
        String message = request.getParameter("message");
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        UsersDao usersDao = new UsersDao("gossip");
        MessageDao messageDao = new MessageDao("gossip");
        InboxDao inboxDao = new InboxDao("gossip");
        Inbox inbox = inboxDao.getInbox(inboxId);
        //if it's a normal chat
        if (inbox.getInboxType() == 1) {
            //send message
            messageDao.sendMessage(inboxId, user.getUserId(), message, 1);
            // get the other person's InboxParticipant
            InboxParticipants ibp = ibpsDao.getOtherInboxParticipant(inboxId, user.getUserId());
            //update unseen messages for the other user
            ibpsDao.updateUnSeenMessages(inboxId, ibp.getUserId());
        } else {
            //send message
            messageDao.sendMessage(inboxId, user.getUserId(), message, 1);
            ArrayList<InboxParticipants> allIbps = ibpsDao.getAllInboxParticipants(inboxId);
            //add unseenMessages for all users in the groupChat
            for (InboxParticipants Ibps : allIbps) {
                ibpsDao.updateUnSeenMessages(inboxId, Ibps.getUserId());
            }
        }
    }

    public void getMessagesHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        int inboxId = Integer.parseInt(request.getParameter("inboxId"));
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        UsersDao usersDao = new UsersDao("gossip");
        InboxDao inboxDao = new InboxDao("gossip");
        String header = "";
        Inbox inbox = inboxDao.getInbox(inboxId);
        if (inbox.getInboxType() == 1) {
            InboxParticipants otherIbp = ibpsDao.getOtherInboxParticipant(inboxId, user.getUserId());
            Users otherUser = usersDao.getUserById(otherIbp.getUserId());
            if (otherUser.getOnline() == 1) {
                header = "<ion-icon class='return' name='arrow-back-outline'></ion-icon> <div class='userimg'><img src='img/" + otherUser.getProfilePicture() + "' alt='profile' class='cover'> </div><h4>" + otherUser.getUserName() + "<br><span>online</span></h4>";
            } else {
                header = "<ion-icon class='return' name='arrow-back-outline'></ion-icon> <div class='userimg'><img src='img/" + otherUser.getProfilePicture() + "' alt='profile' class='cover'> </div><h4>" + otherUser.getUserName() + "<br><span></span></h4>";
            }
        } else {
            header = "<ion-icon class='return' name='arrow-back-outline'></ion-icon> <div class='userimg'><img src='img/profile.jpg' alt='profile' class='cover'> </div><h4>" + inbox.getGroupName() + "<br><span></span></h4>";
        }
        response.getWriter().write(header);
    }

    public void getChatList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        String checkInboxId = null;
        //checkInboxId=(String)session.getAttribute("activeInboxId");
        int activeInboxId = 0;
        /*activeInboxId=(Integer) session.getAttribute("activeInboxId");*/
        /*if(activeInboxId==0){
          activeInboxId=Integer.parseInt(checkInboxId);
        }*/

        InboxParticipantsDao ibpDao = new InboxParticipantsDao("gossip");
        InboxDao inboxDao = new InboxDao("gossip");
        MessageDao messageDao = new MessageDao("gossip");
        UsersDao usersDao = new UsersDao("gossip");
        //gets all the inboxParticipants for that particular user
        ArrayList<InboxParticipants> Ibps = ibpDao.getAllInbox(user.getUserId());
        String chatlist = "";
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
                        chatlist = chatlist + "<div class='block unread' onclick='getMessages(" + ibps.getInboxId() + ")'><div class='imgbox'><img src='img/" + otherUser.getProfilePicture() + "' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + otherUser.getUserName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p> <b>" + ibps.getUnseenMessages() + "</b></div></div></div>";

                    } else if (activeInboxId == ibps.getInboxId()) {
                        chatlist = chatlist + "<div class='block active' onclick='getMessages(" + ibps.getInboxId() + ")'><div class='imgbox'><img src='img/" + otherUser.getProfilePicture() + "' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + otherUser.getUserName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p> </div></div></div>";
                    } else {
                        chatlist = chatlist + "<div class='block' onclick='getMessages(" + ibps.getInboxId() + ")'><div class='imgbox'><img src='img/" + otherUser.getProfilePicture() + "' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + otherUser.getUserName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p></div></div></div>";
                    }
                }
            } else if (myInbox.getInboxType() == 2) {
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
                        chatlist = chatlist + "<div class='block unread' onclick='getMessages(" + ibps.getInboxId() + ")'><div class='imgbox'><img src='img/profile.jpg ' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + groupInbox.getGroupName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p> <b>" + ibps.getUnseenMessages() + "</b></div></div></div>";
                    } else if (activeInboxId == ibps.getInboxId()) {
                        chatlist = chatlist + "<div class='block unread' onclick='getMessages(" + ibps.getInboxId() + ")'><div class='imgbox'><img src='img/profile.jpg ' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + groupInbox.getGroupName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p></div></div></div>";
                    } else {
                        chatlist = chatlist + "<div class='block' onclick='getMessages(" + ibps.getInboxId() + ")'><div class='imgbox'><img src='img/profile.jpg ' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + groupInbox.getGroupName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p></div></div></div>";
                    }
                }
            }
        }
        response.getWriter().write(chatlist);
    }
}