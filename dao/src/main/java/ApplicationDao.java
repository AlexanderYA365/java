import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {
    private final Pool connectionPool;
    private Connection connection;

    public ApplicationDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    public boolean create(Application application) {
        String sql = "INSERT INTO application (applicationType, idApplicant,idRecipient, status)" +
                " VALUES (?, ?, ?, ?);";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, application.getApplicationType());
            query.setInt(2, application.getIdApplicant());
            query.setInt(3, application.getIdRecipient());
            query.setInt(4, application.getStatus());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...createFriend");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public Application read(int id) {
        Application application = new Application();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement query = connection.prepareStatement("SELECT * FROM application WHERE id = ?");
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                application.setId(resultSet.getInt(1));
                application.setApplicationType(resultSet.getInt(2));
                application.setIdApplicant(resultSet.getInt(3));
                application.setIdRecipient(resultSet.getInt(4));
                application.setStatus(resultSet.getInt(5));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroup");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return application;
    }

    public Application read(Group group, int idRecipient) {
        Application application = new Application();
        String sql = "SELECT * FROM application WHERE idApplicant = ? AND idRecipient = ? AND applicationType = 0";
        System.out.println(sql);
        try {
            connection = connectionPool.getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, group.getIdGroup());
            query.setInt(2, idRecipient);
            System.out.println(query.toString());
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                application.setId(resultSet.getInt(1));
                application.setApplicationType(resultSet.getInt(2));
                application.setIdApplicant(resultSet.getInt(3));
                application.setIdRecipient(resultSet.getInt(4));
                application.setStatus(resultSet.getInt(5));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...Application read(Group group, int idRecipient)");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        System.out.println("Application read(group, idRecipient) - " + application);
        return application;
    }

    public Application read(Friend friend) {
        Application application = new Application();
        String sql = "SELECT * FROM application WHERE idApplicant = ? AND idRecipient = ? AND applicationType = 1";
        System.out.println(sql);
        try {
            connection = connectionPool.getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            System.out.println(query.toString());
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                application.setId(resultSet.getInt(1));
                application.setApplicationType(resultSet.getInt(2));
                application.setIdApplicant(resultSet.getInt(3));
                application.setIdRecipient(resultSet.getInt(4));
                application.setStatus(resultSet.getInt(5));
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...Application read(Group group, int idRecipient)");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        System.out.println("Application read(group, idRecipient) - " + application);
        return application;
    }

    public List<Application> readApplications() {
        List<Application> applications = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM application");
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getInt(1));
                application.setApplicationType(resultSet.getInt(2));
                application.setIdApplicant(resultSet.getInt(3));
                application.setIdRecipient(resultSet.getInt(4));
                application.setStatus(resultSet.getInt(5));
                applications.add(application);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readGroups");
            System.out.println(ex);
        }
        connectionPool.returnConnection(connection);
        return applications;
    }

    public boolean update(Application application) {
        String sql = "UPDATE application SET id = ?, applicationType = ?, idAdministrator = ?, " +
                "idAdministrator = ?;";
        try (PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, application.getId());
            query.setInt(2, application.getApplicationType());
            query.setInt(3, application.getIdApplicant());
            query.setInt(4, application.getIdRecipient());
            query.setInt(5, application.getStatus());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...createFriend");
            System.out.println(ex);
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean delete(Application application) {
        try {
            connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM application WHERE id = " + application.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.commit();
                connectionPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
