package servlets;

import app.UserManagement;
import app.UserOnlineManagement;
import app.exception.InternalException;
import dao.DatabaseDao;
import dao.context.DBContext;
import servlets.encrypt.PasswordEncryptor;
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
        } catch (IOException | ServletException e) {
            response.sendRedirect("./"); // userName == null
        }
        response.sendRedirect("./"); // wrong username + password
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DatabaseDao dao = null;

        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());
        } catch (InternalException ex) {
            throw ex;
        }
        UserOnlineManagement userOnline = new UserOnlineManagement(dao);

        UserManagement userManagement = new UserManagement(dao);

        String userName = request.getParameter("userName");
        String password = PasswordEncryptor.getSHA(request.getParameter("password"));

        User user = userManagement.getUserByName(userName);

        if (user != null && user.getPassword().equals(password)) {
            if (!userOnline.isUserOnline(userName)) {
                HttpSession session = request.getSession();
                session.setAttribute("nickName", user.getNickname());
                session.setAttribute("userName", userName);
                userOnline.addOnlineUser(userName);

                response.sendRedirect("./room");
            } else {
                request.setAttribute("status", "FAILED");
                request.setAttribute("message", "This user has already logged in. You must logout before login again!");

                request.getRequestDispatcher("jsps/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("status", "FAILED");
            request.setAttribute("message", "Incorrect user name or password!");

            request.getRequestDispatcher("jsps/login.jsp").forward(request, response);
        }
    }
}
