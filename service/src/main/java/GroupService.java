import java.util.List;

public class GroupService {

    public List<Group> readAccountGroups(Account account) {
        System.out.println("readGroup, account.getId() - " + account.getId());
        GroupDao groupDao = new GroupDao();
        return groupDao.readGroupsAccount(account.getId());
    }

    public void createAccountGroups(Group group) {
        System.out.println("createAccountGroups");
        GroupDao groupDao = new GroupDao();
        try {
            groupDao.create(group);
        } catch (Exception e) {
            System.out.println("createAccountGroups Exception - " + e);
        }
    }

    public List<Group> readGroups() {
        System.out.println("readGroups");
        GroupDao groupDao = new GroupDao();
        return groupDao.readGroups();
    }

    public List<Group> getGroupName(String groupName) {
        System.out.println("getGroupName groupName - " + groupName);
        GroupDao groupDao = new GroupDao();
        return groupDao.read(groupName);
    }

    public Group readGroupID(int idGroup) {
        System.out.println("getGroupID idGroup - " + idGroup);
        GroupDao groupDao = new GroupDao();
        return groupDao.read(idGroup);
    }

    public boolean insertAccountGroup(Group group, int idAccount) {
        System.out.println("Group read idGroup - " + group + " , idAccount - " + idAccount);
        GroupDao groupDao = new GroupDao();
        return groupDao.insertAccount(group, idAccount);
    }

}