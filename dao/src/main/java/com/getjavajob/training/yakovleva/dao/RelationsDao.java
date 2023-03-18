package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationsDao {
    private static final Logger logger = LogManager.getLogger(RelationsDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public RelationsDao() {
        logger.info("RelationsDao(sessionFactory)");
    }

    @Transactional
    public boolean create(Relations relations) {
        logger.info("RelationsDao.create(relations = {})", relations);
        entityManager.merge(relations);
        return true;
    }

    public List<Relations> getAll() {
        logger.info("getAll()");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        CriteriaQuery<Relations> nameQuery = criteriaQuery.select(from);
        return entityManager.createQuery(nameQuery).getResultList();
    }

    public List<Relations> getByAccountID(Relations relations) {
        logger.info("getByAccountID(relations = {})", relations);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        CriteriaQuery<Relations> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        return entityManager.createQuery(nameQuery).getResultList();
    }

    public Relations getByFriendId(Relations relations) {
        logger.info("RelationsDao.getByFriendId(relations = {})", relations);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        conditions.add(criteriaBuilder.equal(from.get("friendId"), relations.getFriendId()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Transactional
    public boolean update(Relations relations) {
        logger.info("RelationsDao.update(relations = {})", relations);
        entityManager.getTransaction().begin();
        entityManager.merge(relations);
        return true;
    }

    @Transactional
    public boolean deleteByAccountId(Relations relations) {
        logger.info("RelationsDao.deleteByAccountId(relations = {})", relations);
        entityManager.remove(relations);
        return true;
    }

}