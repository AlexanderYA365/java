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
public class ApplicationDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApplicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Application application) {
        System.out.println("Application create");
        String sql = "INSERT INTO application (applicationType, idApplicant, idRecipient, status)" +
                " VALUES (?, ?, ?, ?);";
        System.out.println(sql);
        jdbcTemplate.update(sql, application.getApplicationType(), application.getIdApplicant(),
                application.getIdRecipient(), application.getStatus());
        return true;
    }

    public Application read(int id) {
        System.out.println("Application read");
        String sql = "SELECT * FROM application WHERE id = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                (resultSet, i) -> fillFieldQuery(resultSet));
    }

    public Application read(Group group, int idRecipient) {
        System.out.println("Application read(Group group, int idRecipient)");
        String sql = "SELECT * FROM application WHERE idApplicant = ? AND idRecipient = ? AND applicationType = 0";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{group.getIdGroup(), idRecipient},
                (resultSet, i) -> fillFieldQuery(resultSet));
    }

    public Application read(Friend friend) {
        System.out.println("Application read(Friend friend)");
        String sql = "SELECT * FROM application WHERE idApplicant = ? AND idRecipient = ? AND applicationType = 1";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql,
                new Object[]{friend.getIdAccount(), friend.getIdFriendsAccount()},
                (resultSet, i) -> fillFieldQuery(resultSet));
    }

    public List<Application> readApplications() {
        System.out.println("List<Application> readApplications");
        String sql = "SELECT * FROM application";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql,
                (resultSet, i) -> fillApplications(resultSet));
    }

    @Transactional
    public boolean update(Application application) {
        System.out.println("update(Application application)");
        String sql = "UPDATE application SET id = ?, applicationType = ?, idAdministrator = ?, " +
                "idAdministrator = ?;";
        System.out.println(sql);
        jdbcTemplate.update(sql, application.getId(), application.getApplicationType(),
                application.getIdApplicant(), application.getIdRecipient(), application.getStatus());
        return true;
    }

    @Transactional
    public void delete(Application application) {
        System.out.println("delete(Application application)");
        String sql = "DELETE FROM application WHERE id = " + application.getId();
        System.out.println(sql);
        jdbcTemplate.update(sql, application.getId());
    }

    private List<Application> fillApplications(ResultSet resultSet) {
        List<Application> applications = new ArrayList<>();
        try {
            while (resultSet.next()) {
                applications.add(fillFieldQuery(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return applications;
    }

    private Application fillFieldQuery(ResultSet resultSet) throws SQLException {
        Application application = new Application();
        application.setId(resultSet.getInt(1));
        application.setApplicationType(resultSet.getInt(2));
        application.setIdApplicant(resultSet.getInt(3));
        application.setIdRecipient(resultSet.getInt(4));
        application.setStatus(resultSet.getInt(5));
        return application;
    }

}