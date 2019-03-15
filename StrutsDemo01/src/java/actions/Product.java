package actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class Product extends ActionSupport {  
    
    private String idInput;
    private boolean hasError = false;
    private String errorMessage;
    
    public void setIdInput(String idInput)
    {
        this.idInput = idInput;
    }
    public String getIdInput()
    {
        return idInput;
    }
    
    public boolean getHasError()
    {
        return this.hasError;
    }
    
    public String getErrorMessage()
    {
        return this.errorMessage;
    }
    
    private int id;  
    private String name;  
    private float price;  
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public float getPrice() {  
        return price;  
    }  
    public void setPrice(float price) {  
        this.price = price;  
    } 
    
    public void validate() {
      if (name == null || name.trim().equals("")) {
         addFieldError("name","The name is required");
          addActionError("Invalid Data");
      }
      
      if (price < 0) {
         addFieldError("price","Price must be greater than zero");
      }
   }

    public String execute(){
        
        try{
            this.id = Integer.parseInt(idInput);
        }
        catch(Exception ex)
        {
            this.idInput = null;
            this.hasError = true;
            this.errorMessage = "Id phải là số tự nhiên";
            return ERROR;
        }
        
        if(this.getId() <= 0)
        {
            this.idInput = null;
            this.hasError = true;
            this.errorMessage = "Id must be greater than zero";
            return ERROR;
        }
        
        if(this.getId() == 1)
        {
            return "freemaker";
        }
        
        if(this.getId() == 2)
        {
            return "ok_01";
        }
        
        return SUCCESS;  
    }  
}  
