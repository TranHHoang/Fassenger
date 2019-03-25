package servlets;

import app.MessageManagement;
import app.UserManagement;
import app.exception.InternalException;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import models.Message;
import models.User;
import static socket.ChatSocket.TYPE_MESSAGE;
import static socket.ChatSocket.TYPE_STATUS;

public class ChatServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DatabaseDao dao = null;
    MessageManagement messageManagement;
    ArrayList<Message> messageList;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            dao = DatabaseDao.getInstance(DBContext.getInstance());
        } catch (InternalException ex) {
            throw ex;
        }
        messageManagement = MessageManagement.getInstance(dao);
//        messageList = dao.getAllMessages();

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String date = session.getAttribute("lastMessageDate").toString();
            
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SS");
            Date dates = null;
            try {
                dates = format.parse(date);
            } catch (ParseException ex) {
                throw new InternalException(ex.getMessage());
            }
            ArrayList<Message> messageListTemp = messageManagement.getMessagesBeforeDate(2, dates);
            Collections.reverse(messageListTemp);
            response.setContentType("application/text");
            response.setCharacterEncoding("UTF-8");
            session.setAttribute("lastMessageDate", format.format(messageListTemp.get(messageListTemp.size() - 1).getDateCreated()));
            // check null
            response.getWriter()
                    .write(createMessageObjects(messageListTemp, 
                                                session.getAttribute("userName").toString()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public JsonArray createMessageObjects(ArrayList<Message> messages, String username) throws Exception {
        JsonArrayBuilder object = Json.createArrayBuilder();
        
        for (Message message : messages) {
            if (message.getImageContent() == null) {
                object.add(createMessageObj(message, message.getName().equals(username)));
            }
            else {
                object.add(createImageObj(message, message.getName().equals(username)));
            }
        }
        return object.build();
    }
    
    
    private JsonObject createMessageObj(Message msg, boolean isSender) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal2.setTime(msg.getDateCreated());
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        String datePattern = "hh:mm a";
        if (!sameDay) {
            datePattern = "dd/MM/yy 'at' " + datePattern;
        }

        return Json.createObjectBuilder()
                .add("type", TYPE_MESSAGE)
                .add("isSender", isSender)
                .add("user", msg.getName())
                .add("date", new SimpleDateFormat(datePattern).format(msg.getDateCreated()))
                .add("text", msg.getTextContent())
                .build();
    }

    private JsonObject createImageObj(Message msg, boolean isSender) throws Exception {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal2.setTime(msg.getDateCreated());
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        String datePattern = "hh:mm a";
        if (!sameDay) {
            datePattern = "dd/MM/yy 'at' " + datePattern;
        }

        return Json.createObjectBuilder()
                .add("type", TYPE_MESSAGE)
                .add("isSender", isSender)
                .add("user", msg.getName())
                .add("date", new SimpleDateFormat(datePattern).format(msg.getDateCreated()))
                .add("image", msg.getTextContent()) // images/user_18_3_2019_9_29_29_299
                .build();
    }

    
}
