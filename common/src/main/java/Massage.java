import java.io.Serializable;
import java.util.Date;

public class Massage  implements Serializable {
    private static final long serialVersionUID = 2041275512219239992L;
    private int id;
    private int idSender;
    private int idReceiving;
    private String massage;
    private String picture;
    private Date publicationDate;
    private boolean edited;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiving() {
        return idReceiving;
    }

    public void setIdReceiving(int idReceiving) {
        this.idReceiving = idReceiving;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {
        return "Massage{" +
                "id=" + id +
                ", idSender=" + idSender +
                ", idReceiving=" + idReceiving +
                ", massage='" + massage + '\'' +
                ", picture='" + picture + '\'' +
                ", publicationDate=" + publicationDate +
                ", edited=" + edited +
                '}';
    }
}
