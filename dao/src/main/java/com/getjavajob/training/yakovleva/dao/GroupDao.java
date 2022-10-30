package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public GroupDao(EntityManagerFactory entityManagerFactory) {
        logger.info("GroupDao(sessionFactory)");
        this.entityManagerFactory = entityManagerFactory;
    }

    public GroupDao() {
    }

    public boolean create(Group group) {
        logger.info("GroupDao.create(group)");
        logger.debug("GroupDao.create(group = {})", group);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(group);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public Group getGroup(int groupId) {
        logger.info("GroupDao.getGroup(group)");
        logger.debug("GroupDao.getGroup(group_id = {})", groupId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("groupId"), groupId));
        Group group = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.close();
        return group;
    }

    public List<Group> getAllGroups(String groupName) {
        logger.info("GroupDao.getGroups(groupName)");
        logger.debug("GroupDao.getGroups(groupName = {})", groupName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> selectGroupName = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("groupName"), groupName));
        List<Group> groups = entityManager.createQuery(selectGroupName).getResultList();
        entityManager.close();
        return groups;
    }

    public List<Group> getCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("AccountDao.getAccountsLimit(start, end, criteriaName)");
        logger.debug("AccountDao.getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})",
                start, end, criteriaName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> from = cr.from(Group.class);
        cr.select(from);
        CriteriaQuery<Group> nameQuery = cr.select(from).where(cb.like(from.get("groupName"), criteriaName));
        List<Group> groups = entityManager.createQuery(nameQuery).setFirstResult(start).setMaxResults(end).getResultList();
        entityManager.close();
        return groups;
    }

    public List<Group> getAllGroups() {
        logger.info("GroupDao.getGroups()");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> root = cr.from(Group.class);
        cr.select(root);
        List<Group> groups = entityManager.createQuery(cr).getResultList();
        entityManager.close();
        return groups;
    }

    public List<Group> getGroupsAccount(int account_id) {
        logger.info("GroupDao.getGroupsAccount(account_id)");
        logger.debug("GroupDao.getGroupsAccount(account_id = {})", account_id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("accountId"), account_id));
        List<Group> groups = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.close();
        return groups;
    }

    public boolean update(Group group) {
        logger.info("GroupDao.update(group)");
        logger.debug("GroupDao.update(group = {})", group);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(group);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public boolean delete(Group group) {
        logger.info("GroupDao.delete(group)");
        logger.debug("GroupDao.delete(group = {})", group);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(group);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public boolean insertAccount(Group group, int accountId) {
        logger.info("GroupDao.insertAccount(group = {}, accountId = {})", group, accountId);
        logger.debug("GroupDao.insertAccount(group = {}, accountId = {})", group, accountId);
        group.setAccountId(accountId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(group);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Group> from = cq.from(Group.class);
        cq.select(cb.count(from));
        long size = entityManager.createQuery(cq).getSingleResult();
        entityManager.close();
        return size;
    }

    public long getSizeRecords(String search) {
        logger.info("GroupDao.getSizeRecords(search = {})", search);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Group> from = cq.from(Group.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.like(from.get("groupName"), search));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        long size = entityManager.createQuery(cq).getSingleResult();
        entityManager.close();
        return size;
    }

}