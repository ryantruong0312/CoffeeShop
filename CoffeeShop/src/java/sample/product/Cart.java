/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tlmin
 */
public class Cart {

    private Map<String, ProductDTO> cart;

    public Cart() {
    }

    public Cart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public boolean add(ProductDTO product) {
        boolean check = false;
        if (this.cart == null) {
            this.cart = new HashMap<>();
            this.cart.put(product.getProductId(), new ProductDTO());
        }
        if (this.cart.containsKey(product.getProductId())) {
            int currentQuantity = (this.cart.get(product.getProductId()).getProductQuantity());
            product.setProductQuantity(currentQuantity + product.getProductQuantity());
        }
        this.cart.put(product.getProductId(), product);
        check = true;
        return check;
    }
    public boolean remove(String productId){
        boolean check = false;
        if(this.cart!=null){
            if(this.cart.containsKey(productId)){
                this.cart.remove(productId);
                check = true;
            }
        }
        return check;
    }
    public boolean update(String productId, ProductDTO product){
        boolean check = false;
        if(this.cart!=null){
            if(this.cart.containsKey(productId)){
                this.cart.replace(productId, product);
                check = true;
            }
        }
        return check;
    }
}
