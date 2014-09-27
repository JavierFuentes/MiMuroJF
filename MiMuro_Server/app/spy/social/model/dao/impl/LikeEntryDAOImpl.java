package spy.social.model.dao.impl;

import common.model.dao.hibernate.spring.impl.GenericHibernateSpringDAOImpl;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import spy.social.model.dao.LikeEntryDAO;
import spy.social.model.vo.LikeEntry;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

/**
 * Created by javierfuentes on 24/08/14.
 */
public class LikeEntryDAOImpl
        extends GenericHibernateSpringDAOImpl<LikeEntry, Long>
        implements LikeEntryDAO {

    public LikeEntryDAOImpl() {
        super(LikeEntry.class);
    }

    @Override
    public Integer countAllStatusLikeEntries(UserStatus userStatus) {
        // FIXME : Esto podría ser mucho más óptimo si se dispusiese de métodos Count* en super
        List<LikeEntry> returnValue = getLikesListByStatus(userStatus);

        if (returnValue == null)
            return 0;

        return returnValue.size();
    }

    @Override
    public List<LikeEntry> getLikesListByStatus(UserStatus userStatus) {
        try {

            Criterion statusCriteria = null;

            if (userStatus != null)
                statusCriteria = Restrictions.eq("userStatus", userStatus);

            Order dateOrder = Order.desc("date");

            List<LikeEntry> returnValue = super.findByCriteria(null, dateOrder, statusCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("getLikesListByStatus - " + e.toString());
            return null;

        }
    }

    @Override
    public List<LikeEntry> getLikesListByUser(User user) {
        try {

            Criterion userCriteria = null;

            if (user != null)
                userCriteria = Restrictions.eq("user", user);

            Order dateOrder = Order.desc("date");

            List<LikeEntry> returnValue = super.findByCriteria(null, dateOrder, userCriteria);

            return returnValue;

        } catch (Exception e) {

            logger.error("getLikesListByUser - " + e.toString());
            return null;

        }
    }

    @Override
    public List<LikeEntry> getLikeEntryByUserAndStatus(User user, UserStatus userStatus) {

        try {

            Criterion criteria1 = null, criteria2 = null;

            if (user != null)
                criteria1 = Restrictions.eq("user", user);

            if (userStatus != null)
                criteria2 = Restrictions.eq("userStatus", userStatus);

            // La función findUniqueResultByCriteriaNoCache() no parece funcionar bien siempre
            //LikeEntry returnValue = super.findUniqueResultByCriteriaNoCache(criteria1, criteria2);
            List<LikeEntry> returnValue = super.findByCriteria(criteria1, criteria2);

            return returnValue;

        } catch (Exception e) {

            logger.error("getLikeEntryByUserAndStatus - " + e.toString());
            return null;

        }

    }

}
