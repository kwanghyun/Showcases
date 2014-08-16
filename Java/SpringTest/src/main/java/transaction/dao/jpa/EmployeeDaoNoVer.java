package transaction.dao.jpa;

import javax.persistence.LockModeType;

import org.springframework.stereotype.Repository;

import transaction.entity.EmployeeNoVer;
@Repository
public class EmployeeDaoNoVer extends GenericDaoJpa<EmployeeNoVer> {

	public EmployeeDaoNoVer() {
		super(EmployeeNoVer.class);
	}
	
	public EmployeeNoVer getEmployeeByName(String name){
		StringBuffer query = new StringBuffer("Select e from EmployeeNoVer e where e.username= :name");
		return (EmployeeNoVer) entityManager.createQuery(query.toString()).setParameter("name", name).getSingleResult();
	}
	
	public EmployeeNoVer getEmployeeByNameWithLock(String name){
		StringBuffer query = new StringBuffer("Select e from EmployeeNoVer e where e.username= :name");
		EmployeeNoVer emp = (EmployeeNoVer) entityManager.createQuery(query.toString()).setParameter("name", name).getSingleResult();
		entityManager.lock(emp, LockModeType.OPTIMISTIC);
		return emp;
	}
}
