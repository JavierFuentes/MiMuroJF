package spy.social.model;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spy.social.model.dao.CommentDAO;
import spy.social.model.dao.LikeEntryDAO;
import spy.social.model.dao.StatusDAO;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.Comment;
import spy.social.model.vo.LikeEntry;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

/**
 * Created by javierfuenteserfuentes on 16/08/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
@Transactional
public class LikeTest
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

    @Autowired
    @Qualifier(value = "target")
    protected CommentDAO commentDAO;

    @Autowired
    @Qualifier(value = "target")
    protected LikeEntryDAO likeEntryDAO;

    protected User user1, user2;
    protected UserStatus userStatus11, userStatus12;
    protected LikeEntry likeEntry111, likeEntry112, likeEntry121;

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

        // Insert new UserStatus LikeEntries
        likeEntry111 = new LikeEntry();
        likeEntry111.setUserStatus(userStatus11);
        likeEntry111.setUser(user2);
        likeEntry111.setDate(userStatus11.getDate());
        likeEntryDAO.makePersistent(likeEntry111);

        likeEntry112 = new LikeEntry();
        likeEntry112.setUserStatus(userStatus11);
        likeEntry112.setUser(user1);
        likeEntry112.setDate(userStatus12.getDate());
        likeEntryDAO.makePersistent(likeEntry112);

        likeEntry121 = new LikeEntry();
        likeEntry121.setUserStatus(userStatus12);
        likeEntry121.setUser(user1);
        likeEntry121.setDate(userStatus12.getDate());
        likeEntryDAO.makePersistent(likeEntry121);
    }

    @Test
    public void getLikeEntry() {
        // Get the Comment from DB
        LikeEntry likeEntryCopy111 = likeEntryDAO.getById(likeEntry111.getId(), false);
        LikeEntry likeEntryCopy112 = likeEntryDAO.getById(likeEntry112.getId(), false);
        LikeEntry likeEntryCopy121 = likeEntryDAO.getById(likeEntry121.getId(), false);

        // Verify
        Assert.assertEquals(likeEntry111.getUser(), likeEntryCopy111.getUser());
        Assert.assertEquals(likeEntry112.getUserStatus(), likeEntryCopy112.getUserStatus());
        Assert.assertEquals(likeEntry121.getDate(), likeEntryCopy121.getDate());
        Assert.assertEquals(likeEntry121.getUserNick(), likeEntryCopy121.getUserNick());
        Assert.assertEquals(likeEntry121.getStatusId(), likeEntryCopy121.getStatusId());
    }

    @Test
    public void countLikeEntries() {
        // Get All Likes of UserStatus
        int likeEntriesQty11 = likeEntryDAO.countAllStatusLikeEntries(userStatus11);
        int likeEntriesQty12 = likeEntryDAO.countAllStatusLikeEntries(userStatus12);

        // Verify
        Assert.assertEquals(2, likeEntriesQty11);
        Assert.assertEquals(1, likeEntriesQty12);
    }

}
