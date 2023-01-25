package com.swamy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.swamy.entity.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().id(1).employeeName("swamy").employeeAddress("hyd").build();
	}

	@DisplayName("JUnit Test For Save Employee Operation")
	@Test
	public void givenEmployee_whenSave_thenReturnEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);

		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	@DisplayName("JUnit Test For Get All Employees Operation")
	@Test
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() {

		employeeRepository.save(employee);
		List<Employee> employeesList = employeeRepository.findAll();

		assertThat(employeesList).isNotNull();
		assertThat(employeesList.size()).isGreaterThan(0);
	}
	
	@DisplayName("JUnit Test For Get One Employee Operation")
	//@Test
	public void givenEmployee_whenFindById_thenReturnEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);
		Employee employeeObj = employeeRepository.findById(savedEmployee.getId()).get();

		assertThat(employeeObj).isNotNull();
	}
	
	@DisplayName("JUnit Test For Update Employee Operation")
	//@Test
	public void givenEmployeeId_whenUpdate_thenReturnUpdatedEmployee() {

		Employee savedEmployee = employeeRepository.save(employee);
		Employee employeeObj = employeeRepository.findById(savedEmployee.getId()).get();
		employeeObj.setEmployeeName("king");
		employeeObj.setEmployeeAddress("hyd");
		
		Employee updatedEmployee = employeeRepository.save(employeeObj);

		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getEmployeeName()).isEqualTo("king");
	}
	
	@DisplayName("JUnit Test For Delete Employee Operation")
	//@Test
	public void givenEmployeeId_whenDelete_thenReturnNothing() {

		Employee savedEmployee = employeeRepository.save(employee);
		employeeRepository.delete(savedEmployee);
		
		Employee empResp = null;
		Optional<Employee> empOpt = employeeRepository.findById(savedEmployee.getId());
		if(empOpt.isPresent()) {
			empResp = empOpt.get();
		}
		
		assertThat(empResp).isNull();
	}


}
