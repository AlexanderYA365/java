package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RelationsService {
    private static final Logger logger = LogManager.getLogger();
    private RelationsDao relationsDao;

    @Autowired
    public RelationsService(RelationsDao relationsDao) {
        logger.info("RelationsService(RelationsDao relationsDao)");
        this.relationsDao = relationsDao;
    }

    public RelationsService() {
    }

    public boolean create(Relations relations) {
        logger.info("create(Relations relations)");
        logger.debug("create(relations = {})", relations);
        return relationsDao.create(relations);
    }

    public List<Relations> getByAccountId(Relations relations) {
        logger.info("getByAccountId(Relations relations)");
        logger.debug("getByAccountId(relations = {})", relations);
        return relationsDao.getByAccountID(relations);
    }

    public Relations getByFriendId(Relations relations) {
        logger.info("getByFriendId(Relations relations)");
        logger.debug("getByFriendId(relations = {})", relations);
        return relationsDao.getByFriendId(relations);
    }

    public boolean update(Relations relations) {
        logger.info("update(Relations relations)");
        logger.debug("update(relations = {})", relations);
        return relationsDao.update(relations);
    }

    public boolean deleteByAccountId(Relations relations) {
        logger.info("deleteByAccountId(Relations relations)");
        logger.debug("deleteByAccountId(relations = {})", relations);
        return relationsDao.deleteByAccountId(relations);
    }

}
