<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21/02/2024
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="controller" method="post">
    <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">enter reason</label>
        <input type="text" name="reportReason"  class="form-control" id="exampleInputPassword1" />
    </div>
    <input type="hidden" name="action" value="send_report"/>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</body>
</html>
