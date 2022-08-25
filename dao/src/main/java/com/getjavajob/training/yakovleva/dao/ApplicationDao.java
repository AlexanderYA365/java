package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class ApplicationDao {
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    public ApplicationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ApplicationDao() {
    }

    public boolean create(Application application) {
        logger.info("ApplicationDao.create(application)");
        logger.debug("ApplicationDao.create(application = {})", application);
        sessionFactory.getCurrentSession().save(application);
        return true;
    }

    public Application get(int id) {
        logger.info("ApplicationDao.get(id)");
        logger.debug("ApplicationDao.get(id = {})", id);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        Query<Application> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    public Application get(Group group, int recipientId) {
        logger.info("ApplicationDao.get(group, recipientId)");
        logger.debug("ApplicationDao.get(group.id = {}, recipientId = {})", group.getGroupId(), recipientId);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> from = criteriaQuery.from(Application.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("applicantId"), group.getGroupId()),
                criteriaBuilder.equal(from.get("recipientId"), recipientId),
                criteriaBuilder.equal(from.get("applicationType"), 0)));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    public Application get(Relations relations) {
        logger.info("ApplicationDao.get(relations)");
        logger.info("ApplicationDao.get(relations)");
        logger.debug("ApplicationDao.get(relations = {})", relations);
//        Session session = sessionFactory.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
//        Root<Application> from = criteriaQuery.from(Application.class);
//        criteriaQuery.select(from);
//        criteriaQuery.where(criteriaBuilder.and(
//                criteriaBuilder.equal(from.get("applicantId"), relations.getAccountId()),
//                criteriaBuilder.equal(from.get("recipientId"), relations.getFriendId())));
//                criteriaBuilder.equal(from.get("applicationType"), 1)));
//        System.out.println(session.createQuery(criteriaQuery).getSingleResult());
//        return session.createQuery(criteriaQuery).getSingleResult();
        Application application = new Application();
        application.setApplicantId(1);
        application.setRecipientId(1);
        application.setId(1);
        application.setStatus(0);
        return application;
    }

    public List<Application> getApplications() {
        logger.info("ApplicationDao.getApplications()");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Application> cr = cb.createQuery(Application.class);
        Root<Application> root = cr.from(Application.class);
        cr.select(root);
        Query<Application> query = session.createQuery(cr);
        return query.getResultList();
    }

    public boolean update(Application application) {
        logger.info("ApplicationDao.update(application)");
        logger.debug("ApplicationDao.update(application = {})", application);
        return sessionFactory.getCurrentSession().merge(application) != null;
    }

    public boolean delete(Application application) {
        logger.info("ApplicationDao.delete(application)");
        logger.debug("ApplicationDao.delete(application = {})", application);
        sessionFactory.getCurrentSession().delete(application);
        return true;
    }

}