/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import app.UserManagement;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            if (session.getAttribute("userName") != null) {
                RequestDispatcher view = request.getRequestDispatcher("jsps/chatPage.jsp");
                view.forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect("./"); // userName == null
        }
        response.sendRedirect("./"); // wrong username + password
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBContext dbContext = new DBContext();
        DatabaseDao dao = null;

        try {
            dao = new DatabaseDao(dbContext);
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserManagement userManagement = new UserManagement(dao);

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = userManagement.getUserByName(userName);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1 * 60);
            session.setAttribute("nickName", user.getNickname());
            session.setAttribute("userName", userName);
            response.sendRedirect("./room");
        } else {
            request.setAttribute("status", "FAILED");
            request.setAttribute("message", "Incorrect user name or password!");
            RequestDispatcher view = request.getRequestDispatcher("jsps/login.jsp");
            view.forward(request, response);
        }
    }
}
