public class Friend {
    private int idFriends;
    private int idAccount;
    private int idFriendsAccount;

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdFriendsAccount() {
        return idFriendsAccount;
    }

    public void setIdFriendsAccount(int idFriendsAccount) {
        this.idFriendsAccount = idFriendsAccount;
    }

    public int getIdFriends() {
        return idFriends;
    }

    public void setIdFriends(int idFriends) {
        this.idFriends = idFriends;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "idFriends=" + idFriends +
                ", idAccount=" + idAccount +
                ", idFriendsAccount=" + idFriendsAccount +
                '}';
    }

}