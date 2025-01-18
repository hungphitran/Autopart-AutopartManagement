<header class="header">
    <h1 class="logo">
        <img
            src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
            alt="home">
    </h1>

    <div class="search-form">
        <form action="/" method="get">
            <input type="text" placeholder="Search...">
            <button type="submit">
                <i class="fa fa-search"></i>
            </button>
        </form>
    </div>

    <ul class="btn-group">
        <li class="btn-item"><a class="nav-link active"
            aria-current="page" href="/autopart/login.htm">
                <button type="button" class="btn btn-warning">
                    <i class="fa-solid fa-circle-user"></i>
                        <%
                        if (session.getAttribute("user") != null) {
                        %>
                            <%=session.getAttribute("user").toString()%>
                        <%
                        } else {
                        %>
                            Account
                        <%
                        }
                        %>
                </button>
        </a></li>
        <li class="btn-item"><a href="#" class="nav-link active"
            aria-current="page" href="/autopart/login.htm">
                <button type="button" class="btn btn-warning">
                    <i class="fa fa-shopping-cart"></i> Cart
                </button>
        </a> </a></li>
    </ul>
</header>

<div class="header-menu">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-link">Home</a> <a class="nav-link">Brands<i
                        class="fa-solid fa-angle-down"></i></a> <a class="nav-link">Categories<i
                        class="fa-solid fa-angle-down"></i></a> <a class="nav-link">Features</a>
                    <a class="nav-link">Pricing</a> <a class="nav-link">Blog</a>

                </div>
            </div>
        </div>
    </nav>
</div>