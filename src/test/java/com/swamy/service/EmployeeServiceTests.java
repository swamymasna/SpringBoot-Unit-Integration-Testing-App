package com.swamy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.swamy.entity.Employee;
import com.swamy.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTests {

	@Autowired
	private IEmployeeService employeeService;

	@MockBean
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().id(1).employeeName("swamy").employeeAddress("hyd").build();
		employeeRepository.save(employee);
	}

	@DisplayName("JUnit Test For Save Employee Operation")
	@Test
	public void givenEmployee_whenSave_thenReturnEmployee() {

		when(employeeRepository.save(employee)).thenReturn(employee);

		Employee savedEmployee = employeeService.saveEmployee(employee);

		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	@DisplayName("JUnit Test For Get All Employees Operation")
	@Test
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() {

		when(employeeRepository.findAll()).thenReturn(List.of(employee));

		List<Employee> employeesList = employeeService.getAllEmployees();

		assertThat(employeesList).isNotNull();
		assertThat(employeesList.size()).isGreaterThan(0);
	}

	@DisplayName("JUnit Test For Get Employee By Id Operation")
	@Test
	public void givenEmployee_whenGetOneEmployee_thenReturnEmployeeObject() {

		when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

		Employee employeeObj = employeeService.getOneEmployee(employee.getId());

		assertThat(employeeObj).isNotNull();
	}

	@DisplayName("JUnit Test For Update Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployee() {

		when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
		employee.setEmployeeName("simha");
		employee.setEmployeeAddress("nzb");
		when(employeeRepository.save(employee)).thenReturn(employee);

		Employee updatedEmployee = employeeService.updateEmployee(employee.getId(), employee);

		assertThat(updatedEmployee).isNotNull();
	}

	@DisplayName("JUnit Test For Delete Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturnDeletedMessage() {

		when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

		employeeService.deleteEmployee(employee.getId());
		
		verify(employeeRepository, times(1)).delete(employee);

	}
}
