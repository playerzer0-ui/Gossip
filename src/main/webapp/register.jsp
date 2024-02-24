<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Gossip | Register</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="css/sign.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
</head>

<body>
<!--navigation-->
<jsp:include page="navbar.jsp" />

<main class="wallpaper">
    <div class="left">
        <h1><b>Register</b></h1>
        <form action="controller" method="post">
            <div class="mb-3">
                <label for="exampleInputEmail2" class="form-label">username</label>
                <input type="text" name="username" class="form-control" id="exampleInputEmail2" aria-describedby="userhelp"  required/>
                <div id="userhelp" class="form-text">
                    make sure your username is as unique as possible, pray it is not taken by someone
                </div>
            </div>
            <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" name="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" required />
                <div id="emailHelp" class="form-text">
                    your email will be kept secret
                </div>
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Password</label>
                <input type="password" onchange="checkPassword();check_strength();" name="password" class="form-control" id="exampleInputPassword1" required />
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword2" class="form-label">Confirm Password</label>
                <input type="password" onchange="checkPassword();check_strength();" name="password1" class="form-control" id="exampleInputPassword2" required />
                <div id="passwordHelp1" class="form-text">
                    <span class="warning"></span>
                    <br>
                    <span class="warning"></span>
                </div>
            </div>
            <div class="mb-3">
                <label for="exampleInputdoB1" class="form-label">Date of Birth</label>
                <input type="date" name="dateOfBirth" class="form-control" id="exampleInputdoB1" required />
            </div>
            <input type="hidden" name="action" value="do_register"/>
            <button id="theButton" type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
    <div class="right"></div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="js/form.js" async defer></script>
</body>

</html>