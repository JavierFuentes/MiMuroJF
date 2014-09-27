package common.model.dao.hibernate;


import common.model.dao.GenericDAO;

import java.io.Serializable;


/**
 * GenericDAO extension for Hibernate based implementations
 *
 */
public interface GenericHibernateDAO<T, ID extends Serializable> extends GenericDAO<T, ID> {
	//
}
