package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class GroupDao {
    private SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public GroupDao(SessionFactory sessionFactory) {
        logger.info("GroupDao(sessionFactory)");
        this.sessionFactory = sessionFactory;
    }

    public GroupDao() {
    }

    public boolean create(Group group) {
        logger.info("GroupDao.create(group)");
        logger.debug("GroupDao.create(group = {})", group);
        sessionFactory.getCurrentSession().save(group);
        return true;
    }

    public Group getGroup(int group_id) {
        logger.info("GroupDao.getGroup(group)");
        logger.debug("GroupDao.getGroup(group_id = {})", group_id);
        return sessionFactory.getCurrentSession().get(Group.class, group_id);
    }

    public List<Group> getGroups(String groupName) {
        logger.info("GroupDao.getGroups(groupName)");
        logger.debug("GroupDao.getGroups(groupName = {})", groupName);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> selectGroupName = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("groupName"), groupName));
        return session.createQuery(selectGroupName).getResultList();
    }

    public List<Group> getGroups() {
        logger.info("GroupDao.getGroups()");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> root = cr.from(Group.class);
        cr.select(root);
        Query<Group> query = session.createQuery(cr);
        return query.getResultList();
    }

    public List<Group> getGroupsAccount(int account_id) {
        logger.info("GroupDao.getGroupsAccount(account_id)");
        logger.debug("GroupDao.getGroupsAccount(account_id = {})", account_id);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("accountId"), account_id));
        Query<Group> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public boolean update(Group group) {
        logger.info("GroupDao.update(group)");
        logger.debug("GroupDao.update(group = {})", group);
        return sessionFactory.getCurrentSession().merge(group) != null;
    }

    public boolean delete(Group group) {
        logger.info("GroupDao.delete(group)");
        logger.debug("GroupDao.delete(group = {})", group);
        Session session = sessionFactory.getCurrentSession();
        Group deleteGroup = session.find(Group.class, group.getGroupId());
        session.delete(deleteGroup);
        return true;
    }

    public boolean insertAccount(Group group, int accountId) {
        logger.info("GroupDao.delete(group)");
        logger.debug("GroupDao.delete(group = {}, accountId = {})", group, accountId);
        System.out.println("insertAccount, group - " + group + " ,accountId - " + accountId);
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(group_name, logo, administrator_id, account_id) " +
                "VALUES (?, ?, ?, ?)";
        System.out.println(sql);

//        Session session = sessionFactory.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
//        Root<Group> from = criteriaQuery.from(Group.class);
//        criteriaQuery.ins(from);
//        criteriaQuery.where(criteriaBuilder.equal(from.get("accountId"), account_id));
//        Query<Group> query = session.createQuery(criteriaQuery);
//
//        int result = jdbcTemplate.update(sql, group.getGroupName(), group.getLogo(),
//                group.getAdministratorId(), group.getAccountId());
//        return result > 0;
        return true;
    }

}