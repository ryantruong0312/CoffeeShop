/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

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
public class UserDAO {

    private static final String LOGIN = "SELECT userName, userPhone, userAddress, userEmail, userRoleId FROM tblUsers "
                                + " WHERE userId=? AND password=?";
    private static final String SEARCH = "SELECT userId, userName, userPhone, userAddress, userEmail, userRoleId FROM tblUsers "
                                + " WHERE userName like ?";
    private static final String DELETE = "DELETE FROM tblUsers WHERE userId=?";
    private static final String UPDATE = "UPDATE tblUsers SET userName=?, userRoleId=? WHERE userId=?";
    private static final String CHECK_DUPLICATE = "SELECT userId FROM tblUsers WHERE userId=?";
    private static final String INSERT = "INSERT INTO tblUsers(userId,userName,userRoleId,password) VALUES(?,?,?,?)";
    

    public UserDTO checkLogin(String userId, String password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, userId);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String userName = rs.getString("userName");
                    String userPhone = rs.getString("userPhone");
                    String userAddress = rs.getString("userAddress");
                    String userEmail = rs.getString("userEmail");
                    String userRoleId = rs.getString("userRoleId");
                    user = new UserDTO(userId, userName, userPhone, userAddress, userEmail, userRoleId, password);
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
        return user;
    }
    
    public List<UserDTO> getListUser(String search) throws SQLException{
        List<UserDTO> listUser = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm=conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while(rs.next()){
                    String userId = rs.getString("userId");
                    String userName = rs.getString("userName");
                    String userPhone = rs.getString("userPhone");
                    String userAddress = rs.getString("userAddress");
                    String userEmail = rs.getString("userEmail");
                    String userRoleId = rs.getString("userRoleId");
                    String password = "***";
                    listUser.add(new UserDTO(userId, userName, userPhone, userAddress, userEmail, userRoleId, password));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        return listUser;
    }
    
    public boolean delete(String userId) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1, userId);
                check = ptm.executeUpdate()>0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }
    
    public boolean update(UserDTO user) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if(conn!=null){
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, user.getUserName());
                ptm.setString(2, user.getUserRoleId());
                ptm.setString(3, user.getUserId());
                check = ptm.executeUpdate()>0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }

    public boolean checkDuplicate(String userId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setString(1, userId);
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
    
    public boolean insert(UserDTO user) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(INSERT);
            if(conn!=null){
                ptm.setString(1, user.getUserId());
                ptm.setString(2, user.getUserName());
                ptm.setString(3, user.getUserRoleId());
                ptm.setString(4, user.getPassword());
                check = ptm.executeUpdate()>0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return check;
    }
}
