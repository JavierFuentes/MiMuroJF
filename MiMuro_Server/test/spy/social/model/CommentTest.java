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
import spy.social.model.dao.CommentDAO;
import spy.social.model.dao.StatusDAO;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.Comment;
import spy.social.model.vo.UserStatus;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuenteserfuentes on 16/08/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context.xml")
@Transactional
public class CommentTest
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

    protected User user1, user2;
    protected UserStatus userStatus11, userStatus12, userStatus13, userStatus21;
    protected Comment comment111, comment112, comment113, comment121, comment122;

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

        // Insert new UserStatus Comments
        comment111 = new Comment();
        comment111.setUserStatus(userStatus11);
        comment111.setAuthor(user2);
        comment111.setDate(userStatus11.getDate());
        comment111.setText("Comment 1 for UserStatus 1");
        commentDAO.makePersistent(comment111);

        comment112 = new Comment();
        comment112.setUserStatus(userStatus11);
        comment112.setAuthor(user1);
        comment112.setDate(userStatus12.getDate());
        comment112.setText("Comment 2 for UserStatus 1");
        commentDAO.makePersistent(comment112);

        comment113 = new Comment();
        comment113.setUserStatus(userStatus11);
        comment113.setAuthor(user2);
        comment113.setDate(userStatus13.getDate());
        comment113.setText("Comment 3 for UserStatus 1");
        commentDAO.makePersistent(comment113);

        comment121 = new Comment();
        comment121.setUserStatus(userStatus12);
        comment121.setAuthor(user1);
        comment121.setDate(userStatus12.getDate());
        comment121.setText("Comment 1 for UserStatus 2");
        commentDAO.makePersistent(comment121);

        comment122 = new Comment();
        comment122.setUserStatus(userStatus12);
        comment122.setAuthor(user2);
        comment122.setDate(userStatus11.getDate());
        comment122.setText("Comment 2 for UserStatus 2");
        commentDAO.makePersistent(comment122);
    }

    @Test
    public void getComment() {
        // Get the Comment from DB
        Comment commentCopy111 = commentDAO.getById(comment111.getId(), false);
        Comment commentCopy112 = commentDAO.getById(comment112.getId(), false);
        Comment commentCopy113 = commentDAO.getById(comment113.getId(), false);
        Comment commentCopy121 = commentDAO.getById(comment121.getId(), false);
        Comment commentCopy122 = commentDAO.getById(comment122.getId(), false);

        // Verify
        Assert.assertEquals(comment111.getAuthor(), commentCopy111.getAuthor());
        Assert.assertEquals(comment112.getUserStatus(), commentCopy112.getUserStatus());
        Assert.assertEquals(comment113.getDate(), commentCopy113.getDate());
        Assert.assertEquals(comment121.getAuthorNick(), commentCopy121.getAuthorNick());
        Assert.assertEquals(comment122.getStatusId(), commentCopy122.getStatusId());
    }

    @Test
    public void updateComment() {
        // Modify the Comment and save
        comment111.setText("New comment text");
        comment111.setDate(new DateTime(2014, 1, 17, 23, 59).toDate());
        commentDAO.makePersistent(comment111);

        // Get the UserStatus from DB
        Comment commentCopy111 = commentDAO.getById(comment111.getId(), false);

        // Verify
        Assert.assertEquals(comment111.getText(), commentCopy111.getText());
        Assert.assertEquals(comment111.getDate(), commentCopy111.getDate());
    }

    @Test
    public void deleteComment() {
        // Delete the Comment from DB
        commentDAO.makeTransient(comment111);

        // Get the Comment from DB
        Comment commentCopy111 = commentDAO.getById(comment111.getId(), false);

        // Verify
        Assert.assertEquals(null, commentCopy111);
    }

    @Test
    public void getAllStatusComments() {
        // Get All Comments of UserStatus
        List<Comment> commentList11 = commentDAO.getCommentListByStatus(userStatus11);
        List<Comment> commentList12 = commentDAO.getCommentListByStatus(userStatus12);

        // Verify. Ordered Lists
        Assert.assertEquals(3, commentList11.size());
        Assert.assertEquals(2, commentList12.size());

        Assert.assertEquals("Comment 3 for UserStatus 1", commentList11.get(0).getText());
        Assert.assertEquals("Comment 1 for UserStatus 1", commentList11.get(1).getText());
        Assert.assertEquals("Comment 2 for UserStatus 1", commentList11.get(2).getText());

        Assert.assertEquals((new DateTime(2014, 1, 11, 12, 30).toDate()), commentList12.get(0).getDate());  // 'Sat Jan 11 12:31:00 CET 2014'
    }

}
