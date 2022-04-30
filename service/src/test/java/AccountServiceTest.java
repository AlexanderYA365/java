import com.getjavajob.training.yakovleva.dao.Account;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.FriendService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountServiceTest {

    @Test
    void create() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.create(account)).thenReturn(true);
        Assert.assertEquals(true, accountService.create(account));
    }

    @Test
    void update() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.update(account)).thenReturn(true);
        Assert.assertEquals(true, accountService.update(account));
    }

    @Test
    void delete() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.delete(account)).thenReturn(true);
        Assert.assertEquals(true, accountService.delete(account));
    }

    @Test
    void addFriend() {
        Account account = Mockito.mock(Account.class);
        FriendService accountService = Mockito.mock(FriendService.class);
        Mockito.when(accountService.addFriend(account, account)).thenReturn(true);
        Assert.assertEquals(true, accountService.addFriend(account, account));
    }

    @Test
    void deleteFriend() {
        Account account = Mockito.mock(Account.class);
        FriendService accountService = Mockito.mock(FriendService.class);
        Mockito.when(accountService.deleteFriend(account, account)).thenReturn(true);
        Assert.assertEquals(true, accountService.deleteFriend(account, account));
    }

    @Test
    void accountFriends() {
        Account account = Mockito.mock(Account.class);
        FriendService accountService = Mockito.mock(FriendService.class);
        Mockito.when(accountService.accountFriends(account)).thenReturn(null);
        Assert.assertEquals(null, accountService.accountFriends(account));
    }
}