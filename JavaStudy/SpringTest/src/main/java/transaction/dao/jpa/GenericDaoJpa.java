package transaction.dao.jpa;

import java.awt.print.Book;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.Version;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import transaction.entity.DomainObject;
import transaction.entity.Employee;
import transaction.entity.Stock;

public class GenericDaoJpa<T extends DomainObject> {

	private Class<T> type;
	
	private static final Logger logger = LoggerFactory.getLogger(GenericDaoJpa.class);
	

	@PersistenceContext
	protected EntityManager entityManager;
	
	public GenericDaoJpa(Class<T> type) {
		this.type = type;
	}

	private EntityManagerFactory entityManagerFactory;
	

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		if(type == Book.class || type== Stock.class){
			indexAllItems();
		}
	}

	public void setEntityManger(EntityManager em){
		this.entityManager = em;

	}

	public T get(Long id) {
		return entityManager.find(type, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return entityManager.createQuery("select obj from "+ type.getName() + " obj").getResultList();
	}
	
	public void save(T object) {
		entityManager.persist(object);
	}

	//merge() can't save new entities.
	public T merge(T object){
		object = entityManager.merge(object);
		return object;
	}
	
	public T update(T object){
		return null;
	}
	
	public void delete(T object) {
		entityManager.remove(object);
	}

	public void indexEntity(T object) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.index(object);
	}

	public void indexAllItems() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		@SuppressWarnings("unchecked")
		List<T> resultList = fullTextEntityManager.createQuery("from " + this.type.getCanonicalName()).getResultList();
        
        for (T entity : resultList) {
            fullTextEntityManager.index(entity);
        }
        entityManager.close();
	}
	
	public List<T> freeTextSeachEntities(String searchWord, String[] targetFields, 
			String orderBy, boolean reverseOrder, int startIndex, int maxResult){
		
		return null;
	}

}
