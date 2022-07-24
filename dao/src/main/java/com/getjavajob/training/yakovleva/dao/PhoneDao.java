package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Phone;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    public PhoneDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    public boolean create(Phone phone) {
        System.out.println("create phone - " + phone);
        String sql = "INSERT INTO phone(account_id, phone_number, phone_type) " +
                "VALUES (?,?,?);";
        System.out.println(sql);
        boolean result = false;
        try {
            result = jdbcTemplate.update(sql, phone.getAccountId(), phone.getPhoneNumber(), phone.getPhoneType()) > 0;
        } catch (Exception ex) {
            System.out.println("crete phone exception - " + ex);
        }
        return result;
    }

    public List<Phone> get(int accountId) {
        System.out.println("get phone accountId - " + accountId);
        String sql = "SELECT * FROM phone WHERE account_id = ?";
        System.out.println(sql);
        Phone phones = new Phone();
        try {
            Session session;
            try {
                //Step-2: Implementation
                session = sessionFactory.getCurrentSession();
            } catch (HibernateException e) {
                //Step-3: Implementation
                System.out.println("catch ex in hibernate");
                session = sessionFactory.openSession();
            }
            phones = session.get(Phone.class, accountId);
            System.out.println(phones);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        List<Phone> phones1 = new ArrayList<>();
        return phones1;
//        List<Phone> phones = new ArrayList<>();
//        try {
//            phones = jdbcTemplate.query(sql, new Object[]{accountId},
//                    (resultSet, i) -> fillPhone(resultSet));
//        } catch (Exception ex) {
//            System.out.println("get phone exception - " + ex);
//        }
//        return phones;
    }

    public boolean update(Phone phone) {
        System.out.println("update phone - " + phone);
        System.out.println("phone.getPhoneType() - " + phone.getPhoneType());
        String sql = "UPDATE phone SET phone_number = ?, phone_type = ? WHERE id = ?";
        System.out.println(sql);
        boolean result = false;
        try {
            result = jdbcTemplate.update(sql, phone.getPhoneNumber(), phone.getPhoneType(),
                    phone.getId()) > 0;
        } catch (Exception ex) {
            System.out.println("update phone error - " + ex);
        }
        return result;
    }

    public boolean delete(Phone phone) {
        System.out.println("delete phone - " + phone);
        String sql = "DELETE FROM phone WHERE account_id = ? AND phone_number = ?";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, phone.getAccountId(), phone.getPhoneNumber());
        return result > 0;
    }

    private Phone fillPhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getInt(1));
        phone.setPhoneNumber(resultSet.getString(2));
        phone.setPhoneType(resultSet.getInt(3));
        phone.setAccountId(resultSet.getInt(4));
        return phone;
    }

}