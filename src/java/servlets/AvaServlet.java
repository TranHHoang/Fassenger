package servlets;

import app.UserManagement;
import app.exception.InternalException;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
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
            request.setAttribute("errorCode", 404);
            request.setAttribute("errorMessage", "User not found");
            request.getRequestDispatcher("/jsps/errorPage.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hello");
        HttpSession session = request.getSession();
        InputStream image = getUploadAvatar(request);

        DatabaseDao dao = null;
        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());
        } catch (InternalException ex) {
            throw ex;
        }
        UserManagement userManagement = new UserManagement(dao);
        
        User original = userManagement.getUserByName(session.getAttribute("userName").toString());
        User temp = new User(original.getName(), original.getNickname(), original.getPassword(), image);
        
        userManagement.editUserByName(temp);

        response.sendRedirect("./");
    }

    private InputStream getUploadAvatar(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("avatar");
        return filePart.getInputStream();
    }
    
}
