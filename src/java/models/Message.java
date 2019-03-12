/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author LEGION
 */
public class Message {
    private String name;
    private Date dateCreated;
    private byte[] imageContent;
    private String textContent;

    public Message() {
    }

    public Message(String name, Date dateCreated, byte[] imageContent, String textContent) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.imageContent = imageContent;
        this.textContent = textContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Message{" + "name=" + name + ", dateCreated=" + dateCreated + ", imageContent=" + imageContent + ", textContent=" + textContent + '}';
    }
    
}
