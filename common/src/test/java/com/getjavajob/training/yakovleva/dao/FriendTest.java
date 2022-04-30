import com.getjavajob.training.yakovleva.dao.Friend;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class FriendTest {

    @Test
    void getIdAccount() {
        Friend friend = new Friend();
        friend.setIdAccount(1);
        Assert.assertEquals(1, friend.getIdAccount());
    }

    @Test
    void getIdFriendsAccount() {
        Friend friend = new Friend();
        friend.setIdFriendsAccount(1);
        Assert.assertEquals(1, friend.getIdFriendsAccount());
    }

    @Test
    void getIdFriends() {
        Friend friend = new Friend();
        friend.setId(1);
        Assert.assertEquals(1, friend.getId());
    }

    @Test
    void setIdFriends() {
        Friend friend = new Friend();
        friend.setId(1);
        Assert.assertEquals(1, friend.getId());
    }

    @Test
    void setIdAccount() {
        Friend friend = new Friend();
        friend.setIdAccount(1);
        Assert.assertEquals(1, friend.getIdAccount());
    }

    @Test
    void setIdFriendsAccount() {
        Friend friend = new Friend();
        friend.setIdFriendsAccount(1);
        Assert.assertEquals(1, friend.getIdFriendsAccount());
    }
}