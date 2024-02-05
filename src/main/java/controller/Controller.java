package controller;

import java.io.*;
import java.time.LocalDate;

import business.Users;
import daos.UsersDao;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {
    private String message;

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
            switch (action){
                case "index":
                    dest = "index.jsp";
                    response.sendRedirect(dest);
                    break;

                case "show_login":
                    dest = "login.jsp";
                    response.sendRedirect(dest);
                    break;
                case "do_login":
                    dest = Login(request,response);
                    response.sendRedirect(dest);
                    break;

                case "show_register":
                    dest = "register.jsp";
                    response.sendRedirect(dest);
                    break;

                case "chatbox":
                    dest = "chatbox.jsp";
                    response.sendRedirect(dest);
                    break;
                case "getMessages":
                    getMessages(request,response);
                    break;
            }
        }


    }

    public void destroy() {
    }

    public String Login (HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsersDao usersDao = new UsersDao("gossip");
        Users user = usersDao.Login(email,password);

        if(user != null){
            user.setPassword(password);
            session.setAttribute("user", user);
            return "chatbox.jsp";
        }
        else{
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

    public void getMessages(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int inboxId=Integer.parseInt(request.getParameter("inboxId"));
        //response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write("hello inbox is" + inboxId);
//        System.out.println(inboxId  + "inbox");
//        System.out.print("welcome");
    }
}