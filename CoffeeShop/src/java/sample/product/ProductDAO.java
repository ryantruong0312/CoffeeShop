/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author tlmin
 */
public class ProductDAO {

    private static final String GET_CATALOGUE = "SELECT productId, S2.typeName as productType, productName, productPrice, productQuantity, productImg "
            + "FROM tblProducts as S1, tblTypes as S2 WHERE S1.productTypeId = S2.typeId";
    private static final String SEARCH_PRODUCT = "SELECT productId, S2.typeName as productType, productName, productPrice, productQuantity, productImg "
            + "FROM tblProducts as S1, tblTypes as S2 WHERE S1.productName LIKE ? AND S1.productTypeId = S2.typeId";
    private static final String SEARCH_BY_ID = "SELECT productId, S2.typeName as productType, productName, productPrice, productQuantity, productImg "
            + "FROM tblProducts as S1, tblTypes as S2 WHERE S1.productId = ? AND S1.productTypeId = S2.typeId";
    private static final String DELETE_PRODUCT = "DELETE FROM tblProducts WHERE productId=?";
    private static final String UPDATE_PRODUCT = "UPDATE tblProducts SET productName=?, productPrice=?, productQuantity=? WHERE productId=?";
    private static final String UPDATE_QUANTITY = "UPDATE tblProducts SET productQuantity=? WHERE productId=?";
    private static final String GET_PRODUCT_QUANTITY = "SELECT productQuantity FROM tblProducts WHERE productId=?";
    private static final String CHECK_DUPLICATE = "SELECT productId FROM tblProducts WHERE productId=?";
    private static final String INSERT = "INSERT INTO tblProducts(productId,productTypeId,productName,productPrice,productQuantity,productImg) VALUES(?,?,?,?,?,?)";
    private static final String SEARCH_PRODUCT_TYPE_ID = "SELECT typeId FROM tblTypes WHERE typeName=?";

    public List<ProductDTO> getCatalogue() throws SQLException {
        List<ProductDTO> catalogue = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_CATALOGUE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String productType = rs.getString("productType");
                    String productName = rs.getString("productName");
                    double productPrice = rs.getDouble("productPrice");
                    int productQuantity = rs.getInt("productQuantity");
                    String productImg = rs.getString("productImg");
                    catalogue.add(new ProductDTO(productId, productType, productName, productPrice, productQuantity, productImg));
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
        return catalogue;
    }

    public ProductDTO getProductById(String productId) throws SQLException {
        ProductDTO product = new ProductDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_BY_ID);
                ptm.setString(1, productId);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    product.setProductId(productId);
                    product.setProductType(rs.getString("productType"));
                    product.setProductName(rs.getString("productName"));
                    product.setProductPrice(rs.getDouble("productPrice"));
                    product.setProductQuantity(rs.getInt("productQuantity"));
                    product.setProductImg(rs.getString("productImg"));
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
        return product;
    }

    public List<ProductDTO> getProductList(String search) throws SQLException {
        List<ProductDTO> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productId");
                    String productType = rs.getString("productType");
                    String productName = rs.getString("productName");
                    double productPrice = rs.getDouble("productPrice");
                    int productQuantity = rs.getInt("productQuantity");
                    String productImg = rs.getString("productImg");
                    productList.add(new ProductDTO(productId, productType, productName, productPrice, productQuantity, productImg));
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
        return productList;
    }

    public boolean deleteProduct(String productId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_PRODUCT);
                ptm.setString(1, productId);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean updateProduct(String productId, String productName, double productPrice, int productQuantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PRODUCT);
                ptm.setString(1, productName);
                ptm.setString(2, String.valueOf(productPrice));
                ptm.setString(3, String.valueOf(productQuantity));
                ptm.setString(4, productId);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean enoughQuantity(String productId, int orderQuantity) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        int curQuantity = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PRODUCT_QUANTITY);
                ptm.setString(1, productId);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    curQuantity = rs.getInt("productQuantity");
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
        return curQuantity > orderQuantity;
    }

    public boolean checkDuplicate(String productId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setString(1, productId);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
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

    public String getTypeId(String typeName) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        String typeId = "";
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(SEARCH_PRODUCT_TYPE_ID);
            if (conn != null) {
                ptm.setString(1, typeName);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    typeId = rs.getString("typeId");
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
        return typeId;
    }

    public boolean insert(ProductDTO product) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(INSERT);
            if (conn != null) {
                String productTypeId = getTypeId(product.getProductType());
                ptm.setString(1, product.getProductId());
                ptm.setString(2, productTypeId);
                ptm.setString(3, product.getProductName());
                ptm.setDouble(4, product.getProductPrice());
                ptm.setInt(5, product.getProductQuantity());
                ptm.setString(6, product.getProductImg());
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public int getCurQuantity(String productId) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        int curQuantity = 0;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(GET_PRODUCT_QUANTITY);
            if (conn != null) {
                ptm.setString(1, productId);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    curQuantity = rs.getInt("productQuantity");
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
        return curQuantity;
    }

    public boolean updateQuantity(String productId, int productQuantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            int curQuantity = this.getCurQuantity(productId);
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(UPDATE_QUANTITY);
            if (conn != null) {
                ptm.setInt(1, curQuantity - productQuantity);
                ptm.setString(2, productId);
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
