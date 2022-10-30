package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class RelationsDao {
    private static final Logger logger = LogManager.getLogger();
    private EntityManagerFactory entityManagerFactory;

    public RelationsDao(EntityManagerFactory entityManagerFactory) {
        logger.info("RelationsDao(sessionFactory)");
        this.entityManagerFactory = entityManagerFactory;
    }

    public RelationsDao() {
    }

    public boolean create(Relations relations) {
        logger.info("RelationsDao.create(relations)");
        logger.debug("RelationsDao.create(relations = {})", relations);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(relations);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public List<Relations> getByAccountID(Relations relations) {
        logger.info("RelationsDao.getByAccountID(relations)");
        logger.debug("RelationsDao.getByAccountID(relations = {})", relations);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        CriteriaQuery<Relations> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        List<Relations> relationsAccount = entityManager.createQuery(nameQuery).getResultList();
        entityManager.close();
        return relationsAccount;
    }

    public Relations getByFriendId(Relations relations) {
        logger.info("RelationsDao.getByFriendId(relations)");
        logger.debug("RelationsDao.getByFriendId(relations = {})", relations);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        conditions.add(criteriaBuilder.equal(from.get("friendId"), relations.getFriendId()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        Relations friendRelations = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.close();
        return friendRelations;
    }

    public boolean update(Relations relations) {
        logger.info("RelationsDao.update(relations)");
        logger.debug("RelationsDao.update(relations = {})", relations);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(relations);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public boolean deleteByAccountId(Relations relations) {
        System.out.println("RelationsDao.deleteByAccountId()");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(relations);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

}