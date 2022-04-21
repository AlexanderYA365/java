import java.util.List;

public class PhoneService {
    private final PhoneDao phoneDao;

    public PhoneService(){
        phoneDao = new PhoneDao();
    }

    public boolean create(Phone phone) {
        System.out.println("Create new Phone from PhoneService.create");
        boolean result = false;
        try {
            return phoneDao.create(phone);
        }catch (Exception ex){
            System.out.println("create exception - " + ex);
            return false;
        }
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

}