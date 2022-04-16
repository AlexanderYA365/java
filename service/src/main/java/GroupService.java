import java.util.List;

public class GroupService {
    private final GroupDao groupDao;
    private final Pool connectionPool;

    public GroupService(){
        groupDao = GroupDao.getInstance();
        connectionPool = ConnectionPool.getInstance();
    }

    public List<Group> readAccountGroups(Account account) {
        System.out.println("readGroup, account.getId() - " + account.getId());
        return groupDao.readGroupsAccount(account.getId());
    }

    public void createAccountGroups(Group group) {
        System.out.println("createAccountGroups");
        try {
            groupDao.create(group);
            connectionPool.commit();
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
    }

    public List<Group> readGroups() {
        System.out.println("readGroups");
        return groupDao.readGroups();
    }

    public List<Group> getGroupName(String groupName) {
        System.out.println("getGroupName groupName - " + groupName);
        return groupDao.read(groupName);
    }

    public Group readGroupID(int idGroup) {
        System.out.println("getGroupID idGroup - " + idGroup);
        return groupDao.read(idGroup);
    }

    public boolean insertAccountGroup(Group group, int idAccount) {
        System.out.println("Group read idGroup - " + group + " , idAccount - " + idAccount);
        boolean result = false;
        try {
            result = groupDao.insertAccount(group, idAccount);
            connectionPool.commit();
            return result;
        } catch (Exception e){
            connectionPool.rollback();
            e.printStackTrace();
        }
        return result;
    }

    public void closeService() {
        connectionPool.returnConnection();
    }

}