package com.getjavajob.training.yakovleva.dao;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 2041275512219239992L;
    private int id;
    private int idSender;
    private int idReceiving;
    private String message;
    private String picture;
    private Date publicationDate;
    private boolean edited;
    private String usernameSender;
    private String usernameReceiving;
    private MessageType messageType;

    public int getMessageType() {
        return messageType.getStatus();
    }

    public void setMessageType(int status) {
        this.messageType = MessageType.values()[status];
    }

    public String getUsernameReceiving() {
        return usernameReceiving;
    }

    public void setUsernameReceiving(String usernameReceiving) {
        this.usernameReceiving = usernameReceiving;
    }

    public String getUsernameSender() {
        return usernameSender;
    }

    public void setUsernameSender(String usernameSender) {
        this.usernameSender = usernameSender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiving() {
        return idReceiving;
    }

    public void setIdReceiving(int idReceiving) {
        this.idReceiving = idReceiving;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {//TODO как нормально выводить тип сообщений?
        return "Message{" +
                "id=" + id +
                ", idSender=" + idSender +
                ", idReceiving=" + idReceiving +
                ", messageType=" + messageType.getStatus() +
                ", message='" + message + '\'' +
                ", picture='" + picture + '\'' +
                ", publicationDate=" + publicationDate +
                ", edited=" + edited +
                '}';
    }
}
