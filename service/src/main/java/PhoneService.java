import java.util.List;

public class PhoneService {

    public boolean create(Phone phone) {
        System.out.println("Create new Phone from PhoneService.create");
        PhoneDao phoneDao = new PhoneDao();
        return phoneDao.create(phone);
    }

    public List<Phone> read(int idPhone) {
        System.out.println("Read Phone from PhoneService.read");
        PhoneDao phoneDao = new PhoneDao();
        return phoneDao.read(idPhone);
    }

    public boolean update(Phone phone) {
        System.out.println("Update Phone from PhoneService.update");
        PhoneDao phoneDao = new PhoneDao();
        return phoneDao.update(phone);
    }

    public boolean delete(Phone phone) {
        System.out.println("Delete Phone from PhoneService.delete");
        PhoneDao phoneDao = new PhoneDao();
        return phoneDao.delete(phone);
    }

}