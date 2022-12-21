package com.getjavajob.training.yakovleva.common.Enum;

public enum ApplicationType {
    GROUP(0),
    USER(1);

    private int status;

    ApplicationType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
