/*package ajax;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(name = "GetMessages", value = "/getMessages")
public class GetMessages  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       int inboxId=Integer.parseInt(request.getParameter("inboxId"));
       response.getWriter().write("hello inbox is"+inboxId);
    }
}*/
