package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Relations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {
    private final JdbcTemplate jdbcTemplate;

    public ApplicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Application application) {
        System.out.println("Application create");
        String sql = "INSERT INTO application (application_type, applicant_id, recipient_id, status)" +
                " VALUES (?, ?, ?, ?);";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, application.getApplicationType(), application.getApplicantId(),
                application.getRecipientId(), application.getStatus());
        return result > 0;
    }

    public Application get(int id) {
        System.out.println("Application get");
        String sql = "SELECT * FROM application WHERE id = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                (resultSet, i) -> fillFieldQuery(resultSet));
    }

    public Application get(Group group, int recipientId) {
        System.out.println("Application get(Group group, int recipientId)");
        String sql = "SELECT * FROM application WHERE applicant_id = ? AND recipient_id = ? AND application_type = 0";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{group.getGroupId(), recipientId},
                (resultSet, i) -> fillFieldQuery(resultSet));
    }

    public Application get(Relations relations) {
        System.out.println("Application get(Friend friend)");
        String sql = "SELECT * FROM application WHERE applicant_id = ? AND recipient_id = ? AND application_type = 1";
        System.out.println(sql);
        Application application = null;
        try {
            application = jdbcTemplate.queryForObject(sql,
                    new Object[]{ relations.getFriendId(), relations.getAccountId()},
                    (resultSet, i) -> fillFieldQuery(resultSet));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (application == null) {
            System.out.println("new application");
            application = new Application();
            application.setId(0);
            application.setApplicationType(0);
            application.setApplicantId(0);
            application.setStatus(0);
            application.setRecipientId(0);
        }
        return application;
    }

    public List<Application> getApplications() {
        System.out.println("List<Application> getApplications");
        String sql = "SELECT * FROM application";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql,
                (resultSet, i) -> fillApplications(resultSet));
    }

    public boolean update(Application application) {
        System.out.println("update(Application application)");
        String sql = "UPDATE application SET id = ?, application_type = ?, idAdministrator = ?, " +
                "idAdministrator = ?;";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, application.getId(), application.getApplicationType(),
                application.getApplicantId(), application.getRecipientId(), application.getStatus());
        return result > 0;
    }

    public boolean delete(Application application) {
        System.out.println("delete(Application application)");
        String sql = "DELETE FROM application WHERE id = " + application.getId();
        System.out.println(sql);
        return jdbcTemplate.update(sql, application.getId()) > 0;
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
        application.setApplicantId(resultSet.getInt(3));
        application.setRecipientId(resultSet.getInt(4));
        application.setStatus(resultSet.getInt(5));
        return application;
    }

}