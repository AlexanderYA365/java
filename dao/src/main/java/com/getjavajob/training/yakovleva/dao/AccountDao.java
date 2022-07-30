package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AccountDao {
    private SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public AccountDao(SessionFactory sessionFactory) {
        logger.info("create accountDao");
        this.sessionFactory = sessionFactory;
    }

    public AccountDao() {
    }

    public void create(Account account) {
        logger.info("AccountDao.create(Account account)");
        sessionFactory.getCurrentSession().save(account);
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

    public List<Account> getAccounts() {
        logger.info("AccountDao.getAccounts()");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        Query<Account> query = session.createQuery(cr);
        return query.getResultList();
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
        logger.info("AccountDao.getFriendsAccount(accountId)");
        logger.debug("AccountDao.getFriendsAccount(accountId = {})", accountId);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        Join<Object, Object> relations = root.join("relations");
        query.where(criteriaBuilder.and(criteriaBuilder.equal(relations.get("friendId"), root.get("id")),
                criteriaBuilder.equal(relations.get("accountId"), accountId)));
        return session.createQuery(query).getResultList();
    }

    public void createAccounts(List<Account> accounts) throws SQLException {
        logger.info("AccountDao.createAccounts(accounts.size() = %d)", accounts.size());
        logger.debug("AccountDao.createAccounts(accounts.size() = {}", accounts.size());
        sessionFactory.getCurrentSession().save(accounts);
//        DataSource ds = jdbcTemplate.getDataSource();
//        Connection connection = ds.getConnection();
//        connection.setAutoCommit(false);
//        PreparedStatement ps = connection.prepareStatement(sql);
//        for (Account account : accounts) {
//            ps.setString(1, account.getName());
//            ps.setString(2, account.getSurname());
//            ps.setString(3, account.getLastName());
//            ps.setDate(4, new java.sql.Date(account.getDate().getTime()));
//            ps.setInt(5, account.getIcq());
//            ps.setString(6, account.getAddressHome());
//            ps.setString(7, account.getAddressJob());
//            ps.setString(8, account.getEmail());
//            ps.setString(9, account.getAboutMe());
//            ps.setString(10, account.getUsername());
//            ps.setString(11, account.getPassword());
//            ps.setInt(12, account.getRole());
//            ps.setBytes(13, account.getPhoto());
//            ps.setString(14, account.getPhotoFileName());
//            ps.addBatch();
//        }
//        ps.executeBatch();
//        ps.clearBatch();
//        connection.commit();
//        ps.close();

//        int[] argTypes = new int[35];
//        argTypes[0] = Types.VARCHAR;
//        argTypes[1] = Types.VARCHAR;
//        argTypes[2] = Types.VARCHAR;
//        argTypes[3] = Types.DATE;
//        argTypes[4] = Types.INTEGER;
//        argTypes[5] = Types.VARCHAR;
//        argTypes[6] = Types.VARCHAR;
//        argTypes[7] = Types.VARCHAR;
//        argTypes[8] = Types.VARCHAR;
//        argTypes[9] = Types.VARCHAR;
//        argTypes[10] = Types.VARCHAR;
//        argTypes[11] = Types.INTEGER;
//        argTypes[12] = Types.BLOB;
//        argTypes[13] = Types.VARCHAR;
//
//
//        List<Object[]> objects = new ArrayList<>();
//        for (Account account: accounts) {
//            Object[] o = new Object[14];
//            o[0] =  account.getName();
//            o[1] = account.getSurname();
//            o[2] = account.getLastName();
//            o[3] = new java.sql.Date(account.getDate().getTime());
//            o[4] = account.getIcq();
//            o[5] = account.getAddressHome();
//            o[6] = account.getAddressJob();
//            o[7] = account.getEmail();
//            o[8] = account.getAboutMe();
//            o[9] = account.getUsername();
//            o[10] =account.getPassword();
//            o[11] = account.getRole();
//            o[12] = account.getPhoto();
//            o[13] = account.getPhotoFileName();
//            objects.add(o);
//        }
//
//        jdbcTemplate.batchUpdate(sql, objects, argTypes);

//        jdbcTemplate.batchUpdate(sql,
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement ps, int i) throws SQLException {
//                        ps.setString(1, accounts.get(i).getName());
//                        ps.setString(2, accounts.get(i).getSurname());
//                        ps.setString(3, accounts.get(i).getLastName());
//                        ps.setDate(4, new java.sql.Date(accounts.get(i).getDate().getTime()));
//                        ps.setInt(5, accounts.get(i).getIcq());
//                        ps.setString(6, accounts.get(i).getAddressHome());
//                        ps.setString(7, accounts.get(i).getAddressJob());
//                        ps.setString(8, accounts.get(i).getEmail());
//                        ps.setString(9, accounts.get(i).getAboutMe());
//                        ps.setString(10, accounts.get(i).getUsername());
//                        ps.setString(11, accounts.get(i).getPassword());
//                        ps.setInt(12, accounts.get(i).getRole());
//                        ps.setBytes(13, accounts.get(i).getPhoto());
//                        ps.setString(14, accounts.get(i).getPhotoFileName());
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return accounts.size();
//                    }
//                });
    }

}