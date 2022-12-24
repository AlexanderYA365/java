package com.getjavajob.training.yakovleva.dao;

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
        logger.info("ApplicationDao.create(application = {})", application);
        entityManager.merge(application);
        return true;
    }

    public Application get(int id) {
        logger.info("ApplicationDao.get(id = {})", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public Application get(Group group, int recipientId) {
        logger.info("ApplicationDao.get(group.id = {}, recipientId = {})", group.getGroupId(), recipientId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("applicantId"), group.getGroupId()),
                criteriaBuilder.equal(from.get("recipientId"), recipientId),
                criteriaBuilder.equal(from.get("applicationType"), 0)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public Application get(Relations relations) {
        logger.info("ApplicationDao.get(relations = {})", relations);
        logger.info("ApplicationDao.get(relations = {})", relations);
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
            return new Application(0, ApplicationType.GROUP, 0, 0, ApplicationStatusType.REJECTED);
        }
    }

    public List<Application> getApplications() {
        logger.info("ApplicationDao.getApplications()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> cr = cb.createQuery(Application.class);
        Root<Application> root = cr.from(Application.class);
        cr.select(root);
        return entityManager.createQuery(cr).getResultList();
    }

    @Transactional
    public boolean update(Application application) {
        logger.info("ApplicationDao.update(application = {})", application);
        entityManager.merge(application);
        return true;
    }

    @Transactional
    public boolean delete(Application application) {
        logger.info("ApplicationDao.delete(application = {})", application);
        application = entityManager.find(Application.class, application.getId());
        entityManager.remove(application);
        return true;
    }

}