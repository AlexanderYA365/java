import java.util.List;

public class PhoneService {
    private final PhoneDao phoneDao;
    private final Pool connectionPool;

    public PhoneService(){
        phoneDao = PhoneDao.getInstance();
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(Phone phone) {
        System.out.println("Create new Phone from PhoneService.create");
        boolean result = false;
        try {
            result = phoneDao.create(phone);
            connectionPool.commit();
        }catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return result;
    }

    public List<Phone> read(int idPhone) {
        System.out.println("Read Phone from PhoneService.read");
        return phoneDao.read(idPhone);
    }

    public boolean update(Phone phone) {
        System.out.println("Update Phone from PhoneService.update");
        return phoneDao.update(phone);
    }

    public boolean delete(Phone phone) {
        System.out.println("Delete Phone from PhoneService.delete");
        return phoneDao.delete(phone);
    }

    public void closeService() {
        connectionPool.returnConnection();
    }

}