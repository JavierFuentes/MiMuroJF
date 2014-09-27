package spy.social.model.dao.impl;

import common.model.dao.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import spy.social.model.dao.CommentDAO;
import spy.social.model.vo.Comment;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

/**
 * Created by javierfuentes on 24/08/14.
 */
public class CommentDAOImpl
        extends GenericHibernateSpringDAOImpl<Comment, Long>
        implements CommentDAO {

    public CommentDAOImpl() {
        super(Comment.class);
    }

    @Override
    public Integer countAllCommentStatus(UserStatus userStatus) {
        // FIXME : Esto podría ser mucho más óptimo si se dispusiese de métodos Count* en super
        List<Comment> returnValue = getCommentListByStatus(userStatus);

        if (returnValue == null)
            return 0;

        return returnValue.size();
    }

    @Override
    public List<Comment> getCommentListByStatus(UserStatus userStatus) {
        try {

            Criterion statusCriteria = null;

            if (userStatus != null)
                statusCriteria = Restrictions.eq("userStatus", userStatus);

            Order dateOrder = Order.desc("date");

            List<Comment> returnValue = super.findByCriteria(null, dateOrder, statusCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("getCommentListByStatus - " + e.toString());
            return null;

        }
    }

    @Override
    public List<Comment> getCommentListByUser(User author) {
        try {

            Criterion statusCriteria = null;

            if (author != null)
                statusCriteria = Restrictions.eq("author", author);

            Order dateOrder = Order.desc("date");

            List<Comment> returnValue = super.findByCriteria(null, dateOrder, statusCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("getCommentListByUser - " + e.toString());
            return null;

        }
    }
}
