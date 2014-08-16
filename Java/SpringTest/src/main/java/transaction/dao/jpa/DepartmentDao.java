package transaction.dao.jpa;

import org.springframework.stereotype.Repository;

import transaction.entity.Department;

@Repository
public class DepartmentDao extends GenericDaoJpa<Department> {
	public DepartmentDao(){
		super(Department.class);
	}
	
	public Department findDepartmentByName(String name){
		StringBuffer query = new StringBuffer("Select d from Department d where d.name= :name");
		return (Department) entityManager.createQuery(query.toString()).setParameter("name", name).getSingleResult();
	}
	
	public void updateDepartmentWithDelay(Department dept, int sec){
		String threadName = Thread.currentThread().getName();
//		Department _dept = findDepartmentByName(dept.getName());
		Department _dept = get(dept.getId());
		_dept.setBudget(_dept.getBudget()+dept.getBudget());
		_dept.setName(dept.getName());
		System.out.println("["+threadName+"] : " +"LockMode : " + entityManager.getLockMode(_dept));
		System.out.println("["+threadName+"] : " + _dept.toString());
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {}
		
		entityManager.merge(_dept);
	}
}
