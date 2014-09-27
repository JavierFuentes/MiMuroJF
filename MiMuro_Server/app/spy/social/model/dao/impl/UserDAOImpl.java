package spy.social.model.dao.impl;

import common.model.dao.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */
public class UserDAOImpl
        extends GenericHibernateSpringDAOImpl<User, String>
        implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public Boolean checkCredentials(String id, String pass) {
        try {
            User user = super.getById(id, false);

            if (user == null)
                return false;

            if (user.getActive() == false)
                return false;

            return user.getPsw().equals(pass);

        } catch (Exception e) {

            logger.error("checkCredentials - " + e.toString());
            return null;

        }
    }

    @Override
    public User findByNickName(String nick) {
        try {

            Criterion nickCriteria = Restrictions.eq("nickname", nick);

            User returnValue = super.findUniqueResultByCriteriaNoCache(nickCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("findByNickName - " + e.toString());
            return null;

        }
    }


    @Override
    public List<User> getAllUsers(boolean onlyActive) {
        try {

            Criterion activeCriteria;

            if (onlyActive) {
                activeCriteria = Restrictions.eq("active", true);
            } else {
                activeCriteria = Restrictions.like("nickname", "%");
            }

            List<User> returnValue = super.findByCriteria(activeCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("getAllUsers - " + e.toString());
            return null;

        }
    }
}
