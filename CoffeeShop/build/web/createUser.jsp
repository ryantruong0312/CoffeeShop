<%-- 
    Document   : createUser
    Created on : Jun 16, 2022, 9:11:00 AM
    Author     : tlmin
--%>

<%@page import="sample.user.UserError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
        <link rel="stylesheet" href="css/createUser.css" type="text/css">
    </head>
    <body>
        <%
            UserError userError = (UserError) request.getAttribute("USER_ERROR");
            if (userError == null) {
                userError = new UserError();
            }
        %>
        <div class="signup">
            <a class="nonactive" href="login.jsp"> sign in </a>
            <a class="active"> sign up </a>
            <form action="MainController" method="POST">
                <input type="text" class="text" name="userName" required="" placeholder="Enter your name"/>
                <br>
                <div class="error"><%= userError.getUserName()%></div>
                <span>full name</span>
                <br>
                <input type="text" class="text" name="userId" required="" placeholder="Choose a user ID"/>
                <br>
                <div class="error"><%= userError.getUserId()%></div>
                <span>user id</span>
                <br>
                <input type="text" class="text" name="userRoleId" value="US" readonly=""/>
                <br>
                <span class="noError">role id</span><br>
                <input type="password" class="text" name="password" placeholder="Enter password"><br>
                <span class="noError">password</span>
                <br>
                <input type="password" class="text" name="confirm" placeholder="Re-enter password">
                <br>
                <div class="error"><%= userError.getConfirm()%></div>
                <span>confirm password</span>
                <input class="register" type="submit" name="action" value="Register"/>
                <a class="return" href="home.jsp">return homepage</a>
            </form>
        </div>
    </body>
</html>
