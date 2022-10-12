<%-- 
    Document   : viewCart.jsp
    Created on : Jun 27, 2022, 10:57:43 PM
    Author     : tlmin
--%>

<%@page import="sample.user.UserDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sample.product.ProductDAO"%>
<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDTO"%>
<%@page import="sample.product.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <link rel="stylesheet" href="css/viewCart.css" type="text/css">
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
                <li><a class="nav">VIEW CART</a></li>
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
                if ("AD".equals(loginUser.getUserRoleId())) {
                    String emtpy = "";
            %>
            <div class="manageProduct">
                <a href="SearchProductController?search=<%=emtpy%>"> Manage Product </a>
            </div>
            <%
                }
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
            
            <h1>CART DETAIL</h1>
            <%
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
            %>
            
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th colspan="2">Quantity</th>
                        <th>Total</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = 1;
                        double total = 0;
                        for (ProductDTO product : cart.getCart().values()) {
                            total += product.getProductPrice() * product.getProductQuantity();
                    %>
                <form action="MainController">
                    <tr>
                        <td><%= count++%></td>
                        <td>
                            <%= product.getProductId()%>
                            <input type="hidden" name="productId" value="<%=product.getProductId()%>">
                        </td>
                        <td><%= product.getProductName()%></td>
                        <td><%= product.getProductPrice()%></td>
                        <td>
                            <input type="number" name="orderQuantity" value="<%=product.getProductQuantity()%>" required="">
                        </td>
                        <td>
                            <input type="submit" name="action" value="Update Order"/>
                        </td>
                        <td>$<%= product.getProductPrice() * product.getProductQuantity()%></td>
                        <td>
                            <input type="submit" name="action" value="Remove Order"/>
                        </td>
                        
                    </tr>
                </form>

                <%
                    }
                %>

                </tbody>
            </table>
                <h2 class="total">Total: $<%= total%></h2>
            <form action="MainController">
                <input class="checkout" type="submit" name="action" value="Check Out">
            </form>
            <%
                }else{
                %>
                <h3>No product in cart!!!</h3>
                <%
}
                String message = (String) request.getAttribute("MESSAGE");
                if (message == null) {
                    message = "";
                }
            %>
            <%= message%>
            
        </main>
    </body>
</html>
