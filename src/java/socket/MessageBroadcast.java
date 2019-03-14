/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import models.Message;

/**
 *
 * @author TranHoang
 */
public class MessageBroadcast {
    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_TYPING = "typing";
    public static final String TYPE_STATUS = "status";
    
    public static JsonObject createMessageObj(String receiverName, Message msg) {
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
                .add("showAvatar", receiverName.equals(msg.getName()))
                .add("user", msg.getName())
                .add("date", new SimpleDateFormat(datePattern).format(msg.getDateCreated()))
                .add("image", "")
                .add("text", msg.getTextContent())
                .build();
    }
    
    public static JsonObject createTypingObj(String senderName) {
        return Json.createObjectBuilder()
                .add("type", TYPE_TYPING)
                .add("user", senderName)
                .build();
    }
    
    public static JsonObject createStatusObj(String user, String status) {
        return Json.createObjectBuilder()
                .add("type", TYPE_STATUS)
                .add("user", user)
                .add("status", status)
                .build();
    }
}
