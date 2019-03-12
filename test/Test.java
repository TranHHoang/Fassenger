
import dao.DatabaseDao;
import dao.context.DBContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LEGION
 */
public class Test {

    public static void main(String[] args) {
        try {
            DatabaseDao dao = new DatabaseDao(new DBContext());
            ArrayList<Message > messages = dao.getMessagesBeforeDate(2,new java.util.Date("1/1/2020"));
            System.out.println(messages.size());
            for(int i = 0; i < messages.size() ; i ++){
                System.out.println(messages.get(i));
            }
            System.out.println("Sucess");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
