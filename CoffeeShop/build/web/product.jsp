<%-- 
    Document   : product
    Created on : Jun 23, 2022, 10:15:04 AM
    Author     : tlmin
--%>

<%@page import="sample.product.ProductDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sample.user.UserDTO"%>
<%@page import="java.util.List"%>
<%@page import="sample.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Product Page</title>
        <link rel="stylesheet" href="css/product.css" type="text/css">
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
                List<ProductDTO> listProduct = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");
                if (listProduct != null && listProduct.size() > 0) {
                    if (loginUser != null && "AD".equals(loginUser.getUserRoleId())) {
                        
            %>
            <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th></th>
                                <th>Product ID</th>
                                <th>Type</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th colspan="2">Edit Product</th>
                            </tr>
                        </thead>
                        <tbody>            
            <%int count = 1;
                        for (ProductDTO product : listProduct) {
                            
            %>  
                    
                            <tr>
                                <form action="MainController">
                                    <td><%= count++%></td>
                                    <td><img src="<%= product.getProductImg()%>"></td>
                                    <td><%= product.getProductId()%></td>
                                    <td><%= product.getProductType()%></td>
                                    <td><input type="text" name="productName" value="<%= product.getProductName()%>" required="" /></td>
                                    <td><input type="number" step="0.01" name="productPrice" value="<%= product.getProductPrice()%>" required="" /></td>
                                    <td><input type="number" name="productQuantity" value="<%= product.getProductQuantity()%>" required="" /></td>
                                    <!-- Update function-->
                                    <td>
                                        <input type="hidden" name="productId" value="<%= product.getProductId()%>"/>
                                        <input type="hidden" name="search" value="<%= search%>"/>
                                        <input type="submit" name="action" value="Update"/>
                                    </td>
                                    <!-- Delete function-->
                                    <td><a class="del-button" href="MainController?action=Delete&productId=<%= product.getProductId()%>&search=<%= search%>">Delete</a></td>
                                
                                    </form>
                            </tr>
                        <%
                        }
                        %> 
                                
                        </tbody>
                    </table>              
                <%
                        }
                    else {
                        for (ProductDTO product : listProduct) {
                %>
                        <div class="product-base">
                            <div class="product-detail">
                                <img src="<%= product.getProductImg()%>">
                                <h2><a href="MainController?action=Detail&productId=<%= product.getProductId()%>"><%= product.getProductName()%></a></h2>
                                <h3>$<%= product.getProductPrice()%></h3>
                                <input type="hidden" name="productName" value="<%= product.getProductName()%>"/>
                                <input type="hidden" name="productPrice" value="<%= product.getProductPrice()%>"/>
                                <input type="hidden" name="productType" value="<%= product.getProductType()%>"/>
                            </div>
                        </div>
                <%
                        }
                    }
                }
        String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%=error%>

    </main>
</body>
</html>
