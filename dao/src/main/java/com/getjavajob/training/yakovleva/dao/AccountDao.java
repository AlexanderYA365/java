package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AccountDao {
    private JdbcTemplate jdbcTemplate;
    private SessionFactory sessionFactory;

    public AccountDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AccountDao() {
    }

    public void create(Account account) {
        sessionFactory.getCurrentSession().save(account);
    }

    public int getIdAccount(Account account) {
        System.out.println("getIdAccount - account = " + account);
        String sql = "SELECT * FROM account WHERE username LIKE ? AND password LIKE ?";
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
        System.out.println("getAccount - username = " + username + " ,password = " + password);
        String sql = "SELECT * FROM account WHERE username LIKE ? AND password LIKE ?";
        return jdbcTemplate.query(sql,
                new Object[]{username, password}, (resultSet, i) -> fillAccount(resultSet)).get(0);
    }

    public Account getAccount(int id) {
        System.out.println("getAccount(int id)");
        String sql = "SELECT * FROM account WHERE account_id = ?";
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
        System.out.println("getAccountsName");
        String sql = "SELECT * FROM account WHERE name LIKE ?";
        System.out.println(sql);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        CriteriaQuery<Account> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("name"), name));
        return session.createQuery(nameQuery).getResultList();
    }

    public List<Account> getAccounts() {
        System.out.println("getAccounts");
        String sql = "SELECT * FROM account";
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        Query<Account> query = session.createQuery(cr);
        List<Account> results = query.getResultList();
        return results;
    }

    public List<Account> getAccountsLimit(int start, int end) {
        System.out.println("getAccountsLimit");
        String sql = "SELECT * FROM account LIMIT ?, ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{start, end}, (resultSet, i) -> fillAccount(resultSet));
    }

    public boolean deleteAccount(Account account) {
        System.out.println("readAccountsName");
        String sql = "DELETE FROM account WHERE account_id = ?";
        sessionFactory.getCurrentSession().delete(account);
        return true;
    }

    public boolean updateAccount(Account account) {
        return sessionFactory.getCurrentSession().merge(account) != null;
    }

    public List<Account> getFriendsAccount(int accountId) {
        System.out.println("List<Account> getFriendsAccount");
        String sql = "SELECT a.account_id, a.name, a.surname, a.lastname, " +
                "a.date, a.icq, a.address_home, a.address_job, a.email, " +
                "a.about_me, a.username, a.password, a.role, a.photo, a.photo_file_name FROM account a, " +
                "relations r WHERE a.account_id = r.friend_id and r.account_id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{accountId},
                (resultSet, i) -> fillAccount(resultSet));
    }

    public void createAccounts(List<Account> accounts) throws SQLException {
        String sql = "INSERT INTO account(name, surname, lastname, date, icq, address_home, " +
                "address_job, email, about_me, " +
                "username, password, role, photo, photo_file_name) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        System.out.println(sql);

        DataSource ds = jdbcTemplate.getDataSource();
        Connection connection = ds.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(sql);
        for (Account account : accounts) {
            ps.setString(1, account.getName());
            ps.setString(2, account.getSurname());
            ps.setString(3, account.getLastName());
            ps.setDate(4, new java.sql.Date(account.getDate().getTime()));
            ps.setInt(5, account.getIcq());
            ps.setString(6, account.getAddressHome());
            ps.setString(7, account.getAddressJob());
            ps.setString(8, account.getEmail());
            ps.setString(9, account.getAboutMe());
            ps.setString(10, account.getUsername());
            ps.setString(11, account.getPassword());
            ps.setInt(12, account.getRole());
            ps.setBytes(13, account.getPhoto());
            ps.setString(14, account.getPhotoFileName());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.clearBatch();
        connection.commit();
        ps.close();

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

    private Account fillAccount(ResultSet resultSet) {
        Account account = new Account();
        try {
            account.setId(resultSet.getInt(1));
            account.setName(resultSet.getString(2));
            account.setSurname(resultSet.getString(3));
            account.setLastName(resultSet.getString(4));
            account.setDate(resultSet.getDate(5));
            account.setIcq(resultSet.getInt(6));
            account.setAddressHome(resultSet.getString(7));
            account.setAddressJob(resultSet.getString(8));
            account.setEmail(resultSet.getString(9));
            account.setAboutMe(resultSet.getString(10));
            account.setUsername(resultSet.getString(11));
            account.setPassword(resultSet.getString(12));
            account.setRole(resultSet.getInt(13));
            account.setPhoto(resultSet.getBytes(14));
            account.setPhotoFileName(resultSet.getString(15));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

}