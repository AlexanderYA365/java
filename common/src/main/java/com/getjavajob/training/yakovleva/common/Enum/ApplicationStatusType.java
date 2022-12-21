package com.getjavajob.training.yakovleva.common.Enum;

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
