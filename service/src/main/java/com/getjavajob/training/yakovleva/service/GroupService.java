package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class GroupService {
    private GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public GroupService() {
    }

    public List<Group> getAccountGroups(Account account) {
        System.out.println("readGroup, account.getId() - " + account.getId());
        return groupDao.getGroupsAccount(account.getId());
    }

    public boolean createAccountGroups(Group group) {
        System.out.println("createAccountGroups");
        try {
            return groupDao.create(group);
        } catch (Exception ex) {
            System.out.println("createAccountGroups exception - " + ex);
        }
        return false;
    }

    public boolean update(Group group) {
        return groupDao.update(group);
    }

    public boolean delete(Group group) {
        return groupDao.delete(group);
    }

    public List<Group> getGroups() {
        System.out.println("readGroups");
        return groupDao.getGroups();
    }

    public List<Group> getGroupName(String groupName) {
        System.out.println("getGroupName groupName - " + groupName);
        return groupDao.getGroups(groupName);
    }

    public Group getGroupID(int idGroup) {
        System.out.println("getGroupID idGroup - " + idGroup);
        return groupDao.getGroup(idGroup);
    }

    public boolean insertAccountGroup(Group group, int accountId) {
        System.out.println("Group read idGroup - " + group + " , accountId - " + accountId);
        return groupDao.insertAccount(group, accountId);
    }

}