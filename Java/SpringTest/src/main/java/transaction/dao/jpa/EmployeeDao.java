package transaction.dao.jpa;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import org.springframework.stereotype.Repository;

import transaction.entity.Department;
import transaction.entity.Employee;

@Repository  //Use for Exception Translation to DataAccessException
public class EmployeeDao extends GenericDaoJpa<Employee> {

	public EmployeeDao() {
		super(Employee.class);
	}
	
	public Employee getEmployeeByName(String name){
		StringBuffer query = new StringBuffer("Select e from Employee e where e.username= :name");
		return (Employee) entityManager.createQuery(query.toString()).setParameter("name", name).getSingleResult();
	}
	
	public Employee updateEmployee(Employee emp, int sec, LockModeType lockMode){
		String threadName = Thread.currentThread().getName();

//		Employee _emp = getEmployeeByName(emp.getUsername()); 
		Employee _emp = get(emp.getId());
		System.out.println("["+threadName+"] : " +"LockMode : " + entityManager.getLockMode(_emp));
		System.out.println("["+threadName+"] : " + _emp.toString());
		
		if(lockMode!=null){
			entityManager.lock(_emp, lockMode);
		}
		
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {}
		
		_emp.setPassword(emp.getPassword());
		/*
		 * Caution: To throw OptimisticLockException, need to read the field first (_emp.getVacation())  
		 * */
		_emp.setVacation(_emp.getVacation() + emp.getVacation()); 
		
		return  entityManager.merge(_emp);
	}
	
	public Employee updateEmployeeAndDepartment(Employee emp, int sec, LockModeType lockMode){
		String threadName = Thread.currentThread().getName();

//		Employee _emp = getEmployeeByName(emp.getUsername()); 
		Employee _emp = get(emp.getId());

		System.out.println("["+threadName+"] : " + _emp.toString());
		
		Department dept = _emp.getDepartment();
		System.out.println("["+threadName+"] : " +"Locking dept : " + dept.getName());
		
		if(lockMode!=null){
			entityManager.lock(dept, lockMode);
			System.out.println("["+threadName+"] : " +"LockMode : " + entityManager.getLockMode(dept));
			if(lockMode.equals(LockModeType.OPTIMISTIC_FORCE_INCREMENT)){
				System.out.println("@@@@@@Flushing@@@@@@@@");
				entityManager.flush();
			}
		}
		
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {}
		
		_emp.setPassword(emp.getPassword());
		_emp.setVacation(_emp.getVacation() + emp.getVacation());
		dept.setBudget(dept.getBudget()+emp.getDepartment().getBudget());
		_emp.setDepartment(dept);		

		return entityManager.merge(_emp);
	}
	
	public void lockEmployee(Employee emp){
		entityManager.lock(emp,LockModeType.PESSIMISTIC_READ);
	}
	
	public long getAllEmployeesVacation(){
		String threadName = Thread.currentThread().getName();
		long total =0;
		int count = 1;
		List<Employee> list = this.getAll();
		
		for(Employee emp : list){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("["+ threadName +"] - "+entityManager.getLockMode(emp));
			entityManager.lock(emp, LockModeType.PESSIMISTIC_READ);
			total += emp.getVacation();

			System.out.println("["+threadName +"] - " +count+". : total = " + total);
			count++;
		}
		return total;
	}
}
