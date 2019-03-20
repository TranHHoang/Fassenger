package servlets;

import app.UserManagement;
import app.exception.RequestNotFoundException;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;

/**
 *
 * @author Kiruu
 */
public class AvaServlet extends HttpServlet {

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

        String userName = request.getRequestURL().toString().split("/")[5];

        DatabaseDao dao = null;

        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserManagement userManagement = new UserManagement(dao);

        InputStream is = null;
        User user = userManagement.getUserByName(userName);

        try {
            if (user != null) {
                if (user.getAvatar() == null) {
                    is = new BufferedInputStream(new FileInputStream(getServletContext().getRealPath("/defaultImage/ava.jpg")));
                } else {
                    is = user.getAvatar();
                }

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int read;
                byte[] data = new byte[1024];
                while ((read = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, read);
                }

                buffer.flush();

                response.getOutputStream().write(buffer.toByteArray());
            } else {
                throw new RequestNotFoundException("Request not found! User '" + userName + "' not existed");
            }
        } catch (RequestNotFoundException e) {
            request.setAttribute("errorCode", e.getErrorCode());
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/jsps/errorPage.jsp").forward(request, response);
        }
    }
}
