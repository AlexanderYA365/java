import java.util.List;

public class ApplicationService {
    private ApplicationDao applicationDao;

    public ApplicationService() {
        applicationDao = new ApplicationDao();
    }

    public boolean create(Application application) {
        System.out.println("Creat new Application from ApplicationService.create");
        try {
            applicationDao.create(application);
            return true;
        } catch (Exception ex) {
            System.out.println("create Exception - " + ex);
        }
        return false;
    }

    public boolean update(Application application) {
        System.out.println("update new Application from ApplicationService.update");
        try {
            applicationDao.update(application);
            return true;
        } catch (Exception ex) {
            System.out.println("update Exception - " + ex);
        }
        return false;
    }

    public boolean delete(Application application) {
        System.out.println("delete new Application from ApplicationService.update");
        try {
            applicationDao.delete(application);
            return true;
        } catch (Exception ex) {
            System.out.println("delete Exception - " + ex);
        }
        return false;
    }

    public Application read(int idApplication) {
        System.out.println("Application read idApplication - " + idApplication);
        return applicationDao.read(idApplication);
    }

    public Application readGroupAccount(Group group, int idRecipient) {
        System.out.println("Application read idGroup - " + group + " ,idRecipient - " + idRecipient);
        return applicationDao.read(group, idRecipient);
    }

    public Application readAccount(Friend friend) {
        System.out.println("Application read friend - " + friend);
        return applicationDao.read(friend);
    }

    public List<Application> readAllApplication() {
        System.out.println("readAllApplication");
        return applicationDao.readApplications();
    }

}