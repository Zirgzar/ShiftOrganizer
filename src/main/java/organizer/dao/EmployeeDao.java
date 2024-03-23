package organizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import organizer.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
