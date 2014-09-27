package common.model.dao;


import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <T> DOCUMENT ME!
 */
public interface AbstractDAO<T> {
    /**
     * Get all T type instances
     */
    List<T> findAll();

	List<T> findAllPaginated(Integer firstResult, Integer maxResults, String orderBy);
    
    /**
     * Get all T type objects filtering by example
     */
    List<T> findByExample(T exampleInstance);

    /**
     * Persists a T type object
     */
    T makePersistent(T entity);

    /**
     * Removes a T type object
     */
    void makeTransient(T entity);
}
