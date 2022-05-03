package com.getjavajob.training.yakovleva.dao;

public enum ApplicationStatusType {
    ACCEPTED(0),
    CONFIRMATION(1),
    REJECTED(2);

    private int status;

    ApplicationStatusType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
