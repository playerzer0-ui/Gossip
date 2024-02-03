<%@ page import="java.io.IOException" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03/02/2024
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    /* protected class GetMessages  extends HttpServlet {

         protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {*/
    int inboxId = Integer.parseInt(request.getParameter("inboxId"));
    response.getWriter().write("hello inbox is" );
    //out.print("hey");

    /*   }
    }*/
%>
</body>
</html>
