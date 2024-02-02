package controller;

import java.io.*;
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

        if (action == null) {
            action = "dashboard";
        }

        response.sendRedirect(dest);
    }

    public void destroy() {
    }
}