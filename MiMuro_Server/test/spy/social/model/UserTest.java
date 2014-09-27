package spy.social.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
@Transactional
public class UserTest
        extends AbstractTransactionalJUnit4SpringContextTests {

    protected final String USERID1 = "TestUser1";
    protected final String USERNICK1 = "Test1";

    protected final String USERID2 = "TestUser2";
    protected final String USERNICK2 = "Test2";


    @Autowired
    @Qualifier(value = "target")
    protected UserDAO userDAO;

    protected User user1, user2;

    @Before
    public void init() {
        // Insert new Users
        user1 = new User();
        user1.setId(USERID1);
        user1.setPsw(USERNICK1);
        user1.setNickname(USERNICK1);
        userDAO.makePersistent(user1);

        user2 = new User();
        user2.setId(USERID2);
        user2.setPsw(USERNICK2);
        user2.setNickname(USERNICK2);
        user2.setActive(false);
        userDAO.makePersistent(user2);
    }

    @Test
    public void getUser() {
        // Get the User from DB
        User userCopy1 = userDAO.getById(USERID1, false);
        User userCopy2 = userDAO.getById(USERID2, false);

        // Verify
        Assert.assertEquals(user1.getNickname(), userCopy1.getNickname());
        Assert.assertEquals(true, userCopy1.getActive());
        Assert.assertEquals(user2.getNickname(), userCopy2.getNickname());
        Assert.assertEquals(false, userCopy2.getActive());
    }

    @Test
    public void updateUser() {
        // Modify the User and save
        user1.setActive(false);
        user1.setNickname("Test1MOD");
        userDAO.makePersistent(user1);

        // Get the User from DB
        User userCopy1 = userDAO.getById(USERID1, false);

        // Verify
        Assert.assertEquals(false, userCopy1.getActive());
        Assert.assertEquals("Test1MOD", userCopy1.getNickname());
    }

    @Test
    public void deleteUser() {
        // Delete the User from DB
        userDAO.makeTransient(user1);

        // Get the User from DB
        User userCopy1 = userDAO.getById(USERID1, false);

        // Verify
        Assert.assertEquals(null, userCopy1);
    }

    @Test
    public void checkCredentials() {
        // Find the Users from DB
//      User userCopy1 = userDAO.findById(USERID1, false);
//      User userCopy2 = userDAO.findById(USERID2, false);
        User userCopy1 = userDAO.findByNickName(USERNICK1);
        User userCopy2 = userDAO.findByNickName(USERNICK2);

        // Verify
        Assert.assertEquals(USERNICK1, userCopy1.getPsw());
        Assert.assertEquals(USERNICK2, userCopy2.getPsw());
    }

    @Test
    public void getAllActiveUsers() {
        // Get All Active Users
        List<User> userList = userDAO.getAllUsers(true);

        // Verify
        Assert.assertTrue(userList.size() >= 1);
    }

}
