public enum Role {
    USER(0),
    ADMIN(1);

    private int status;

    Role(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}