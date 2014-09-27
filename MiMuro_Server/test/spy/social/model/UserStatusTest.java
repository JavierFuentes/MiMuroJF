package spy.social.model;

import org.joda.time.DateTime;
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
import spy.social.model.dao.StatusDAO;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.UserStatus;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
@Transactional
public class UserStatusTest
        extends AbstractTransactionalJUnit4SpringContextTests {

    protected final String USERID1 = "TestUser1";
    protected final String USERNICK1 = "Test1";

    protected final String USERID2 = "TestUser2";
    protected final String USERNICK2 = "Test2";


    @Autowired
    @Qualifier(value = "target")
    protected UserDAO userDAO;

    @Autowired
    @Qualifier(value = "target")
    protected StatusDAO statusDAO;

    protected User user1, user2;
    protected UserStatus userStatus11, userStatus12, userStatus13, userStatus21;

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

        // Insert new User UserStatus
        userStatus11 = new UserStatus();
        userStatus11.setUser(user1);
        userStatus11.setDate(new DateTime(2014, 1, 11, 12, 30).toDate());
        userStatus11.setText("UserStatus 1 of Test User 1");
        statusDAO.makePersistent(userStatus11);

        userStatus12 = new UserStatus();
        userStatus12.setUser(user1);
        userStatus12.setDate(new DateTime(2014, 1, 10, 10, 0).toDate());
        userStatus12.setText("First UserStatus (2) of Test User 1");
        statusDAO.makePersistent(userStatus12);

        userStatus13 = new UserStatus();
        userStatus13.setUser(user1);
        userStatus13.setDate(new DateTime(2014, 1, 13, 12, 30).toDate());
        userStatus13.setText("Last UserStatus (3) of Test User 1");
        statusDAO.makePersistent(userStatus13);

        userStatus21 = new UserStatus();
        userStatus21.setUser(user2);
        userStatus21.setDate(new DateTime(2014, 12, 31, 12, 0).toDate());
        userStatus21.setText("UserStatus 1 of Test User 2");
        statusDAO.makePersistent(userStatus21);
    }

    @Test
    public void getStatus() {
        // Get the UserStatus from DB
        UserStatus userStatusCopy11 = statusDAO.getById(userStatus11.getId(), false);
        UserStatus userStatusCopy12 = statusDAO.getById(userStatus12.getId(), false);
        UserStatus userStatusCopy13 = statusDAO.getById(userStatus13.getId(), false);
        UserStatus userStatusCopy21 = statusDAO.getById(userStatus21.getId(), false);

        // Verify
        Assert.assertEquals(userStatus11.getUser(), userStatusCopy11.getUser());
        Assert.assertEquals(userStatus12.getDate(), userStatusCopy12.getDate());
        Assert.assertEquals(userStatus13.getText(), userStatusCopy13.getText());
        Assert.assertEquals(userStatus21.getUserNick(), userStatusCopy21.getUserNick());
    }

    @Test
    public void updateStatus() {
        // Modify the UserStatus and save
        userStatus11.setText("New userStatus text");
        userStatus11.setDate(new DateTime(2014, 1, 17, 23, 59).toDate());
        statusDAO.makePersistent(userStatus11);

        // Get the UserStatus from DB
        UserStatus userStatusCopy11 = statusDAO.getById(userStatus11.getId(), false);

        // Verify
        Assert.assertEquals(userStatus11.getText(), userStatusCopy11.getText());
        Assert.assertEquals(userStatus11.getDate(), userStatusCopy11.getDate());
    }

    @Test
    public void deleteStatus() {
        // Delete the UserStatus from DB
        statusDAO.makeTransient(userStatus11);

        // Get the UserStatus from DB
        UserStatus userStatusCopy11 = statusDAO.getById(userStatus11.getId(), false);

        // Verify
        Assert.assertEquals(null, userStatusCopy11);
    }

    @Test
    public void getAllUserStatus() {
        // Get All UserStatus of User
        List<UserStatus> userStatusList1 = statusDAO.getAllUserStatus(user1);
        List<UserStatus> userStatusList2 = statusDAO.getAllUserStatus(user2);

        // Verify. Ordered Lists
        Assert.assertEquals(3, userStatusList1.size());
        Assert.assertEquals(1, userStatusList2.size());

        Assert.assertEquals("Last UserStatus (3) of Test User 1", userStatusList1.get(0).getText());
        Assert.assertEquals("UserStatus 1 of Test User 1", userStatusList1.get(1).getText());
        Assert.assertEquals("First UserStatus (2) of Test User 1", userStatusList1.get(2).getText());

        Assert.assertEquals((new DateTime(2014, 12, 31, 12, 0).toDate()), userStatusList2.get(0).getDate());  // 'Wed Dec 31 12:00:00 CET 2014'
    }

}
