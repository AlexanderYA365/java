package com.getjavajob.training.yakovleva.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhoneDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Phone phone) {
        String sql = "INSERT INTO phone(idaccount, phonenumber, phonetype) " +
                "VALUES (?,?,?);";
        System.out.println(sql);
        jdbcTemplate.update(sql, phone.getIdAccount(), phone.getPhoneNumber(), phone.getPhoneType());//TODO
        return true;
    }

    public List<Phone> read(int idAccount) {
        String sql = "SELECT * FROM phone WHERE idaccount = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{idAccount},
                (resultSet, i) -> fillPhone(resultSet));
    }

    @Transactional
    public boolean update(Phone phone) {
        String sql = "UPDATE phone SET phonenumber = ?, phonetype = ? WHERE IdAccount = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, phone.getPhoneNumber(), phone.getPhoneType(), phone.getIdAccount());
        return true;
    }

    @Transactional
    public boolean delete(Phone phone) {
        String sql = "DELETE FROM phone WHERE IdAccount = ? AND phonenumber = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, phone.getIdAccount(), phone.getPhoneNumber());
        return true;
    }

    private Phone fillPhone(ResultSet resultSet) throws SQLException {
            Phone phone = new Phone();
            phone.setId(resultSet.getInt(1));
            phone.setPhoneNumber(resultSet.getString(2));
            phone.setPhoneType(resultSet.getInt(3));
            phone.setIdAccount(resultSet.getInt(4));
            return phone;
    }

}