package spy.social.model.dao.impl;

import common.model.dao.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import spy.social.model.dao.StatusDAO;
import spy.social.model.vo.UserStatus;
import spy.social.model.vo.User;

import java.util.List;

/**
 * Created by javierfuentes on 24/08/14.
 */
public class StatusDAOImpl
        extends GenericHibernateSpringDAOImpl<UserStatus, Long>
        implements StatusDAO {

    public StatusDAOImpl() {
        super(UserStatus.class);
    }

    @Override
    public List<UserStatus> getAllStatus() {
        try {

            Order dateOrder = Order.desc("date");

            List<UserStatus> returnValue = super.findByCriteria(null, dateOrder);

            return returnValue;

        } catch (Exception e) {

            logger.error("getAllStatus - " + e.toString());
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public List<UserStatus> getAllUserStatus(User user) {
        try {

            Criterion userCriteria = null;

            if (user != null)
                userCriteria = Restrictions.eq("user", user);

            Order dateOrder = Order.desc("date");

            List<UserStatus> returnValue = super.findByCriteria(null, dateOrder, userCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("getAllUserStatus - " + e.toString());
            return null;

        }
    }
}
