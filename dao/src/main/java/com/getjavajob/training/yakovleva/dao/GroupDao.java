package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class GroupDao {
    private JdbcTemplate jdbcTemplate;
    private SessionFactory sessionFactory;

    @Autowired
    public GroupDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GroupDao() {
    }

    public boolean create(Group group) {
        sessionFactory.getCurrentSession().save(group);
        return true;
    }

    public Group getGroup(int group_id) {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE group_id = ?";
        System.out.println(sql);
        return sessionFactory.getCurrentSession().get(Group.class, group_id);
    }

    public List<Group> getGroups(String groupName) {
        System.out.println("Group read(String groupName)");
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE group_name = ?";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> selectEmail = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("groupName"), groupName));
        return session.createQuery(selectEmail).getResultList();
    }

    public List<Group> getGroups() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> root = cr.from(Group.class);
        cr.select(root);
        Query<Group> query = session.createQuery(cr);
        List<Group> results = query.getResultList();
        return results;
    }

    public List<Group> getGroupsAccount(int account_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("accountId"), account_id));
        Query<Group> query = session.createQuery(criteriaQuery);
        List<Group> results = query.getResultList();
        return results;
    }

    public boolean update(Group group) {
        return sessionFactory.getCurrentSession().merge(group) != null;
    }

    public boolean delete(Group group) {
        Session session = sessionFactory.getCurrentSession();
        Group deleteGroup = session.find(Group.class, group.getGroupId());
        session.delete(deleteGroup);
        return true;
    }

    public boolean insertAccount(Group group, int accountId) {
        System.out.println("insertAccount, group - " + group + " ,accountId - " + accountId);
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(group_name, logo, administrator_id, account_id) " +
                "VALUES (?, ?, ?, ?)";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, group.getGroupName(), group.getLogo(),
                group.getAdministratorId(), group.getAccountId());
        return result > 0;
    }

    private Group fillGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setGroupId(resultSet.getInt(1));
        group.setGroupName(resultSet.getString(2));
        group.setLogo(resultSet.getString(3));
        group.setAdministratorId(resultSet.getInt(4));
        group.setAccountId(resultSet.getInt(5));
        return group;
    }

}