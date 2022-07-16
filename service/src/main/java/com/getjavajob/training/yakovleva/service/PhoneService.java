package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Phone;
import com.getjavajob.training.yakovleva.dao.PhoneDao;

import java.util.List;

//@Service
public class PhoneService {
    private final PhoneDao phoneDao;

    //@Autowired
    public PhoneService(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public boolean create(Phone phone) {
        System.out.println("Create new Phone from PhoneService.create");
        boolean result = false;
        try {
            return phoneDao.create(phone);
        } catch (Exception ex) {
            System.out.println("create exception - " + ex);
            return false;
        }
    }

    public List<Phone> get(int idPhone) {
        System.out.println("Read Phone from PhoneService.read");
        return phoneDao.get(idPhone);
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