/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import app.UserManagement;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
public class ChatServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();
        try {
            String userName = session.getAttribute("userName").toString();
            if (userName != null) {
                RequestDispatcher view = request.getRequestDispatcher("jsps/chatPage.jsp");
                view.forward(request, response);
            }
        } catch (Exception e) {
            //userName not found
            System.out.println(e);
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
            byte[] image = uploadFile(request);
    
            DBContext dbContext = new DBContext();
            DatabaseDao dao = null;       
            try {
                dao = new DatabaseDao(dbContext);
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            UserManagement userManagement = new UserManagement(dao);
            User original = userManagement.getUserByName(session.getAttribute("userName").toString());
            User temp = new User(original.getName(), original.getNickName(), original.getPassword(), image);
            userManagement.editUserByName(temp);
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    
    
    private byte[] uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        ArrayList<Byte> tempImage = new ArrayList<>();
        try {
            Part filePart = request.getPart("avatar");
            fileName = (String) getFileName(filePart);
            InputStream fileContent = filePart.getInputStream();

            int data = fileContent.read();
            while (data != -1) {
                data = fileContent.read();
                tempImage.add((byte) data);
            }
            fileContent.close();

        } catch (Exception e) {
            fileName = "";
            System.out.println(e);
        }
        
        byte [] temp = new byte[tempImage.size()];
        for (int i = 0; i < tempImage.size(); i++) {
            temp[i] = tempImage.get(i);
        }
        
        return temp;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
