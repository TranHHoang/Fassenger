package servlets;

import app.UserManagement;
import app.exception.InternalException;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import models.User;

public class ChatServlet extends HttpServlet {

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
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

        HttpSession session = request.getSession();
        try {
            String userName = session.getAttribute("userName").toString();
            if (userName != null) {
                RequestDispatcher view = request.getRequestDispatcher("jsps/chatPage.jsp");
                view.forward(request, response);
            }
        } catch (NullPointerException e) {
            response.sendRedirect("./");
        }
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
