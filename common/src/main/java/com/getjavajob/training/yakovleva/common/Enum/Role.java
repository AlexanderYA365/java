package com.getjavajob.training.yakovleva.common.Enum;

public enum Role {
    ROLE_USER(0),
    ROLE_ADMIN(1);

    private int status;

    Role(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
