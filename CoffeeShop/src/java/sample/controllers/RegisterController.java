/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.user.UserError;

/**
 *
 * @author tlmin
 */
public class RegisterController extends HttpServlet {

    private static final String ERROR = "createUser.jsp";
    private static final String SUCCESS = "LoginController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        UserError userError = new UserError();
        boolean checkValidation = true;
        UserDAO dao = new UserDAO();
        try {
            String userId = request.getParameter("userId");
            String userName = request.getParameter("userName");
            String userRoleId = request.getParameter("userRoleId");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            if (userId.length() > 10 || userId.length() < 3) {
                userError.setUserId("UserID must from 3-10 characters!!");
                checkValidation = false;
            }
//           check duplicate userID
            boolean checkDuplicate = dao.checkDuplicate(userId);
            if (checkDuplicate) {
                userError.setUserId("This userID has been taken!!");
                checkValidation = false;
            }
//           check fullName range validation
            if (userName.length() > 50 || userName.length() < 5) {
                userError.setUserName("Username must from 6-50 characters!!");
                checkValidation = false;
            }
//           check confirm validation
            if (!confirm.equals(password)) {
                userError.setConfirm("Passwords do not match!!");
                checkValidation = false;
            }
            if (checkValidation) {
                UserDTO user = new UserDTO(userId, userName, "", "", "", userRoleId, password);
                boolean checkInsert = dao.insert(user);
                if (checkInsert) {
                    url = SUCCESS + "?userId=" + userId + "&password=" + password;
                } else {
                    userError.setError("Error detected!!");
                }
            } else {
                request.setAttribute("USER_ERROR", userError);
            }
        } catch (Exception e) {
            log("Error at CreateController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
