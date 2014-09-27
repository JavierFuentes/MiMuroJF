package spy.social.model.dao;

import common.model.dao.hibernate.GenericHibernateDAO;
import spy.social.model.vo.LikeEntry;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
public interface LikeEntryDAO extends GenericHibernateDAO<LikeEntry, Long> {

    public Integer countAllStatusLikeEntries(UserStatus userStatus);

    public List<LikeEntry> getLikesListByStatus(UserStatus userStatus);

    public List<LikeEntry> getLikesListByUser(User user);

    public List<LikeEntry> getLikeEntryByUserAndStatus(User user, UserStatus userStatus);

}
