package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountDao {
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    @Autowired
    public AccountDao(SessionFactory sessionFactory) {
        logger.info("create accountDao");
        this.sessionFactory = sessionFactory;
    }

    public AccountDao() {
    }

    public boolean create(Account account) {
        logger.info("AccountDao.create(Account account)");
        sessionFactory.getCurrentSession().save(account);
        return true;
    }

    public int getIdAccount(Account account) {
        logger.info("AccountDao.getIdAccount(Account account)");
        logger.debug("AccountDao.getIdAccount(account.username = {) , account.password = {)",
                account.getUsername(), account.getPassword());
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.like(from.get("username"), account.getUsername()));
        conditions.add(criteriaBuilder.like(from.get("password"), account.getPassword()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        TypedQuery<Account> query = session.createQuery(criteriaQuery);
        return query.getSingleResult().getId();
    }

    public Account getAccount(String username, String password) {
        logger.info("AccountDao.getAccount(username, password)");
        logger.debug("AccountDao.getAccount(username = {}, password = {})", username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.like(from.get("username"), username));
        conditions.add(criteriaBuilder.like(from.get("password"), password));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        TypedQuery<Account> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    public Account getAccount(int id) {
        logger.info("AccountDao.getAccount(id)");
        logger.debug("AccountDao.getAccount(id = {})", id);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        Query<Account> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    public List<Account> getAccountsName(String name) {
        logger.info("AccountDao.getAccountsName(name)");
        logger.debug("AccountDao.getAccountsName(name = {})", name);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        CriteriaQuery<Account> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("name"), name));
        return session.createQuery(nameQuery).getResultList();
    }

    public List<Account> getAllAccounts() {
        logger.info("AccountDao.getAccounts()");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        Query<Account> query = session.createQuery(cr);
        return query.getResultList();
    }

    public List<Account> getAccountsCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("AccountDao.getAccountsLimit(start, end, criteriaName)");
        logger.debug("AccountDao.getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})",
                start, end, criteriaName);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> from = cr.from(Account.class);
        cr.select(from);
        CriteriaQuery<Account> nameQuery = cr.select(from).where(
                cb.or(cb.like(from.get("name"), criteriaName),
                        cb.like(from.get("surname"), criteriaName),
                        cb.like(from.get("lastName"), criteriaName)));
        Query<Account> query = session.createQuery(nameQuery).setFirstResult(start).setMaxResults(end);
        return query.getResultList();
    }

    public int getSizeRecords() {
        logger.info("getSizeRecords");
        Object result = sessionFactory.getCurrentSession().createCriteria(Account.class)
                .setProjection(Projections.rowCount()).uniqueResult();
        return Integer.parseInt(result.toString());
    }

    public long getSizeRecords(String search) {
        logger.info("AccountDao.getSizeRecords(search = {})", search);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Account> from = cq.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.or(
                cb.like(from.get("name"), search),
                cb.like(from.get("surname"), search),
                cb.like(from.get("lastName"), search)));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        return session.createQuery(cq).getSingleResult();
    }

    public List<Account> getAccountsLimit(int start, int end) {
        logger.info("AccountDao.getAccountsLimit(start, end)");
        logger.debug("AccountDao.getAccountsLimit(start = {}, end = {})", start, end);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        Query<Account> query = session.createQuery(cr).setFirstResult(start).setMaxResults(end);
        return query.getResultList();
    }

    public boolean deleteAccount(Account account) {
        logger.info("AccountDao.deleteAccount(Account account)");
        logger.debug("AccountDao.getFriendsAccount(account.id = {})", account.getId());
        sessionFactory.getCurrentSession().delete(account);
        return true;
    }

    public boolean updateAccount(Account account) {
        return sessionFactory.getCurrentSession().merge(account) != null;
    }

    public List<Account> getFriendsAccount(int accountId) {
        logger.info("AccountDao.getFriendsAccount(accountId = {})", accountId);
        logger.debug("AccountDao.getFriendsAccount(accountId = {})", accountId);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("id"), accountId));
        List<Relations> relations = session.createQuery(query).getSingleResult().getRelations();
        List<Account> friends = new ArrayList<>();
        for (Relations accountFriends : relations) {
            friends.add(getAccount(accountFriends.getFriendId()));
        }
        return friends;
    }

    @Transactional
    public void createAccounts(List<Account> accounts) {
        logger.info("AccountDao.createAccounts(accounts.size() = {})", accounts.size());
        logger.debug("AccountDao.createAccounts(accounts.size() = {}", accounts.size());
        for (Account a : accounts) {
            sessionFactory.getCurrentSession().save(a);
        }
    }

}