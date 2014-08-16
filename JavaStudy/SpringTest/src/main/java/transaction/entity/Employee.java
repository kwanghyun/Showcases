package transaction.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Employee implements DomainObject {
	@Id @GeneratedValue
	private long id;
	@Column(unique=true)
	private String username;
	private String password;
	private int vacation;
	
	@ManyToOne //Bidirectional Many to One
	@JoinColumn(name="DEPT_ID")
	private Department department;
	
	@Version
	private int version;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getVacation() {
		return vacation;
	}
	public void setVacation(int vacation) {
		this.vacation = vacation;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", vacation=" + vacation
				+ ", department=" + department.getName() + ", version=" + version + "]";
	}

	
}
