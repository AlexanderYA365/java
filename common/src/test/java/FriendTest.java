import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        friend.setIdFriends(1);
        Assert.assertEquals(1, friend.getIdFriends());
    }

    @Test
    void setIdFriends() {
        Friend friend = new Friend();
        friend.setIdFriends(1);
        Assert.assertEquals(1, friend.getIdFriends());
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