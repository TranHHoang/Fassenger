
import dao.DatabaseDao;
import dao.context.DBContext;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("got : " + dao.getUserByName("admin").getPassword());
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
