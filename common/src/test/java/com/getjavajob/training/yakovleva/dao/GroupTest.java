package com.getjavajob.training.yakovleva.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GroupTest {

    @Test
    void getIdAccount() {
        Group group = new Group();
        group.setAccountId(1);
        Assert.assertEquals(1, group.getAccountId());
    }

    @Test
    void getGroupName() {
        Group group = new Group();
        group.setGroupName("test");
        Assert.assertEquals("test", group.getGroupName());
    }

    @Test
    void getLogo() {
        Group group = new Group();
        group.setLogo("test");
        Assert.assertEquals("test", group.getLogo());
    }

    @Test
    void getIdAdministrator() {
        Group group = new Group();
        group.setAdministratorId(1);
        Assert.assertEquals(1, group.getAdministratorId());
    }

    @Test
    void getIdGroup() {
        Group group = new Group();
        group.setGroupId(1);
        Assert.assertEquals(1, group.getGroupId());
    }

    @Test
    void setIdGroup() {
        Group group = new Group();
        group.setGroupId(1);
        Assert.assertEquals(1, group.getGroupId());
    }

    @Test
    void setIdAccount() {
        Group group = new Group();
        group.setAccountId(1);
        Assert.assertEquals(1, group.getAccountId());
    }

    @Test
    void setGroupName() {
        Group group = new Group();
        group.setGroupName("test");
        Assert.assertEquals("test", group.getGroupName());
    }

    @Test
    void setLogo() {
        Group group = new Group();
        group.setLogo("test");
        Assert.assertEquals("test", group.getLogo());
    }

    @Test
    void setIdAdministrator() {
        Group group = new Group();
        group.setAdministratorId(1);
        Assert.assertEquals(1, group.getAdministratorId());
    }

    @Test
    void testToString() {
        Group group = new Group();
        group.setLogo("test");
        Assert.assertEquals("Group{idGroup=0, accountId=0, groupName='null', logo='test', administrator_id=0}", group.toString());
    }
}