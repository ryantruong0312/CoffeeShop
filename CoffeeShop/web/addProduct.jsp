<%-- 
    Document   : addProduct.jsp
    Created on : Jul 3, 2022, 9:23:29 AM
    Author     : tlmin
--%>

<%@page import="sample.product.ProductError"%>
<%@page import="sample.user.UserDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="sample.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product Page</title>
        <link rel="stylesheet" href="css/addProduct.css" type="text/css">
        <link rel="stylesheet" href="css/header.css" type="text/css">
        <script src="https://kit.fontawesome.com/4ab9226add.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <header>
            <ul class="navigation">
                <li><a href="home.jsp" class="nav">HOME</a></li>
                <li class="menu"><a class="nav" href="SearchProductController?search=">MENU</a>
                    <div class="nav-menu">
                    </div>
                </li>
                <li class="contact"><span class="nav">CONTACT</span>
                    <ul class="nav-contact">
                        <li class="diachi">
                            <i class="fa-solid fa-location-dot"></i>
                            Store 1: 22 Nguyen Thi Minh Khai St. District 1  
                        </li>
                        <li class="diachi">
                            <i class="fa-solid fa-location-dot"></i>
                            Store 2:  1889 Pham The Hien St. District 8
                        </li>
                        <li class="diachi">
                            <i class="fa-solid fa-location-dot"></i>
                            Store 3: 36 Nguyen Huu Tho St. District 7
                        </li>
                        <li class="website">
                            <i class="fa-brands fa-internet-explorer"></i>
                            Website: <a href="">www.bunimocoffee.com</a>
                        </li>
                        <li class="facebook">
                            <i class="fa-brands fa-facebook-square"></i>
                            Facebook: <a href="b">www.facebook.com/bunimocoffee</a>
                        </li>
                        <li class="email">
                            <i class="fa-solid fa-envelope"></i>
                            Email: bunimocoffee@gmail.com
                        </li>
                        <li class="tel">
                            <i class="fa-solid fa-phone"></i>
                            Tel: 028.123.456
                        </li>
                    </ul>
                </li>
                <%
                    UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
                    if (loginUser != null && "AD".equals(loginUser.getUserRoleId())) {
                        String empty = "";
                %>
                <li class="inventory"><span class="nav">INVENTORY</span>
                    <ul class="nav-inventory">
                        <li>
                            <a href="SearchProductController?search=<%=empty%>">UPDATE PRODUCT</a>
                        </li>
                        <li>
                            <a href="addProduct.jsp">ADD PRODUCT</a>
                        </li>
                    </ul>
                </li>
                <%
                } else {
                %>
                <li><a class="nav" href="MainController?action=View Cart">VIEW CART</a></li>
                    <%
                        }
                    %>
            </ul>
            <%
                String search = request.getParameter("search");
                if (search == null) {
                    search = "";
                }
            %>
            <form class="searchbox" action="MainController">
                <input type="text" name="search"  placeholder="Keywords ..." value="<%=search%>" />
                <input type="submit" name="action" value="Search"/>
            </form>
            <%
                if (loginUser != null) {
            %>
            <div class="loginField">
                Hi, <a class="greet" href=""><%=loginUser.getUserName()%></a> |
                <a class="logout" href="MainController?action=Sign out">Sign out</a>
            </div>
            <%
            } else {
            %>
            <div class="loginField">
                <a class="login" href="login.jsp" >Sign in</a> |
                <a class="signup" href="createUser.jsp" >Sign up</a>
            </div>
            <%
                }
            %>
        </header>
        <main>
            <%
            ProductError productError = (ProductError) request.getAttribute("PRODUCT_ERROR");
            if (productError == null) {
                productError = new ProductError();
            }
            %>
            <h1>ADD PRODUCT</h1>
            <form class="add-form" action="MainController">
                Product ID:     <input type="text" name="productId" required=""/><br>
                <div class="error"><%= productError.getProductId()%></div>
                Image Link:     <input type="text" name="productImg" required=""/><br><br>
                Product Type:   <select name="productType" required="">
                    <%
                        for (String type : typeList) {
                    %>
                    <option value="<%=type%>"><%=type%></option>
                    <%
                        }
                    %>
                </select><br><br>
                Product Name:   <input type="text" name="productName" required=""/><br><br>
                Product Price:  <input type="number" step="0.01" name="productPrice" required=""/><br><br>
                Quantity:       <input type="number" name="productQuantity" required=""/><br><br>
                <div class="add-button">
                     <input type="submit" name="action" value="Add Product">
                </div>
            </form>
                <%
                String message = (String) request.getAttribute("MESSAGE");
                if (message == null) {
                    message = "";
                }
                %>
                <div class="message">
                     <%= message%>
                </div>
        </main>
    </body>
</html>
