package controller;

import java.io.*;

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
                    break;

                case "show_login":
                    dest = "login.jsp";
                    break;
                case "do_login":
                    dest = Login(request,response);
                    break;

                case "show_register":
                    dest = "register.jsp";
                    break;

                case "chatbox":
                    dest = "chatbox.jsp";
                    break;
            }
        }

        response.sendRedirect(dest);
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
}