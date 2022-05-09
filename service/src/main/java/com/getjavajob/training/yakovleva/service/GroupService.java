package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.dao.Group;
import com.getjavajob.training.yakovleva.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> readAccountGroups(Account account) {
        System.out.println("readGroup, account.getId() - " + account.getId());
        return groupDao.readGroupsAccount(account.getId());
    }

    public void createAccountGroups(Group group) {
        System.out.println("createAccountGroups");
        try {
            groupDao.create(group);
        } catch (Exception ex) {
            System.out.println("createAccountGroups exception - " + ex);
        }
    }

    public List<Group> readGroups() {
        System.out.println("readGroups");
        return groupDao.readGroups();
    }

    public List<Group> getGroupName(String groupName) {
        System.out.println("getGroupName groupName - " + groupName);
        return groupDao.read(groupName);
    }

    public Group readGroupID(int idGroup) {
        System.out.println("getGroupID idGroup - " + idGroup);
        return groupDao.read(idGroup);
    }

    public boolean insertAccountGroup(Group group, int idAccount) {
        System.out.println("Group read idGroup - " + group + " , idAccount - " + idAccount);
        try {
            return groupDao.insertAccount(group, idAccount);
        } catch (Exception ex) {
            System.out.println("createAccountGroups exception - " + ex);
            return false;
        }
    }

}