package transaction.dao.jpa;

import org.springframework.stereotype.Repository;

import transaction.entity.Stock;

@Repository
public class StockJpaDao extends GenericDaoJpa<Stock> {

	public StockJpaDao() {
		super(Stock.class);
	}

}
