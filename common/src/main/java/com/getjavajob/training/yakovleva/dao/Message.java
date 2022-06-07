package com.getjavajob.training.yakovleva.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Message implements Serializable {
    private int id;
    private int senderId;
    private int receiverId;
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

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
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
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", messageType=" + messageType.getStatus() +
                ", message='" + message + '\'' +
                ", picture='" + picture + '\'' +
                ", publicationDate=" + publicationDate +
                ", edited=" + edited +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id &&
                senderId == message1.senderId &&
                receiverId == message1.receiverId &&
                edited == message1.edited &&
                Objects.equals(message, message1.message) &&
                Objects.equals(picture, message1.picture) &&
                Objects.equals(publicationDate, message1.publicationDate) &&
                Objects.equals(usernameSender, message1.usernameSender) &&
                Objects.equals(usernameReceiving, message1.usernameReceiving) &&
                messageType == message1.messageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderId, receiverId, message, picture, publicationDate, edited, usernameSender, usernameReceiving, messageType);
    }

}