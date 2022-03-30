public class Application {
    private int id;
    private ApplicationType applicationType;
    private int idApplicant;
    private int idRecipient;
    private ApplicationStatusType status;

    public Application() {
    }

    public Application(int id, ApplicationType applicationType, int idApplicant, int idRecipient, ApplicationStatusType status) {
        this.id = id;
        this.applicationType = applicationType;
        this.idApplicant = idApplicant;
        this.idRecipient = idRecipient;
        this.status = status;
    }

    public int getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(int idRecipient) {
        this.idRecipient = idRecipient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationType() {
        return applicationType.getStatus();
    }

    public void setApplicationType(int status) {
        this.applicationType = ApplicationType.values()[status];
    }

    public int getIdApplicant() {
        return idApplicant;
    }

    public void setIdApplicant(int idApplicant) {
        this.idApplicant = idApplicant;
    }

    public int getStatus() {
        return status.getStatus();
    }

    public void setStatus(int status) {
        this.status = ApplicationStatusType.values()[status];
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicationType=" + applicationType.getStatus() +
                ", idApplicant=" + idApplicant +
                ", idRecipient=" + idRecipient +
                ", status=" + status.getStatus() +
                '}';
    }
}
