import java.io.Serializable;
import java.util.Date;

public class WallMassage implements Serializable {
    private static final long serialVersionUID = 2041275512219239990L;
    private int id;
    private int idSender;
    private int idReceiving;
    private String massage;
    private String picture;
    private Date publicationDate;
    private boolean edited;

    public WallMassage(){}

    public int getId() {
        return id;
    }

    public int getIdSender() {
        return idSender;
    }

    public int getIdReceiving() {
        return idReceiving;
    }

    public String getMassage() {
        return massage;
    }

    public String getPicture() {
        return picture;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public void setIdReceiving(int idReceiving) {
        this.idReceiving = idReceiving;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "WallMassage{" +
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
