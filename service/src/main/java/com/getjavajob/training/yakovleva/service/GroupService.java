package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.dao.GroupDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class GroupService {
    private static final Logger logger = LogManager.getLogger();
    private GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        logger.info("GroupService(GroupDao groupDao)");
        this.groupDao = groupDao;
    }

    public GroupService() {
    }

    public List<Group> getAccountGroups(Account account) {
        logger.info("getAccountGroups(Account account)");
        logger.debug("getAccountGroups(account = {})", account);
        return groupDao.getGroupsAccount(account.getId());
    }

    public boolean createAccountGroups(Group group) {
        logger.info("createAccountGroups(Group group)");
        logger.debug("createAccountGroups(group = {})", group);
        try {
            return groupDao.create(group);
        } catch (Exception ex) {
            logger.error("createAccountGroups exception - {}", ex);
        }
        return false;
    }

    public boolean update(Group group) {
        logger.info("update(Group group)");
        logger.debug("update(group = {})", group);
        return groupDao.update(group);
    }

    public boolean delete(Group group) {
        logger.info("delete(Group group)");
        logger.debug("delete(group = {})", group);
        return groupDao.delete(group);
    }

    public List<Group> getCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("getCriteriaLimit(int start, int end, String criteriaName)");
        logger.debug("getCriteriaLimit(start = {}, end = {}, criteriaName = {})", start, end, criteriaName);
        return groupDao.getCriteriaLimit(start, end, criteriaName);
    }

    public List<Group> getAllGroups() {
        logger.info("getAllGroups()");
        logger.debug("getAllGroups()");
        return groupDao.getAllGroups();
    }

    public List<Group> getGroupName(String groupName) {
        logger.info("getGroupName(String groupName)");
        logger.debug("getGroupName(groupName = {})", groupName);
        return groupDao.getAllGroups(groupName);
    }

    public Group getGroupID(int idGroup) {
        logger.info("getGroupID(int idGroup)");
        logger.debug("getGroupID(idGroup = {})", idGroup);
        return groupDao.getGroup(idGroup);
    }

    public boolean insertAccountGroup(Group group, int accountId) {
        logger.info("insertAccountGroup(Group group, int accountId)");
        logger.debug("ginsertAccountGroup(group = {}, accountId = {})", group, accountId);
        return groupDao.insertAccount(group, accountId);
    }

}