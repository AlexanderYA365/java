package com.getjavajob.training.yakovleva.dao;

import java.util.Objects;

public class Application {
    private int id;
    private ApplicationType applicationType;
    private int applicantId;
    private int recipientId;
    private ApplicationStatusType status;

    public Application(int id, ApplicationType applicationType, int applicantId, int recipientId, ApplicationStatusType status) {
        this.id = id;
        this.applicationType = applicationType;
        this.applicantId = applicantId;
        this.recipientId = recipientId;
        this.status = status;
    }

    public Application() {

    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationType() {
        return applicationType.getStatus();
    }

    public void setApplicationType(int status) {
        this.applicationType = ApplicationType.values()[status];
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getStatus() {
        return status.getStatus();
    }

    public void setStatus(int status) {
        this.status = ApplicationStatusType.values()[status];
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicationType=" + applicationType.getStatus() +
                ", applicantId=" + applicantId +
                ", recipientId=" + recipientId +
                ", status=" + status.getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id &&
                applicantId == that.applicantId &&
                recipientId == that.recipientId &&
                applicationType == that.applicationType &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationType, applicantId, recipientId, status);
    }

}