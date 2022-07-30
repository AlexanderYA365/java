package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RelationsService {
    private RelationsDao relationsDao;

    @Autowired
    public RelationsService(RelationsDao relationsDao) {
        this.relationsDao = relationsDao;
    }

    public RelationsService() {
    }

    public boolean create(Relations relations) {
        System.out.println("RelationsService.create()");
        return relationsDao.create(relations);
    }

    public List<Relations> getByAccountId(Relations relations) {
        System.out.println("RelationsService.getByAccountId()");
        return relationsDao.getByAccountID(relations);
    }

    public Relations getByFriendId(Relations relations) {
        System.out.println("RelationsService.getByAccountId()");
        return relationsDao.getByFriendId(relations);
    }

    public boolean update(Relations relations) {
        System.out.println("RelationsService.update()");
        return relationsDao.update(relations);
    }

    public boolean deleteByAccountId(Relations relations) {
        System.out.println("RelationsService.deleteByAccountId()");
        return relationsDao.deleteByAccountId(relations);
    }

}
