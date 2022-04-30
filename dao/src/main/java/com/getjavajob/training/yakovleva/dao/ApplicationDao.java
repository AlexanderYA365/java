import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicationDao {
    private final JNDIPool connectionPool;

    public ApplicationDao() {
       // connectionPool = JNDIPool.getInstance();
        connectionPool = JNDIPool.getInstance();
    }

    public boolean create(Application application) {
        String sql = "INSERT INTO application (applicationType, idApplicant,idRecipient, status)" +
                " VALUES (?, ?, ?, ?);";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, application.getApplicationType());
            query.setInt(2, application.getIdApplicant());
            query.setInt(3, application.getIdRecipient());
            query.setInt(4, application.getStatus());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...create - " + ex);
        }
        return true;
    }

    public Application read(int id) {
        Application application = new Application();
        String sql = "SELECT * FROM application WHERE id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, id);
            fillFieldQuery(application, query);
        } catch (Exception ex) {
            System.out.println("Connection failed...read(int id) - " + ex);
        }
        return application;
    }


    public Application read(Group group, int idRecipient) {
        Application application = new Application();
        String sql = "SELECT * FROM application WHERE idApplicant = ? AND idRecipient = ? AND applicationType = 0";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, group.getIdGroup());
            query.setInt(2, idRecipient);
            fillFieldQuery(application, query);
        } catch (Exception ex) {
            System.out.println("Connection failed...read(Group group, int idRecipient) - " + ex);
        }
        return application;
    }

    public Application read(Friend friend) {
        Application application = new Application();
        String sql = "SELECT * FROM application WHERE idApplicant = ? AND idRecipient = ? AND applicationType = 1";
        System.out.println(sql);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, friend.getIdAccount());
            query.setInt(2, friend.getIdFriendsAccount());
            fillFieldQuery(application, query);
        } catch (Exception ex) {
            System.out.println("Connection failed...read(Friend friend) - " + ex);
        }
        return application;
    }

    public List<Application> readApplications() {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM application";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    Application application = new Application();
                    application.setId(resultSet.getInt(1));
                    application.setApplicationType(resultSet.getInt(2));
                    application.setIdApplicant(resultSet.getInt(3));
                    application.setIdRecipient(resultSet.getInt(4));
                    application.setStatus(resultSet.getInt(5));
                    applications.add(application);
                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...readApplications - " + ex);
        }
        return applications;
    }

    public boolean update(Application application) {
        String sql = "UPDATE application SET id = ?, applicationType = ?, idAdministrator = ?, " +
                "idAdministrator = ?;";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            query.setInt(1, application.getId());
            query.setInt(2, application.getApplicationType());
            query.setInt(3, application.getIdApplicant());
            query.setInt(4, application.getIdRecipient());
            query.setInt(5, application.getStatus());
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (Exception ex) {
            System.out.println("Connection failed...update - " + ex);
        }
        return true;
    }

    public void delete(Application application) {
        String sql = "DELETE FROM application WHERE id = " + application.getId();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(sql)) {
            int rows = query.executeUpdate();
            System.out.println("Added " + rows + " rows");
        } catch (SQLException ex) {
            System.out.println("Connection failed...delete - " + ex);
        }
    }

    private void fillFieldQuery(Application application, PreparedStatement query) {
        try (ResultSet resultSet = query.executeQuery()) {
            while (resultSet.next()) {
                application.setId(resultSet.getInt(1));
                application.setApplicationType(resultSet.getInt(2));
                application.setIdApplicant(resultSet.getInt(3));
                application.setIdRecipient(resultSet.getInt(4));
                application.setStatus(resultSet.getInt(5));
            }
        } catch (SQLException ex) {
            System.out.println("fillFieldQuery exception - " + ex);
        }
    }

}