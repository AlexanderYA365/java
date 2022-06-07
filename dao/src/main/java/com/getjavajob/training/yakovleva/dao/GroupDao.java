package com.getjavajob.training.yakovleva.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDao {
    private final JdbcTemplate jdbcTemplate;

    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Group group) {
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(group_name, logo, administrator_id, account_id)" +
                " VALUES ('?', '?', ?, ?);";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, group.getGroupName(), group.getLogo(), group.getAdministratorId(),
                group.getAccountId());
        return result > 0;
    }

    public Group getGroup(int id) {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE group_id = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> fillGroup(resultSet));
    }

    public List<Group> getGroups(String groupName) {
        System.out.println("Group read(String groupName)");
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE group_name = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{groupName}, (resultSet, i) -> fillGroup(resultSet));
    }

    public List<Group> getGroups() {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group`";
        System.out.println(sql);
        return jdbcTemplate.query(sql, (resultSet, i) -> fillGroup(resultSet));
    }

    public List<Group> getGroupsAccount(int id) {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE account_id = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{id}, (resultSet, i) -> fillGroup(resultSet));
    }

    public boolean update(Group group) {
        String sql = "UPDATE heroku_dc02d468f96562c.`group`" +
                " SET group_name = '?', logo = '?', administrator_id = ?, account_id = ? WHERE group_id = ?";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, group.getGroupName(), group.getLogo(), group.getAdministratorId(),
                group.getAccountId(), group.getGroupId());
        return result > 0;
    }

    public boolean delete(Group group) {
        String sql = "DELETE FROM heroku_dc02d468f96562c.`group` WHERE group_id = ?";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, group.getGroupId());
        return result > 0;
    }

    public boolean insertAccount(Group group, int accountId) {
        System.out.println("insertAccount, group - " + group + " ,accountId - " + accountId);
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(group_name, logo, administrator_id, account_id) " +
                "VALUES (?, ?, ?, ?)";
        System.out.println(sql);
        int result = jdbcTemplate.update(sql, group.getDateCreateGroup(), group.getLogo(),
                group.getAdministratorId(), group.getAccountId());
        return result > 0;
    }

    private Group fillGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setGroupId(resultSet.getInt(1));
        group.setGroupName(resultSet.getString(2));
        group.setLogo(resultSet.getString(3));
        group.setAdministratorId(resultSet.getInt(4));
        group.setAccountId(resultSet.getInt(5));
        return group;
    }

}