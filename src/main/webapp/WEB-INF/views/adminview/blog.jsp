<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Shop Updates</title>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f4f4f4;
}

.wrapper {
    display: flex;
    padding-top: 120px;
    margin-left:200px ;
}

.container {
    height: 100vh;
    margin-left: 10px;
    padding: 10px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.container div {
    display: block;
}

.section {
    margin-bottom: 20px;
    display: none; /* Hide sections by default */
}

.section h2 {
    border-bottom: 2px solid #333;
    padding-bottom: 10px;
    color: #007BFF;
    cursor: pointer; /* Make the title clickable */
}

nav ul {
    list-style-type: none;
    padding: 0;
}

nav ul li {
    display: inline;
    margin-right: 10px;
}

nav ul li a {
    text-decoration: none;
    color: #007BFF;
    transition: color 0.3s;
}

nav ul li a:hover {
    color: #0056b3;
}

</style>
</head>
<body>
    <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
    <jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

    <div class="wrapper">
        <div class="container">
            <div>

                <div id="news" class="section">
                    <h2>News</h2>
                    <p>Latest news will be displayed here.</p>
                </div>
                <div id="notifications" class="section">
                    <h2>Notifications</h2>
                    <p>Latest notifications will be displayed here.</p>
                </div>
            </div>

        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Show the news section by default
            document.getElementById('news').style.display = 'block';

            // Add click event listeners to the section titles
            document.querySelectorAll('.section h2').forEach(function(title) {
                title.addEventListener('click', function() {
                    // Hide all sections
                    document.querySelectorAll('.section').forEach(function(section) {
                        section.style.display = 'none';
                    });
                    // Show the clicked section
                    this.parentElement.style.display = 'block';
                });
            });
        });
    </script>

</body>
</html>
