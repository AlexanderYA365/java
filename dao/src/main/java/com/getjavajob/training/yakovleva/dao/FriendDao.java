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
public class FriendDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Friend friend) {
        System.out.println("create - start");
        String sql = "INSERT INTO friends(idAccount, idFriendsAccount) VALUES (?, ?)";
        jdbcTemplate.update(sql, friend.getIdAccount(), friend.getIdFriendsAccount());
        return true;
    }

    public List<Friend> readFriends(int id) {
        String sql = "SELECT * FROM friends WHERE idAccount = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{id}, (resultSet, i) -> fillFriend(resultSet));
    }

    public Friend read(int idFriends) {
        String sql = "SELECT * FROM friends WHERE idfriends = ?";
        System.out.println(sql);
        System.out.println("idFriends - " + idFriends);
        return jdbcTemplate.queryForObject(sql, new Object[]{idFriends},
                (resultSet, i) -> fillFriend(resultSet));
    }

    public List<Friend> readAllFriends() {
        String sql = "SELECT * FROM friends";
        System.out.println(sql);
        return jdbcTemplate.query(sql, (resultSet, i) -> fillFriend(resultSet));
    }

    @Transactional
    public void update(Friend friend) {
        String sql = "UPDATE friends SET idAccount = ?, idFriendsAccount = ? WHERE idFriends = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, friend.getIdAccount(), friend.getIdFriendsAccount(),
                friend.getId());
    }

    public void deleteFriendIdAccountIdFriendAccount(int idAccount, int idFriend) {
        String sql = "DELETE FROM friends WHERE idAccount = ? AND idFriendsAccount = ? ";
        System.out.println(sql);
        jdbcTemplate.update(sql, idAccount, idFriend);
    }

    public void delete(Friend friend) {
        String sql = "DELETE FROM friends WHERE idFriends = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, friend.getId());
    }

    public List<Friend> readFriendsName(int idAccount) {
        System.out.println("readFriendsName - start");
        String sql = "SELECT a.username, a.name,  " +
                "f.idAccount, f.idFriendsAccount, f.idFriends FROM friends f JOIN account a " +
                "ON f.idFriendsAccount = a.idAccount WHERE f.idAccount = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{idAccount},
                (resultSet, i) -> fillFriendName(resultSet));
    }

    private List<Friend> fillFriendName(ResultSet resultSet) throws SQLException {
        List<Friend> friends = new ArrayList<>();
        while (resultSet.next()) {
            Friend friend = new Friend();
            friend.setUsername(resultSet.getString(1));
            friend.setName(resultSet.getString(2));
            friend.setIdAccount(resultSet.getInt(3));
            friend.setIdFriendsAccount(resultSet.getInt(4));
            friend.setId(resultSet.getInt(5));
            friends.add(friend);
        }
        return friends;
    }

    private Friend fillFriend(ResultSet resultSet) throws SQLException {
        Friend friend = new Friend();
        friend.setId(resultSet.getInt(1));
        friend.setIdAccount(resultSet.getInt(2));
        friend.setIdFriendsAccount(resultSet.getInt(3));
        return friend;
    }

}