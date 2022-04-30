import com.getjavajob.training.yakovleva.dao.Group;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GroupTest {

    @Test
    void getIdAccount() {
        Group group = new Group();
        group.setIdAccount(1);
        Assert.assertEquals(1, group.getIdAccount());
    }

    @Test
    void getGroupName() {
        Group group = new Group();
        group.setGroupName("test");
        Assert.assertEquals("test", group.getGroupName());
    }

    @Test
    void getLogo() {
        Group group = new Group();
        group.setLogo("test");
        Assert.assertEquals("test", group.getLogo());
    }

    @Test
    void getIdAdministrator() {
        Group group = new Group();
        group.setIdAdministrator(1);
        Assert.assertEquals(1, group.getIdAdministrator());
    }

    @Test
    void getIdGroup() {
        Group group = new Group();
        group.setIdGroup(1);
        Assert.assertEquals(1, group.getIdGroup());
    }

    @Test
    void setIdGroup() {
        Group group = new Group();
        group.setIdGroup(1);
        Assert.assertEquals(1, group.getIdGroup());
    }

    @Test
    void setIdAccount() {
        Group group = new Group();
        group.setIdAccount(1);
        Assert.assertEquals(1, group.getIdAccount());
    }

    @Test
    void setGroupName() {
        Group group = new Group();
        group.setGroupName("test");
        Assert.assertEquals("test", group.getGroupName());
    }

    @Test
    void setLogo() {
        Group group = new Group();
        group.setLogo("test");
        Assert.assertEquals("test", group.getLogo());
    }

    @Test
    void setIdAdministrator() {
        Group group = new Group();
        group.setIdAdministrator(1);
        Assert.assertEquals(1, group.getIdAdministrator());
    }

    @Test
    void testToString() {
        Group group = new Group();
        group.setLogo("test");
        Assert.assertEquals("com.getjavajob.training.yakovleva.dao.Group{idGroup=0, idAccount=0, groupName='null', logo='test', idAdministrator=0}", group.toString());
    }
}