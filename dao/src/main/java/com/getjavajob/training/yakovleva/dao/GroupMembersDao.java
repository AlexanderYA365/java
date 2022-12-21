package com.getjavajob.training.yakovleva.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GroupMembersDao {
    private static final Logger logger = LogManager.getLogger(GroupMembersDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public GroupMembersDao() {
        logger.info("GroupMembersDao()");
    }

}
