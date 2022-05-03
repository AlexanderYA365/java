package com.getjavajob.training.yakovleva.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface Pool {

    Connection getConnection() throws SQLException;

    void returnConnection();

    void rollback();

    void commit();

}
