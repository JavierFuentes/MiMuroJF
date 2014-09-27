package spy.social.model.dao;

import common.model.dao.hibernate.GenericHibernateDAO;
import spy.social.model.vo.Comment;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
public interface CommentDAO extends GenericHibernateDAO<Comment, Long> {

    public Integer countAllCommentStatus(UserStatus userStatus);

    public List<Comment> getCommentListByStatus(UserStatus userStatus);

    public List<Comment> getCommentListByUser(User user);

}
