<%-- 
    Document   : productDetail
    Created on : Jun 25, 2022, 4:38:34 PM
    Author     : tlmin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="sample.user.UserDTO"%>
<%@page import="sample.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
        <link rel="stylesheet" href="css/productDetail.css" type="text/css">
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
                    if(loginUser!=null && "AD".equals(loginUser.getUserRoleId())){
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
                    }else{
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
                String error = (String) request.getAttribute("ERROR");
                if (error == null) {
                    error = "";
                }
            %>
            <%= error%>
            
        </header>
        <main>
            <%
                ProductDTO product = (ProductDTO) request.getAttribute("PRODUCT_DETAIL");
            %>
            <div class="mainContainer">
            <div class="left">
                <img src="<%= product.getProductImg()%>">
            </div>
            <div class="right">
                <h1><%= product.getProductName()%></h1>
                <h3>Product ID: <%= product.getProductId()%></h3>
                <h3>Category: <%= product.getProductType()%></h3>
                <h3>Price: $<%= product.getProductPrice()%></h3>
                <form action="MainController">
                    <input type="number" name="orderQuantity" value="1" required=""/>
                    <input type="hidden" name="productId" value="<%= product.getProductId()%>"/>
                    <input type="hidden" name="productImg" value="<%= product.getProductImg()%>"/>
                    <input type="hidden" name="productName" value="<%= product.getProductName()%>"/>
                    <input type="hidden" name="productPrice" value="<%= product.getProductPrice()%>"/>
                    <input type="hidden" name="productType" value="<%= product.getProductType()%>"/>
                    <input type="submit" name="action" value="Add to Cart">
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
            </div>
            </div>
        </main>
    </body>
</html>
