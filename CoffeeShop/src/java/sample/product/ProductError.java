/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

/**
 *
 * @author tlmin
 */
public class ProductError {
    private String productId;
    private String error;

    public ProductError() {
        this.productId = "<br>";
        this.error = "";
    }

    public ProductError(String productId, String error) {
        this.productId = productId;
        this.error = error;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
}
