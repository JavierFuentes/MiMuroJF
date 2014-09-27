package spy.social.model.dao;

import common.model.dao.hibernate.GenericHibernateDAO;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
public interface UserDAO extends GenericHibernateDAO<User, String> {

    public Boolean checkCredentials(String id, String pass);

    public User findByNickName(String nick);

    public List<User> getAllUsers(boolean onlyActive);

}
