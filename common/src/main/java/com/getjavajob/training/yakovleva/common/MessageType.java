package com.getjavajob.training.yakovleva.common;

public enum MessageType {
    WALL(0),
    PRIVATE(1),
    GROUP(2);

    private int status;

    MessageType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
