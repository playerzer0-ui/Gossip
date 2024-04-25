<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Gossip | home</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="icon" href="img/favicon.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="manifest" href="manifest.json">
    <link rel="apple-touch-icon" href="img/icons/apple-touch-icon.png">
    <meta name="apple-mobile-web-app-status-bar" content="#FFE1C4">
    <meta name="theme-color" content="#FFE1C4">
</head>
<body>
<!--navigation-->
<jsp:include page="navbar.jsp" />

<div class="wallpaper">
    <div class="row align-items-start">
        <div class="col-lg-6 d-flex align-content-end flex-wrap">
            <div class="title">
                <h1><b>Gossip</b></h1>
                <p>chat with the people you know, no one will know what you chatted</p>
            </div>
        </div>
        <div class="col-lg-6">
            <img src="img/phones.png" class="phones" alt="">
        </div>
    </div>
</div>

<header>
    <h2>Why use gossip?</h2>
</header>

<main class="container">
    <div class="container text-center">
        <div class="row">
            <div class="col-md-6 column-box">
                <img src="img/picture1.jpg" class="thumbnail">
                <p>No ads, trackers or anything, just gossip</p>
            </div>
            <div class="col-md-6 column-box">
                <img src="img/picture2.jpg" class="thumbnail">
                <p>End-to-end-encryption, just like real gossip, it would take lots of effort to understand them</p>
            </div>
        </div>
    </div>

    <div class="buffer">
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>I love gossiping</p>
            </blockquote>
            <figcaption class="blockquote-footer">
                Someone famous
            </figcaption>
        </figure>
        <figure class="text-center">
            <blockquote class="blockquote">
                <p>Can approve gossip is awesome</p>
            </blockquote>
            <figcaption class="blockquote-footer">
                Someone famous in <cite title="Source Title">somewhere</cite>
            </figcaption>
        </figure>
    </div>
</main>

<footer>
    <figure class="text-center">
        <blockquote class="blockquote">
            <h2><b>What are you waiting for? get <u>gossiping!</u></b></h2>
        </blockquote>
    </figure>
</footer>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="js/app.js"></script>
</body>
</html>