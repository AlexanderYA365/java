package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class ApplicationDao {
    private static final Logger logger = LogManager.getLogger();
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public ApplicationDao(EntityManagerFactory entityManagerFactory) {
        logger.info("create ApplicationDao");
        this.entityManagerFactory = entityManagerFactory;
    }

    public ApplicationDao() {
    }

    public boolean create(Application application) {
        logger.info("ApplicationDao.create(application)");
        logger.debug("ApplicationDao.create(application = {})", application);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(application);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public Application get(int id) {
        logger.info("ApplicationDao.get(id = {})", id);
        logger.debug("ApplicationDao.get(id = {})", id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        Application application = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.close();
        return application;
    }

    public Application get(Group group, int recipientId) {
        logger.info("ApplicationDao.get(group, recipientId)");
        logger.debug("ApplicationDao.get(group.id = {}, recipientId = {})", group.getGroupId(), recipientId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("applicantId"), group.getGroupId()),
                criteriaBuilder.equal(from.get("recipientId"), recipientId),
                criteriaBuilder.equal(from.get("applicationType"), 0)));
        Application application = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.close();
        return application;
    }

    public Application get(Relations relations) {
        logger.info("ApplicationDao.get(relations = {})", relations);
        logger.debug("ApplicationDao.get(relations = {})", relations);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("applicantId"), relations.getAccountId()),
                criteriaBuilder.equal(from.get("recipientId"), relations.getFriendId()),
                criteriaBuilder.equal(from.get("applicationType"), 1)));
        List<Application> application = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.close();
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> cr = cb.createQuery(Application.class);
        Root<Application> root = cr.from(Application.class);
        cr.select(root);
        List<Application> applications = entityManager.createQuery(cr).getResultList();
        entityManager.close();
        return applications;
    }

    public boolean update(Application application) {
        logger.info("ApplicationDao.update(application)");
        logger.debug("ApplicationDao.update(application = {})", application);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(application);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public boolean delete(Application application) {
        logger.info("ApplicationDao.delete(application)");
        logger.debug("ApplicationDao.delete(application = {})", application);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(application);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

}