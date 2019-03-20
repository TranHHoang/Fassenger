/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import app.MessageManagement;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import models.Message;

/**
 *
 * @author Kiruu
 */
public class ImageServlet extends HttpServlet {
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
        String context = request.getPathInfo().substring(1);
        String[] list = context.split("_");
        String date = String.format("%s-%s-%s-%s-%s-%s-%s", list[1], list[2], list[3], list[4], list[5], list[6], list[7]);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS");
        Date dates = null;

        try {
            dates = format.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(ImageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        DatabaseDao dao = null;
        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageManagement messageManagement = MessageManagement.getInstance(dao);
        Message image = messageManagement.getMessagesByDate(dates);

        InputStream is = image.getImageContent();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[1024];
        while ((read = is.read(data, 0, data.length)) != -1) {
            System.out.println("hello");
            buffer.write(data, 0, read);
        }

        buffer.flush();

        response.getOutputStream().write(buffer.toByteArray());
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
        InputStream image = getUploadImage(request);

        DatabaseDao dao = null;
        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());

        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageManagement messageManagement = MessageManagement.getInstance(dao);
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SSS");

        messageManagement.addMessage(new Message(session.getAttribute("userName").toString(), date, image, session.getAttribute("userName") + "_" + format.format(date)));
        response.getWriter().write(session.getAttribute("userName") + "_" + format.format(date));
    }

    private InputStream getUploadImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("uploadImage");
        return filePart.getInputStream();
    }
}
