<!--navigation-->
<nav class="navbar bg-body-tertiary navbar-expand-lg fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="controller?action=index">
            <img src="img/logo2.png" alt="Logo" width="34" height="24" class="d-inline-block align-text-top">
            Gossip
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
                aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Menu</h5>
                <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>
            <div class="offcanvas-body">
                <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="controller?action=show_login">login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?action=show_register">register</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</nav>
