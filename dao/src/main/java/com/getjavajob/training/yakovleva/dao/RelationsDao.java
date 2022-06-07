package com.getjavajob.training.yakovleva.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;

public class RelationsDao {
    private final JdbcTemplate jdbcTemplate;

    public RelationsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Relations relations){
        System.out.println("RelationsDao.create()");
        String sql = "INSERT INTO relations(account_id, friend_id) VALUES(?, ?)";
        return jdbcTemplate.update(sql, relations.getAccountId(), relations.getFriendId()) > 0;
    }

    public List<Relations> getByAccountID(Relations relations){
        System.out.println("RelationsDao.getByAccountID()");
        String sql = "SELECT * FROM relations WHERE account_id = ?";
        return jdbcTemplate.query(sql,
                new Object[] {relations.getAccountId()},
                (resultSet, i) -> fillRelations(resultSet));
    }

    public Relations getByFriendId(Relations relations) {
        System.out.println("RelationsDao.getByFriendId()");
        String sql = "SELECT * FROM relations WHERE account_id = ? AND friend_id = ?";
        return (Relations) jdbcTemplate.query(sql,
                new Object[] {relations.getAccountId(), relations.getFriendId()},
                (resultSet, i) -> fillRelations(resultSet));
    }

    public boolean update(Relations relations){
        System.out.println("RelationsDao.update()");
        String sql = "UPDATE relations SET account_id = ?, friend_id = ?";
        return jdbcTemplate.update(sql, relations.getAccountId(), relations.getFriendId()) > 0;
    }

    public boolean deleteByAccountId(Relations relations){
        System.out.println("RelationsDao.deleteByAccountId()");
        String sql = "DELETE FROM relations WHERE account_id = ?";
        return jdbcTemplate.update(sql, relations.getAccountId()) > 0;
    }

    private Relations fillRelations(ResultSet resultSet){
        Relations relations = new Relations();
        try{
            relations.setAccountId(resultSet.getInt(1));
            relations.setFriendId(resultSet.getInt(2));
        } catch (Exception ex){
            System.out.println("fillRelations exception - " + ex);
        }
        return relations;
    }

}
