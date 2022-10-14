package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GroupDao {
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

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

    public List<Group> getAllGroups(String groupName) {
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

    public List<Group> getCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("AccountDao.getAccountsLimit(start, end, criteriaName)");
        logger.debug("AccountDao.getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})",
                start, end, criteriaName);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> from = cr.from(Group.class);
        cr.select(from);
        CriteriaQuery<Group> nameQuery = cr.select(from).where(cb.like(from.get("groupName"), criteriaName));
        Query<Group> query = session.createQuery(nameQuery).setFirstResult(start).setMaxResults(end);
        return query.getResultList();
    }

    public List<Group> getAllGroups() {
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
        logger.info("GroupDao.insertAccount(group = {}, accountId = {})", group, accountId);
        logger.debug("GroupDao.insertAccount(group = {}, accountId = {})", group, accountId);
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(group_name, logo, administrator_id, account_id) " +
                "VALUES (?, ?, ?, ?)";
        group.setAccountId(accountId);
        sessionFactory.getCurrentSession().save(group);
        return true;
    }

    public int getSizeRecords() {
        logger.info("getSizeRecords");
        Object result = sessionFactory.getCurrentSession().createCriteria(Group.class)
                .setProjection(Projections.rowCount()).uniqueResult();
        return Integer.parseInt(result.toString());
    }

    public long getSizeRecords(String search) {
        logger.info("GroupDao.getSizeRecords(search = {})", search);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Group> from = cq.from(Group.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.like(from.get("groupName"), search));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        return session.createQuery(cq).getSingleResult();
    }

}