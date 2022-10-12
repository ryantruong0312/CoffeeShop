/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

/**
 *
 * @author tlmin
 */
public class UserError {

    private String userId;
    private String userName;
    private String userRoleId;
    private String password;
    private String confirm;
    private String error;

    public UserError(String userId, String userName, String userRoleId, String password, String confirm, String error) {
        this.userId = userId;
        this.userName = userName;
        this.userRoleId = userRoleId;
        this.password = password;
        this.confirm = confirm;
        this.error = error;
    }

    public UserError() {
        this.userId = "<br>";
        this.userName = "<br>";
        this.userRoleId = "";
        this.password = "";
        this.confirm = "<br>";
        this.error = "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setRoleID(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
