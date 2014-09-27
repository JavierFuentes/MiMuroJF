package common.model.dao.hibernate.spring.impl;

import common.model.dao.hibernate.GenericHibernateDAO;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


public abstract class GenericHibernateSpringDAOImpl<T, ID extends Serializable> extends HibernateDaoSupport implements GenericHibernateDAO<T, ID> {
    
    private Class<T> persistentClass;
   
    public GenericHibernateSpringDAOImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }

  
    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) throws DataAccessException {
        T entity;

        // Comprobamos que el ID viene informado.
        if (id == null) {
            throw new InvalidDataAccessApiUsageException("El ID del objeto a recuperar debe ser distinto de NULL");
        }

        if (lock) {
            entity = (T) super.getHibernateTemplate().load(this.getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) this.getHibernateTemplate().load(this.getPersistentClass(), id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public T getById(ID id, boolean lock) throws DataAccessException {
        T entity;

        // Comprobamos que el ID viene informado.
        if (id == null) {
            throw new InvalidDataAccessApiUsageException("El ID del objeto a recuperar debe ser distinto de NULL");
        }

        if (lock) {
            entity = (T) super.getHibernateTemplate().get(this.getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) this.getHibernateTemplate().get(this.getPersistentClass(), id);
        }

        return entity;
    }    
    
   
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DataAccessException {
        // Delegamos en la API Criteria de Hibernate.
        return this.getHibernateTemplate().find("from " + this.getPersistentClass().getName());
    }
    
	public List<T> findAllPaginated(Integer firstResult, Integer maxResults, String orderBy) {
        Criterion dummyCriteria = Restrictions.sqlRestriction("1 = 1");
    	Order order = Order.asc(orderBy);
        return this.findByCriteriaNoCache(firstResult, maxResults, order, dummyCriteria);
	}
	
	public List<T> findAllPaginated(Integer firstResult, Integer maxResults, String orderBy, Boolean ascending) {
        Criterion dummyCriteria = Restrictions.sqlRestriction("1 = 1");
        Order order = null;
        if(ascending) {
            order = Order.asc(orderBy);
        } else {
            order = Order.desc(orderBy);
        }
        
        return this.findByCriteriaNoCache(firstResult, maxResults, order, dummyCriteria);
    }
   
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance) throws DataAccessException {
        return this.getHibernateTemplate().findByExample(exampleInstance);
    }

    public T makePersistent(T entity) throws DataAccessException {
        this.getHibernateTemplate().saveOrUpdate(entity);

        return entity;
    }

    
    public void makeTransient(T entity) throws DataAccessException {
        this.getHibernateTemplate().delete(entity);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteriaNoCache(
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
                    
                    criteria.setLockMode(LockMode.UPGRADE);
                    
                    return criteria.list();
                }
            });
    }
    
    @SuppressWarnings("unchecked")
    protected T findUniqueResultByCriteriaNoCache(
        final Criterion... criterion) {
        final Class theClass = this.getPersistentClass();

        return (T) this.getHibernateTemplate().execute(
            new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                    Criteria criteria = session.createCriteria(theClass);
                    criteria.setCacheable(false);


                    for (Criterion c : criterion) {
                        criteria.add(c);
                    }
                    
                    return criteria.uniqueResult();
                }
            });
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByQueryNoCache(final Integer firstResult, final Integer maxResults, final String queryStr) {
        return (List<T>) this.getHibernateTemplate().executeFind(
            new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                
                    Query query = session.createQuery(queryStr);
                    
                    query.setCacheable(false);

                    if (firstResult != null) {
                        query.setFirstResult(firstResult);
                    }

                    if (maxResults != null) {
                        query.setMaxResults(maxResults);
                    }

                    return query.list();
                }
            });
    }

    protected List<T> findByCriteriaNoCache(final Criterion... criterion) {
        return this.findByCriteriaNoCache(null, null, null, criterion);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(final Criterion... criterion)
        throws DataAccessException {
        DetachedCriteria crit = DetachedCriteria.forClass(this.getPersistentClass());

        for (Criterion c : criterion) {
            crit.add(c);
        }

        return this.getHibernateTemplate().findByCriteria(crit);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Integer firstResult, Integer maxResults, Order order, Criterion... criterion)
        throws DataAccessException {
        DetachedCriteria crit = DetachedCriteria.forClass(this.getPersistentClass());

        //Add order
        if (order != null) {
            crit.addOrder(order);
        }

        //Add criterions
        for (Criterion c : criterion) {
            crit.add(c);
        }

        HibernateTemplate ht = this.getHibernateTemplate();

        if (firstResult != null) {
            return ht.findByCriteria(crit, firstResult, maxResults);
        }
        else if(maxResults != null) {
        	return ht.findByCriteria(crit, 0, maxResults);
        } 
        else {
            return ht.findByCriteria(crit);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(int maxResults, List<Order> orders, Criterion... criterion)
        throws DataAccessException {
    	DetachedCriteria crit = DetachedCriteria.forClass(this.getPersistentClass());
        
    	if (orders != null) {
    		
	    	for(Order order : orders) {
	    		crit.addOrder(order);
	    	}
    	}
    	
        //Add criterions
        for (Criterion c : criterion) {
            crit.add(c);
        }    	
    	
    	HibernateTemplate ht = this.getHibernateTemplate();
    	
    	return ht.findByCriteria(crit, 0, maxResults);
    }
    
    protected List<T> findByCriteria(int maxResults, Criterion... criterion)
        throws DataAccessException {
        return this.findByCriteria(null, maxResults, null, criterion);
    }

    protected List<T> findByCriteria(Integer maxResults, Order order, Criterion... criterion) {
        return this.findByCriteria(null, maxResults, order, criterion);
    }

    protected void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    protected void executeUpdate(String update, Object[] params) {
        this.getHibernateTemplate().bulkUpdate(update, params);
    }

    protected void executeUpdate(String update, Object param) {
        this.getHibernateTemplate().bulkUpdate(update, param);
    }
}
