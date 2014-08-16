package transaction.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.lang.reflect.Field;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import transaction.util.DBException;


/**
 * All DAO's will extend from this DAO for common functionality.
 * 
 * @author mthimmar
 * 
 */
public class GenericDAO<T> {
    private Class<T> entityBeanType;

    /**
     * Creates and maintains Hibernate Sessions
     */
    private EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            this.entityBeanType = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    /**
     * Get entity type.
     * 
     * @return
     */
    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    /**
     * Setter for EntityManagerFactory.
     * 
     * @param entityManagerFactory
     * @throws DBException
     */
    public void setEntityManagerFactory(
            EntityManagerFactory entityManagerFactory) throws DBException{
        this.entityManagerFactory = entityManagerFactory;
/*        if (getEntityBeanType() == Notification.class
                || getEntityBeanType() == ItemCategory.class
                || getEntityBeanType() == SellerEntity.class
                || User.class.isAssignableFrom(getEntityBeanType())
                || Item.class.isAssignableFrom(getEntityBeanType())
                || ItemInstance.class.isAssignableFrom(getEntityBeanType())) {
            this.indexEntity();
        }*/
    }

    /**
     * Get Entity manager.
     * 
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Find entity by given id.
     * 
     * @param id
     *            Entity Id.
     * @return Entity for the given id.
     */
    public T findById(long id) throws DBException {
        EntityManager entityManager = this.getEntityManager();
        try {
            return entityManager.find(getEntityBeanType(), id);
        } catch (IllegalArgumentException e) {
            throw new DBException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    /**
     * Find all the entities.
     * 
     * @return List of all entities.
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DBException {
        EntityManager entityManager = this.getEntityManager();
        try {
            return entityManager.createQuery(
                    "from " + getEntityBeanType().getName()).getResultList();
        } catch (IllegalArgumentException e) {
            throw new DBException(e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    /**
     * Find the entities for the given set of keys and entity type
     * 
     * @param key
     *            Contains the keys to be searched for.
     * @param cls
     *            The entity type to be searched for.
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object findByKey(Object key, Class cls) {
        EntityManager entityManager = this.getEntityManager();
        try {
            return entityManager.find(cls, key);
        } finally {
            entityManager.close();
        }

    }

    /**
     * Add the entity to the persistence context and return the new created
     * managed entity.
     * 
     * @param entity
     *            To be persisted.
     * @return Entity for managing.
     */

    public T executePersist(T entity) throws DBException {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entity = entityManager.merge(entity);
            System.out.println("@LockMode : "+entityManager.getLockMode(entity));
            entityTransaction.commit();
            return entity;
        } catch (IllegalArgumentException e) {
            throw new DBException(e.getMessage());
        } catch (TransactionRequiredException e) {
            throw new DBException(e.getMessage());
        } finally {
//            entityManager.close();
        }

    }

    /**
     * Remove the entity from the persistence context.
     * 
     * @param entity
     *            Entity to be removed.
     */

    public void executeRemove(T entity) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Remove the entity from the persistence context.
     * 
     * @param entity
     *            Entity to be removed.
     * @return number of records deleted
     */
    @SuppressWarnings("unchecked")
    public int executeRemove(long id) {
        EntityManager entityManager = this.getEntityManager();
        try {
            EntityTransaction entityTransaction = entityManager
                    .getTransaction();
            entityTransaction.begin();
            // TODO: Need to change the deletion logic to only API calls.
            // mthimmar
            int records= entityManager
                    .createQuery(
                            "delete from " + this.getEntityBeanType().getName()
                                    + " where id = :id").setParameter("id", id)
                    .executeUpdate();
            entityTransaction.commit();
            return records;
        } finally {
            entityManager.close();
        }
    }
    
    public List<T> findAllByPositions(int pageNumber, int pageSize,
            String columnName, String sortOrder) throws NoSuchFieldException {
        EntityManager entityManager = this.getEntityManager();

        int start = pageSize * (pageNumber - 1);
        int end = pageSize;
        List<T> returnList = null;
        Field field = null;
        try {
            field = this.entityBeanType.getDeclaredField(columnName);
            StringBuffer queryString = new StringBuffer(" from ").append(
                    this.entityBeanType.getName()).append(" as gentity");
            if (columnName != null && !columnName.equalsIgnoreCase("")) {
                if (field.getType().getName()
                        .equalsIgnoreCase("java.lang.String")) {
                    queryString.append(" order by UPPER(gentity." + columnName
                            + ") " + sortOrder);
                } else {
                    queryString.append(" order by gentity." + columnName + " "
                            + sortOrder);
                }

            } else if (columnName == null || columnName.length() < 0) {
                queryString.append(" order by gentity.id ");

            }

            Query query = entityManager.createQuery(queryString.toString());
            query.setFirstResult(start);
            query.setMaxResults(end);
            returnList = query.getResultList();
            return returnList;
        } finally {
            entityManager.close();
        }
    }

    public int getTotalRecordsCount(final String query) throws DBException {
        EntityManager entityManager = this.getEntityManager();
        try {
            Long count = (Long) entityManager.createQuery(query)
                    .getSingleResult();
            return count.intValue();
        } catch (IllegalArgumentException e) {
            throw new DBException(e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public int getTotalRecordsCount(final Query query) throws Exception {
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }

    public List<T> findAllByQuery(int pageNumber, int pageSize,
            final String hqlQuery) throws DBException {
        EntityManager entityManager = this.getEntityManager();
        try {
            int start = pageSize * (pageNumber - 1);
            int end = pageSize;
            Query query = entityManager.createQuery(hqlQuery);
            query.setFirstResult(start);
            query.setMaxResults(end);
            return query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new DBException(e.getMessage());
        } finally {
            entityManager.close();
        }

    }

    public List<T> findAllByQuery(int pageNumber, int pageSize,
            final Query query) throws DBException {
        int start = pageSize * (pageNumber - 1);
        int end = pageSize;
        // Query query = entityManager.createQuery(hqlQuery);
        query.setFirstResult(start);
        query.setMaxResults(end);
        return query.getResultList();
    }

    /**
     * Indexes the existing data in DB for Hibernate search.
     * 
     * @throws DBException
     */
    @SuppressWarnings("unchecked")
    private void indexEntity() throws DBException {
        EntityManager entityManager = this.getEntityManager();
        try {
            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                    .getFullTextEntityManager(entityManager);
            List<T> entityList = entityManager.createQuery(
                    "from " + getEntityBeanType().getName()).getResultList();
            for (T entity : entityList) {
                fullTextEntityManager.index(entity);
            }
        } catch (IllegalArgumentException e) {
            throw new DBException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    /**
     * Search for the item with the given search string.
     * 
     * @param searchString
     *            search string
     * @param fieldsToSearch
     *            fields/columns to be searched for
     * @param orderBy
     *            field for ordering the result
     * @param reverseOrder
     *            flag to enable the ordering on the result, true to enable the
     *            reverse order.
     * @param isPaginated
     *            flag to enable pagination, true to enable.
     * @param pageNumber
     *            current page number
     * @param pageSize
     *            size of the page
     * @return list of items
     * @throws ParseException
     *             throws exception if unable to parse the given search string.
     */
    @SuppressWarnings("unchecked")
    public Collection<T> searchEntities(String searchString,
            String[] fieldsToSearch, String orderBy, boolean reverseOrder,
            boolean isPaginated, int pageNumber, int pageSize)
            throws ParseException {
        EntityManager entityManager = this.getEntityManager();
        try {
            FullTextEntityManager fullTextEntityManager = Search
                    .getFullTextEntityManager(entityManager);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(
                    fieldsToSearch, new StandardAnalyzer());
            parser.setAllowLeadingWildcard(true);
            org.apache.lucene.search.Query query = parser.parse(searchString);
            FullTextQuery fullTextQuery = fullTextEntityManager
                    .createFullTextQuery(query, getEntityBeanType());
            if (orderBy != null) {
                Sort sort = new Sort(new SortField(orderBy, reverseOrder));
                fullTextQuery.setSort(sort);
            }
            if (isPaginated) {
                fullTextQuery.setFirstResult(pageSize * (pageNumber - 1));
                fullTextQuery.setMaxResults(pageSize);
            }

            return fullTextQuery.getResultList();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Search for the item with the given search string.
     * 
     * @param searchString
     *            search string
     * @param fieldsToSearch
     *            fields/columns to be searched for
     * @param orderBy
     *            field for ordering the result
     * @param reverseOrder
     *            flag to enable the ordering on the result, true to enable the
     *            reverse order.
     * @param isPaginated
     *            flag to enable pagination, true to enable.
     * @param pageNumber
     *            current page number
     * @param pageSize
     *            size of the page
     * @return no of items
     * @throws ParseException
     *             throws exception if unable to parse the given search string.
     */

    @SuppressWarnings("unchecked")
    public int searchTotalRecords(String searchString, String[] fieldsToSearch,
            String orderBy, boolean reverseOrder, boolean isPaginated,
            int pageNumber, int pageSize) throws ParseException {
        EntityManager entityManager = this.getEntityManager();
        try {
            FullTextEntityManager fullTextEntityManager = Search
                    .getFullTextEntityManager(entityManager);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(
                    fieldsToSearch, new StandardAnalyzer());
            parser.setAllowLeadingWildcard(true);
            org.apache.lucene.search.Query query = parser.parse(searchString);
            FullTextQuery fullTextQuery = fullTextEntityManager
                    .createFullTextQuery(query, getEntityBeanType());

            return fullTextQuery.getResultSize();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Find all the entities based on query.
     * 
     * @return List of all entities.
     */
    @SuppressWarnings("unchecked")
    public List<T> findAllByQuery(final Query query) {
        return query.getResultList();
    }
}
