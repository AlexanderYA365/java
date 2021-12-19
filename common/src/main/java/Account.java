import java.util.Date;

public class Account {
    private int id;
    private String name;
    private String surname;
    private String lastName;
    private Date date;
    private String phone;
    private int icq;
    private String addressHome;
    private String addressJob;
    private String email;
    private String aboutMe;

    public Account(){
    }

    public Account(String name, String surname, String lastName, Date date, String phone,
                   int icq, String addressHome, String addressJob, String email, String aboutMe){
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.date = date;
        this.phone = phone;
        this.icq = icq;
        this.addressHome = addressHome;
        this.addressJob = addressJob;
        this.email = email;
        this.aboutMe = aboutMe;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public int getIcq() {
        return icq;
    }

    public String getAddressHome() {
        return addressHome;
    }

    public String getAddressJob() {
        return addressJob;
    }

    public String getEmail() {
        return email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIcq(int icq) {
        this.icq = icq;
    }

    public void setAddressHome(String addressHome) {
        this.addressHome = addressHome;
    }

    public void setAddressJob(String addressJob) {
        this.addressJob = addressJob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                ", phone='" + phone + '\'' +
                ", icq=" + icq +
                ", addressHome='" + addressHome + '\'' +
                ", addressJob='" + addressJob + '\'' +
                ", email='" + email + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }
}