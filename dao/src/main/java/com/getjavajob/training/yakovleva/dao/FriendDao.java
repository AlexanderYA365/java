package com.getjavajob.training.yakovleva.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean create(Friend friend) {
        System.out.println("create - start");
        String sql = "INSERT INTO friends(account_id, idFriendsAccount) VALUES (?, ?)";
        int result = jdbcTemplate.update(sql, friend.getAccountId(), friend.getFriendId());
        return result > 0;
    }

    public List<Friend> getFriends(int id) {
        String sql = "SELECT * FROM friends WHERE account_id = ?";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{id}, (resultSet, i) -> fillFriend(resultSet));
    }

    public Friend get(int idFriends) {
        String sql = "SELECT * FROM friends WHERE idfriends = ?";
        System.out.println(sql);
        System.out.println("idFriends - " + idFriends);
        return jdbcTemplate.queryForObject(sql, new Object[]{idFriends},
                (resultSet, i) -> fillFriend(resultSet));
    }

    public List<Friend> getAllFriends() {
        String sql = "SELECT * FROM friends";
        System.out.println(sql);
        return jdbcTemplate.query(sql, (resultSet, i) -> fillFriend(resultSet));
    }

    //@Transactional
    public void update(Friend friend) {
        String sql = "UPDATE friends SET account_id = ?, idFriendsAccount = ? WHERE idFriends = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, friend.getAccountId(), friend.getFriendId(),
                friend.getId());
    }

    public boolean deleteFriendIdAccountIdFriendAccount(int idAccount, int idFriend) {
        String sql = "DELETE FROM friends WHERE account_id = ? AND idFriendsAccount = ? ";
        System.out.println(sql);
        return jdbcTemplate.update(sql, idAccount, idFriend) > 0;
    }

    public void delete(Friend friend) {
        String sql = "DELETE FROM friends WHERE idFriends = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, friend.getId());
    }

    public List<Friend> getFriendsName(int idAccount) {
        System.out.println("readFriendsName - start");
        String sql = "SELECT a.username, a.name,  " +
                "f.account_id, f.idFriendsAccount, f.idFriends FROM friends f JOIN account a " +
                "ON f.idFriendsAccount = a.account_id WHERE f.account_id = ?";
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
            friend.setAccountId(resultSet.getInt(3));
            friend.setFriendId(resultSet.getInt(4));
            friend.setId(resultSet.getInt(5));
            friends.add(friend);
        }
        return friends;
    }

    private Friend fillFriend(ResultSet resultSet) throws SQLException {
        Friend friend = new Friend();
        friend.setId(resultSet.getInt(1));
        friend.setAccountId(resultSet.getInt(2));
        friend.setFriendId(resultSet.getInt(3));
        return friend;
    }

}