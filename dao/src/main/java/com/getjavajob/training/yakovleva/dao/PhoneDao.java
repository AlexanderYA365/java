package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class PhoneDao {
    private SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public PhoneDao(SessionFactory sessionFactory) {
        logger.info("PhoneDao(sessionFactory)");
        this.sessionFactory = sessionFactory;
    }

    public PhoneDao() {
    }

    public boolean create(Phone phone) {
        logger.info("PhoneDao.create(phone)");
        logger.debug("PhoneDao.create(phone = {})", phone);
        sessionFactory.getCurrentSession().save(phone);
        return true;
    }

    public List<Phone> get(int accountId) {
        logger.info("PhoneDao.get(accountId)");
        logger.debug("PhoneDao.get(accountId = {})", accountId);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Phone> criteriaQuery = criteriaBuilder.createQuery(Phone.class);
        Root<Phone> from = criteriaQuery.from(Phone.class);
        CriteriaQuery<Phone> selectAccountId = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), accountId));
        return session.createQuery(selectAccountId).getResultList();
    }

    public boolean update(Phone phone) {
        logger.info("PhoneDao.update(phone)");
        logger.debug("PhoneDao.update(phone = {})", phone);
        return sessionFactory.getCurrentSession().merge(phone) != null;
    }

    public boolean delete(Phone phone) {
        logger.info("PhoneDao.delete(phone)");
        logger.debug("PhoneDao.delete(phone = {})", phone);
        Session session = sessionFactory.getCurrentSession();
        Phone deletePhone = session.find(Phone.class, phone.getId());
        session.delete(deletePhone);
        return true;
    }

}