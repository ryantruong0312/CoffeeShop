/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.orders;

import java.sql.Date;

/**
 *
 * @author tlmin
 */
public class OrderDTO {
    private String orderId;
    private String userId;
    private Date orderDate;
    private int orderTotal;

    public OrderDTO() {
        this.orderId = "";
        this.userId = "";
        java.util.Date now = new java.util.Date(); 
        this.orderDate = new java.sql.Date(now.getTime());
        this.orderTotal = 0;
    }

    public OrderDTO(String orderId, String userId, Date orderDate, int orderTotal) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    
}
