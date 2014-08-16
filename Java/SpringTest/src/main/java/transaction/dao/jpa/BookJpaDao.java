package transaction.dao.jpa;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.Expression;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;

import transaction.entity.Book;
import transaction.entity.Stock;

@Repository
public class BookJpaDao extends GenericDaoJpa<Book> {
	
	public BookJpaDao() {
		super(Book.class);
	}
	
    public void indexBookEntity(Book book) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.index(book);
    }
	
	@SuppressWarnings("unchecked")
	public List<Book> freeTextSeachEntities(String searchWord, String[] targetFields, 
			String orderBy, boolean reverseOrder, int startIndex, int maxResult){
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryParser parser = new MultiFieldQueryParser(org.apache.lucene.util.Version.LUCENE_29,targetFields, 
				new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_29));
		parser.setAllowLeadingWildcard(true);
		
		org.apache.lucene.search.Query luceneQuery = null;
		try{
			luceneQuery = parser.parse(searchWord);
			System.out.println("@@@luceneQuery : " +luceneQuery.toString());
		}catch(ParseException e){
			System.out.println("@@@ParseEcxetpion : " +e.getMessage());
		}
		FullTextQuery fullTextQuery= fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);

		if(orderBy!=null){
			Sort sort = new Sort(new SortField(orderBy,SortField.STRING_VAL,reverseOrder ));
			fullTextQuery.setSort(sort);
		}
		
		if(startIndex > 0 && maxResult > 0){
			fullTextQuery.setFirstResult(startIndex);
			fullTextQuery.setMaxResults(maxResult);
		}

		Query jpaQuery = fullTextQuery;
		
		List<Book> resultEntities = jpaQuery.getResultList();

		return resultEntities;
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> criteriaSearch(){
		List<Book> resultEntities = null;
		Session hSession = (Session)entityManager.getDelegate(); 
		Criteria criteria = hSession.createCriteria(Book.class);
		
		/*Equal */
//		criteria.add(Restrictions.eq("name", "Spring 3").ignoreCase());
		
		/*LIKE , Between - Integer*/
//		criteria.add(Restrictions.like("name", "%Hibernate%")).
//		add(Restrictions.between("price", new Integer(10), new Integer(40)));
		
		/*Logical Operation, LIKE , Between - Integer*/
//		criteria.add(
//				Restrictions.or(
//						Restrictions.like("name", "%Hibernate%"),
//						Restrictions.like("name", "%Spring%"))).
//						add(Restrictions.between("price", new Integer(10), new Integer(60)));
		
		/*(String) MatchMode*/
//		Criterion startRest = Restrictions.like("name", "Spring", MatchMode.START);
//		Criterion anywhereRest = Restrictions.like("name", "Spring", MatchMode.ANYWHERE);
//		criteria.add(anywhereRest);
		
		/*JOIN, ORDER */
//		criteria.createAlias("stock","st").
//		addOrder(Order.desc("name")).
//		addOrder(Order.asc("publishedDate")).
//		add(Restrictions.like("name","%Spring%")).
//		add(Restrictions.ge("st.count", new Integer(5)));
		
		/*QBE - Query By Example : Not working now*/
		Book book = new Book();
		book.setId(1769472);
		Example exampleBook = Example.create(book);
		criteria.add(exampleBook);
		
		
		System.out.println("[Criteria] = "+criteria.toString());
		resultEntities = criteria.list();
		return resultEntities;
	}
	
	public Object getAggregateFunctionResult(){
		Session hSession = (Session)entityManager.getDelegate(); 
		Criteria criteria = hSession.createCriteria(Book.class);
		Object returnObj = 0;
		
//		criteria.setProjection(Projections.avg("price"));
		criteria.setProjection(Projections.max("price"));
		List<Book> books = criteria.list();
//		criteria.setProjection((Projection)Property.forName("name")).
//		setProjection((Projection)Property.forName("publishedDate"));
		System.out.println("[Criteria] = "+criteria.toString());
		returnObj = books;
		return returnObj;
	}
		
}
