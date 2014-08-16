package transaction.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
public class Book implements DomainObject {

	/*
	 * ## Spring Persistence with Hibernate, Chapter_10-2-Free-Text Search ##
	 * 
	 * The document ID is used by Hibernate Search to bridge the Lucene and
	 * Hibernate worlds. In each index, Lucene stores a Hibernate entity's full
	 * class name as well as its identifier. Together, they form the basis for
	 * querying in Hibernate Search, and allow a list of documents returned by a
	 * Lucene query to be exchanged for a list of active Hibernate entities.
	 */
	@Id
	@GeneratedValue
	@DocumentId
	private long id;

	/*
	 * Unless a domain class property is annotated with the @Field annotation,
	 * it will not be indexed. The @Field annotation specifies that a particular
	 * domain class property be included in the Lucene index.
	 * 
	 * a field can be tokenized, which will extract the contents of a particular
	 * property into a stream of tokens, leveraging a particular analyzer to
	 * filter out superfluous words, perform stemming, insert synonyms, and
	 * possibly perform a range of other options, depending on the analyzer
	 * used. A field can also be stored, which means that the original content
	 * will be inserted into the Lucene index. Storing a field can increase the
	 * size of the index, so it is rarely a good idea for large blocks of text.
	 * However, fields containing data that may need to be displayed by the
	 * application—such as a title, file name, or a business identifier—should
	 * be marked as stored.
	 */
	/*
	 * The @Boost annotation can be used to boost the weighting of a
	 * particularfield within the Lucene index.
	 */
	@Field(index = Index.TOKENIZED, store = Store.YES)
	@Boost(2.0f)
	private String name;

	/*
	 * Without CascadeType.ALL, it throw Exception "object references an unsaved
	 * transient instance - save the transient instance before flushing"
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STOCK_ID")
	@IndexedEmbedded(targetElement=Stock.class)
	private Stock stock;

	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@Column(columnDefinition="int default 0")
	private int price;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String author;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String publishedCompany;

	/*
	 * This mapping parameterizes the @DateBridge so that the date value is
	 * converted to text that can be lexicographically sorted. The date is
	 * stored within the Lucene index has a resolution of a minute. For
	 * performance reasons, you generally want to use the largest resolution
	 * setting your application can support (for instance, prefer a resolution
	 * of Minute over Second, or better yet, Day rather than Minute or Hour).
	 */
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@DateBridge(resolution = Resolution.DAY)
	private Date publishedDate;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishedCompany() {
		return publishedCompany;
	}

	public void setPublishedCompany(String publishedCompany) {
		this.publishedCompany = publishedCompany;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Book [name=");
		builder.append(name);
		builder.append(", price=");
		builder.append(price);
		builder.append(", author=");
		builder.append(author);
		builder.append(", publishedDate=");
		builder.append(publishedDate);
		builder.append("]");
		return builder.toString();
	}

}
