package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Phone;
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
public class PhoneDao {
    private static final Logger logger = LogManager.getLogger();
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public PhoneDao(EntityManagerFactory entityManagerFactory) {
        logger.info("PhoneDao(sessionFactory)");
        this.entityManagerFactory = entityManagerFactory;
    }

    public PhoneDao() {
    }

    public boolean create(Phone phone) {
        logger.info("PhoneDao.create(phone)");
        logger.debug("PhoneDao.create(phone = {})", phone);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(phone);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public List<Phone> get(int accountId) {
        logger.info("PhoneDao.get(accountId)");
        logger.debug("PhoneDao.get(accountId = {})", accountId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Phone> criteriaQuery = criteriaBuilder.createQuery(Phone.class);
        Root<Phone> from = criteriaQuery.from(Phone.class);
        CriteriaQuery<Phone> selectAccountId = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), accountId));
        List<Phone> phones = entityManager.createQuery(selectAccountId).getResultList();
        entityManager.close();
        return phones;
    }

    public boolean update(Phone phone) {
        logger.info("PhoneDao.update(phone)");
        logger.debug("PhoneDao.update(phone = {})", phone);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(phone);
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public boolean delete(Phone phone) {
        logger.info("PhoneDao.delete(phone)");
        logger.debug("PhoneDao.delete(phone = {})", phone);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Phone deletePhone = entityManager.find(Phone.class, phone.getId());
            entityManager.remove(deletePhone);
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

}