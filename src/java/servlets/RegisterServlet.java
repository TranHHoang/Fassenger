package servlets;

import app.UserManagement;
import dao.DatabaseDao;
import dao.context.DBContext;
import encryptor.PasswordEncryptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String userName = request.getParameter("userName");
        String password = PasswordEncryptor.getSHA(request.getParameter("password"));

        UserManagement userManagement = new UserManagement(dao);

        userManagement.addUser(new User(userName, userName, password, null));

        request.setAttribute("status", "SUCCESS");
        request.setAttribute("message", "Register successful! You can now sign in.");
        RequestDispatcher view = request.getRequestDispatcher("jsps/login.jsp");
        view.forward(request, response);
    }
}
