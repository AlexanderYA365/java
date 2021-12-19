public class Group {
    private int idGroup;
    private int idAccount;
    private String groupName;
    private String logo;
    private int idAdministrator;

    public Group(){
    }

    public Group(String groupName, String logo, int idAdministrator, int idAccount){
        this.groupName = groupName;
        this.logo = logo;
        this.idAdministrator = idAdministrator;
        this.idAccount = idAccount;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getLogo() {
        return logo;
    }

    public int getIdAdministrator() {
        return idAdministrator;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setIdAdministrator(int idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idGroup=" + idGroup +
                ", idAccount=" + idAccount +
                ", groupName='" + groupName + '\'' +
                ", logo='" + logo + '\'' +
                ", idAdministrator=" + idAdministrator +
                '}';
    }
}