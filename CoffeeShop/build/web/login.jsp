<%-- 
    Document   : login.jsp
    Created on : May 31, 2022, 10:47:54 AM
    Author     : tlmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="css/login.css" type="text/css">
    </head>
    <body>
        <div class="login">
            <a class="active"> sign in </a>
            <a class ="nonactive" href="createUser.jsp"> sign up </a>
            <form action="MainController" method="POST">
                <input type="text" class="text" name="userId" placeholder="User ID">
                <span>user id</span>
                <br>
                <input type="password" class="text" name="password" placeholder="Password">
                <span>password</span>
                <br>
                <div class="loginError" >
                <%
                    String error = (String) request.getAttribute("ERROR");
                    if (error == null) {
                        error = "<br>";
                    }
                %>
                <%= error%>
                </div>
                <input class="signin" type="submit" name="action" value="Sign in"/>
                <a class="return" href="home.jsp">return homepage</a>
            </form>
        </div>

    </body>
</html>

<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>

