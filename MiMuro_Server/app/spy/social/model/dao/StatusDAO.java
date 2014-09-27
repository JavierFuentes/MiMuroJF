package spy.social.model.dao;

import common.model.dao.hibernate.GenericHibernateDAO;
import spy.social.model.vo.UserStatus;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
public interface StatusDAO extends GenericHibernateDAO<UserStatus, Long> {

    public List<UserStatus> getAllStatus();

    public List<UserStatus> getAllUserStatus(User user);

}
