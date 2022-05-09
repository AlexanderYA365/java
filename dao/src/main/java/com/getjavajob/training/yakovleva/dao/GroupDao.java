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
public class GroupDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Group group) {
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account)" +
                " VALUES ('?', '?', ?, ?);";
        System.out.println(sql);
        jdbcTemplate.update(sql, group.getGroupName(), group.getLogo(), group.getIdAdministrator(),
                group.getIdAccount());
        return true;
    }

    public Group read(int id) {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE idgroup = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> fillGroup(resultSet));
    }

    public List<Group> read(String groupName) {
        System.out.println("Group read(String groupName)");
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE groupname = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{groupName}, (resultSet, i) -> fillGroup(resultSet));
    }

    public List<Group> readGroups() {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group`";
        System.out.println(sql);
        return jdbcTemplate.query(sql, (resultSet, i) -> fillGroup(resultSet));
    }

    public List<Group> readGroupsAccount(int id) {
        String sql = "SELECT * FROM heroku_dc02d468f96562c.`group` WHERE Account = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{id}, (resultSet, i) -> fillGroup(resultSet));
    }

    @Transactional
    public boolean update(Group group) {
        String sql = "UPDATE heroku_dc02d468f96562c.`group`" +
                " SET groupName = '?', logo = '?', idAdministrator = ?, Account = ? WHERE idgroup = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, group.getGroupName(), group.getLogo(), group.getIdAdministrator(),
                group.getIdAccount(), group.getIdGroup());
        return true;
    }

    @Transactional
    public boolean delete(Group group) {
        String sql = "DELETE FROM heroku_dc02d468f96562c.`group` WHERE idgroup = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, group.getIdGroup());
        return true;
    }

    public boolean insertAccount(Group group, int idAccount) {
        System.out.println("insertAccount, group - " + group + " ,idAccount - " + idAccount);
        String sql = "INSERT INTO heroku_dc02d468f96562c.`group`(groupname, logo, idAdministrator, account) " +
                "VALUES (?, ?, ?, ?)";
        System.out.println(sql);
        jdbcTemplate.update(sql, group.getDateCreateGroup(), group.getLogo(),
                group.getIdAdministrator(), group.getIdAccount());
        return true;
    }

    private Group fillGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setIdGroup(resultSet.getInt(1));
        group.setGroupName(resultSet.getString(2));
        group.setLogo(resultSet.getString(3));
        group.setIdAdministrator(resultSet.getInt(4));
        group.setIdAccount(resultSet.getInt(5));
        return group;
    }

}