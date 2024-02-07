package controller;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import business.Inbox;
import business.InboxParticipants;
import business.Message;
import business.Users;
import daos.InboxDao;
import daos.InboxParticipantsDao;
import daos.MessageDao;
import daos.UsersDao;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {
    private String message;
    private Users user;

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

                case "logout":
                    dest = "index.jsp";
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
//                case "do_register":
//                    dest = Register(request,response);
//                    response.sendRedirect(dest);
//                    break;

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
        user = usersDao.Login(email, password);

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

//    public String Register (HttpServletRequest request, HttpServletResponse response){
//        HttpSession session = request.getSession(true);
//        String username = request.getParameter("username");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
//
//        if (username != null && email != null && password != null && dateOfBirth !=null && !username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
//            UsersDao userDao = new UsersDao("gossip");
//            int id = userDao.Register(email,username,"",password,dateOfBirth,0 ,0,"",0);
//
//            if(id != -1){
//                String msg = "You have been registered successfully!";
//                Users user = new Users(id, email, username, "", password, dateOfBirth, 0,0,"",0);
//                session.setAttribute("user", user);
//                session.setAttribute("msg", msg);
//                return  "index.jsp";
//            }
//            else{
//                String msg = "Registration was not successful, please try again!";
//                session.setAttribute("msg", msg);
//                return "register.jsp";
//            }
//        }
//        return "register.jsp";
//    }

    public void getMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int inboxId = Integer.parseInt(request.getParameter("inboxId"));
        MessageDao messageDao = new MessageDao("gossip");
        ArrayList<Message> allMessages = messageDao.getMessages(inboxId);
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        //set unseenMessages to 0
        ibpsDao.resetUnSeenMessages(inboxId, user.getUserId());
        //set open state to true
        ibpsDao.openInbox(inboxId, user.getUserId(), 1);
        String messages = "";
        for (Message m : allMessages) {
            //if it's the user that send the message
            if (user.getUserId() == m.getSenderId()) {
                messages = messages + "<div class='message my-message'><p>" + m.getMessage() + "<br><span>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</span></p> </div>";
            } else {
                messages = messages + "<div class='message frnd-message'><p>" + m.getMessage() + "<br><span>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</span></p> </div>";
            }
        }
        response.getWriter().write(messages);

    }

    public void firstMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
}