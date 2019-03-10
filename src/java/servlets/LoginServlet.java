/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
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
            Cookie cookies[] = request.getCookies();
            for (Cookie cooky : cookies) {
                if(cooky.getName().equals("isLogin")) {
                    if (cooky.getValue().equals("true")) {
                        RequestDispatcher view = request.getRequestDispatcher("jsps/chatPage.jsp");
                        view.forward(request, response);
                    }
                    else {
                        response.sendRedirect("./");
                    }
                    return;
                }
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
                String userName = request.getParameter("userName");
                String password = request.getParameter("password");
                if (userName.equals("admin") && password.equals("123")) {
                    response.addCookie(new Cookie("isLogin", "true"));
                    request.setAttribute("username", "admin");
                    RequestDispatcher view = request.getRequestDispatcher("jsps/chatPage.jsp");
                    view.forward(request, response);
                }
                else if (userName.equals("admin2") && password.equals("123")) {
                    response.addCookie(new Cookie("isLogin", "true"));
                    request.setAttribute("username", "admin2");
                    RequestDispatcher view = request.getRequestDispatcher("jsps/chatPage.jsp");
                    view.forward(request, response);
                }
                else {
                    response.addCookie(new Cookie("isLogin", "false"));
                    response.sendRedirect("./");
                }
        
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
