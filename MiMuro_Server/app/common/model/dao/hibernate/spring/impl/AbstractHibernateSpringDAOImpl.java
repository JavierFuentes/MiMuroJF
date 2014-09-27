package common.model.dao.hibernate.spring.impl;


import common.model.dao.hibernate.AbstractHibernateDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.List;


/**
 * DOCUMENT ME!
 *
  */
public class AbstractHibernateSpringDAOImpl<T> extends HibernateDaoSupport implements AbstractHibernateDAO<T> {
    private Class<T> persistentClass;

    /**
    * Creates a new ZipcodeDAOImpl object.
    */
    @SuppressWarnings("unchecked")
    public AbstractHibernateSpringDAOImpl(Class persistentClass) {
        this.persistentClass = persistentClass;
    }

    @SuppressWarnings("unchecked")
    public Class getPersistentClass() {
        return this.persistentClass;
    }

    @SuppressWarnings("unchecked")
    public void setPersistentClass(Class persistentClass) {
        this.persistentClass = persistentClass;
    }

    public T makePersistent(T entity) throws DataAccessException {
        this.getHibernateTemplate().saveOrUpdate(entity);

        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteriaNoCache(
        final Integer firstResult, final Integer maxResults, final Order order, final Criterion... criterion) {
        final Class theClass = this.getPersistentClass();

        return (List<T>) this.getHibernateTemplate().execute(
            new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                    Criteria criteria = session.createCriteria(theClass);
                    criteria.setCacheable(false);

                    if (firstResult != null) {
                        criteria.setFirstResult(firstResult);
                    }

                    if (maxResults != null) {
                        criteria.setMaxResults(maxResults);
                    }

                    if (order != null) {
                        criteria.addOrder(order);
                    }

                    for (Criterion c : criterion) {
                        criteria.add(c);
                    }

                    return criteria.list();
                }
            });
    }

    protected List<T> findByCriteriaNoCache(final Criterion... criterion) {
        return this.findByCriteriaNoCache(null, null, null, criterion);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return this.getHibernateTemplate().find("from " + this.getPersistentClass().getName());
    }
    

    @Override
    @SuppressWarnings("unchecked")    
	public List<T> findAllPaginated(Integer firstResult, Integer maxResults, String orderBy) {
        Criterion dummyCriteria = Restrictions.sqlRestriction("1 = 1");
    	Order order = Order.asc(orderBy);
        return this.findByCriteriaNoCache(firstResult, maxResults, order, dummyCriteria);
	}
    
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance) {
        return this.getHibernateTemplate().findByExample(exampleInstance);
    }

    @Override
    public void makeTransient(T entity) {
        this.getHibernateTemplate().delete(entity);
    }
    
    protected void executeUpdate(String update, Object[] params) {
        this.getHibernateTemplate().bulkUpdate(update, params);
    }

    protected void executeUpdate(String update, Object param) {
        this.getHibernateTemplate().bulkUpdate(update, param);
    }
}
