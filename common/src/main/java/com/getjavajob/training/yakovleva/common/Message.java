package com.getjavajob.training.yakovleva.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "sender_id")
    private int senderId;
    @Column(name = "receiver_id")
    private int receiverId;
    @Column(name = "message")
    private String message;
    @Column(name = "picture")
    private String picture;
    @Column(name = "publication_date")
    private Date publicationDate;
    @Column(name = "edited")
    private boolean edited;
    @Transient
    private String usernameSender;
    @Transient
    private String usernameReceiving;
    @Column(name = "message_type")
    private MessageType messageType;
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, insertable = false, name = "receiver_id", referencedColumnName = "account_id")
    @JsonIgnore
    private Account account;

    public Message() {
    }

    public Message(int id, int senderId, int receiverId, String message, String picture, Date publicationDate,
                   boolean edited, MessageType messageType) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.picture = picture;
        this.publicationDate = publicationDate;
        this.edited = edited;
        this.usernameSender = usernameSender;
        this.usernameReceiving = usernameReceiving;
        this.messageType = messageType;
    }

//    public Message(int id, int senderId, int receiverId, String message, String picture, Date publicationDate,
//                   boolean edited, MessageType messageType, Account account) {
//        this.id = id;
//        this.senderId = senderId;
//        this.receiverId = receiverId;
//        this.message = message;
//        this.picture = picture;
//        this.publicationDate = publicationDate;
//        this.edited = edited;
//        this.usernameSender = usernameSender;
//        this.usernameReceiving = usernameReceiving;
//        this.messageType = messageType;
//        this.account = account;
//    }

//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }

    public int getMessageType() {
        return messageType.getStatus();
    }

    public void setMessageType(int status) {
        this.messageType = MessageType.values()[status];
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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