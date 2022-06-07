package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.*;

import java.util.List;

//@Service
public class ApplicationService {
    private ApplicationDao applicationDao;

    //@Autowired
    public ApplicationService(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    public boolean create(Application application) {
        System.out.println("Creat new Application from ApplicationService.create");
        try {
            return applicationDao.create(application);
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
            return applicationDao.delete(application);
        } catch (Exception ex) {
            System.out.println("delete Exception - " + ex);
        }
        return false;
    }

    public Application get(int idApplication) {
        System.out.println("Application read idApplication - " + idApplication);
        return applicationDao.get(idApplication);
    }

    public Application getGroupAccount(Group group, int recipientId) {
        System.out.println("Application read idGroup - " + group + " ,recipientId - " + recipientId);
        return applicationDao.get(group, recipientId);
    }

    public Application getAccount(Relations relations) {
        System.out.println("Application get relations - " + relations);
        return applicationDao.get(relations);
    }

    public List<Application> getAllApplication() {
        System.out.println("readAllApplication");
        return applicationDao.getApplications();
    }

}