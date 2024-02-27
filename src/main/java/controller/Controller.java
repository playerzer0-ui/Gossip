package controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import business.Inbox;
import business.InboxParticipants;
import business.Message;
import business.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import daos.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import miscellaneous.Aes;
import miscellaneous.Miscellaneous;

import javax.imageio.ImageIO;

//@RestController
@WebServlet(name = "Controller", value = "/controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 - 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
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
                    String x = getServletContext().getRealPath("/");
                    getServletContext().log("sdasdadsadsadssd");
                    response.sendRedirect(dest);
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
                case "sendFile":
                    try {
                        sendFile(request, response);
                    } catch (ServletException ex) {
                        response.sendRedirect("register.jsp");
                    }
                    break;
                case "showReport":
                    session.setAttribute("reportedId", request.getParameter("reportedId"));
                    System.out.println("id is " + request.getParameter("reportedId"));
                    response.sendRedirect("report.jsp");
                    break;
                case "send_report":
                    sendReport(request, response);
                    dest = "chatbox.jsp";
                    response.sendRedirect(dest);
                    break;
                case "changeProfilePic":
                    try {
                        changeProfilePicture(request, response);
                    } catch (ServletException ex) {
                        response.sendRedirect("register.jsp");
                    }
                    dest = "chatbox.jsp";
                    response.sendRedirect(dest);
                    break;

                case "show_admin":
                    dest = "admin.jsp";
                    response.sendRedirect(dest);
                    break;

                case "show_admin_report":
                    dest = "reportPage.jsp";
                    response.sendRedirect(dest);
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
        Aes aes= new Aes();
        //set unseenMessages to 0
        ibpsDao.resetUnSeenMessages(inboxId, user.getUserId());
        //set open state to true
        ibpsDao.openInbox(inboxId, user.getUserId(), 1);
        String messages = "";
        ArrayList<String[]> messagesList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Message m : allMessages) {
            String[] messageArray = new String[7];
            try {
                String message = aes.decrypt(m.getMessage(), m.getMessageKey());
                messageArray[0] = m.getMessageId() + "";
                messageArray[1] = m.getInboxId() + "";
                messageArray[2] = m.getSenderId() + "";
                messageArray[3] = message;
                messageArray[4] = m.getMessageType() + "";
                messageArray[5] = m.getTimeSent().toString();
                messageArray[6] = m.getDeletedState() + "";
            }catch(Exception ex){
                System.out.println("error occurred while getting messages" + ex.getMessage());
            }
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
        Aes aes = new Aes();
        int key = aes.generateKey();
        try {
            message = aes.encrypt(message, key);
            //if it's a normal chat
            if (inbox.getInboxType() == 1) {
                //send message
                messageDao.sendMessage(inboxId, user.getUserId(), message, 1, key);
                // get the other person's InboxParticipant
                InboxParticipants ibp = ibpsDao.getOtherInboxParticipant(inboxId, user.getUserId());
                //update unseen messages for the other user
                ibpsDao.updateUnSeenMessages(inboxId, ibp.getUserId());
            } else {
                //send message
                messageDao.sendMessage(inboxId, user.getUserId(), message, 1, key);
                ArrayList<InboxParticipants> allIbps = ibpsDao.getAllInboxParticipants(inboxId);
                //add unseenMessages for all users in the groupChat
                for (InboxParticipants Ibps : allIbps) {
                    if (Ibps.getUserId() != user.getUserId()) {
                        ibpsDao.updateUnSeenMessages(inboxId, Ibps.getUserId());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("error occurred when sending message" + ex.getMessage());
            response.sendRedirect("Sorry error occurred while sending message");
        }

    }

    public void getMessagesHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        int inboxId = Integer.parseInt(request.getParameter("inboxId"));
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        UsersDao usersDao = new UsersDao("gossip");
        InboxDao inboxDao = new InboxDao("gossip");
        Miscellaneous miscellaneous = new Miscellaneous();
        String header = "";
        Inbox inbox = inboxDao.getInbox(inboxId);
        if (inbox.getInboxType() == 1) {
            InboxParticipants otherIbp = ibpsDao.getOtherInboxParticipant(inboxId, user.getUserId());
            Users otherUser = usersDao.getUserById(otherIbp.getUserId());
            if (otherUser.getOnline() == 1) {
                header = "<ion-icon class='return' onclick='seeChatList()' name='arrow-back-outline'></ion-icon> <div class='userimg'><img src='profilePictures/" + otherUser.getProfilePicture() + "' alt='profile' class='cover'> </div><h4>" + otherUser.getUserName() + "<br><span>online</span></h4>   <div class='drop-menu-chat' id='drop-menu-chat'> <ul>  <a href='controller?action=block_user'> <li>block user</li> </a>  <a href='controller?action=showReport&reportedId=" + otherUser.getUserId() + "' > <li>report user</li>  </a><a href='controller?action=leave_chat'>  <li>leave chat</li></a></ul>   </div>    </div>";

            } else {
                header = "<ion-icon class='return' onclick='seeChatList()' name='arrow-back-outline'></ion-icon> <div class='userimg'><img src='profilePictures/" + otherUser.getProfilePicture() + "' alt='profile' class='cover'> </div><h4>" + otherUser.getUserName() + "<br><span></span></h4>   <div class='drop-menu-chat' id='drop-menu-chat'> <ul>  <a href='controller?action=block_user'> <li>block user</li> </a>  <a href='controller?action=showReport&reportedId=" + otherUser.getUserId() + "' > <li>report user</li>  </a><a href='controller?action=leave_chat'>  <li>leave chat</li></a></ul>   </div>    </div>";
            }
        } else {
            header = "<ion-icon class='return' onclick='seeChatList()' name='arrow-back-outline'></ion-icon> <div class='userimg'><img src='profilePictures/profile.jpg' alt='profile' class='cover'> </div><h4>" + inbox.getGroupName() + "<br><span></span></h4>";
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
        Aes aes= new Aes();
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
                    try {
                        m.setMessage(aes.decrypt(m.getMessage(), m.getMessageKey()));
                    }
                    catch(Exception ex){
                        System.out.println("error occurred when getting last message" + ex.getMessage());
                    }
                    //if there are unseenMessages
                    if (ibps.getUnseenMessages() > 0) {
                        chatlist = chatlist + "<div class='block unread' onclick='getMessages(" + ibps.getInboxId() + ");seeMessage();'><div class='imgbox'><img src='profilePictures/" + otherUser.getProfilePicture() + "' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + otherUser.getUserName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p> <b>" + ibps.getUnseenMessages() + "</b></div></div></div>";

                    } else if (activeInboxId == ibps.getInboxId()) {
                        chatlist = chatlist + "<div class='block active' onclick='getMessages(" + ibps.getInboxId() + ");seeMessage();'><div class='imgbox'><img src='profilePictures/" + otherUser.getProfilePicture() + "' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + otherUser.getUserName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p> </div></div></div>";
                    } else {
                        chatlist = chatlist + "<div class='block' onclick='getMessages(" + ibps.getInboxId() + ");seeMessage();'><div class='imgbox'><img src='profilePictures/" + otherUser.getProfilePicture() + "' alt='' class='cover'>";
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
                    try {
                        m.setMessage(aes.decrypt(m.getMessage(), m.getMessageKey()));
                    }
                    catch(Exception ex){
                        System.out.println("error occurred when getting last message" + ex.getMessage());
                    }
                    //if there are unseen messages
                    if (ibps.getUnseenMessages() > 0) {
                        chatlist = chatlist + "<div class='block unread' onclick='getMessages(" + ibps.getInboxId() + ");seeMessage();'><div class='imgbox'><img src='profilePictures/profile.jpg ' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + groupInbox.getGroupName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p> <b>" + ibps.getUnseenMessages() + "</b></div></div></div>";
                    } else if (activeInboxId == ibps.getInboxId()) {
                        chatlist = chatlist + "<div class='block unread' onclick='getMessages(" + ibps.getInboxId() + ");seeMessage();'><div class='imgbox'><img src='profilePictures/profile.jpg ' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + groupInbox.getGroupName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p></div></div></div>";
                    } else {
                        chatlist = chatlist + "<div class='block' onclick='getMessages(" + ibps.getInboxId() + ");seeMessage();'><div class='imgbox'><img src='profilePictures/profile.jpg ' alt='' class='cover'>";
                        chatlist = chatlist + "</div> <div class='details'> <div class='listhead'> <h4>" + groupInbox.getGroupName() + "</h4>       <p class='time'>" + m.getTimeSent().getHour() + ":" + m.getTimeSent().getMinute() + "</p></div> <div class='message-p'><p>" + m.getMessage() + "</p></div></div></div>";
                    }
                }
            }
        }
        response.getWriter().write(chatlist);
    }

    public void sendFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        int inboxId = Integer.parseInt(request.getParameter("inboxId"));
        String extension = request.getParameter("extension");
        Part file = request.getPart("file");
        //String currentTime = LocalDateTime.now().toString() + user.getUserId();
        String filteredFileName = generateFileName(user.getUserId(), extension);
       /* for (int i = 0; i < currentTime.length(); i++) {
            if (!(currentTime.charAt(i) + "").equalsIgnoreCase(":")) {
                filteredFileName = filteredFileName + currentTime.charAt(i);
            }
        }*/

        //filteredFileName = filteredFileName + "." + extension;
        InboxParticipantsDao ibpsDao = new InboxParticipantsDao("gossip");
        UsersDao usersDao = new UsersDao("gossip");
        MessageDao messageDao = new MessageDao("gossip");
        InboxDao inboxDao = new InboxDao("gossip");
        Inbox inbox = inboxDao.getInbox(inboxId);
        Aes aes = new Aes();
        int key = aes.generateKey();
        //if it's a normal chat
        try {
            if (inbox.getInboxType() == 1) {
                //if it's an image or video
                if (checkImage(extension)) {
                    boolean uploadState = uploadFile(file, filteredFileName, "imageMessages\\");
                    if (uploadState) {
                        filteredFileName = aes.encrypt(filteredFileName, key);
                        //send message
                        messageDao.sendMessage(inboxId, user.getUserId(), filteredFileName, 2, key);
                    }
                } else if (checkVideo(extension)) {
                    boolean uploadState = uploadFile(file, filteredFileName, "videoMessages\\");
                    if (uploadState) {
                        filteredFileName = aes.encrypt(filteredFileName, key);
                        messageDao.sendMessage(inboxId, user.getUserId(), filteredFileName, 3, key);
                    }
                } /*else {
                messageDao.sendMessage(inboxId, user.getUserId(), filteredFileName, 3);
            }*/
                // get the other person's InboxParticipant
                InboxParticipants ibp = ibpsDao.getOtherInboxParticipant(inboxId, user.getUserId());
                //update unseen messages for the other user
                ibpsDao.updateUnSeenMessages(inboxId, ibp.getUserId());
            } else {
                //if it's an image
                if (checkImage(extension)) {
                    boolean uploadState = uploadFile(file, filteredFileName, "imageMessages\\");
                    if (uploadState) {
                        filteredFileName = aes.encrypt(filteredFileName, key);
                        //send message
                        messageDao.sendMessage(inboxId, user.getUserId(), filteredFileName, 2, key);
                    }
                } //if it's a video
                else if (checkVideo(extension)) {
                    boolean uploadState = uploadFile(file, filteredFileName, "videoMessages\\");
                    if (uploadState) {
                        filteredFileName = aes.encrypt(filteredFileName, key);
                        messageDao.sendMessage(inboxId, user.getUserId(), filteredFileName, 3, key);
                    }
                }
            /*else {
                //send message
                messageDao.sendMessage(inboxId, user.getUserId(), filteredFileName, 3);
            }*/
                ArrayList<InboxParticipants> allIbps = ibpsDao.getAllInboxParticipants(inboxId);
                //add unseenMessages for all users in the groupChat
                for (InboxParticipants Ibps : allIbps) {
                    if (Ibps.getUserId() != user.getUserId()) {
                        ibpsDao.updateUnSeenMessages(inboxId, Ibps.getUserId());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("error occurred when sending file" + ex.getMessage());
            response.sendRedirect("Sorry error occurred while sending file");
        }

    }

    /**
     * Check if the extension is an image, returns true if it's an image and false for otherwise
     **/
    public boolean checkImage(String extension) {
        return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("gif");
    }

    /**
     * Check if the extension is a video, returns true if it's a video and false for otherwise
     **/
    public boolean checkVideo(String extension) {
        return extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("avi");
    }

    public boolean uploadFile(Part file, String fileName, String directory) {
        String fullPath = getServletContext().getRealPath("/");
        int index = fullPath.indexOf("target");
        String resultPath = fullPath.substring(0, index);

        try (InputStream inputStream = file.getInputStream();
             //FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\user\\OneDrive - Dundalk Institute of Technology\\d00243400\\Y3\\software project\\Gossip\\src\\main\\webapp\\" + fileName))) imageMessages\{
             //you need to change the location to match that where the webapp folder is stored on your computer, go to its properties and copy its location and paste it down here
             FileOutputStream outputStream = new FileOutputStream(resultPath + "src\\main\\webapp\\" + directory + fileName)) {
            FileOutputStream targetStream = new FileOutputStream( fullPath + directory + fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                targetStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File " + fileName + " has been uploaded successfully.");
        } catch (IOException e) {
            System.err.println("Error uploading the file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void sendReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        int reportedId = Integer.parseInt((String) session.getAttribute("reportedId"));
        String reportReason = request.getParameter("reportReason");
        ReportsDao reportsDao = new ReportsDao("gossip");
        reportsDao.addReport(user.getUserId(), reportedId, reportReason, LocalDateTime.now(), 1);
    }

    public String generateFileName(int id, String extension) {
        String currentTime = LocalDateTime.now().toString() + id;
        String filteredFileName = "";
        for (int i = 0; i < currentTime.length(); i++) {
            if (!(currentTime.charAt(i) + "").equalsIgnoreCase(":")) {
                filteredFileName = filteredFileName + currentTime.charAt(i);
            }
        }
        return filteredFileName + "." + extension;
    }

    public void changeProfilePicture(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        UsersDao usersDao = new UsersDao("gossip");
        Users user = (Users) session.getAttribute("user");
        String extension = request.getParameter("extension");
        Part file = request.getPart("file");
        if (user.getProfilePicture().equalsIgnoreCase("default.png")) {
            String filteredFileName = generateFileName(user.getUserId(), extension);
            boolean uploadState = uploadFile(file, filteredFileName, "profilePictures\\");
            if (uploadState == true) {
                setProfilePicture(user, filteredFileName);
                session.setAttribute("user", user);
            }
        } else {
            //then delete previous picture
            boolean deleteState = deleteImage("profilePictures\\", user.getProfilePicture());
            if (deleteState == true) {
                String filteredFileName = generateFileName(user.getUserId(), extension);
                boolean uploadState = uploadFile(file, filteredFileName, "profilePictures\\");
                if (uploadState == true) {
                    setProfilePicture(user, filteredFileName);
                    session.setAttribute("user", user);
                }
            }
        }
    }

    /**
     * updates the profile picture of the user that is signed in
     **/
    public void setProfilePicture(Users u, String imageName) {
        UsersDao usersDao = new UsersDao("gossip");
        u.setProfilePicture(imageName);
        usersDao.updateUser(u);
    }

    /**
     * deletes a profile picture or file
     **/
    public boolean deleteImage(String folder, String image) {
        File temp_file = new File(
                "C:\\Users\\user\\OneDrive - Dundalk Institute of Technology\\d00243400\\Y3\\software project\\Gossip\\src\\main\\webapp\\" + folder + image
        ); // Object of file class
        if (temp_file.delete()) {
            System.out.println(temp_file.getName() + " is successfully deleted");
            return true;
        } else {
            System.out.println("Failed to delete " + temp_file.getName() + " file");

        }
        return false;
    }
}

