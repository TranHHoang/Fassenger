package servlets;

import app.UserManagement;
import app.exception.InternalException;
import dao.DatabaseDao;
import dao.context.DBContext;
import servlets.encrypt.PasswordEncryptor;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;

/**
 *
 * @author Kiruu
 */
public class RegisterServlet extends HttpServlet {

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

        DatabaseDao dao = null;
        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());
        } catch (InternalException ex) {
            throw ex;
        }

        UserManagement userManagement = new UserManagement(dao);

        String userName = request.getParameter("userName");

        if (userManagement.getUserByName(userName) != null) {
            request.setAttribute("status", "FAILED");
            request.setAttribute("message", "This username is already taken!");

            RequestDispatcher view = request.getRequestDispatcher("jsps/login.jsp");
            view.forward(request, response);
        } else {
            String password = PasswordEncryptor.getSHA(request.getParameter("password"));
            String rePassword = PasswordEncryptor.getSHA(request.getParameter("repassword"));

            if (password.equals(rePassword)) {
                userManagement.addUser(new User(userName, userName, password, null));

                request.setAttribute("status", "SUCCESS");
                request.setAttribute("message", "Register successful! You can now sign in.");

                RequestDispatcher view = request.getRequestDispatcher("jsps/login.jsp");
                view.forward(request, response);
            } else {
                request.setAttribute("status", "FAILED");
                request.setAttribute("message", "Passwords are not matched!");

                RequestDispatcher view = request.getRequestDispatcher("jsps/login.jsp");
                view.forward(request, response);
            }
        }
    }
}
