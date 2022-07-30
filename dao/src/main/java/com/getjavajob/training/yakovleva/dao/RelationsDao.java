package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RelationsDao {
    private SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    public RelationsDao(SessionFactory sessionFactory) {
        logger.info("RelationsDao(sessionFactory)");
        this.sessionFactory = sessionFactory;
    }

    public RelationsDao() {
    }

    public boolean create(Relations relations) {
        logger.info("RelationsDao.create(relations)");
        logger.debug("RelationsDao.create(relations = {})", relations);
        sessionFactory.getCurrentSession().save(relations);
        return true;
    }

    public List<Relations> getByAccountID(Relations relations) {
        logger.info("RelationsDao.getByAccountID(relations)");
        logger.debug("RelationsDao.getByAccountID(relations = {})", relations);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        CriteriaQuery<Relations> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        return session.createQuery(nameQuery).getResultList();
    }

    public Relations getByFriendId(Relations relations) {
        logger.info("RelationsDao.getByFriendId(relations)");
        logger.debug("RelationsDao.getByFriendId(relations = {})", relations);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        conditions.add(criteriaBuilder.equal(from.get("friendId"), relations.getFriendId()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        TypedQuery<Relations> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    public boolean update(Relations relations) {
        logger.info("RelationsDao.update(relations)");
        logger.debug("RelationsDao.update(relations = {})", relations);
        return sessionFactory.getCurrentSession().save(relations) != null;
    }

    public boolean deleteByAccountId(Relations relations) {
        System.out.println("RelationsDao.deleteByAccountId()");
        sessionFactory.getCurrentSession().delete(relations);
        return true;
    }

}