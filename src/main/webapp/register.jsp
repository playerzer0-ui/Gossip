<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Gossip | Register</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
    <link rel="stylesheet" href="css/form.css">
</head>
<body>
<!--navigation-->
<%@include file="navbar.jsp"%>

<main>
    <h1>Register</h1>
    <form action="controller" method="post">
        <label>username</label> <br>
        <input type="text" name="username" placeholder="enter username" required /><br>
        <label>email</label><br>
        <input type="text" name="email" placeholder="enter email" required /><br>
        <label>password</label><br>
        <input type="password" name="password" placeholder="enter password" onchange="checkPassword();check_strength();" required /><br>
        <span class="info">at least 8 characters long, one number and symbol</span> <br>
        <label>re-confirm password</label><br>
        <input type="password" placeholder="enter password again" onchange="checkPassword();check_strength();" required /><br>
        <span class="warning"></span>
        <br>
        <span class="warning"></span> <br>
        <label>date of birth</label><br>
        <input type="date" name="dateOfBirth" required /><br>

        <input type="hidden" name="action" value="do_register"/>
        <button id="theButton" type="submit">Register</button>
    </form>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="js/form.js" async defer></script>
</body>
</html>