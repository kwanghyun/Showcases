package transaction.dao.jpa;

import org.springframework.stereotype.Repository;

import transaction.entity.Account;

@Repository
public class AccountJpaDao extends GenericDaoJpa<Account> {
	
	public AccountJpaDao() {
		super(Account.class);
	}
	
	public Account findByName(String name){
		StringBuffer query = new StringBuffer("Select a from Account a where a.name= :name");
		return (Account) entityManager.createQuery(query.toString()).setParameter("name", name).getSingleResult();
	}
	
}
