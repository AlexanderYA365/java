package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.Enum.ApplicationType;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicationDao {
    private static final Logger logger = LogManager.getLogger(ApplicationDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public ApplicationDao() {
        logger.info("create ApplicationDao");
    }

    @Transactional
    public boolean create(Application application) {
        logger.info("create(application = {})", application);
        entityManager.merge(application);
        return true;
    }

    public Application get(int id) {
        logger.info("get(id = {})", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public Application get(int applicantId, int recipientId) {
        logger.info("get(applicantId = {}, recipientId = {})", applicantId, recipientId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(from.get("recipientId"), recipientId),
                criteriaBuilder.equal(from.get("applicantId"), applicantId)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Application> getRejectedUsers(Account account) {
        logger.info("getRejectedUsers(account = {})", account);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("recipientId"), account.getId()),
                criteriaBuilder.equal(from.get("status"), ApplicationStatusType.REJECTED),
                criteriaBuilder.equal(from.get("applicationType"), ApplicationType.USER)));
        List<Application> application = entityManager.createQuery(criteriaQuery).getResultList();
        if (application.size() != 0) {
            return application;
        } else {
            logger.info("application == null");
            Application empty = new Application(0, ApplicationType.USER, 0, 0, ApplicationStatusType.ACCEPTED);
            List<Application> emptyList = new ArrayList<>();
            emptyList.add(empty);
            return emptyList;
        }
    }

    public List<Application> get(Account account) {
        logger.info("get(account = {})", account);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("recipientId"), account.getId()),
                criteriaBuilder.equal(from.get("status"), 1),
                criteriaBuilder.equal(from.get("applicationType"), 1)));
        List<Application> application = entityManager.createQuery(criteriaQuery).getResultList();
        if (application.size() != 0) {
            return application;
        } else {
            logger.info("application == null");
            Application empty = new Application(0, ApplicationType.USER, 0, 0, ApplicationStatusType.ACCEPTED);
            List<Application> emptyList = new ArrayList<>();
            emptyList.add(empty);
            return emptyList;
        }
    }

    public Application get(Group group, int recipientId) {
        logger.info("get(group.id = {}, recipientId = {})", group.getGroupId(), recipientId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("applicantId"), group.getGroupId()),
                criteriaBuilder.equal(from.get("recipientId"), recipientId),
                criteriaBuilder.equal(from.get("applicationType"), 0)));
        List<Application> application = entityManager.createQuery(criteriaQuery).getResultList();
        if (application.size() != 0) {
            logger.info("application = {}", application);
            return application.get(0);
        } else {
            logger.info("application == null");
            return new Application(0, ApplicationType.GROUP, 0, 0, ApplicationStatusType.REJECTED);
        }
    }

    public Application get(Relations relations) {
        logger.info("get(relations = {})", relations);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("applicantId"), relations.getAccountId()),
                criteriaBuilder.equal(from.get("recipientId"), relations.getFriendId()),
                criteriaBuilder.equal(from.get("applicationType"), 1)));
        List<Application> application = entityManager.createQuery(criteriaQuery).getResultList();
        if (application.size() >= 1) {
            logger.info("application.size() = {}", application.size());
            return application.get(0);
        } else {
            logger.info("application.size() =>else = {}", application.size());
            return new Application(0, ApplicationType.USER, 0, 0, ApplicationStatusType.REJECTED);
        }
    }

    public List<Application> getApplications() {
        logger.info("getApplications()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> cr = cb.createQuery(Application.class);
        Root<Application> root = cr.from(Application.class);
        cr.select(root);
        return entityManager.createQuery(cr).getResultList();
    }

    @Transactional
    public boolean update(Application application) {
        logger.info("update(application = {})", application);
        entityManager.merge(application);
        return true;
    }

    @Transactional
    public boolean delete(Application application) {
        logger.info("delete(application = {})", application);
        application = entityManager.find(Application.class, application.getId());
        entityManager.remove(application);
        return true;
    }

}