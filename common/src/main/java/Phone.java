public class Phone {
    private int idPhone;
    private String phoneNumber;
    private boolean sign;

    public void setIdPhone(int idPhone) {
        this.idPhone = idPhone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public int getIdPhone() {
        return idPhone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean getIsSign() {
        return sign;
    }
}
