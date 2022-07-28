package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PhoneService {
    private final PhoneDao phoneDao;

    @Autowired
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
        System.out.println("Read Phone from PhoneService.get");
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