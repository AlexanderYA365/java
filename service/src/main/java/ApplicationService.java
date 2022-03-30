import java.util.List;

public class ApplicationService {

    public boolean create(Application application) {
        System.out.println("Creat new Application from ApplicationService.create");
        ApplicationDao applicationDao = new ApplicationDao();
        try {
            return applicationDao.create(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Application application) {
        ApplicationDao applicationDao = new ApplicationDao();
        return applicationDao.update(application);
    }

    public boolean delete(Application application) {
        ApplicationDao applicationDao = new ApplicationDao();
        return applicationDao.delete(application);
    }

    public Application read(int idApplication) {
        System.out.println("Application read idApplication - " + idApplication);
        ApplicationDao applicationDao = new ApplicationDao();
        return applicationDao.read(idApplication);
    }

    public Application readGroupAccount(Group group, int idRecipient) {
        System.out.println("Application read idGroup - " + group + " ,idRecipient - " + idRecipient);
        ApplicationDao applicationDao = new ApplicationDao();
        return applicationDao.read(group, idRecipient);
    }

    public Application readAccount(Friend friend) {
        System.out.println("Application read friend - " + friend);
        ApplicationDao applicationDao = new ApplicationDao();
        return applicationDao.read(friend);
    }

    public List<Application> readAllApplication() {
        System.out.println("create account dao");
        ApplicationDao applicationDao = new ApplicationDao();
        return applicationDao.readApplications();
    }

}