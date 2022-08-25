package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.dao.PhoneDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PhoneService {
    private static final Logger logger = LogManager.getLogger();
    private PhoneDao phoneDao;

    @Autowired
    public PhoneService(PhoneDao phoneDao) {
        logger.info("MessageService(PhoneDao phoneDao)");
        this.phoneDao = phoneDao;
    }

    public PhoneService() {
    }

    public boolean create(Phone phone) {
        logger.info("create(Phone phone)");
        logger.debug("create(phone = {})", phone);
        try {
            return phoneDao.create(phone);
        } catch (Exception ex) {
            logger.error("create exception - {}", ex);
            return false;
        }
    }

    public List<Phone> get(int idPhone) {
        logger.info("get(int idPhone)");
        logger.debug("get(idPhone = {})", idPhone);
        return phoneDao.get(idPhone);
    }

    public boolean update(Phone phone) {
        logger.info("update(Phone phone)");
        logger.debug("update(phone = {})", phone);
        return phoneDao.update(phone);
    }

    public boolean delete(Phone phone) {
        logger.info("delete(Phone phone)");
        logger.debug("delete(phone = {})", phone);
        return phoneDao.delete(phone);
    }

}