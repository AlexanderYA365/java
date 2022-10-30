package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountDao {
    private static final Logger logger = LogManager.getLogger();
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public AccountDao(EntityManagerFactory entityManagerFactory) {
        logger.info("create accountDao");
        this.entityManagerFactory = entityManagerFactory;
    }

    public AccountDao() {
    }

    public boolean create(Account account) {
        logger.info("AccountDao.create(Account account)");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(account);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public int getIdAccount(Account account) {
        logger.info("AccountDao.getIdAccount(Account account)");
        logger.debug("AccountDao.getIdAccount(account.username = {) , account.password = {)",
                account.getUsername(), account.getPassword());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.like(from.get("username"), account.getUsername()));
        conditions.add(criteriaBuilder.like(from.get("password"), account.getPassword()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        int id = entityManager.createQuery(criteriaQuery).getSingleResult().getId();
        entityManager.close();
        return id;
    }

    public Account getAccount(String username, String password) {
        logger.info("AccountDao.getAccount(username, password)");
        logger.debug("AccountDao.getAccount(username = {}, password = {})", username, password);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.like(from.get("username"), username));
        conditions.add(criteriaBuilder.like(from.get("password"), password));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        Account account = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.close();
        return account;
    }

    public Account getAccount(int id) {
        logger.info("AccountDao.getAccount(id)");
        logger.debug("AccountDao.getAccount(id = {})", id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        Account account = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.close();
        return account;
    }

    public List<Account> getAccountsName(String name) {
        logger.info("AccountDao.getAccountsName(name)");
        logger.debug("AccountDao.getAccountsName(name = {})", name);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        CriteriaQuery<Account> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("name"), name));
        List<Account> accounts = entityManager.createQuery(nameQuery).getResultList();
        entityManager.close();
        return accounts;
    }

    public List<Account> getAllAccounts() {
        logger.info("AccountDao.getAccounts()");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        List<Account> accounts = entityManager.createQuery(cr).getResultList();
        entityManager.close();
        return accounts;
    }

    public List<Account> getAccountsCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("AccountDao.getAccountsLimit(start, end, criteriaName)");
        logger.debug("AccountDao.getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})",
                start, end, criteriaName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> from = cr.from(Account.class);
        cr.select(from);
        CriteriaQuery<Account> nameQuery = cr.select(from).where(
                cb.or(cb.like(from.get("name"), criteriaName),
                        cb.like(from.get("surname"), criteriaName),
                        cb.like(from.get("lastName"), criteriaName)));
        List<Account> accounts = entityManager.createQuery(nameQuery).setFirstResult(start).setMaxResults(end).
                getResultList();
        entityManager.close();
        return accounts;
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Account> from = cq.from(Account.class);
        cq.select(cb.count(from));
        long size = entityManager.createQuery(cq).getSingleResult();
        entityManager.close();
        return size;
    }

    public long getSizeRecords(String search) {
        logger.info("AccountDao.getSizeRecords(search = {})", search);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Account> from = cq.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.or(
                cb.like(from.get("name"), search),
                cb.like(from.get("surname"), search),
                cb.like(from.get("lastName"), search)));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        long size = entityManager.createQuery(cq).getSingleResult();
        entityManager.close();
        return size;
    }

    public List<Account> getAccountsLimit(int start, int end) {
        logger.info("AccountDao.getAccountsLimit(start, end)");
        logger.debug("AccountDao.getAccountsLimit(start = {}, end = {})", start, end);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        List<Account> accounts = entityManager.createQuery(cr).setFirstResult(start).setMaxResults(end).getResultList();
        entityManager.close();
        return accounts;
    }

    public boolean deleteAccount(Account account) {
        logger.info("AccountDao.deleteAccount(Account account)");
        logger.debug("AccountDao.getFriendsAccount(account.id = {})", account.getId());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(account);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public boolean updateAccount(Account account) {
        logger.info("updateAccount(account = {})", account);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(account);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        return true;
    }

    public List<Account> getFriendsAccount(int accountId) {
        logger.info("AccountDao.getFriendsAccount(accountId = {})", accountId);
        logger.debug("AccountDao.getFriendsAccount(accountId = {})", accountId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("id"), accountId));
        List<Relations> relations = entityManager.createQuery(query).getSingleResult().getRelations();
        List<Account> friends = new ArrayList<>();
        for (Relations accountFriends : relations) {
            friends.add(getAccount(accountFriends.getFriendId()));
        }
        entityManager.close();
        return friends;
    }

    @Transactional
    public void createAccounts(List<Account> accounts) {
        logger.info("AccountDao.createAccounts(accounts.size() = {})", accounts.size());
        logger.debug("AccountDao.createAccounts(accounts.size() = {}", accounts.size());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Account a : accounts) {
            try {
                entityManager.persist(a);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                entityManager.getTransaction().rollback();
            }
        }
        entityManager.close();
    }

}