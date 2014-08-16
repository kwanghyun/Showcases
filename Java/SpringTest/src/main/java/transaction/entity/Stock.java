package transaction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Check;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Indexed
@Entity
public class Stock  implements DomainObject{
	@Id
	@GeneratedValue
	@DocumentId
	private long id;
	
	@Field(index=Index.UN_TOKENIZED, store=Store.YES)
	private int count;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
