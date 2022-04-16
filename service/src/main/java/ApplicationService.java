import java.util.List;

public class ApplicationService {
    private ApplicationDao applicationDao;
    private Pool connectionPool;

    public ApplicationService(){
        applicationDao = ApplicationDao.getInstance();
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(Application application) {
        System.out.println("Creat new Application from ApplicationService.create");
        try {
            applicationDao.create(application);
            connectionPool.commit();
            return true;
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Application application) {
        System.out.println("update new Application from ApplicationService.update");
        try {
            applicationDao.update(application);
            connectionPool.commit();
            return true;
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Application application) {
        System.out.println("delete new Application from ApplicationService.update");
        try {
            applicationDao.delete(application);
            connectionPool.commit();
            return true;
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
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
        System.out.println("create account dao");
        return applicationDao.readApplications();
    }

    public void closeService() {
        connectionPool.returnConnection();
    }

}