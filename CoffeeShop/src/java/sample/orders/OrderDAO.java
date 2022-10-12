/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.orders;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sample.product.Cart;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.utils.DBUtils;

/**
 *
 * @author tlmin
 */
public class OrderDAO {

    private static final String CREATE_ORDER = "INSERT INTO tblOrders (orderId, userId, orderDate, orderTotal) VALUES (?,?,?,?)";
    private static final String CREATE_ORDER_DETAIL = "INSERT INTO tblOrderDetails (detailId, orderId, productId, productPrice, orderQuantity) VALUES (?,?,?,?,?)";
    private static final String GET_ORDER_ID_LIST = "SELECT orderId FROM tblOrders";
    private static final String GET_ORDER_DETAIL_ID_LIST = "SELECT detailId FROM tblOrderDetails";

    public String generateOrderId() throws SQLException {
        List<String> orderIdList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String newOrderId;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_ID_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderId = rs.getString("orderId");
                    orderIdList.add(orderId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        int order = orderIdList.size() + 1;
        for (int i = 0; i < orderIdList.size(); i++) {
            int curOrder = (Integer.parseInt(orderIdList.get(i).substring(1)));
            if (curOrder == order) {
                order += 1;
            }
        }
        newOrderId = "O" + String.format("%02d", order);
        return newOrderId;
    }

    public String generateOrderDetailId() throws SQLException {
        List<String> detailIdList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String newDetailId;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_DETAIL_ID_LIST);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String detailId = rs.getString("detailId");
                    detailIdList.add(detailId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        int detail = detailIdList.size() + 1;
        for (int i = 0; i < detailIdList.size(); i++) {
            int curDetail = (Integer.parseInt(detailIdList.get(i).substring(1)));
            if (curDetail == detail) {
                detail += 1;
            }
        }
        newDetailId = "D" + String.format("%02d", detail);
        return newDetailId;
    }

    public boolean createOrder(Cart cart, String orderId, String userId) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                double total = 0;
                for (ProductDTO product : cart.getCart().values()) {
                    total += product.getProductPrice() * product.getProductQuantity();
                }
                Date now = Date.valueOf(LocalDate.MAX);
                ptm = conn.prepareStatement(CREATE_ORDER);
                ptm.setString(1, orderId);
                ptm.setString(2, userId);
                ptm.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                ptm.setDouble(4, total);
                check = ptm.executeUpdate() > 0;
                if (check) {
                    for (String productId : cart.getCart().keySet()) {
                        if (check) {
                            ProductDAO dao = new ProductDAO();
                            ProductDTO product = cart.getCart().get(productId);
                            String detailId = generateOrderDetailId();
                            ptm = conn.prepareStatement(CREATE_ORDER_DETAIL);
                            ptm.setString(1, detailId);
                            ptm.setString(2, orderId);
                            ptm.setString(3, productId);
                            ptm.setDouble(4, product.getProductPrice());
                            ptm.setInt(5, product.getProductQuantity());
                            check = ptm.executeUpdate() > 0;
                            if (check) {
                                dao.updateQuantity(productId, product.getProductQuantity());
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
}
